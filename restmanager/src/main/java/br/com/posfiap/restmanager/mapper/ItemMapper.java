package br.com.posfiap.restmanager.mapper;

import br.com.posfiap.restmanager.domain.Item;
import br.com.posfiap.restmanager.dto.ItemCreateDto;
import br.com.posfiap.restmanager.dto.ItemResponseDto;
import br.com.posfiap.restmanager.dto.ItemUpdateDto;
import br.com.posfiap.restmanager.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurante", ignore = true)
    Item mapToItem(ItemCreateDto itemCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurante", ignore = true)
    Item mapToItem(ItemUpdateDto itemUpdateDto);

    ItemResponseDto mapToItemResponseDto(Item item);

    ItemEntity mapToItemEntity(Item item);

    @Mapping(target = "restaurante", ignore = true)
    Item mapToItem(ItemEntity itemEntity);
}