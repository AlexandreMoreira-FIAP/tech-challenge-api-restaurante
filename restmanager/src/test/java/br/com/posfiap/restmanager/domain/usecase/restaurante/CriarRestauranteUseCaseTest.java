package br.com.posfiap.restmanager.domain.usecase.restaurante;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.RestauranteRepository;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CriarRestauranteUseCaseTest {

    private RestauranteRepository restauranteRepository;
    private UsuarioRepository usuarioRepository;
    private CriarRestauranteUseCase useCase;

    @BeforeEach
    void setUp() {
        restauranteRepository = mock(RestauranteRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        useCase = new CriarRestauranteUseCase(restauranteRepository, usuarioRepository);
    }

    @Test
    void deveCriarRestauranteComSucesso() {
        // Arrange
        Long idProprietario = 1L;

        Usuario usuario = Usuario.builder()
                .id(idProprietario)
                .nome("João")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        Restaurante restaurante = Restaurante.builder()
                .nome("Restaurante Teste")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("10h às 22h")
                .build();

        Restaurante restauranteSalvo = Restaurante.builder()
                .id(10L)
                .nome("Restaurante Teste")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("10h às 22h")
                .proprietario(usuario)
                .dataUltimaAlteracao(now())
                .build();

        when(usuarioRepository.buscarPorId(idProprietario)).thenReturn(Optional.of(usuario));
        when(restauranteRepository.salvar(any())).thenReturn(restauranteSalvo);

        // Act
        Restaurante resultado = useCase.executar(idProprietario, restaurante);

        // Assert
        assertEquals("Restaurante Teste", resultado.getNome());
        assertEquals("Italiana", resultado.getTipoCozinha());
        assertEquals("João", resultado.getProprietario().getNome());
        verify(usuarioRepository).buscarPorId(idProprietario);
        verify(restauranteRepository).salvar(any());
    }
}
