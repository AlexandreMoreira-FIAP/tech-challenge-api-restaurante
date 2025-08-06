package br.com.posfiap.restmanager.domain.usecase.itemcardapio;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.ItemCardapio;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.ItemCardapioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarItemCardapioPorIdUseCaseTest {

    private ItemCardapioRepository itemCardapioRepository;
    private BuscarItemCardapioPorIdUseCase useCase;

    @BeforeEach
    void setUp() {
        itemCardapioRepository = mock(ItemCardapioRepository.class);
        useCase = new BuscarItemCardapioPorIdUseCase(itemCardapioRepository);
    }

    @Test
    void deveBuscarItemCardapioPorIdComSucesso() {
        // Arrange
        Long idItem = 1L;

        Usuario proprietario = Usuario.builder()
                .id(10L)
                .nome("João")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        Restaurante restaurante = Restaurante.builder()
                .id(5L)
                .nome("Restaurante Teste")
                .proprietario(proprietario)
                .build();

        ItemCardapio item = ItemCardapio.builder()
                .id(idItem)
                .nome("Pizza Margherita")
                .descricao("Pizza com tomate, mozzarella e manjericão")
                .preco(BigDecimal.valueOf(35.90))
                .disponivel(true)
                .caminhoFoto("/fotos/pizza-margherita.jpg")
                .restaurante(restaurante)
                .dataUltimaAlteracao(now())
                .build();

        when(itemCardapioRepository.buscarPorId(idItem)).thenReturn(Optional.of(item));

        // Act
        ItemCardapio resultado = useCase.executar(idItem);

        // Assert
        assertEquals(idItem, resultado.getId());
        assertEquals("Pizza Margherita", resultado.getNome());
        assertEquals("Pizza com tomate, mozzarella e manjericão", resultado.getDescricao());
        assertEquals(BigDecimal.valueOf(35.90), resultado.getPreco());
        assertTrue(resultado.getDisponivel());
        assertEquals("/fotos/pizza-margherita.jpg", resultado.getCaminhoFoto());
        assertEquals(restaurante, resultado.getRestaurante());
        verify(itemCardapioRepository).buscarPorId(idItem);
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontrado() {
        // Arrange
        Long idItem = 1L;

        when(itemCardapioRepository.buscarPorId(idItem)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> useCase.executar(idItem));
        
        assertTrue(exception.getMessage().contains("Item do cardápio não encontrado"));
        assertTrue(exception.getMessage().contains(idItem.toString()));
        verify(itemCardapioRepository).buscarPorId(idItem);
    }
}