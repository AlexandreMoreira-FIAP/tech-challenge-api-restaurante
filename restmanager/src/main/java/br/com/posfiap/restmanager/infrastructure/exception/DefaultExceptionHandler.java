package br.com.posfiap.restmanager.infrastructure.exception;

import br.com.posfiap.restmanager.domain.exception.AuthenticationException;
import br.com.posfiap.restmanager.domain.exception.BusinessException;
import br.com.posfiap.restmanager.domain.exception.NotFoundException;
import br.com.posfiap.restmanager.domain.exception.UnauthorizedException;
import br.com.posfiap.restmanager.application.dto.ErroResponseDto;
import br.com.posfiap.restmanager.infrastructure.util.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

import static java.text.MessageFormat.format;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDto> handleValidationException(MethodArgumentNotValidException ex) {

        var erros = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors().forEach(e -> erros.put(e.getField(), e.getDefaultMessage()));

        var msg = format("Erro ao validar requisição. Descrição: {0}.", erros.toString());
        Logger.logError(msg, ex);

        return ResponseEntity.badRequest().body(buildErroResponseDto(msg, now()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroResponseDto> handleNotFoundException(NotFoundException ex) {
        Logger.logError(ex.getMessage(), ex);
        return ResponseEntity.status(NOT_FOUND).body(ex.getErroResponseDto());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErroResponseDto> handleBusinessException(BusinessException ex) {
        Logger.logError(ex.getMessage(), ex);
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(ex.getErroResponseDto());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErroResponseDto> handleAuthenticationException(AuthenticationException ex) {
        Logger.logError(ex.getMessage(), ex);
        return ResponseEntity.status(UNAUTHORIZED).body(ex.getErroResponseDto());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErroResponseDto> handleUnauthorizedException(UnauthorizedException ex) {
        Logger.logError(ex.getMessage(), ex);
        return ResponseEntity.status(UNAUTHORIZED).body(ex.getErroResponseDto());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDto> handleGenericException(Exception ex) {
        var msg = "Erro interno inesperado.";
        Logger.logError(msg, ex);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(buildErroResponseDto(msg, now()));
    }

    private ErroResponseDto buildErroResponseDto(String msg, LocalDateTime data) {
        return ErroResponseDto.builder()
                .msg(msg)
                .data(data)
                .build();
    }
}
