package br.com.posfiap.restmanager.domain;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.entity.UsuarioEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Restaurante {

    Long id;

    String nome;

    String tipoDeCozinha;

    String login;

    String senha;

    TipoUsuario tipoUsuario;

    Endereco endereco;

    LocalDateTime dataUltimaAlteracao;

    private List<UsuarioEntity> usuarios;
}