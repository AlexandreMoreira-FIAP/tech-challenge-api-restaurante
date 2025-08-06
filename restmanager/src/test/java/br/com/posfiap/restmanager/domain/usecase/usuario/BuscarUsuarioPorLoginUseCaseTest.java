package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarUsuarioPorLoginUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private BuscarUsuarioPorLoginUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        useCase = new BuscarUsuarioPorLoginUseCase(usuarioRepository);
    }

    @Test
    void deveBuscarUsuarioPorLoginComSucesso() {
        // Arrange
        String login = "joao.silva";

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .login(login)
                .senha("senhaEncriptada")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .dataUltimaAlteracao(now())
                .build();

        when(usuarioRepository.buscarPorLogin(login)).thenReturn(Optional.of(usuario));

        // Act
        Optional<Usuario> resultado = useCase.executar(login);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        assertEquals("João Silva", resultado.get().getNome());
        assertEquals("joao@email.com", resultado.get().getEmail());
        assertEquals(login, resultado.get().getLogin());
        assertEquals(TipoUsuario.PROPRIETARIO, resultado.get().getTipoUsuario());
        verify(usuarioRepository).buscarPorLogin(login);
    }

    @Test
    void deveRetornarOptionalVazioQuandoUsuarioNaoEncontrado() {
        // Arrange
        String login = "usuario.inexistente";

        when(usuarioRepository.buscarPorLogin(login)).thenReturn(Optional.empty());

        // Act
        Optional<Usuario> resultado = useCase.executar(login);

        // Assert
        assertFalse(resultado.isPresent());
        verify(usuarioRepository).buscarPorLogin(login);
    }
}