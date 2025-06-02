package br.com.posfiap.restmanager.error;

import br.com.posfiap.restmanager.dto.ErroResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

import static br.com.posfiap.restmanager.util.Logger.logError;
import static java.text.MessageFormat.format;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
class DefaultExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErroResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        var erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(erro -> erros.put(erro.getField(), erro.getDefaultMessage()));

        var msg = format("Erro ao validar requisição. Descrição: {0}.", erros.toString());

        logError(msg, ex);
        return ResponseEntity
                .badRequest()
                .body(buildErroResponseDto(msg, now()));
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErroResponseDto> handleNotFoundException(NotFoundException ex) {

        logError(ex.getMessage(), ex);
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ex.getErroResponseDto());
    }

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<ErroResponseDto> handleBusinessException(BusinessException ex) {

        logError(ex.getMessage(), ex);
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .body(ex.getErroResponseDto());
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ErroResponseDto> handleAuthenticationException(AuthenticationException ex) {

        logError(ex.getMessage(), ex);
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ex.getErroResponseDto());
    }

    private ErroResponseDto buildErroResponseDto(String msg, LocalDateTime data) {

        return ErroResponseDto.builder()
                .msg(msg)
                .data(now())
                .build();
    }
}