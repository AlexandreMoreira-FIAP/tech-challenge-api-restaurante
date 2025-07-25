package br.com.posfiap.restmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo de usuário do sistema")
public record TipoUsuarioDto(
        @Schema(description = "Código do tipo de usuário", example = "CLIENTE")
        String codigo,
        
        @Schema(description = "Descrição do tipo de usuário", example = "Cliente do restaurante")
        String descricao
) {
}