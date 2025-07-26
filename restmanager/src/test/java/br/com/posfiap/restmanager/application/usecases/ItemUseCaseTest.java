package br.com.posfiap.restmanager.application.usecases;

import br.com.posfiap.restmanager.domain.entities.Item;
import br.com.posfiap.restmanager.domain.ports.ItemRepositoryPort;
import br.com.posfiap.restmanager.error.NotFoundException;
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
class ItemUseCaseTest {

    @Mock
    private ItemRepositoryPort itemRepositoryPort;

    @InjectMocks
    private ItemUseCase itemUseCase;

    private Item item;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .id(1L)
                .nome("Pizza Margherita")
                .descricao("Pizza com molho de tomate, mozzarella e manjericão")
                .preco(BigDecimal.valueOf(35.90))
                .somenteConsumoLocal(false)
                .foto("https://example.com/pizza.jpg")
                .build();
    }

    @Test
    void deveIncluirItemComSucesso() {
        when(itemRepositoryPort.salvar(any(Item.class))).thenReturn(item);

        Item resultado = itemUseCase.incluir(item);

        assertNotNull(resultado);
        assertEquals(item.getId(), resultado.getId());
        verify(itemRepositoryPort).salvar(item);
    }

    @Test
    void deveBuscarItemPorIdComSucesso() {
        when(itemRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(item));

        Item resultado = itemUseCase.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(item.getId(), resultado.getId());
        verify(itemRepositoryPort).buscarPorId(1L);
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontrado() {
        when(itemRepositoryPort.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemUseCase.buscarPorId(1L));
        
        verify(itemRepositoryPort).buscarPorId(1L);
    }

    @Test
    void deveExcluirItemComSucesso() {
        when(itemRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(item));

        itemUseCase.excluir(1L);

        verify(itemRepositoryPort).buscarPorId(1L);
        verify(itemRepositoryPort).excluir(1L);
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
        
        when(itemRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(item));
        when(itemRepositoryPort.salvar(any(Item.class))).thenReturn(itemAtualizado);

        Item resultado = itemUseCase.atualizar(1L, itemAtualizado);

        assertNotNull(resultado);
        verify(itemRepositoryPort).buscarPorId(1L);
        verify(itemRepositoryPort).salvar(itemAtualizado);
        assertEquals(item.getId(), itemAtualizado.getId()); // Verifica se o ID foi preservado
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontradoAoAtualizar() {
        Item itemAtualizado = Item.builder()
                .nome("Pizza Pepperoni")
                .build();
        
        when(itemRepositoryPort.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemUseCase.atualizar(1L, itemAtualizado));
        
        verify(itemRepositoryPort).buscarPorId(1L);
        verify(itemRepositoryPort, never()).salvar(any());
    }
}