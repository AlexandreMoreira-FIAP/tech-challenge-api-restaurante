package br.com.posfiap.restmanager.mapper;

import br.com.posfiap.restmanager.domain.Endereco;
import br.com.posfiap.restmanager.domain.Restaurante;
import br.com.posfiap.restmanager.dto.EnderecoDto;
import br.com.posfiap.restmanager.dto.RestauranteCreateDto;
import br.com.posfiap.restmanager.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.dto.RestauranteUpdateDto;
import br.com.posfiap.restmanager.dto.UsuarioSimpleDto;
import br.com.posfiap.restmanager.entity.UsuarioEntity;
import br.com.posfiap.restmanager.entity.EnderecoEntity;
import br.com.posfiap.restmanager.entity.RestauranteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface RestauranteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    Restaurante mapToRestaurante(RestauranteCreateDto restauranteCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    Restaurante mapToRestaurante(RestauranteUpdateDto restauranteUpdateDto);

    RestauranteResponseDto mapToRestauranteResponseDto(Restaurante restaurante);

    @Mapping(target = "id", ignore = true)
    Endereco mapToEndereco(EnderecoDto enderecoDto);

    @Mapping(target = "dataUltimaAlteracao", expression = "java(LocalDateTime.now())")
    RestauranteEntity mapToRestauranteEntity(Restaurante restaurante);

    Restaurante mapToRestaurante(RestauranteEntity restauranteEntity);

    EnderecoEntity mapToEnderecoEntity(Endereco endereco);

    UsuarioSimpleDto mapToUsuarioSimpleDto(UsuarioEntity usuarioEntity);
}