package br.com.posfiap.restmanager.service;

import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.application.usecases.UsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioUseCase usuarioUseCase;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .login("usuario@test.com")
                .senha("senha123")
                .build();
    }

    @Test
    void deveIncluirUsuario() {
        when(usuarioUseCase.incluir(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.incluir(usuario);

        assertNotNull(resultado);
        assertEquals(usuario, resultado);
        verify(usuarioUseCase).incluir(usuario);
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(usuarioUseCase.buscarPorId(1L)).thenReturn(usuario);

        Usuario resultado = usuarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(usuario, resultado);
        verify(usuarioUseCase).buscarPorId(1L);
    }

    @Test
    void deveExcluirUsuario() {
        doNothing().when(usuarioUseCase).excluir(1L);

        usuarioService.excluir(1L);

        verify(usuarioUseCase).excluir(1L);
    }

    @Test
    void deveBuscarUsuarioPorLogin() {
        when(usuarioUseCase.buscarPorLogin("usuario@test.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorLogin("usuario@test.com");

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
        verify(usuarioUseCase).buscarPorLogin("usuario@test.com");
    }

    @Test
    void deveAlterarSenha() {
        doNothing().when(usuarioUseCase).alterarSenha(1L, "senhaAtual", "novaSenha");

        usuarioService.alterarSenha(1L, "senhaAtual", "novaSenha");

        verify(usuarioUseCase).alterarSenha(1L, "senhaAtual", "novaSenha");
    }

    @Test
    void deveAtualizarUsuario() {
        when(usuarioUseCase.atualizar(1L, usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.atualizar(1L, usuario);

        assertNotNull(resultado);
        assertEquals(usuario, resultado);
        verify(usuarioUseCase).atualizar(1L, usuario);
    }
}