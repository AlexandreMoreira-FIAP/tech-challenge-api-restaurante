package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Item;
import br.com.posfiap.restmanager.infrastructure.entities.ItemEntity;
import br.com.posfiap.restmanager.mapper.ItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRepositoryImplTest {

    @Mock
    private ItemJpaRepository itemJpaRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemRepositoryImpl itemRepositoryImpl;

    private Item item;
    private ItemEntity itemEntity;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .id(1L)
                .nome("Pizza Margherita")
                .descricao("Pizza com molho de tomate, mozzarella e manjericão")
                .preco(new BigDecimal("25.90"))
                .somenteConsumoLocal(false)
                .foto("/images/pizza-margherita.jpg")
                .build();

        itemEntity = ItemEntity.builder()
                .id(1L)
                .nome("Pizza Margherita")
                .descricao("Pizza com molho de tomate, mozzarella e manjericão")
                .preco(new BigDecimal("25.90"))
                .somenteConsumoLocal(false)
                .foto("/images/pizza-margherita.jpg")
                .build();
    }

    @Test
    void deveSalvarItem() {
        when(itemMapper.mapToItemEntity(item)).thenReturn(itemEntity);
        when(itemJpaRepository.save(itemEntity)).thenReturn(itemEntity);
        when(itemMapper.mapToItem(itemEntity)).thenReturn(item);

        Item resultado = itemRepositoryImpl.salvar(item);

        assertNotNull(resultado);
        assertEquals(item, resultado);
        verify(itemMapper).mapToItemEntity(item);
        verify(itemJpaRepository).save(itemEntity);
        verify(itemMapper).mapToItem(itemEntity);
    }

    @Test
    void deveBuscarItemPorId() {
        when(itemJpaRepository.findById(1L)).thenReturn(Optional.of(itemEntity));
        when(itemMapper.mapToItem(itemEntity)).thenReturn(item);

        Optional<Item> resultado = itemRepositoryImpl.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(item, resultado.get());
        verify(itemJpaRepository).findById(1L);
        verify(itemMapper).mapToItem(itemEntity);
    }

    @Test
    void deveRetornarVazioQuandoItemNaoEncontrado() {
        when(itemJpaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Item> resultado = itemRepositoryImpl.buscarPorId(1L);

        assertFalse(resultado.isPresent());
        verify(itemJpaRepository).findById(1L);
        verify(itemMapper, never()).mapToItem(any(ItemEntity.class));
    }

    @Test
    void deveExcluirItem() {
        doNothing().when(itemJpaRepository).deleteById(1L);

        itemRepositoryImpl.excluir(1L);

        verify(itemJpaRepository).deleteById(1L);
    }
}