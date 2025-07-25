package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.entities.Item;
import br.com.posfiap.restmanager.error.NotFoundException;
import br.com.posfiap.restmanager.infrastructure.adapters.persistence.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ItemService itemService;

    private Item item;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .id(1L)
                .nome("Pizza Margherita")
                .descricao("Pizza com tomate e mozzarella")
                .preco(BigDecimal.valueOf(35.90))
                .somenteConsumoLocal(false)
                .foto("https://example.com/pizza.jpg")
                .build();
    }

    @Test
    void deveIncluirItemComSucesso() {
        when(itemRepository.salvar(any(Item.class))).thenReturn(item);

        Item resultado = itemService.incluir(item);

        assertNotNull(resultado);
        assertEquals(item, resultado);
        verify(itemRepository).salvar(item);
    }

    @Test
    void deveBuscarItemPorIdComSucesso() {
        when(itemRepository.buscarPorId(1L)).thenReturn(Optional.of(item));

        Item resultado = itemService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(item, resultado);
        verify(itemRepository).buscarPorId(1L);
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontrado() {
        when(itemRepository.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, 
            () -> itemService.buscarPorId(1L));

        verify(itemRepository).buscarPorId(1L);
    }

    @Test
    void deveExcluirItemComSucesso() {
        when(itemRepository.buscarPorId(1L)).thenReturn(Optional.of(item));

        itemService.excluir(1L);

        verify(itemRepository).buscarPorId(1L);
        verify(itemRepository).excluir(1L);
    }

    @Test
    void deveAtualizarItemComSucesso() {
        Item itemAtualizado = Item.builder()
                .nome("Pizza Pepperoni")
                .descricao("Pizza com pepperoni")
                .preco(BigDecimal.valueOf(42.90))
                .somenteConsumoLocal(true)
                .foto("https://example.com/pepperoni.jpg")
                .build();

        when(itemRepository.buscarPorId(1L)).thenReturn(Optional.of(item));
        when(itemRepository.salvar(any(Item.class))).thenReturn(itemAtualizado);

        Item resultado = itemService.atualizar(1L, itemAtualizado);

        assertNotNull(resultado);
        verify(itemRepository).buscarPorId(1L);
        verify(itemRepository).salvar(itemAtualizado);
        assertEquals(item.getId(), itemAtualizado.getId());
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontradoAoAtualizar() {
        Item itemAtualizado = Item.builder()
                .nome("Pizza Pepperoni")
                .build();

        when(itemRepository.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, 
            () -> itemService.atualizar(1L, itemAtualizado));

        verify(itemRepository).buscarPorId(1L);
        verify(itemRepository, never()).salvar(any());
    }
}