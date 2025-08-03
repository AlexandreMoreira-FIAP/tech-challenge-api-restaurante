package br.com.posfiap.restmanager.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RestauranteResponseDto {

    Long id;

    String nome;

    String tipoCozinha;

    String horarioFuncionamento;

    EnderecoDto endereco;

    Long idProprietario;

    String nomeProprietario;

    String emailProprietario;
}
