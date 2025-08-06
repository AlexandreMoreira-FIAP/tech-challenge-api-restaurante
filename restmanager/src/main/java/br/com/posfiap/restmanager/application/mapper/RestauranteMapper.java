package br.com.posfiap.restmanager.application.mapper;

import br.com.posfiap.restmanager.application.dto.RestauranteCreateDto;
import br.com.posfiap.restmanager.application.dto.RestauranteResponseDto;
import br.com.posfiap.restmanager.application.dto.RestauranteUpdateDto;
import br.com.posfiap.restmanager.domain.model.Restaurante;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.RestauranteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class, UsuarioMapper.class}, imports = LocalDateTime.class)
public interface RestauranteMapper {

    @Mapping(target = "dataUltimaAlteracao", expression = "java(LocalDateTime.now())")
    RestauranteEntity toEntity(Restaurante restaurante);

    Restaurante toDomain(RestauranteEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proprietario", ignore = true)
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    Restaurante mapToRestaurante(RestauranteCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proprietario", ignore = true)
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    Restaurante mapToRestaurante(RestauranteUpdateDto dto);

    @Mapping(target = "idProprietario", source = "proprietario.id")
    @Mapping(target = "nomeProprietario", source = "proprietario.nome")
    @Mapping(target = "emailProprietario", source = "proprietario.email")
    RestauranteResponseDto mapToRestauranteResponseDto(Restaurante restaurante);
}
