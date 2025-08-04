package br.com.posfiap.restmanager.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemCardapioCreateDto {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal preco;

    @NotNull
    private Boolean disponivel;

    private String caminhoFoto;

    @NotNull
    private Long idRestaurante;
}
