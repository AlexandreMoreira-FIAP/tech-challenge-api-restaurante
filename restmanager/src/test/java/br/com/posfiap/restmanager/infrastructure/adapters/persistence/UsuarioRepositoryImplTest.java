package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.infrastructure.entities.UsuarioEntity;
import br.com.posfiap.restmanager.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioRepositoryImplTest {

    @Mock
    private UsuarioJpaRepository usuarioJpaRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioRepositoryImpl usuarioRepositoryImpl;

    private Usuario usuario;
    private UsuarioEntity usuarioEntity;

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

        usuarioEntity = UsuarioEntity.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@test.com")
                .login("joao.silva")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.CLIENTE.name())
                .build();
    }

    @Test
    void deveSalvarUsuario() {
        when(usuarioMapper.mapToUsuarioEntity(usuario)).thenReturn(usuarioEntity);
        when(usuarioJpaRepository.save(usuarioEntity)).thenReturn(usuarioEntity);
        when(usuarioMapper.mapToUsuario(usuarioEntity)).thenReturn(usuario);

        Usuario resultado = usuarioRepositoryImpl.salvar(usuario);

        assertNotNull(resultado);
        assertEquals(usuario, resultado);
        verify(usuarioMapper).mapToUsuarioEntity(usuario);
        verify(usuarioJpaRepository).save(usuarioEntity);
        verify(usuarioMapper).mapToUsuario(usuarioEntity);
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(usuarioJpaRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        when(usuarioMapper.mapToUsuario(usuarioEntity)).thenReturn(usuario);

        Optional<Usuario> resultado = usuarioRepositoryImpl.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
        verify(usuarioJpaRepository).findById(1L);
        verify(usuarioMapper).mapToUsuario(usuarioEntity);
    }

    @Test
    void deveRetornarVazioQuandoUsuarioNaoEncontrado() {
        when(usuarioJpaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioRepositoryImpl.buscarPorId(1L);

        assertFalse(resultado.isPresent());
        verify(usuarioJpaRepository).findById(1L);
        verify(usuarioMapper, never()).mapToUsuario(any(UsuarioEntity.class));
    }

    @Test
    void deveExcluirUsuario() {
        doNothing().when(usuarioJpaRepository).deleteById(1L);

        usuarioRepositoryImpl.excluir(1L);

        verify(usuarioJpaRepository).deleteById(1L);
    }

    @Test
    void deveBuscarUsuarioPorLogin() {
        when(usuarioJpaRepository.findByLogin("joao.silva")).thenReturn(Optional.of(usuarioEntity));
        when(usuarioMapper.mapToUsuario(usuarioEntity)).thenReturn(usuario);

        Optional<Usuario> resultado = usuarioRepositoryImpl.buscarPorLogin("joao.silva");

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
        verify(usuarioJpaRepository).findByLogin("joao.silva");
        verify(usuarioMapper).mapToUsuario(usuarioEntity);
    }

    @Test
    void deveRetornarVazioQuandoUsuarioNaoEncontradoPorLogin() {
        when(usuarioJpaRepository.findByLogin("joao.silva")).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioRepositoryImpl.buscarPorLogin("joao.silva");

        assertFalse(resultado.isPresent());
        verify(usuarioJpaRepository).findByLogin("joao.silva");
        verify(usuarioMapper, never()).mapToUsuario(any(UsuarioEntity.class));
    }
}