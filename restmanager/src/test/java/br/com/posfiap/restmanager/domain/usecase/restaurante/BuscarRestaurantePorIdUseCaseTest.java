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

class BuscarRestaurantePorIdUseCaseTest {

    private RestauranteRepository restauranteRepository;
    private BuscarRestaurantePorIdUseCase useCase;

    @BeforeEach
    void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        useCase = new BuscarRestaurantePorIdUseCase(restauranteRepository);
    }

    @Test
    void deveBuscarRestaurantePorIdComSucesso() {
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
        Restaurante resultado = useCase.executar(idRestaurante);

        // Assert
        assertEquals(idRestaurante, resultado.getId());
        assertEquals("Restaurante Teste", resultado.getNome());
        assertEquals("Italiana", resultado.getTipoCozinha());
        assertEquals("10h às 22h", resultado.getHorarioFuncionamento());
        assertEquals(proprietario, resultado.getProprietario());
        verify(restauranteRepository).buscarPorId(idRestaurante);
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
    }
}