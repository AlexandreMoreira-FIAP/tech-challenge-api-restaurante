package br.com.posfiap.restmanager.dto;

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