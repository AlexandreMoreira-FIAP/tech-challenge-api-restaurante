package br.com.posfiap.restmanager.application.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ItemCardapioResponseDto {

    Long id;

    String nome;

    String descricao;

    BigDecimal preco;

    Boolean disponivel;

    String caminhoFoto;

    Long idRestaurante;

    String nomeRestaurante;
}
