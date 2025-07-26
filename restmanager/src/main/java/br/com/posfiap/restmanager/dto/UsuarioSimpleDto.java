package br.com.posfiap.restmanager.dto;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UsuarioSimpleDto {

    Long id;

    String nome;

    String email;

    String login;

    TipoUsuario tipoUsuario;
}