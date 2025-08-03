package br.com.posfiap.restmanager.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioComRestaurantesDto {

    Long id;

    String nome;

    String email;

    EnderecoDto endereco;

    List<RestauranteResumoDto> restaurantes;
}
