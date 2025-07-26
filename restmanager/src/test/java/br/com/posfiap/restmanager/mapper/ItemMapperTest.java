package br.com.posfiap.restmanager.mapper;

import br.com.posfiap.restmanager.domain.entities.Item;
import br.com.posfiap.restmanager.dto.ItemCreateDto;
import br.com.posfiap.restmanager.dto.ItemResponseDto;
import br.com.posfiap.restmanager.infrastructure.entities.ItemEntity;
import br.com.posfiap.restmanager.infrastructure.entities.RestauranteEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {

    private final ItemMapper mapper = Mappers.getMapper(ItemMapper.class);

    @Test
    void testToItem() {
        ItemCreateDto dto = ItemCreateDto.builder()
                .nome("Pizza Margherita")
                .descricao("Pizza tradicional italiana")
                .preco(new BigDecimal("25.90"))
                .somenteConsumoLocal(false)
                .foto("pizza.jpg")
                .restauranteId(1L)
                .build();

        Item result = mapper.mapToItem(dto);

        assertNotNull(result);
        assertEquals("Pizza Margherita", result.getNome());
        assertEquals("Pizza tradicional italiana", result.getDescricao());
        assertEquals(new BigDecimal("25.90"), result.getPreco());
        assertEquals(false, result.getSomenteConsumoLocal());
        assertEquals("pizza.jpg", result.getFoto());
        assertEquals(1L, result.getRestauranteId());
    }

    @Test
    void testToItemEntity() {
        Item item = Item.builder()
                .id(1L)
                .nome("Pizza Margherita")
                .descricao("Pizza tradicional italiana")
                .preco(new BigDecimal("25.90"))
                .somenteConsumoLocal(false)
                .foto("pizza.jpg")
                .restauranteId(1L)
                .build();

        ItemEntity result = mapper.mapToItemEntity(item);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pizza Margherita", result.getNome());
        assertEquals("Pizza tradicional italiana", result.getDescricao());
        assertEquals(new BigDecimal("25.90"), result.getPreco());
        assertEquals(false, result.getSomenteConsumoLocal());
        assertEquals("pizza.jpg", result.getFoto());
    }

    @Test
    void testToItemResponseDto() {
        RestauranteEntity restaurante = RestauranteEntity.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .build();

        ItemEntity entity = ItemEntity.builder()
                .id(1L)
                .nome("Pizza Margherita")
                .descricao("Pizza tradicional italiana")
                .preco(new BigDecimal("25.90"))
                .somenteConsumoLocal(false)
                .foto("pizza.jpg")
                .restaurante(restaurante)
                .build();

        ItemResponseDto result = mapper.mapToItemResponseDto(entity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pizza Margherita", result.getNome());
        assertEquals("Pizza tradicional italiana", result.getDescricao());
        assertEquals(new BigDecimal("25.90"), result.getPreco());
        assertEquals(false, result.getSomenteConsumoLocal());
        assertEquals("pizza.jpg", result.getFoto());
        assertEquals(1L, result.getRestauranteId());
        assertNotNull(result.getRestaurante());
        assertEquals("Restaurante Teste", result.getRestaurante().getNome());
    }
}