package br.com.posfiap.restmanager.infrastructure.persistence;

import br.com.posfiap.restmanager.application.mapper.RestauranteMapper;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.RestauranteEntity;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.UsuarioEntity;
import br.com.posfiap.restmanager.infrastructure.persistence.jpa.RestauranteJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestauranteRepositoryImplTest {

    @Mock
    private RestauranteJpaRepository jpaRepository;

    @Mock
    private RestauranteMapper mapper;

    @InjectMocks
    private RestauranteRepositoryImpl restauranteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarRestaurante() {
        // Arrange
        Restaurante restaurante = Restaurante.builder()
                .nome("Restaurante Teste")
                .build();
                
        RestauranteEntity entity = new RestauranteEntity();
        RestauranteEntity savedEntity = new RestauranteEntity();
        Restaurante savedRestaurante = Restaurante.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .build();

        when(mapper.toEntity(restaurante)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedRestaurante);

        // Act
        Restaurante resultado = restauranteRepository.salvar(restaurante);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Restaurante Teste", resultado.getNome());
        verify(mapper).toEntity(restaurante);
        verify(jpaRepository).save(entity);
        verify(mapper).toDomain(savedEntity);
    }

    @Test
    void deveBuscarRestaurantePorId() {
        // Arrange
        Long id = 1L;
        RestauranteEntity entity = new RestauranteEntity();
        Restaurante restaurante = Restaurante.builder()
                .id(id)
                .nome("Restaurante Teste")
                .build();

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(restaurante);

        // Act
        Optional<Restaurante> resultado = restauranteRepository.buscarPorId(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals("Restaurante Teste", resultado.get().getNome());
        verify(jpaRepository).findById(id);
        verify(mapper).toDomain(entity);
    }

    @Test
    void deveRetornarVazioQuandoRestauranteNaoEncontrado() {
        // Arrange
        Long id = 1L;
        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Restaurante> resultado = restauranteRepository.buscarPorId(id);

        // Assert
        assertFalse(resultado.isPresent());
        verify(jpaRepository).findById(id);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void deveListarRestaurantesPorProprietario() {
        // Arrange
        Long idProprietario = 1L;
        
        UsuarioEntity proprietario = new UsuarioEntity();
        proprietario.setId(idProprietario);
        
        RestauranteEntity entity1 = new RestauranteEntity();
        entity1.setProprietario(proprietario);
        
        RestauranteEntity entity2 = new RestauranteEntity();
        entity2.setProprietario(proprietario);
        
        RestauranteEntity entity3 = new RestauranteEntity();
        UsuarioEntity outroProprietario = new UsuarioEntity();
        outroProprietario.setId(2L);
        entity3.setProprietario(outroProprietario);

        List<RestauranteEntity> entities = Arrays.asList(entity1, entity2, entity3);
        
        Restaurante restaurante1 = Restaurante.builder().nome("Restaurante 1").build();
        Restaurante restaurante2 = Restaurante.builder().nome("Restaurante 2").build();

        when(jpaRepository.findAll()).thenReturn(entities);
        when(mapper.toDomain(entity1)).thenReturn(restaurante1);
        when(mapper.toDomain(entity2)).thenReturn(restaurante2);

        // Act
        List<Restaurante> resultado = restauranteRepository.listarPorProprietario(idProprietario);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Restaurante 1", resultado.get(0).getNome());
        assertEquals("Restaurante 2", resultado.get(1).getNome());
        verify(jpaRepository).findAll();
        verify(mapper).toDomain(entity1);
        verify(mapper).toDomain(entity2);
        verify(mapper, never()).toDomain(entity3);
    }

    @Test
    void deveRetornarListaVaziaQuandoProprietarioNaoTemRestaurantes() {
        // Arrange
        Long idProprietario = 1L;
        
        RestauranteEntity entity1 = new RestauranteEntity();
        UsuarioEntity outroProprietario = new UsuarioEntity();
        outroProprietario.setId(2L);
        entity1.setProprietario(outroProprietario);

        List<RestauranteEntity> entities = Arrays.asList(entity1);

        when(jpaRepository.findAll()).thenReturn(entities);

        // Act
        List<Restaurante> resultado = restauranteRepository.listarPorProprietario(idProprietario);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(jpaRepository).findAll();
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void deveRetornarListaVaziaQuandoRestauranteNaoTemProprietario() {
        // Arrange
        Long idProprietario = 1L;
        
        RestauranteEntity entity1 = new RestauranteEntity();
        entity1.setProprietario(null);

        List<RestauranteEntity> entities = Arrays.asList(entity1);

        when(jpaRepository.findAll()).thenReturn(entities);

        // Act
        List<Restaurante> resultado = restauranteRepository.listarPorProprietario(idProprietario);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(jpaRepository).findAll();
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void deveDeletarRestaurantePorId() {
        // Arrange
        Long id = 1L;

        // Act
        restauranteRepository.deletarPorId(id);

        // Assert
        verify(jpaRepository).deleteById(id);
    }
}