package br.com.posfiap.restmanager.infrastructure.persistence;

import br.com.posfiap.restmanager.application.mapper.UsuarioMapper;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.UsuarioEntity;
import br.com.posfiap.restmanager.infrastructure.persistence.jpa.UsuarioJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioRepositoryImplTest {

    @Mock
    private UsuarioJpaRepository usuarioJpaRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioRepositoryImpl usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarUsuario() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .nome("João")
                .login("joao@email.com")
                .build();
        
        UsuarioEntity entity = new UsuarioEntity();
        UsuarioEntity savedEntity = new UsuarioEntity();
        Usuario savedUsuario = Usuario.builder()
                .id(1L)
                .nome("João")
                .login("joao@email.com")
                .build();

        when(usuarioMapper.mapToUsuarioEntity(usuario)).thenReturn(entity);
        when(usuarioJpaRepository.save(entity)).thenReturn(savedEntity);
        when(usuarioMapper.mapToUsuario(savedEntity)).thenReturn(savedUsuario);

        // Act
        Usuario resultado = usuarioRepository.salvar(usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João", resultado.getNome());
        verify(usuarioMapper).mapToUsuarioEntity(usuario);
        verify(usuarioJpaRepository).save(entity);
        verify(usuarioMapper).mapToUsuario(savedEntity);
    }

    @Test
    void deveBuscarUsuarioPorId() {
        // Arrange
        Long id = 1L;
        UsuarioEntity entity = new UsuarioEntity();
        Usuario usuario = Usuario.builder()
                .id(id)
                .nome("João")
                .build();

        when(usuarioJpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(usuarioMapper.mapToUsuario(entity)).thenReturn(usuario);

        // Act
        Optional<Usuario> resultado = usuarioRepository.buscarPorId(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals("João", resultado.get().getNome());
        verify(usuarioJpaRepository).findById(id);
        verify(usuarioMapper).mapToUsuario(entity);
    }

    @Test
    void deveRetornarVazioQuandoUsuarioNaoEncontrado() {
        // Arrange
        Long id = 1L;
        when(usuarioJpaRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Usuario> resultado = usuarioRepository.buscarPorId(id);

        // Assert
        assertFalse(resultado.isPresent());
        verify(usuarioJpaRepository).findById(id);
        verify(usuarioMapper, never()).mapToUsuario(any(UsuarioEntity.class));
    }

    @Test
    void deveExcluirUsuario() {
        // Arrange
        Long id = 1L;

        // Act
        usuarioRepository.excluir(id);

        // Assert
        verify(usuarioJpaRepository).deleteById(id);
    }

    @Test
    void deveBuscarUsuarioPorLogin() {
        // Arrange
        String login = "joao@email.com";
        UsuarioEntity entity = new UsuarioEntity();
        Usuario usuario = Usuario.builder()
                .login(login)
                .nome("João")
                .build();

        when(usuarioJpaRepository.findByLogin(login)).thenReturn(Optional.of(entity));
        when(usuarioMapper.mapToUsuario(entity)).thenReturn(usuario);

        // Act
        Optional<Usuario> resultado = usuarioRepository.buscarPorLogin(login);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(login, resultado.get().getLogin());
        assertEquals("João", resultado.get().getNome());
        verify(usuarioJpaRepository).findByLogin(login);
        verify(usuarioMapper).mapToUsuario(entity);
    }

    @Test
    void deveRetornarVazioQuandoUsuarioNaoEncontradoPorLogin() {
        // Arrange
        String login = "naoexiste@email.com";
        when(usuarioJpaRepository.findByLogin(login)).thenReturn(Optional.empty());

        // Act
        Optional<Usuario> resultado = usuarioRepository.buscarPorLogin(login);

        // Assert
        assertFalse(resultado.isPresent());
        verify(usuarioJpaRepository).findByLogin(login);
        verify(usuarioMapper, never()).mapToUsuario(any(UsuarioEntity.class));
    }
}