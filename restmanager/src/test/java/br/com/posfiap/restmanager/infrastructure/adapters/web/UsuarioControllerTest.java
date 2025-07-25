package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.dto.SenhaDto;
import br.com.posfiap.restmanager.dto.UsuarioCreateDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.dto.UsuarioUpdateDto;
import br.com.posfiap.restmanager.mapper.UsuarioMapper;
import br.com.posfiap.restmanager.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;
    private UsuarioCreateDto usuarioCreateDto;
    private UsuarioUpdateDto usuarioUpdateDto;
    private UsuarioResponseDto usuarioResponseDto;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .login("joao@test.com")
                .senha("senha123")
                .build();

        usuarioCreateDto = UsuarioCreateDto.builder()
                .nome("João Silva")
                .login("joao@test.com")
                .senha("senha123")
                .build();

        usuarioUpdateDto = UsuarioUpdateDto.builder()
                .nome("João Silva Atualizado")
                .login("joao.novo@test.com")
                .build();

        usuarioResponseDto = UsuarioResponseDto.builder()
                .id(1L)
                .nome("João Silva")
                .login("joao@test.com")
                .build();
    }

    @Test
    void deveIncluirUsuarioComSucesso() {
        when(usuarioMapper.mapToUsuario(usuarioCreateDto)).thenReturn(usuario);
        when(usuarioService.incluir(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.mapToUsuarioResponseDto(usuario)).thenReturn(usuarioResponseDto);

        UsuarioResponseDto resultado = usuarioController.incluir(usuarioCreateDto);

        assertNotNull(resultado);
        assertEquals(usuarioResponseDto, resultado);
        verify(usuarioMapper).mapToUsuario(usuarioCreateDto);
        verify(usuarioService).incluir(usuario);
        verify(usuarioMapper).mapToUsuarioResponseDto(usuario);
    }

    @Test
    void deveBuscarUsuarioPorIdComSucesso() {
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        when(usuarioMapper.mapToUsuarioResponseDto(usuario)).thenReturn(usuarioResponseDto);

        UsuarioResponseDto resultado = usuarioController.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(usuarioResponseDto, resultado);
        verify(usuarioService).buscarPorId(1L);
        verify(usuarioMapper).mapToUsuarioResponseDto(usuario);
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {
        when(usuarioMapper.mapToUsuario(usuarioUpdateDto)).thenReturn(usuario);
        when(usuarioService.atualizar(anyLong(), any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.mapToUsuarioResponseDto(usuario)).thenReturn(usuarioResponseDto);

        UsuarioResponseDto resultado = usuarioController.atualizar(1L, usuarioUpdateDto);

        assertNotNull(resultado);
        assertEquals(usuarioResponseDto, resultado);
        verify(usuarioMapper).mapToUsuario(usuarioUpdateDto);
        verify(usuarioService).atualizar(1L, usuario);
        verify(usuarioMapper).mapToUsuarioResponseDto(usuario);
    }

    @Test
    void deveExcluirUsuarioComSucesso() {
        doNothing().when(usuarioService).excluir(1L);

        usuarioController.excluir(1L);

        verify(usuarioService).excluir(1L);
    }

    @Test
    void deveAlterarSenhaComSucesso() {
        SenhaDto senhaDto = SenhaDto.builder()
                .senhaAtual("senhaAtual")
                .novaSenha("novaSenha")
                .build();

        doNothing().when(usuarioService).alterarSenha(1L, "senhaAtual", "novaSenha");

        usuarioController.alterarSenha(1L, senhaDto);

        verify(usuarioService).alterarSenha(1L, "senhaAtual", "novaSenha");
    }
}