package br.com.posfiap.restmanager.domain.usecase.itemcardapio;

import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.repository.ItemCardapioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeletarItemCardapioPorIdUseCaseTest {

    private ItemCardapioRepository itemCardapioRepository;
    private DeletarItemCardapioPorIdUseCase useCase;

    @BeforeEach
    void setUp() {
        itemCardapioRepository = mock(ItemCardapioRepository.class);
        useCase = new DeletarItemCardapioPorIdUseCase(itemCardapioRepository);
    }

    @Test
    void deveDeletarItemCardapioComSucesso() {
        // Arrange
        Long idItem = 1L;

        when(itemCardapioRepository.existePorId(idItem)).thenReturn(true);

        // Act
        useCase.executar(idItem);

        // Assert
        verify(itemCardapioRepository).existePorId(idItem);
        verify(itemCardapioRepository).deletarPorId(idItem);
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontrado() {
        // Arrange
        Long idItem = 1L;

        when(itemCardapioRepository.existePorId(idItem)).thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> useCase.executar(idItem));
        
        assertTrue(exception.getMessage().contains("Item do cardápio não encontrado"));
        assertTrue(exception.getMessage().contains(idItem.toString()));
        verify(itemCardapioRepository).existePorId(idItem);
        verify(itemCardapioRepository, never()).deletarPorId(anyLong());
    }
}