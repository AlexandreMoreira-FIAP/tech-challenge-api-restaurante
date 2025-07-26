package br.com.posfiap.restmanager.domain.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Endereco {

    private Long id;

    private String rua;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String cep;

    private String estado;

    private String pais;
}