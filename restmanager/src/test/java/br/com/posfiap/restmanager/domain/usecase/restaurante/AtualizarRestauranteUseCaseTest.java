package br.com.posfiap.restmanager.domain.usecase.restaurante;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.exception.UnauthorizedException;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtualizarRestauranteUseCaseTest {

    private RestauranteRepository restauranteRepository;
    private AtualizarRestauranteUseCase useCase;

    @BeforeEach
    void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        useCase = new AtualizarRestauranteUseCase(restauranteRepository);
    }

    @Test
    void deveAtualizarRestauranteComSucesso() {
        // Arrange
        Long idRestaurante = 1L;
        Long idUsuario = 10L;

        Usuario proprietario = Usuario.builder()
                .id(idUsuario)
                .nome("João")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        Restaurante restauranteExistente = Restaurante.builder()
                .id(idRestaurante)
                .nome("Restaurante Antigo")
                .tipoCozinha("Italiana")
                .proprietario(proprietario)
                .build();

        Restaurante restauranteAtualizado = Restaurante.builder()
                .nome("Restaurante Atualizado")
                .tipoCozinha("Brasileira")
                .horarioFuncionamento("11h às 23h")
                .build();

        Restaurante restauranteSalvo = Restaurante.builder()
                .id(idRestaurante)
                .nome("Restaurante Atualizado")
                .tipoCozinha("Brasileira")
                .horarioFuncionamento("11h às 23h")
                .proprietario(proprietario)
                .dataUltimaAlteracao(now())
                .build();

        when(restauranteRepository.buscarPorId(idRestaurante)).thenReturn(Optional.of(restauranteExistente));
        when(restauranteRepository.salvar(any())).thenReturn(restauranteSalvo);

        // Act
        Restaurante resultado = useCase.executar(idRestaurante, idUsuario, restauranteAtualizado);

        // Assert
        assertEquals("Restaurante Atualizado", resultado.getNome());
        assertEquals("Brasileira", resultado.getTipoCozinha());
        assertEquals("11h às 23h", resultado.getHorarioFuncionamento());
        assertEquals(idRestaurante, resultado.getId());
        assertNotNull(resultado.getDataUltimaAlteracao());
        verify(restauranteRepository).buscarPorId(idRestaurante);
        verify(restauranteRepository).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        // Arrange
        Long idRestaurante = 1L;
        Long idUsuario = 10L;
        Restaurante restauranteAtualizado = Restaurante.builder().nome("Teste").build();

        when(restauranteRepository.buscarPorId(idRestaurante)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> useCase.executar(idRestaurante, idUsuario, restauranteAtualizado));
        
        assertTrue(exception.getMessage().contains("Restaurante não encontrado"));
        verify(restauranteRepository).buscarPorId(idRestaurante);
        verify(restauranteRepository, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoAutorizado() {
        // Arrange
        Long idRestaurante = 1L;
        Long idUsuarioProprietario = 10L;
        Long idUsuarioNaoAutorizado = 20L;

        Usuario proprietario = Usuario.builder()
                .id(idUsuarioProprietario)
                .nome("João")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        Restaurante restauranteExistente = Restaurante.builder()
                .id(idRestaurante)
                .nome("Restaurante Teste")
                .proprietario(proprietario)
                .build();

        Restaurante restauranteAtualizado = Restaurante.builder().nome("Novo Nome").build();

        when(restauranteRepository.buscarPorId(idRestaurante)).thenReturn(Optional.of(restauranteExistente));

        // Act & Assert
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, 
            () -> useCase.executar(idRestaurante, idUsuarioNaoAutorizado, restauranteAtualizado));
        
        assertEquals("Usuário com ID {0} não tem permissão para atualizar o restaurante com ID {1}.", exception.getMessage());
        verify(restauranteRepository).buscarPorId(idRestaurante);
        verify(restauranteRepository, never()).salvar(any());
    }
}