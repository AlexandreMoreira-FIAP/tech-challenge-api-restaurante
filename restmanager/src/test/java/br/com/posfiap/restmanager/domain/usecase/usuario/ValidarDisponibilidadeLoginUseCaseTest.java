package br.com.posfiap.restmanager.domain.usecase.usuario;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.domain.exception.BusinessException;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidarDisponibilidadeLoginUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private ValidarDisponibilidadeLoginUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        useCase = new ValidarDisponibilidadeLoginUseCase(usuarioRepository);
    }

    @Test
    void deveValidarLoginDisponivelComSucesso() {
        // Arrange
        String login = "login.disponivel";

        when(usuarioRepository.buscarPorLogin(login)).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> useCase.executar(login));
        verify(usuarioRepository).buscarPorLogin(login);
    }

    @Test
    void deveLancarExcecaoQuandoLoginJaExiste() {
        // Arrange
        String login = "login.existente";

        Usuario usuarioExistente = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .login(login)
                .senha("senhaEncriptada")
                .tipoUsuario(TipoUsuario.PROPRIETARIO)
                .dataUltimaAlteracao(now())
                .build();

        when(usuarioRepository.buscarPorLogin(login)).thenReturn(Optional.of(usuarioExistente));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> useCase.executar(login));
        
        assertTrue(exception.getMessage().contains("não está disponível"));
        assertTrue(exception.getMessage().contains(login));
        verify(usuarioRepository).buscarPorLogin(login);
    }
}