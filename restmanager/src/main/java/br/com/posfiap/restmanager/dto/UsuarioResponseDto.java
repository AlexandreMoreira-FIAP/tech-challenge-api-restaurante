package br.com.posfiap.restmanager.dto;

import br.com.posfiap.restmanager.domain.enums.TipoUsuario;
<<<<<<< HEAD
import br.com.posfiap.restmanager.infrastructure.entities.RestauranteEntity;
=======
>>>>>>> 8bf6e47a7f610d7bf2ccf011f2f45fae6aadb6ed
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

<<<<<<< HEAD
    List<RestauranteSimpleDto> restaurantes;
=======
    List<RestauranteResponseDto> restaurantes;
>>>>>>> 8bf6e47a7f610d7bf2ccf011f2f45fae6aadb6ed
}