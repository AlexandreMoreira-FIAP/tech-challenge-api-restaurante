package br.com.posfiap.restmanager.error;

import br.com.posfiap.restmanager.dto.ErroResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultExceptionHandlerTest {

    @InjectMocks
    private DefaultExceptionHandler defaultExceptionHandler;

    private NotFoundException notFoundException;
    private BusinessException businessException;
    private AuthenticationException authenticationException;
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @BeforeEach
    void setUp() {
        notFoundException = new NotFoundException("Usuário não encontrado");
        businessException = new BusinessException("Login já está em uso");
        authenticationException = new AuthenticationException("Credenciais inválidas");
    }

    @Test
    void deveHandleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("usuario", "nome", "Nome é obrigatório");
        FieldError fieldError2 = new FieldError("usuario", "email", "Email deve ter formato válido");
        
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));
        
        methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<ErroResponseDto> response = defaultExceptionHandler
                .handleMethodArgumentNotValidException(methodArgumentNotValidException);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMsg().contains("Erro ao validar requisição"));
        assertTrue(response.getBody().getMsg().contains("nome=Nome é obrigatório"));
        assertTrue(response.getBody().getMsg().contains("email=Email deve ter formato válido"));
        assertNotNull(response.getBody().getData());
    }

    @Test
    void deveHandleNotFoundException() {
        ResponseEntity<ErroResponseDto> response = defaultExceptionHandler
                .handleNotFoundException(notFoundException);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuário não encontrado", response.getBody().getMsg());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void deveHandleBusinessException() {
        ResponseEntity<ErroResponseDto> response = defaultExceptionHandler
                .handleBusinessException(businessException);

        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Login já está em uso", response.getBody().getMsg());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void deveHandleAuthenticationException() {
        ResponseEntity<ErroResponseDto> response = defaultExceptionHandler
                .handleAuthenticationException(authenticationException);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Credenciais inválidas", response.getBody().getMsg());
        assertNotNull(response.getBody().getData());
    }
}