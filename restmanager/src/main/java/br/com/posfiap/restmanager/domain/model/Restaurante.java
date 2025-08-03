package br.com.posfiap.restmanager.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Restaurante {

    Long id;

    String nome;

    Endereco endereco;

    String tipoCozinha;

    String horarioFuncionamento;

    Usuario proprietario;

    LocalDateTime dataUltimaAlteracao;
}
