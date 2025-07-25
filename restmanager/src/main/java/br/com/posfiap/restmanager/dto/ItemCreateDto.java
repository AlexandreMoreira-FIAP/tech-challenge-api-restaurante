package br.com.posfiap.restmanager.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class ItemCreateDto {

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

}
