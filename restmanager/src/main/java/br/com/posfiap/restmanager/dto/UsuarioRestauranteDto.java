package br.com.posfiap.restmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UsuarioRestauranteDto {

    @NotNull
    Long usuarioId;

    @NotNull
    Long restauranteId;
}