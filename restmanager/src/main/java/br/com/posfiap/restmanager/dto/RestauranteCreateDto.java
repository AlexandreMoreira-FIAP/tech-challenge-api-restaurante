package br.com.posfiap.restmanager.dto;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
import br.com.posfiap.restmanager.infrastructure.entities.UsuarioEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
@Builder
@ToString(exclude = "senha")
public class RestauranteCreateDto {

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

<<<<<<< HEAD
    @NotNull
    Long usuarioProprietarioId;
=======
    List<Long> usuarioIds;
>>>>>>> 8bf6e47a7f610d7bf2ccf011f2f45fae6aadb6ed
}
