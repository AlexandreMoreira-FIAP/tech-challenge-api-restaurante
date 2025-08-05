package br.com.posfiap.restmanager.application.mapper;

import br.com.posfiap.restmanager.application.dto.ItemCardapioCreateDto;
import br.com.posfiap.restmanager.application.dto.ItemCardapioUpdateDto;
import br.com.posfiap.restmanager.domain.model.ItemCardapio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItemCardapioMapperTest {

    private ItemCardapioMapper itemCardapioMapper;

    @BeforeEach
    void setUp() {
        itemCardapioMapper = Mappers.getMapper(ItemCardapioMapper.class);
    }

    @Test
    void deveMapearCreateDtoParaItemCardapio() {
        // Arrange
        ItemCardapioCreateDto dto = new ItemCardapioCreateDto();
        dto.setNome("Pizza Margherita");
        dto.setDescricao("Pizza com queijo mussarela e manjericão");
        dto.setPreco(new BigDecimal("45.90"));
        dto.setDisponivel(true);
        dto.setCaminhoFoto("/fotos/pizza-margherita.jpg");

        // Act
        ItemCardapio item = itemCardapioMapper.mapToItemCardapio(dto);

        // Assert
        assertNotNull(item);
        assertNull(item.getId());
        assertNull(item.getRestaurante());
        assertNull(item.getDataUltimaAlteracao());
        assertEquals(dto.getNome(), item.getNome());
        assertEquals(dto.getDescricao(), item.getDescricao());
        assertEquals(dto.getPreco(), item.getPreco());
        assertEquals(dto.getDisponivel(), item.getDisponivel());
        assertEquals(dto.getCaminhoFoto(), item.getCaminhoFoto());
    }

    @Test
    void deveMapearUpdateDtoParaItemCardapio() {
        // Arrange
        ItemCardapioUpdateDto dto = new ItemCardapioUpdateDto();
        dto.setNome("Pizza Margherita Premium");
        dto.setDescricao("Pizza com queijo mussarela especial e manjericão fresco");
        dto.setPreco(new BigDecimal("55.90"));
        dto.setDisponivel(false);
        dto.setCaminhoFoto("/fotos/pizza-margherita-premium.jpg");

        // Act
        ItemCardapio item = itemCardapioMapper.mapToItemCardapio(dto);

        // Assert
        assertNotNull(item);
        assertNull(item.getId());
        assertNull(item.getRestaurante());
        assertNull(item.getDataUltimaAlteracao());
        assertEquals(dto.getNome(), item.getNome());
        assertEquals(dto.getDescricao(), item.getDescricao());
        assertEquals(dto.getPreco(), item.getPreco());
        assertEquals(dto.getDisponivel(), item.getDisponivel());
        assertEquals(dto.getCaminhoFoto(), item.getCaminhoFoto());
    }

    @Test
    void deveManipularValoresNulos() {
        // Assert
        assertNull(itemCardapioMapper.mapToItemCardapio((ItemCardapioCreateDto) null));
        assertNull(itemCardapioMapper.mapToItemCardapio((ItemCardapioUpdateDto) null));
    }

    @Test
    void deveMapearCreateDtoSemCaminhoFoto() {
        // Arrange
        ItemCardapioCreateDto dto = new ItemCardapioCreateDto();
        dto.setNome("Lasanha Bolonhesa");
        dto.setDescricao("Lasanha com molho bolonhesa e queijo");
        dto.setPreco(new BigDecimal("38.90"));
        dto.setDisponivel(true);

        // Act
        ItemCardapio item = itemCardapioMapper.mapToItemCardapio(dto);

        // Assert
        assertNotNull(item);
        assertEquals("Lasanha Bolonhesa", item.getNome());
        assertEquals("Lasanha com molho bolonhesa e queijo", item.getDescricao());
        assertEquals(new BigDecimal("38.90"), item.getPreco());
        assertTrue(item.getDisponivel());
        assertNull(item.getCaminhoFoto());
    }

    @Test
    void deveMapearUpdateDtoSemCaminhoFoto() {
        // Arrange
        ItemCardapioUpdateDto dto = new ItemCardapioUpdateDto();
        dto.setNome("Hambúrguer Artesanal");
        dto.setDescricao("Hambúrguer com carne artesanal e pão especial");
        dto.setPreco(new BigDecimal("32.50"));
        dto.setDisponivel(true);

        // Act
        ItemCardapio item = itemCardapioMapper.mapToItemCardapio(dto);

        // Assert
        assertNotNull(item);
        assertEquals("Hambúrguer Artesanal", item.getNome());
        assertEquals("Hambúrguer com carne artesanal e pão especial", item.getDescricao());
        assertEquals(new BigDecimal("32.50"), item.getPreco());
        assertTrue(item.getDisponivel());
        assertNull(item.getCaminhoFoto());
    }
}