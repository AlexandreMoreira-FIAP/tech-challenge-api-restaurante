package br.com.posfiap.restmanager.application.usecases;

import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.ports.UsuarioRepositoryPort;
import br.com.posfiap.restmanager.error.AuthenticationException;
import br.com.posfiap.restmanager.error.BusinessException;
import br.com.posfiap.restmanager.error.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    private Usuario usuario;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = Endereco.builder()
                .id(1L)
                .rua("Rua Teste")
                .numero("123")
                .bairro("Centro")
                .cidade("São Paulo")
                .cep("01234-567")
                .estado("SP")
                .pais("Brasil")
                .build();

        usuario = Usuario.builder()
                .id(1L)
                .nome("Teste Usuario")
                .login("teste@email.com")
                .senha("senha123")
                .tipoUsuario(TipoUsuario.CLIENTE)
                .endereco(endereco)
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
    }

    @Test
    void deveIncluirUsuarioComSucesso() {
        when(usuarioRepositoryPort.buscarPorLogin(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("senhaEncriptada");
        when(usuarioRepositoryPort.salvar(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioUseCase.incluir(usuario);

        assertNotNull(resultado);
        verify(usuarioRepositoryPort).buscarPorLogin(usuario.getLogin());
        verify(passwordEncoder).encode("senha123");
        verify(usuarioRepositoryPort).salvar(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoLoginJaExisteAoIncluir() {
        when(usuarioRepositoryPort.buscarPorLogin(anyString())).thenReturn(Optional.of(usuario));

        assertThrows(BusinessException.class, () -> usuarioUseCase.incluir(usuario));
        
        verify(usuarioRepositoryPort).buscarPorLogin(usuario.getLogin());
        verify(usuarioRepositoryPort, never()).salvar(any());
    }

    @Test
    void deveBuscarUsuarioPorIdComSucesso() {
        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioUseCase.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        verify(usuarioRepositoryPort).buscarPorId(1L);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioUseCase.buscarPorId(1L));
        
        verify(usuarioRepositoryPort).buscarPorId(1L);
    }

    @Test
    void deveExcluirUsuarioComSucesso() {
        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        usuarioUseCase.excluir(1L);

        verify(usuarioRepositoryPort).buscarPorId(1L);
        verify(usuarioRepositoryPort).excluir(1L);
    }

    @Test
    void deveBuscarUsuarioPorLogin() {
        when(usuarioRepositoryPort.buscarPorLogin("teste@email.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioUseCase.buscarPorLogin("teste@email.com");

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
        verify(usuarioRepositoryPort).buscarPorLogin("teste@email.com");
    }

    @Test
    void deveAlterarSenhaComSucesso() {
        String senhaAtual = "senhaAtual";
        String novaSenha = "novaSenha"; 
        String novaSenhaEncriptada = "novaSenhaEncriptada";
        
        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(senhaAtual, "senha123")).thenReturn(true);
        when(passwordEncoder.encode(novaSenha)).thenReturn(novaSenhaEncriptada);
        when(usuarioRepositoryPort.salvar(any(Usuario.class))).thenReturn(usuario);

        usuarioUseCase.alterarSenha(1L, senhaAtual, novaSenha);

        verify(usuarioRepositoryPort).buscarPorId(1L);
        verify(passwordEncoder).matches(senhaAtual, "senha123");
        verify(passwordEncoder).encode(novaSenha);
        verify(usuarioRepositoryPort).salvar(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoSenhaAtualErrada() {
        String senhaAtual = "senhaErrada";
        String novaSenha = "novaSenha";
        
        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(senhaAtual, "senha123")).thenReturn(false);

        assertThrows(AuthenticationException.class, 
            () -> usuarioUseCase.alterarSenha(1L, senhaAtual, novaSenha));

        verify(usuarioRepositoryPort).buscarPorId(1L);
        verify(passwordEncoder).matches(senhaAtual, "senha123");
        verify(usuarioRepositoryPort, never()).salvar(any());
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {
        Usuario usuarioAtualizado = Usuario.builder()
                .nome("Nome Atualizado")
                .login("novo@email.com")
                .endereco(endereco)
                .build();
        
        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepositoryPort.buscarPorLogin("novo@email.com")).thenReturn(Optional.empty());
        when(usuarioRepositoryPort.salvar(any(Usuario.class))).thenReturn(usuarioAtualizado);

        Usuario resultado = usuarioUseCase.atualizar(1L, usuarioAtualizado);

        assertNotNull(resultado);
        verify(usuarioRepositoryPort).buscarPorId(1L);
        verify(usuarioRepositoryPort).buscarPorLogin("novo@email.com");
        verify(usuarioRepositoryPort).salvar(usuarioAtualizado);
    }

    @Test
    void deveAtualizarUsuarioSemMudarLogin() {
        Usuario usuarioAtualizado = Usuario.builder()
                .nome("Nome Atualizado")
                .login("teste@email.com") // mesmo login
                .endereco(endereco)
                .build();
        
        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepositoryPort.salvar(any(Usuario.class))).thenReturn(usuarioAtualizado);

        Usuario resultado = usuarioUseCase.atualizar(1L, usuarioAtualizado);

        assertNotNull(resultado);
        verify(usuarioRepositoryPort).buscarPorId(1L);
        verify(usuarioRepositoryPort, never()).buscarPorLogin(anyString());
        verify(usuarioRepositoryPort).salvar(usuarioAtualizado);
    }

    @Test
    void deveAtualizarUsuarioComEnderecoNulo() {
        Usuario usuarioSemEndereco = Usuario.builder()
                .nome("Nome Atualizado")
                .login("novo@email.com")
                .endereco(null)
                .build();
        
        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepositoryPort.buscarPorLogin("novo@email.com")).thenReturn(Optional.empty());
        when(usuarioRepositoryPort.salvar(any(Usuario.class))).thenReturn(usuarioSemEndereco);

        Usuario resultado = usuarioUseCase.atualizar(1L, usuarioSemEndereco);

        assertNotNull(resultado);
        verify(usuarioRepositoryPort).salvar(usuarioSemEndereco);
    }

    @Test
    void deveLancarExcecaoQuandoLoginJaExisteAoAtualizar() {
        Usuario outroUsuario = Usuario.builder().id(2L).login("novo@email.com").build();
        Usuario usuarioAtualizado = Usuario.builder()
                .nome("Nome Atualizado")
                .login("novo@email.com")
                .build();
        
        when(usuarioRepositoryPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepositoryPort.buscarPorLogin("novo@email.com")).thenReturn(Optional.of(outroUsuario));

        assertThrows(BusinessException.class, () -> usuarioUseCase.atualizar(1L, usuarioAtualizado));
        verify(usuarioRepositoryPort, never()).salvar(any());
    }
}