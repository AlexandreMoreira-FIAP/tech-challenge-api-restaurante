package br.com.posfiap.restmanager.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ErroResponseDto {

    String msg;
    LocalDateTime data;
}