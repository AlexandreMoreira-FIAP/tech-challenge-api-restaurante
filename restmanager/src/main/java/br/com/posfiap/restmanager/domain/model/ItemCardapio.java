package br.com.posfiap.restmanager.domain.model;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

import java.time.LocalDateTime;

@Data
@Builder
public class ItemCardapio {

    Long id;

    String nome;

    String descricao;

    BigDecimal preco;

    Boolean disponivel;

    String caminhoFoto;

    Restaurante restaurante;

    LocalDateTime dataUltimaAlteracao;
}
