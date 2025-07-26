package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRepositoryAdapterTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemRepositoryAdapter itemRepositoryAdapter;

    private Item item;

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
    }

    @Test
    void deveSalvarItem() {
        when(itemRepository.salvar(item)).thenReturn(item);

        Item resultado = itemRepositoryAdapter.salvar(item);

        assertNotNull(resultado);
        assertEquals(item, resultado);
        verify(itemRepository).salvar(item);
    }

    @Test
    void deveBuscarItemPorId() {
        when(itemRepository.buscarPorId(1L)).thenReturn(Optional.of(item));

        Optional<Item> resultado = itemRepositoryAdapter.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(item, resultado.get());
        verify(itemRepository).buscarPorId(1L);
    }

    @Test
    void deveExcluirItem() {
        doNothing().when(itemRepository).excluir(1L);

        itemRepositoryAdapter.excluir(1L);

        verify(itemRepository).excluir(1L);
    }
}