package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.exception.AuthenticationException;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlterarSenhaUsuarioUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private AlterarSenhaUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        useCase = new AlterarSenhaUsuarioUseCase(usuarioRepository, passwordEncoder);
    }

    @Test
    void deveAlterarSenhaComSucesso() {
        // Arrange
        Long idUsuario = 1L;
        String senhaAtual = "senhaAtual123";
        String novaSenha = "novaSenha456";
        String senhaAtualEncriptada = "senhaAtualEncriptada";
        String novaSenhaEncriptada = "novaSenhaEncriptada";

        Usuario usuario = Usuario.builder()
                .id(idUsuario)
                .nome("João Silva")
                .email("joao@email.com")
                .login("joao.silva")
                .senha(senhaAtualEncriptada)
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        when(usuarioRepository.buscarPorId(idUsuario)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(senhaAtual, senhaAtualEncriptada)).thenReturn(true);
        when(passwordEncoder.encode(novaSenha)).thenReturn(novaSenhaEncriptada);

        // Act
        useCase.executar(idUsuario, senhaAtual, novaSenha);

        // Assert
        verify(usuarioRepository).buscarPorId(idUsuario);
        verify(passwordEncoder).matches(senhaAtual, senhaAtualEncriptada);
        verify(passwordEncoder).encode(novaSenha);
        verify(usuarioRepository).salvar(argThat(u -> 
            u.getId().equals(idUsuario) && 
            u.getSenha().equals(novaSenhaEncriptada) &&
            u.getDataUltimaAlteracao() != null
        ));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        Long idUsuario = 1L;
        String senhaAtual = "senhaAtual123";
        String novaSenha = "novaSenha456";

        when(usuarioRepository.buscarPorId(idUsuario)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> useCase.executar(idUsuario, senhaAtual, novaSenha));
        
        assertTrue(exception.getMessage().contains("Usuário não encontrado"));
        assertTrue(exception.getMessage().contains(idUsuario.toString()));
        verify(usuarioRepository).buscarPorId(idUsuario);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaAtualIncorreta() {
        // Arrange
        Long idUsuario = 1L;
        String senhaAtual = "senhaIncorreta";
        String novaSenha = "novaSenha456";
        String senhaAtualEncriptada = "senhaAtualEncriptada";

        Usuario usuario = Usuario.builder()
                .id(idUsuario)
                .nome("João Silva")
                .email("joao@email.com")
                .login("joao.silva")
                .senha(senhaAtualEncriptada)
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        when(usuarioRepository.buscarPorId(idUsuario)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(senhaAtual, senhaAtualEncriptada)).thenReturn(false);

        // Act & Assert
        AuthenticationException exception = assertThrows(AuthenticationException.class, 
            () -> useCase.executar(idUsuario, senhaAtual, novaSenha));
        
        assertEquals("Senha incorreta.", exception.getMessage());
        verify(usuarioRepository).buscarPorId(idUsuario);
        verify(passwordEncoder).matches(senhaAtual, senhaAtualEncriptada);
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).salvar(any());
    }
}