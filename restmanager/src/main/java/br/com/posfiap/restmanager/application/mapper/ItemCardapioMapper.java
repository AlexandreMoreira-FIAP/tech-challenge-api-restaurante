package br.com.posfiap.restmanager.application.mapper;

import br.com.posfiap.restmanager.application.dto.ItemCardapioCreateDto;
import br.com.posfiap.restmanager.application.dto.ItemCardapioResponseDto;
import br.com.posfiap.restmanager.application.dto.ItemCardapioUpdateDto;
import br.com.posfiap.restmanager.domain.model.ItemCardapio;
import br.com.posfiap.restmanager.infrastructure.persistence.entity.ItemCardapioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {RestauranteMapper.class}, imports = LocalDateTime.class)
public interface ItemCardapioMapper {

    @Mapping(target = "dataUltimaAlteracao", expression = "java(LocalDateTime.now())")
    ItemCardapioEntity toEntity(ItemCardapio itemCardapio);

    ItemCardapio toDomain(ItemCardapioEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurante", ignore = true)
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    ItemCardapio mapToItemCardapio(ItemCardapioCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurante", ignore = true)
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    ItemCardapio mapToItemCardapio(ItemCardapioUpdateDto dto);

    @Mapping(target = "idRestaurante", source = "restaurante.id")
    @Mapping(target = "nomeRestaurante", source = "restaurante.nome")
    ItemCardapioResponseDto mapToItemCardapioResponseDto(ItemCardapio itemCardapio);
}
