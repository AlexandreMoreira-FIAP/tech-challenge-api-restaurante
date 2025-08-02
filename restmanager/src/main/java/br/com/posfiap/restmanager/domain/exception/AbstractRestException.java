package br.com.posfiap.restmanager.domain.exception;

import br.com.posfiap.restmanager.application.dto.ErroResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class AbstractRestException extends RuntimeException {

    private final ErroResponseDto erroResponseDto;

    public AbstractRestException(String msg) {
        super(msg);
        this.erroResponseDto = ErroResponseDto.builder()
                .msg(msg)
                .data(LocalDateTime.now())
                .build();
    }
}
