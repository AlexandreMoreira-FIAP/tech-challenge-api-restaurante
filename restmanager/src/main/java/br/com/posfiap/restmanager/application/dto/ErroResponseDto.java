package br.com.posfiap.restmanager.application.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ErroResponseDto {

    String msg;
    LocalDateTime data;
}
