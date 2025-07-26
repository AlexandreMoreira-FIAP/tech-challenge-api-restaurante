package br.com.posfiap.restmanager.dto;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.infrastructure.entities.UsuarioEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class RestauranteUpdateDto {

    @NotBlank
    String nome;

    @NotBlank
    String login;

    @NotBlank
    String senha;

    @NotBlank
    String tipoDeCozinha;

    @NotBlank
    String horarioFuncionamento;

    @NotNull
    TipoUsuario tipoUsuario;

    @Valid
    @NotNull
    EnderecoDto endereco;

    @NotNull
    Long usuarioProprietarioId;

}
