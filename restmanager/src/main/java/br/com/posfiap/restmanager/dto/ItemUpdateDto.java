package br.com.posfiap.restmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ItemUpdateDto {

    @NotBlank
    String nome;

    @NotBlank
    String descricao;

    @NotNull
    BigDecimal preco;

    @NotNull
    Boolean somenteConsumoLocal;

    @NotBlank
    String foto; // Caminho ou URL da foto

    @NotNull
    Long restauranteId;

}
