package br.com.posfiap.restmanager.domain.model;

import lombok.Builder;
import lombok.Data;

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
}