package br.com.posfiap.restmanager.error;

import br.com.posfiap.restmanager.dto.ErroResponseDto;
import lombok.Getter;

import static java.time.LocalDateTime.now;

@Getter
public class BusinessException extends RuntimeException {

    private final ErroResponseDto erroResponseDto;

    public BusinessException(String msg) {

        super(msg);
        this.erroResponseDto = ErroResponseDto.builder()
                .msg(msg)
                .data(now())
                .build();
    }
}