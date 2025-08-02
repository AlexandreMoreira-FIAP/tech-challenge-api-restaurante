package br.com.posfiap.restmanager.application.dto;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class UsuarioResponseDto {

    Long id;

    String nome;

    String email;

    String login;

    TipoUsuario tipoUsuario;

    EnderecoDto endereco;

    LocalDateTime dataUltimaAlteracao;

}