package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarUsuarioPorIdUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private BuscarUsuarioPorIdUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        useCase = new BuscarUsuarioPorIdUseCase(usuarioRepository);
    }

    @Test
    void deveBuscarUsuarioPorIdComSucesso() {
        // Arrange
        Long idUsuario = 1L;

        Usuario usuario = Usuario.builder()
                .id(idUsuario)
                .nome("João Silva")
                .email("joao@email.com")
                .login("joao.silva")
                .senha("senhaEncriptada")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .dataUltimaAlteracao(now())
                .build();

        when(usuarioRepository.buscarPorId(idUsuario)).thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado = useCase.executar(idUsuario);

        // Assert
        assertEquals(idUsuario, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
        assertEquals("joao.silva", resultado.getLogin());
        assertEquals(TipoUsuario.PROPRIETARIO, resultado.getTipoUsuario());
        verify(usuarioRepository).buscarPorId(idUsuario);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        Long idUsuario = 1L;

        when(usuarioRepository.buscarPorId(idUsuario)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, 
            () -> useCase.executar(idUsuario));
        
        assertTrue(exception.getMessage().contains("Usuário não encontrado"));
        assertTrue(exception.getMessage().contains(idUsuario.toString()));
        verify(usuarioRepository).buscarPorId(idUsuario);
    }
}