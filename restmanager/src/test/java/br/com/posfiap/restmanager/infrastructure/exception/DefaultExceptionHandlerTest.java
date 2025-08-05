package br.com.posfiap.restmanager.infrastructure.exception;

import br.com.posfiap.restmanager.application.dto.ErroResponseDto;
import br.com.posfiap.restmanager.domain.exception.AuthenticationException;
import br.com.posfiap.restmanager.domain.exception.BusinessException;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

class DefaultExceptionHandlerTest {

    @InjectMocks
    private DefaultExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveManipularMethodArgumentNotValidException() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("usuario", "nome", "Nome é obrigatório");
        FieldError fieldError2 = new FieldError("usuario", "email", "Email é obrigatório");
        
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));

        // Act
        ResponseEntity<ErroResponseDto> response = exceptionHandler.handleValidationException(ex);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMsg().contains("Erro ao validar requisição"));
        assertNotNull(response.getBody().getData());
    }

    @Test
    void deveManipularNotFoundException() {
        // Arrange
        NotFoundException ex = new NotFoundException("Recurso não encontrado");

        // Act
        ResponseEntity<ErroResponseDto> response = exceptionHandler.handleNotFoundException(ex);

        // Assert
        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Recurso não encontrado", response.getBody().getMsg());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void deveManipularBusinessException() {
        // Arrange
        BusinessException ex = new BusinessException("Erro de negócio");

        // Act
        ResponseEntity<ErroResponseDto> response = exceptionHandler.handleBusinessException(ex);

        // Assert
        assertEquals(UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro de negócio", response.getBody().getMsg());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void deveManipularAuthenticationException() {
        // Arrange
        AuthenticationException ex = new AuthenticationException("Erro de autenticação");

        // Act
        ResponseEntity<ErroResponseDto> response = exceptionHandler.handleAuthenticationException(ex);

        // Assert
        assertEquals(UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro de autenticação", response.getBody().getMsg());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void deveManipularUnauthorizedException() {
        // Arrange
        UnauthorizedException ex = new UnauthorizedException("Erro de autorização");

        // Act
        ResponseEntity<ErroResponseDto> response = exceptionHandler.handleUnauthorizedException(ex);

        // Assert
        assertEquals(UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro de autorização", response.getBody().getMsg());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void deveManipularGenericException() {
        // Arrange
        Exception ex = new RuntimeException("Erro inesperado");

        // Act
        ResponseEntity<ErroResponseDto> response = exceptionHandler.handleGenericException(ex);

        // Assert
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro interno inesperado.", response.getBody().getMsg());
        assertNotNull(response.getBody().getData());
    }
}