package br.com.posfiap.restmanager.domain.usecase.itemcardapio;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.model.ItemCardapio;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.ItemCardapioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarItensCardapioPorRestauranteUseCaseTest {

    private ItemCardapioRepository itemCardapioRepository;
    private ListarItensCardapioPorRestauranteUseCase useCase;

    @BeforeEach
    void setUp() {
        itemCardapioRepository = mock(ItemCardapioRepository.class);
        useCase = new ListarItensCardapioPorRestauranteUseCase(itemCardapioRepository);
    }

    @Test
    void deveListarItensCardapioPorRestauranteComSucesso() {
        // Arrange
        Long idRestaurante = 5L;

        Usuario proprietario = Usuario.builder()
                .id(10L)
                .nome("João")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        Restaurante restaurante = Restaurante.builder()
                .id(idRestaurante)
                .nome("Restaurante Teste")
                .proprietario(proprietario)
                .build();

        ItemCardapio item1 = ItemCardapio.builder()
                .id(1L)
                .nome("Pizza Margherita")
                .descricao("Pizza com tomate, mozzarella e manjericão")
                .preco(BigDecimal.valueOf(35.90))
                .disponivel(true)
                .caminhoFoto("/fotos/pizza-margherita.jpg")
                .restaurante(restaurante)
                .dataUltimaAlteracao(now())
                .build();

        ItemCardapio item2 = ItemCardapio.builder()
                .id(2L)
                .nome("Lasanha Bolonhesa")
                .descricao("Lasanha com molho bolonhesa e queijo")
                .preco(BigDecimal.valueOf(42.50))
                .disponivel(true)
                .caminhoFoto("/fotos/lasanha-bolonhesa.jpg")
                .restaurante(restaurante)
                .dataUltimaAlteracao(now())
                .build();

        List<ItemCardapio> itens = List.of(item1, item2);

        when(itemCardapioRepository.buscarPorRestauranteId(idRestaurante)).thenReturn(itens);

        // Act
        List<ItemCardapio> resultado = useCase.executar(idRestaurante);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Pizza Margherita", resultado.get(0).getNome());
        assertEquals("Lasanha Bolonhesa", resultado.get(1).getNome());
        assertEquals(BigDecimal.valueOf(35.90), resultado.get(0).getPreco());
        assertEquals(BigDecimal.valueOf(42.50), resultado.get(1).getPreco());
        assertEquals(restaurante, resultado.get(0).getRestaurante());
        assertEquals(restaurante, resultado.get(1).getRestaurante());
        verify(itemCardapioRepository).buscarPorRestauranteId(idRestaurante);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverItens() {
        // Arrange
        Long idRestaurante = 5L;
        List<ItemCardapio> listaVazia = new ArrayList<>();

        when(itemCardapioRepository.buscarPorRestauranteId(idRestaurante)).thenReturn(listaVazia);

        // Act
        List<ItemCardapio> resultado = useCase.executar(idRestaurante);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(itemCardapioRepository).buscarPorRestauranteId(idRestaurante);
    }
}