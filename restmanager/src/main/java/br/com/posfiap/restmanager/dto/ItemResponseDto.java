package br.com.posfiap.restmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ItemResponseDto {

    Long id;

    String nome;

    String descricao;

    BigDecimal preco;

    Boolean somenteConsumoLocal;

    String foto; // Caminho ou URL da foto

    Long restauranteId;

    RestauranteSimpleDto restaurante;
}
