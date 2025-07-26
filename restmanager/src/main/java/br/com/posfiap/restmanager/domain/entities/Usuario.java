package br.com.posfiap.restmanager.domain.entities;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Usuario {

    private Long id;

    private String nome;

    private String email;

    private String login;

    private String senha;

    private TipoUsuario tipoUsuario;

    private Endereco endereco;

    private LocalDateTime dataUltimaAlteracao;
}