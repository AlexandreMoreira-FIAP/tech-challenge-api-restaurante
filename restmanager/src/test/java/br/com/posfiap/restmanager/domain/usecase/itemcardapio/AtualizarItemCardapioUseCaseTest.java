package br.com.posfiap.restmanager.domain.usecase.itemcardapio;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.exception.UnauthorizedException;
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

class AtualizarItemCardapioUseCaseTest {

    private ItemCardapioRepository itemCardapioRepository;
    private RestauranteRepository restauranteRepository;
    private AtualizarItemCardapioUseCase useCase;

    @BeforeEach
    void setUp() {
        itemCardapioRepository = mock(ItemCardapioRepository.class);
        restauranteRepository = mock(RestauranteRepository.class);
        useCase = new AtualizarItemCardapioUseCase(itemCardapioRepository, restauranteRepository);
    }

    @Test
    void deveAtualizarItemCardapioComSucesso() {
        // Arrange
        Long idItem = 1L;
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

        ItemCardapio itemExistente = ItemCardapio.builder()
                .id(idItem)
                .nome("Pizza Margherita Antiga")
                .descricao("Descrição antiga")
                .preco(BigDecimal.valueOf(30.00))
                .disponivel(false)
                .restaurante(restaurante)
                .build();

        ItemCardapio itemAtualizado = ItemCardapio.builder()
                .nome("Pizza Margherita Nova")
                .descricao("Nova descrição com ingredientes frescos")
                .preco(BigDecimal.valueOf(39.90))
                .disponivel(true)
                .caminhoFoto("/fotos/pizza-nova.jpg")
                .build();

        ItemCardapio itemSalvo = ItemCardapio.builder()
                .id(idItem)
                .nome("Pizza Margherita Nova")
                .descricao("Nova descrição com ingredientes frescos")
                .preco(BigDecimal.valueOf(39.90))
                .disponivel(true)
                .caminhoFoto("/fotos/pizza-nova.jpg")
                .restaurante(restaurante)
                .dataUltimaAlteracao(now())
                .build();

        when(itemCardapioRepository.buscarPorId(idItem)).thenReturn(Optional.of(itemExistente));
        when(restauranteRepository.buscarPorId(idRestaurante)).thenReturn(Optional.of(restaurante));
        when(itemCardapioRepository.salvar(any())).thenReturn(itemSalvo);

        // Act
        ItemCardapio resultado = useCase.executar(idItem, idRestaurante, itemAtualizado);

        // Assert
        assertEquals(idItem, resultado.getId());
        assertEquals("Pizza Margherita Nova", resultado.getNome());
        assertEquals("Nova descrição com ingredientes frescos", resultado.getDescricao());
        assertEquals(BigDecimal.valueOf(39.90), resultado.getPreco());
        assertTrue(resultado.getDisponivel());
        assertEquals("/fotos/pizza-nova.jpg", resultado.getCaminhoFoto());
        assertEquals(restaurante, resultado.getRestaurante());
        assertNotNull(resultado.getDataUltimaAlteracao());
        verify(itemCardapioRepository).buscarPorId(idItem);
        verify(restauranteRepository).buscarPorId(idRestaurante);
        verify(itemCardapioRepository).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontrado() {
        // Arrange
        Long idItem = 1L;
        Long idRestaurante = 5L;

        ItemCardapio itemAtualizado = ItemCardapio.builder()
                .nome("Pizza Nova")
                .build();

        when(itemCardapioRepository.buscarPorId(idItem)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> useCase.executar(idItem, idRestaurante, itemAtualizado));
        
        assertTrue(exception.getMessage().contains("Item do cardápio não encontrado"));
        assertTrue(exception.getMessage().contains(idItem.toString()));
        verify(itemCardapioRepository).buscarPorId(idItem);
        verify(restauranteRepository, never()).buscarPorId(anyLong());
        verify(itemCardapioRepository, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        // Arrange
        Long idItem = 1L;
        Long idRestaurante = 5L;

        Restaurante restauranteExistente = Restaurante.builder()
                .id(idRestaurante)
                .nome("Restaurante Teste")
                .build();

        ItemCardapio itemExistente = ItemCardapio.builder()
                .id(idItem)
                .nome("Pizza Teste")
                .restaurante(restauranteExistente)
                .build();

        ItemCardapio itemAtualizado = ItemCardapio.builder()
                .nome("Pizza Nova")
                .build();

        when(itemCardapioRepository.buscarPorId(idItem)).thenReturn(Optional.of(itemExistente));
        when(restauranteRepository.buscarPorId(idRestaurante)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> useCase.executar(idItem, idRestaurante, itemAtualizado));
        
        assertTrue(exception.getMessage().contains("Restaurante não encontrado"));
        assertTrue(exception.getMessage().contains(idRestaurante.toString()));
        verify(itemCardapioRepository).buscarPorId(idItem);
        verify(restauranteRepository).buscarPorId(idRestaurante);
        verify(itemCardapioRepository, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoPertenceAoRestaurante() {
        // Arrange
        Long idItem = 1L;
        Long idRestauranteExistente = 5L;
        Long idRestauranteDiferente = 10L;

        Restaurante restauranteExistente = Restaurante.builder()
                .id(idRestauranteExistente)
                .nome("Restaurante Original")
                .build();

        Restaurante restauranteDiferente = Restaurante.builder()
                .id(idRestauranteDiferente)
                .nome("Restaurante Diferente")
                .build();

        ItemCardapio itemExistente = ItemCardapio.builder()
                .id(idItem)
                .nome("Pizza Teste")
                .restaurante(restauranteExistente) // pertence ao restaurante 5
                .build();

        ItemCardapio itemAtualizado = ItemCardapio.builder()
                .nome("Pizza Nova")
                .build();

        when(itemCardapioRepository.buscarPorId(idItem)).thenReturn(Optional.of(itemExistente));
        when(restauranteRepository.buscarPorId(idRestauranteDiferente)).thenReturn(Optional.of(restauranteDiferente));

        // Act & Assert
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, 
            () -> useCase.executar(idItem, idRestauranteDiferente, itemAtualizado));
        
        assertEquals("Este item não pertence ao restaurante informado.", exception.getMessage());
        verify(itemCardapioRepository).buscarPorId(idItem);
        verify(restauranteRepository).buscarPorId(idRestauranteDiferente);
        verify(itemCardapioRepository, never()).salvar(any());
    }
}