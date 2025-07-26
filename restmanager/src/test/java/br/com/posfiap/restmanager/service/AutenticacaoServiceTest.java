package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.error.AuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .login("usuario@test.com")
                .senha("senhaEncriptada")
                .build();
    }

    @Test
    void deveAutenticarComSucesso() {
        when(usuarioService.buscarPorLogin("usuario@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaCorreta", "senhaEncriptada")).thenReturn(true);

        assertDoesNotThrow(() -> autenticacaoService.autenticarLogin("usuario@test.com", "senhaCorreta"));

        verify(usuarioService).buscarPorLogin("usuario@test.com");
        verify(passwordEncoder).matches("senhaCorreta", "senhaEncriptada");
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(usuarioService.buscarPorLogin("usuario@test.com")).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, 
            () -> autenticacaoService.autenticarLogin("usuario@test.com", "senhaCorreta"));

        verify(usuarioService).buscarPorLogin("usuario@test.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaIncorreta() {
        when(usuarioService.buscarPorLogin("usuario@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaIncorreta", "senhaEncriptada")).thenReturn(false);

        AuthenticationException exception = assertThrows(AuthenticationException.class, 
            () -> autenticacaoService.autenticarLogin("usuario@test.com", "senhaIncorreta"));

        assertEquals("Login ou senha incorreto.", exception.getMessage());
        verify(usuarioService).buscarPorLogin("usuario@test.com");
        verify(passwordEncoder).matches("senhaIncorreta", "senhaEncriptada");
    }

    @Test
    void deveLancarExcecaoComMensagemCorretaQuandoUsuarioNaoEncontrado() {
        when(usuarioService.buscarPorLogin("usuario@test.com")).thenReturn(Optional.empty());

        AuthenticationException exception = assertThrows(AuthenticationException.class, 
            () -> autenticacaoService.autenticarLogin("usuario@test.com", "senhaCorreta"));

        assertEquals("Login ou senha incorreto.", exception.getMessage());
        verify(usuarioService).buscarPorLogin("usuario@test.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }
}