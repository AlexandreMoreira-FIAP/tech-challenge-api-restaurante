package br.com.posfiap.restmanager.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TipoUsuario {

    Long id;

    String nome;

    LocalDateTime dataUltimaAlteracao;
}