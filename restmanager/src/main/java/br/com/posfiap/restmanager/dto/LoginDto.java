package br.com.posfiap.restmanager.dto;

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