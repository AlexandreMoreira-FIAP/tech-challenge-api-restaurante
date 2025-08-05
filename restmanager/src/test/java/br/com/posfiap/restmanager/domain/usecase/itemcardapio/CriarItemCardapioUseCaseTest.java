package br.com.posfiap.restmanager.domain.usecase.itemcardapio;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.ItemCardapio;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.ItemCardapioRepository;
import br.com.posfiap.restmanager.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CriarItemCardapioUseCaseTest {

    private ItemCardapioRepository itemCardapioRepository;
    private RestauranteRepository restauranteRepository;
    private CriarItemCardapioUseCase useCase;

    @BeforeEach
    void setUp() {
        itemCardapioRepository = mock(ItemCardapioRepository.class);
        restauranteRepository = mock(RestauranteRepository.class);
        useCase = new CriarItemCardapioUseCase(itemCardapioRepository, restauranteRepository);
    }

    @Test
    void deveCriarItemCardapioComSucesso() {
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

        ItemCardapio itemParaCriar = ItemCardapio.builder()
                .nome("Pizza Margherita")
                .descricao("Pizza com tomate, mozzarella e manjericão")
                .preco(BigDecimal.valueOf(35.90))
                .disponivel(true)
                .caminhoFoto("/fotos/pizza-margherita.jpg")
                .build();

        ItemCardapio itemSalvo = ItemCardapio.builder()
                .id(1L)
                .nome("Pizza Margherita")
                .descricao("Pizza com tomate, mozzarella e manjericão")
                .preco(BigDecimal.valueOf(35.90))
                .disponivel(true)
                .caminhoFoto("/fotos/pizza-margherita.jpg")
                .restaurante(restaurante)
                .dataUltimaAlteracao(now())
                .build();

        when(restauranteRepository.buscarPorId(idRestaurante)).thenReturn(Optional.of(restaurante));
        when(itemCardapioRepository.salvar(any())).thenReturn(itemSalvo);

        // Act
        ItemCardapio resultado = useCase.executar(idRestaurante, itemParaCriar);

        // Assert
        assertEquals(1L, resultado.getId());
        assertEquals("Pizza Margherita", resultado.getNome());
        assertEquals("Pizza com tomate, mozzarella e manjericão", resultado.getDescricao());
        assertEquals(BigDecimal.valueOf(35.90), resultado.getPreco());
        assertTrue(resultado.getDisponivel());
        assertEquals("/fotos/pizza-margherita.jpg", resultado.getCaminhoFoto());
        assertEquals(restaurante, resultado.getRestaurante());
        assertNotNull(resultado.getDataUltimaAlteracao());
        verify(restauranteRepository).buscarPorId(idRestaurante);
        verify(itemCardapioRepository).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        // Arrange
        Long idRestaurante = 5L;

        ItemCardapio itemParaCriar = ItemCardapio.builder()
                .nome("Pizza Margherita")
                .descricao("Pizza com tomate, mozzarella e manjericão")
                .preco(BigDecimal.valueOf(35.90))
                .disponivel(true)
                .build();

        when(restauranteRepository.buscarPorId(idRestaurante)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> useCase.executar(idRestaurante, itemParaCriar));
        
        assertTrue(exception.getMessage().contains("Restaurante não encontrado"));
        assertTrue(exception.getMessage().contains(idRestaurante.toString()));
        verify(restauranteRepository).buscarPorId(idRestaurante);
        verify(itemCardapioRepository, never()).salvar(any());
    }
}