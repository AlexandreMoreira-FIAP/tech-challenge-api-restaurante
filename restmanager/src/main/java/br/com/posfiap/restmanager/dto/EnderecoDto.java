package br.com.posfiap.restmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EnderecoDto {

    @NotBlank
    String rua;

    @NotBlank
    String numero;

    String complemento;

    @NotBlank
    String bairro;

    @NotBlank
    String cidade;

    @NotBlank
    String cep;

    @NotBlank
    String estado;

    @NotBlank
    String pais;
}