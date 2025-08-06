package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.model.Endereco;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtualizarUsuarioUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private ValidarDisponibilidadeLoginUseCase validarDisponibilidadeLoginUseCase;
    private AtualizarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        buscarUsuarioPorIdUseCase = mock(BuscarUsuarioPorIdUseCase.class);
        validarDisponibilidadeLoginUseCase = mock(ValidarDisponibilidadeLoginUseCase.class);
        useCase = new AtualizarUsuarioUseCase(usuarioRepository, buscarUsuarioPorIdUseCase, validarDisponibilidadeLoginUseCase);
    }

    @Test
    void deveAtualizarUsuarioSemAlterarLogin() {
        // Arrange
        Long idUsuario = 1L;
        String loginOriginal = "joao.silva";
        String senhaOriginal = "senhaEncriptada123";

        Usuario usuarioAtual = Usuario.builder()
                .id(idUsuario)
                .nome("João Silva")
                .email("joao@email.com")
                .login(loginOriginal)
                .senha(senhaOriginal)
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        Usuario usuarioAtualizado = Usuario.builder()
                .nome("João Santos")
                .email("joao.santos@email.com")
                .login(loginOriginal) // mesmo login
                .tipoUsuario(TipoUsuario.CLIENTE)
                .build();

        Usuario usuarioSalvo = Usuario.builder()
                .id(idUsuario)
                .nome("João Santos")
                .email("joao.santos@email.com")
                .login(loginOriginal)
                .senha(senhaOriginal) // senha preservada
                .tipoUsuario(TipoUsuario.CLIENTE)
                .dataUltimaAlteracao(now())
                .build();

        when(buscarUsuarioPorIdUseCase.executar(idUsuario)).thenReturn(usuarioAtual);
        when(usuarioRepository.salvar(any())).thenReturn(usuarioSalvo);

        // Act
        Usuario resultado = useCase.executar(idUsuario, usuarioAtualizado);

        // Assert
        assertEquals(idUsuario, resultado.getId());
        assertEquals("João Santos", resultado.getNome());
        assertEquals("joao.santos@email.com", resultado.getEmail());
        assertEquals(loginOriginal, resultado.getLogin());
        assertEquals(senhaOriginal, resultado.getSenha()); // senha preservada
        assertEquals(TipoUsuario.CLIENTE, resultado.getTipoUsuario());
        assertNotNull(resultado.getDataUltimaAlteracao());
        verify(buscarUsuarioPorIdUseCase).executar(idUsuario);
        verify(validarDisponibilidadeLoginUseCase, never()).executar(anyString()); // não valida login se não mudou
        verify(usuarioRepository).salvar(any());
    }

    @Test
    void deveAtualizarUsuarioComNovoLogin() {
        // Arrange
        Long idUsuario = 1L;
        String loginOriginal = "joao.silva";
        String novoLogin = "joao.santos";
        String senhaOriginal = "senhaEncriptada123";

        Usuario usuarioAtual = Usuario.builder()
                .id(idUsuario)
                .nome("João Silva")
                .email("joao@email.com")
                .login(loginOriginal)
                .senha(senhaOriginal)
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .build();

        Usuario usuarioAtualizado = Usuario.builder()
                .nome("João Santos")
                .email("joao.santos@email.com")
                .login(novoLogin) // login diferente
                .tipoUsuario(TipoUsuario.CLIENTE)
                .build();

        Usuario usuarioSalvo = Usuario.builder()
                .id(idUsuario)
                .nome("João Santos")
                .email("joao.santos@email.com")
                .login(novoLogin)
                .senha(senhaOriginal)
                .tipoUsuario(TipoUsuario.CLIENTE)
                .dataUltimaAlteracao(now())
                .build();

        when(buscarUsuarioPorIdUseCase.executar(idUsuario)).thenReturn(usuarioAtual);
        when(usuarioRepository.salvar(any())).thenReturn(usuarioSalvo);

        // Act
        Usuario resultado = useCase.executar(idUsuario, usuarioAtualizado);

        // Assert
        assertEquals(idUsuario, resultado.getId());
        assertEquals("João Santos", resultado.getNome());
        assertEquals(novoLogin, resultado.getLogin());
        assertEquals(senhaOriginal, resultado.getSenha()); // senha preservada
        verify(buscarUsuarioPorIdUseCase).executar(idUsuario);
        verify(validarDisponibilidadeLoginUseCase).executar(novoLogin); // valida novo login
        verify(usuarioRepository).salvar(any());
    }

    @Test
    void deveAtualizarUsuarioComEndereco() {
        // Arrange
        Long idUsuario = 1L;
        Long idEnderecoExistente = 10L;

        Endereco enderecoAtual = Endereco.builder()
                .id(idEnderecoExistente)
                .rua("Rua Antiga")
                .numero("123")
                .build();

        Usuario usuarioAtual = Usuario.builder()
                .id(idUsuario)
                .nome("João Silva")
                .login("joao.silva")
                .senha("senhaEncriptada123")
                .endereco(enderecoAtual)
                .build();

        Endereco novoEndereco = Endereco.builder()
                .rua("Rua Nova")
                .numero("456")
                .build();

        Usuario usuarioAtualizado = Usuario.builder()
                .nome("João Santos")
                .login("joao.silva") // mesmo login
                .endereco(novoEndereco)
                .build();

        Usuario usuarioSalvo = Usuario.builder()
                .id(idUsuario)
                .nome("João Santos")
                .login("joao.silva")
                .senha("senhaEncriptada123")
                .endereco(novoEndereco)
                .dataUltimaAlteracao(now())
                .build();

        when(buscarUsuarioPorIdUseCase.executar(idUsuario)).thenReturn(usuarioAtual);
        when(usuarioRepository.salvar(any())).thenReturn(usuarioSalvo);

        // Act
        Usuario resultado = useCase.executar(idUsuario, usuarioAtualizado);

        // Assert
        assertEquals(idUsuario, resultado.getId());
        assertEquals("João Santos", resultado.getNome());
        assertEquals(idEnderecoExistente, resultado.getEndereco().getId()); // ID do endereço preservado
        assertEquals("Rua Nova", resultado.getEndereco().getRua());
        assertEquals("456", resultado.getEndereco().getNumero());
        verify(buscarUsuarioPorIdUseCase).executar(idUsuario);
        verify(usuarioRepository).salvar(any());
    }
}