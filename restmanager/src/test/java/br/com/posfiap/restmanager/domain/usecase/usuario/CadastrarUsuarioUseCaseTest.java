package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.exception.BusinessException;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarUsuarioUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private CadastrarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        useCase = new CadastrarUsuarioUseCase(usuarioRepository, passwordEncoder);
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        // Arrange
        String login = "joao.silva";
        String senhaOriginal = "senha123";
        String senhaEncriptada = "senhaEncriptada123";

        Usuario usuario = Usuario.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .login(login)
                .senha(senhaOriginal)
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        Usuario usuarioSalvo = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .login(login)
                .senha(senhaEncriptada)
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .dataUltimaAlteracao(now())
                .build();

        when(usuarioRepository.buscarPorLogin(login)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(senhaOriginal)).thenReturn(senhaEncriptada);
        when(usuarioRepository.salvar(any())).thenReturn(usuarioSalvo);

        // Act
        Usuario resultado = useCase.executar(usuario);

        // Assert
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
        assertEquals(login, resultado.getLogin());
        assertEquals(senhaEncriptada, resultado.getSenha());
        assertEquals(TipoUsuario.PROPRIETARIO, resultado.getTipoUsuario());
        verify(usuarioRepository).buscarPorLogin(login);
        verify(passwordEncoder).encode(senhaOriginal);
        verify(usuarioRepository).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoLoginJaExiste() {
        // Arrange
        String login = "login.existente";

        Usuario usuarioExistente = Usuario.builder()
                .id(1L)
                .nome("Usuário Existente")
                .login(login)
                .build();

        Usuario novoUsuario = Usuario.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .login(login)
                .senha("senha123")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .build();

        when(usuarioRepository.buscarPorLogin(login)).thenReturn(Optional.of(usuarioExistente));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> useCase.executar(novoUsuario));
        
        assertTrue(exception.getMessage().contains("não está disponível"));
        assertTrue(exception.getMessage().contains(login));
        verify(usuarioRepository).buscarPorLogin(login);
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).salvar(any());
    }
}