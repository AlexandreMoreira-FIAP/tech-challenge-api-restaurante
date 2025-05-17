package br.com.posfiap.restmanager.domain;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

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