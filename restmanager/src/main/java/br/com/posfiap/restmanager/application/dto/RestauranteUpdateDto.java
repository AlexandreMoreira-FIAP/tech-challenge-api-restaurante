package br.com.posfiap.restmanager.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RestauranteUpdateDto {

    String nome;

    String tipoCozinha;

    String horarioFuncionamento;

    EnderecoDto endereco;
}
