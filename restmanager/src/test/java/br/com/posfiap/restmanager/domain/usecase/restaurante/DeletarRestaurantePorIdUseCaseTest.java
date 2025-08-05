package br.com.posfiap.restmanager.domain.usecase.restaurante;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeletarRestaurantePorIdUseCaseTest {

    private RestauranteRepository restauranteRepository;
    private DeletarRestaurantePorIdUseCase useCase;

    @BeforeEach
    void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        useCase = new DeletarRestaurantePorIdUseCase(restauranteRepository);
    }

    @Test
    void deveDeletarRestaurantePorIdComSucesso() {
        // Arrange
        Long idRestaurante = 1L;

        Usuario proprietario = Usuario.builder()
                .id(10L)
                .nome("João")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        Restaurante restaurante = Restaurante.builder()
                .id(idRestaurante)
                .nome("Restaurante Teste")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("10h às 22h")
                .proprietario(proprietario)
                .dataUltimaAlteracao(now())
                .build();

        when(restauranteRepository.buscarPorId(idRestaurante)).thenReturn(Optional.of(restaurante));

        // Act
        useCase.executar(idRestaurante);

        // Assert
        verify(restauranteRepository).buscarPorId(idRestaurante);
        verify(restauranteRepository).deletarPorId(idRestaurante);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        // Arrange
        Long idRestaurante = 1L;

        when(restauranteRepository.buscarPorId(idRestaurante)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> useCase.executar(idRestaurante));
        
        assertTrue(exception.getMessage().contains("Restaurante não encontrado"));
        assertTrue(exception.getMessage().contains(idRestaurante.toString()));
        verify(restauranteRepository).buscarPorId(idRestaurante);
        verify(restauranteRepository, never()).deletarPorId(anyLong());
    }
}