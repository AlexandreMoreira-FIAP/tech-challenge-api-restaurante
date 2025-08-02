package br.com.posfiap.restmanager.application.mapper;

import br.com.posfiap.restmanager.domain.model.Endereco;
import br.com.posfiap.restmanager.domain.model.Usuario;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.EnderecoEntity;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.UsuarioEntity;
import br.com.posfiap.restmanager.application.dto.UsuarioCreateDto;
import br.com.posfiap.restmanager.application.dto.UsuarioResponseDto;
import br.com.posfiap.restmanager.application.dto.UsuarioUpdateDto;
import br.com.posfiap.restmanager.application.dto.EnderecoDto;
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

    @Mapping(target = "id", ignore = true)
    Endereco mapToEndereco(EnderecoDto enderecoDto);

    UsuarioResponseDto mapToUsuarioResponseDto(Usuario usuario);

    @Mapping(target = "dataUltimaAlteracao", expression = "java(LocalDateTime.now())")
    UsuarioEntity mapToUsuarioEntity(Usuario usuario);

    EnderecoEntity mapToEnderecoEntity(Endereco endereco);

    Usuario mapToUsuario(UsuarioEntity usuarioEntity);
}
