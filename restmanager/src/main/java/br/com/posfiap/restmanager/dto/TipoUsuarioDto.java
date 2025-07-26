package br.com.posfiap.restmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Tipo de usuário do sistema")
public record TipoUsuarioDto(
        @Schema(description = "ID do tipo de usuário", example = "1")
        Long id,
        
        @NotBlank
        @Schema(description = "Código do tipo de usuário", example = "CLIENTE")
        String codigo,
        
        @NotBlank
        @Schema(description = "Descrição do tipo de usuário", example = "Cliente do restaurante")
        String descricao
) {
}