package br.com.posfiap.restmanager.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class Endereco {

    Long id;

    String rua;

    String numero;

    String complemento;

    String bairro;

    String cidade;

    String cep;

    String estado;

    String pais;

    Long id_usuario;
}