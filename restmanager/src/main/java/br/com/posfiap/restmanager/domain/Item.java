package br.com.posfiap.restmanager.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Item {

    Long id;

    String nome;

    String descricao;

    BigDecimal preco;

    Boolean somenteConsumoLocal;

    String foto; // Caminho ou URL da foto

}
