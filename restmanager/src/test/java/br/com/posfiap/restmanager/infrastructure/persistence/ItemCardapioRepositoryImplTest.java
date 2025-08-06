package br.com.posfiap.restmanager.infrastructure.persistence;

import br.com.posfiap.restmanager.application.mapper.ItemCardapioMapper;
import br.com.posfiap.restmanager.domain.model.ItemCardapio;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.ItemCardapioEntity;
import br.com.posfiap.restmanager.infrastructure.persistence.jpa.ItemCardapioJpaRepository;
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

class ItemCardapioRepositoryImplTest {

    @Mock
    private ItemCardapioJpaRepository jpaRepository;

    @Mock
    private ItemCardapioMapper mapper;

    @InjectMocks
    private ItemCardapioRepositoryImpl itemCardapioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarItemCardapio() {
        // Arrange
        ItemCardapio itemCardapio = ItemCardapio.builder()
                .nome("Pizza Margherita")
                .build();
                
        ItemCardapioEntity entity = new ItemCardapioEntity();
        ItemCardapioEntity savedEntity = new ItemCardapioEntity();
        ItemCardapio savedItem = ItemCardapio.builder()
                .id(1L)
                .nome("Pizza Margherita")
                .build();

        when(mapper.toEntity(itemCardapio)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedItem);

        // Act
        ItemCardapio resultado = itemCardapioRepository.salvar(itemCardapio);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Pizza Margherita", resultado.getNome());
        verify(mapper).toEntity(itemCardapio);
        verify(jpaRepository).save(entity);
        verify(mapper).toDomain(savedEntity);
    }

    @Test
    void deveBuscarItemCardapioPorId() {
        // Arrange
        Long id = 1L;
        ItemCardapioEntity entity = new ItemCardapioEntity();
        ItemCardapio itemCardapio = ItemCardapio.builder()
                .id(id)
                .nome("Pizza Margherita")
                .build();

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(itemCardapio);

        // Act
        Optional<ItemCardapio> resultado = itemCardapioRepository.buscarPorId(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals("Pizza Margherita", resultado.get().getNome());
        verify(jpaRepository).findById(id);
        verify(mapper).toDomain(entity);
    }

    @Test
    void deveRetornarVazioQuandoItemCardapioNaoEncontrado() {
        // Arrange
        Long id = 1L;
        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<ItemCardapio> resultado = itemCardapioRepository.buscarPorId(id);

        // Assert
        assertFalse(resultado.isPresent());
        verify(jpaRepository).findById(id);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void deveBuscarItensPorRestauranteId() {
        // Arrange
        Long restauranteId = 1L;
        
        ItemCardapioEntity entity1 = new ItemCardapioEntity();
        ItemCardapioEntity entity2 = new ItemCardapioEntity();
        List<ItemCardapioEntity> entities = Arrays.asList(entity1, entity2);
        
        ItemCardapio item1 = ItemCardapio.builder().nome("Pizza").build();
        ItemCardapio item2 = ItemCardapio.builder().nome("Hambúrguer").build();

        when(jpaRepository.findByRestauranteId(restauranteId)).thenReturn(entities);
        when(mapper.toDomain(entity1)).thenReturn(item1);
        when(mapper.toDomain(entity2)).thenReturn(item2);

        // Act
        List<ItemCardapio> resultado = itemCardapioRepository.buscarPorRestauranteId(restauranteId);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Pizza", resultado.get(0).getNome());
        assertEquals("Hambúrguer", resultado.get(1).getNome());
        verify(jpaRepository).findByRestauranteId(restauranteId);
        verify(mapper).toDomain(entity1);
        verify(mapper).toDomain(entity2);
    }

    @Test
    void deveRetornarListaVaziaQuandoRestauranteNaoTemItens() {
        // Arrange
        Long restauranteId = 1L;
        when(jpaRepository.findByRestauranteId(restauranteId)).thenReturn(Arrays.asList());

        // Act
        List<ItemCardapio> resultado = itemCardapioRepository.buscarPorRestauranteId(restauranteId);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(jpaRepository).findByRestauranteId(restauranteId);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void deveDeletarItemCardapioPorId() {
        // Arrange
        Long id = 1L;

        // Act
        itemCardapioRepository.deletarPorId(id);

        // Assert
        verify(jpaRepository).deleteById(id);
    }

    @Test
    void deveVerificarSeItemCardapioExiste() {
        // Arrange
        Long id = 1L;
        when(jpaRepository.existsById(id)).thenReturn(true);

        // Act
        boolean resultado = itemCardapioRepository.existePorId(id);

        // Assert
        assertTrue(resultado);
        verify(jpaRepository).existsById(id);
    }

    @Test
    void deveRetornarFalsoQuandoItemCardapioNaoExiste() {
        // Arrange
        Long id = 1L;
        when(jpaRepository.existsById(id)).thenReturn(false);

        // Act
        boolean resultado = itemCardapioRepository.existePorId(id);

        // Assert
        assertFalse(resultado);
        verify(jpaRepository).existsById(id);
    }
}