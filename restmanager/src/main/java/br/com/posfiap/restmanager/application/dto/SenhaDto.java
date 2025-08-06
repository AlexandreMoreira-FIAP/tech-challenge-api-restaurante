package br.com.posfiap.restmanager.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SenhaDto {

    @NotBlank
    String senhaAtual;

    @NotBlank
    String novaSenha;
}