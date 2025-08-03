package br.com.posfiap.restmanager.application.dto;

import lombok.Data;

@Data
public class RestauranteResumoDto {

    Long id;

    String nome;

    String tipoCozinha;

    String horarioFuncionamento;

    EnderecoDto endereco;
}
