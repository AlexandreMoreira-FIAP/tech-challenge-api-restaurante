package br.com.posfiap.restmanager.domain.entities;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Item {

    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean somenteConsumoLocal;

    private String foto; // Caminho ou URL da foto

    private Long restauranteId;

}
