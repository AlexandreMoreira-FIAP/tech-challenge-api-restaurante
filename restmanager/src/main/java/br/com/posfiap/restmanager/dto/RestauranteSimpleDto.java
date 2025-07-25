package br.com.posfiap.restmanager.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RestauranteSimpleDto {

    Long id;

    String nome;

    String tipoDeCozinha;

    String login;
}