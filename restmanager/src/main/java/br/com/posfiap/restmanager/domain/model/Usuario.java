package br.com.posfiap.restmanager.domain.model;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Usuario {

    Long id;

    String nome;

    String email;

    String login;

    String senha;

    TipoUsuario tipoUsuario;

    Endereco endereco;

    LocalDateTime dataUltimaAlteracao;

}