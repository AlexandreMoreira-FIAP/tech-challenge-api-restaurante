package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Restaurante;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.infrastructure.entities.RestauranteEntity;
import br.com.posfiap.restmanager.mapper.RestauranteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteRepositoryImplTest {

    @Mock
    private RestauranteJpaRepository restauranteJpaRepository;

    @Mock
    private RestauranteMapper restauranteMapper;

    @InjectMocks
    private RestauranteRepositoryImpl restauranteRepositoryImpl;

    private Restaurante restaurante;
    private RestauranteEntity restauranteEntity;

    @BeforeEach
    void setUp() {
        restaurante = Restaurante.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoDeCozinha("Italiana")
                .login("restaurante@test.com")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        restauranteEntity = RestauranteEntity.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoDeCozinha("Italiana")
                .login("restaurante@test.com")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();
    }

    @Test
    void deveSalvarRestaurante() {
        when(restauranteMapper.mapToRestauranteEntity(restaurante)).thenReturn(restauranteEntity);
        when(restauranteJpaRepository.save(restauranteEntity)).thenReturn(restauranteEntity);
        when(restauranteMapper.mapToRestaurante(restauranteEntity)).thenReturn(restaurante);

        Restaurante resultado = restauranteRepositoryImpl.salvar(restaurante);

        assertNotNull(resultado);
        assertEquals(restaurante, resultado);
        verify(restauranteMapper).mapToRestauranteEntity(restaurante);
        verify(restauranteJpaRepository).save(restauranteEntity);
        verify(restauranteMapper).mapToRestaurante(restauranteEntity);
    }

    @Test
    void deveBuscarRestaurantePorId() {
        when(restauranteJpaRepository.findById(1L)).thenReturn(Optional.of(restauranteEntity));
        when(restauranteMapper.mapToRestaurante(restauranteEntity)).thenReturn(restaurante);

        Optional<Restaurante> resultado = restauranteRepositoryImpl.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(restaurante, resultado.get());
        verify(restauranteJpaRepository).findById(1L);
        verify(restauranteMapper).mapToRestaurante(restauranteEntity);
    }

    @Test
    void deveRetornarVazioQuandoRestauranteNaoEncontrado() {
        when(restauranteJpaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Restaurante> resultado = restauranteRepositoryImpl.buscarPorId(1L);

        assertFalse(resultado.isPresent());
        verify(restauranteJpaRepository).findById(1L);
        verify(restauranteMapper, never()).mapToRestaurante(any(RestauranteEntity.class));
    }

    @Test
    void deveExcluirRestaurante() {
        doNothing().when(restauranteJpaRepository).deleteById(1L);

        restauranteRepositoryImpl.excluir(1L);

        verify(restauranteJpaRepository).deleteById(1L);
    }

    @Test
    void deveBuscarRestaurantePorLogin() {
        when(restauranteJpaRepository.findByLogin("restaurante@test.com")).thenReturn(Optional.of(restauranteEntity));
        when(restauranteMapper.mapToRestaurante(restauranteEntity)).thenReturn(restaurante);

        Optional<Restaurante> resultado = restauranteRepositoryImpl.buscarPorLogin("restaurante@test.com");

        assertTrue(resultado.isPresent());
        assertEquals(restaurante, resultado.get());
        verify(restauranteJpaRepository).findByLogin("restaurante@test.com");
        verify(restauranteMapper).mapToRestaurante(restauranteEntity);
    }

    @Test
    void deveRetornarVazioQuandoRestauranteNaoEncontradoPorLogin() {
        when(restauranteJpaRepository.findByLogin("restaurante@test.com")).thenReturn(Optional.empty());

        Optional<Restaurante> resultado = restauranteRepositoryImpl.buscarPorLogin("restaurante@test.com");

        assertFalse(resultado.isPresent());
        verify(restauranteJpaRepository).findByLogin("restaurante@test.com");
        verify(restauranteMapper, never()).mapToRestaurante(any(RestauranteEntity.class));
    }
}