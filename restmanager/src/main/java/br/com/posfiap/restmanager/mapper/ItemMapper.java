package br.com.posfiap.restmanager.mapper;

import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.entities.Item;
import br.com.posfiap.restmanager.dto.EnderecoDto;
import br.com.posfiap.restmanager.dto.ItemCreateDto;
import br.com.posfiap.restmanager.dto.ItemResponseDto;
import br.com.posfiap.restmanager.dto.ItemUpdateDto;
import br.com.posfiap.restmanager.infrastructure.entities.EnderecoEntity;
import br.com.posfiap.restmanager.infrastructure.entities.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(target = "id", ignore = true)
    Item mapToItem(ItemCreateDto itemCreateDto);

    @Mapping(target = "id", ignore = true)
    Item mapToItem(ItemUpdateDto itemUpdateDto);

    ItemResponseDto mapToItemResponseDto(Item item);

    @Mapping(target = "id", ignore = true)
    Endereco mapToEndereco(EnderecoDto enderecoDto);

    ItemEntity mapToItemEntity(Item item);

    Item mapToItem(ItemEntity itemEntity);

    EnderecoEntity mapToEnderecoEntity(Endereco endereco);
}