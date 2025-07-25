package br.com.posfiap.restmanager.infrastructure.adapters.web;

import br.com.posfiap.restmanager.dto.LoginDto;
import br.com.posfiap.restmanager.service.AutenticacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticacaoControllerTest {

    @Mock
    private AutenticacaoService autenticacaoService;

    @InjectMocks
    private AutenticacaoController autenticacaoController;

    private LoginDto loginDto;

    @BeforeEach
    void setUp() {
        loginDto = LoginDto.builder()
                .login("usuario@test.com")
                .senha("senha123")
                .build();
    }

    @Test
    void deveAutenticarLoginComSucesso() {
        doNothing().when(autenticacaoService).autenticarLogin("usuario@test.com", "senha123");

        autenticacaoController.autenticarLogin(loginDto);

        verify(autenticacaoService).autenticarLogin("usuario@test.com", "senha123");
    }
}