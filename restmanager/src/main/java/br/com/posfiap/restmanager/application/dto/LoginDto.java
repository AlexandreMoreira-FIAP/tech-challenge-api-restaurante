package br.com.posfiap.restmanager.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginDto {

    @NotBlank
    String login;

    @NotBlank
    String senha;
}