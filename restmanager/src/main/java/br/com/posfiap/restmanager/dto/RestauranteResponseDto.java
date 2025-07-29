package br.com.posfiap.restmanager.dto;

import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.infrastructure.entities.UsuarioEntity;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class RestauranteResponseDto {

    Long id;

    String nome;

    String tipoDeCozinha;

    String horarioFuncionamento;

    String login;

    String senha;

    TipoUsuario tipoUsuario;

    Endereco endereco;

    LocalDateTime dataUltimaAlteracao;
    
    List<ItemResponseDto> itens;

    List<UsuarioSimpleDto> usuarios;

    UsuarioSimpleDto usuarioProprietario;

}
