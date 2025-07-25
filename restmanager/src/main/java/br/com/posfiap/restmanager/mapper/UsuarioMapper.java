package br.com.posfiap.restmanager.mapper;

import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.entities.Usuario;
import br.com.posfiap.restmanager.dto.EnderecoDto;
import br.com.posfiap.restmanager.dto.UsuarioCreateDto;
import br.com.posfiap.restmanager.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.dto.UsuarioUpdateDto;
import br.com.posfiap.restmanager.dto.RestauranteSimpleDto;
import br.com.posfiap.restmanager.infrastructure.entities.RestauranteEntity;
import br.com.posfiap.restmanager.infrastructure.entities.EnderecoEntity;
import br.com.posfiap.restmanager.infrastructure.entities.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    Usuario mapToUsuario(UsuarioCreateDto usuarioCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    Usuario mapToUsuario(UsuarioUpdateDto usuarioUpdateDto);

    UsuarioResponseDto mapToUsuarioResponseDto(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    Endereco mapToEndereco(EnderecoDto enderecoDto);

    @Mapping(target = "dataUltimaAlteracao", expression = "java(LocalDateTime.now())")
    UsuarioEntity mapToUsuarioEntity(Usuario usuario);

    Usuario mapToUsuario(UsuarioEntity usuarioEntity);

    EnderecoEntity mapToEnderecoEntity(Endereco endereco);

    RestauranteSimpleDto mapToRestauranteSimpleDto(RestauranteEntity restauranteEntity);
}