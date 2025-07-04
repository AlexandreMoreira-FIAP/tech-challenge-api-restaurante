package br.com.posfiap.restmanager.dto;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UsuarioUpdateDto {

    @NotBlank
    String nome;

    @NotBlank
    String email;

    @NotBlank
    String login;

    @NotNull
    TipoUsuario tipoUsuario;

    @Valid
    @NotNull
    EnderecoDto endereco;
}