package br.com.posfiap.restmanager.application.dto;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(exclude = "senha")
public class UsuarioCreateDto {

    @NotBlank
    String nome;

    @NotBlank
    String email;

    @NotBlank
    String login;

    @NotBlank
    String senha;

    @NotNull
    TipoUsuario tipoUsuario;

    @Valid
    @NotNull
    EnderecoDto endereco;


}