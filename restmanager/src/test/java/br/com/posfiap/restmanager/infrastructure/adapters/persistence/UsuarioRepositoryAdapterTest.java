package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
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
class UsuarioRepositoryAdapterTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioRepositoryAdapter usuarioRepositoryAdapter;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@test.com")
                .login("joao.silva")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .build();
    }

    @Test
    void deveSalvarUsuario() {
        when(usuarioRepository.salvar(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioRepositoryAdapter.salvar(usuario);

        assertNotNull(resultado);
        assertEquals(usuario, resultado);
        verify(usuarioRepository).salvar(usuario);
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(usuarioRepository.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioRepositoryAdapter.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
        verify(usuarioRepository).buscarPorId(1L);
    }

    @Test
    void deveBuscarUsuarioPorLogin() {
        when(usuarioRepository.buscarPorLogin("joao.silva")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioRepositoryAdapter.buscarPorLogin("joao.silva");

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
        verify(usuarioRepository).buscarPorLogin("joao.silva");
    }

    @Test
    void deveExcluirUsuario() {
        doNothing().when(usuarioRepository).excluir(1L);

        usuarioRepositoryAdapter.excluir(1L);

        verify(usuarioRepository).excluir(1L);
    }
}