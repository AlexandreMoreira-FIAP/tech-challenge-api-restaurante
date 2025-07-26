package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.domain.entities.Item;
import br.com.posfiap.restmanager.dto.ItemCreateDto;
import br.com.posfiap.restmanager.dto.ItemResponseDto;
import br.com.posfiap.restmanager.dto.ItemUpdateDto;
import br.com.posfiap.restmanager.mapper.ItemMapper;
import br.com.posfiap.restmanager.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemController itemController;

    private Item item;
    private ItemCreateDto itemCreateDto;
    private ItemUpdateDto itemUpdateDto;
    private ItemResponseDto itemResponseDto;

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

        itemCreateDto = ItemCreateDto.builder()
                .nome("Pizza Margherita")
                .descricao("Pizza com tomate e mozzarella")
                .preco(BigDecimal.valueOf(35.90))
                .somenteConsumoLocal(false)
                .foto("https://example.com/pizza.jpg")
                .build();

        itemUpdateDto = ItemUpdateDto.builder()
                .nome("Pizza Pepperoni")
                .descricao("Pizza com pepperoni")
                .preco(BigDecimal.valueOf(42.90))
                .somenteConsumoLocal(true)
                .foto("https://example.com/pepperoni.jpg")
                .build();

        itemResponseDto = ItemResponseDto.builder()
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
        when(itemMapper.mapToItem(itemCreateDto)).thenReturn(item);
        when(itemService.incluir(any(Item.class))).thenReturn(item);
        when(itemMapper.mapToItemResponseDto(item)).thenReturn(itemResponseDto);

        ItemResponseDto resultado = itemController.incluir(itemCreateDto);

        assertNotNull(resultado);
        assertEquals(itemResponseDto, resultado);
        verify(itemMapper).mapToItem(itemCreateDto);
        verify(itemService).incluir(item);
        verify(itemMapper).mapToItemResponseDto(item);
    }

    @Test
    void deveBuscarItemPorIdComSucesso() {
        when(itemService.buscarPorId(1L)).thenReturn(item);
        when(itemMapper.mapToItemResponseDto(item)).thenReturn(itemResponseDto);

        ItemResponseDto resultado = itemController.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(itemResponseDto, resultado);
        verify(itemService).buscarPorId(1L);
        verify(itemMapper).mapToItemResponseDto(item);
    }

    @Test
    void deveAtualizarItemComSucesso() {
        when(itemMapper.mapToItem(itemUpdateDto)).thenReturn(item);
        when(itemService.atualizar(anyLong(), any(Item.class))).thenReturn(item);
        when(itemMapper.mapToItemResponseDto(item)).thenReturn(itemResponseDto);

        ItemResponseDto resultado = itemController.atualizar(1L, itemUpdateDto);

        assertNotNull(resultado);
        assertEquals(itemResponseDto, resultado);
        verify(itemMapper).mapToItem(itemUpdateDto);
        verify(itemService).atualizar(1L, item);
        verify(itemMapper).mapToItemResponseDto(item);
    }

    @Test
    void deveExcluirItemComSucesso() {
        doNothing().when(itemService).excluir(1L);

        itemController.excluir(1L);

        verify(itemService).excluir(1L);
    }
}