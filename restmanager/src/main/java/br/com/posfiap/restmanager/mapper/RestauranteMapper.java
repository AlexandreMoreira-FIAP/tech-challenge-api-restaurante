package br.com.posfiap.restmanager.mapper;

import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.entities.Restaurante;
import br.com.posfiap.restmanager.dto.EnderecoDto;
import br.com.posfiap.restmanager.dto.RestauranteCreateDto;
import br.com.posfiap.restmanager.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.dto.RestauranteUpdateDto;
import br.com.posfiap.restmanager.dto.UsuarioSimpleDto;
import br.com.posfiap.restmanager.infrastructure.entities.UsuarioEntity;
import br.com.posfiap.restmanager.infrastructure.entities.EnderecoEntity;
import br.com.posfiap.restmanager.infrastructure.entities.RestauranteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class, uses = ItemMapper.class)
public interface RestauranteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "itens", ignore = true)
    Restaurante mapToRestaurante(RestauranteCreateDto restauranteCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "itens", ignore = true)
    Restaurante mapToRestaurante(RestauranteUpdateDto restauranteUpdateDto);

    RestauranteResponseDto mapToRestauranteResponseDto(Restaurante restaurante);

    @Mapping(target = "dataUltimaAlteracao", expression = "java(LocalDateTime.now())")
    @Mapping(target = "itens", ignore = true)
    RestauranteEntity mapToRestauranteEntity(Restaurante restaurante);

    @Mapping(target = "usuarios", ignore = true)
    Restaurante mapToRestaurante(RestauranteEntity restauranteEntity);

    @Mapping(target = "id", ignore = true)
    Endereco mapToEndereco(EnderecoDto enderecoDto);

    EnderecoEntity mapToEnderecoEntity(Endereco endereco);

    UsuarioSimpleDto mapToUsuarioSimpleDto(UsuarioEntity usuarioEntity);
}