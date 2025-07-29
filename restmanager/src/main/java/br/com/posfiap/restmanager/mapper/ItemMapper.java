package br.com.posfiap.restmanager.mapper;

<<<<<<< HEAD
import br.com.posfiap.restmanager.domain.entities.Endereco;
import br.com.posfiap.restmanager.domain.entities.Item;
import br.com.posfiap.restmanager.dto.EnderecoDto;
import br.com.posfiap.restmanager.dto.ItemCreateDto;
import br.com.posfiap.restmanager.dto.ItemResponseDto;
import br.com.posfiap.restmanager.dto.ItemUpdateDto;
import br.com.posfiap.restmanager.infrastructure.entities.EnderecoEntity;
import br.com.posfiap.restmanager.infrastructure.entities.ItemEntity;
import br.com.posfiap.restmanager.infrastructure.entities.RestauranteEntity;
import br.com.posfiap.restmanager.infrastructure.adapters.persistence.RestauranteJpaRepository;
=======
import br.com.posfiap.restmanager.domain.Item;
import br.com.posfiap.restmanager.dto.ItemCreateDto;
import br.com.posfiap.restmanager.dto.ItemResponseDto;
import br.com.posfiap.restmanager.dto.ItemUpdateDto;
import br.com.posfiap.restmanager.entity.ItemEntity;
>>>>>>> 8bf6e47a7f610d7bf2ccf011f2f45fae6aadb6ed
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public abstract class ItemMapper {

    @Autowired
    protected RestauranteJpaRepository restauranteJpaRepository;

    @Mapping(target = "id", ignore = true)
<<<<<<< HEAD
    public abstract Item mapToItem(ItemCreateDto itemCreateDto);

    @Mapping(target = "id", ignore = true)
    public abstract Item mapToItem(ItemUpdateDto itemUpdateDto);
=======
    @Mapping(target = "restaurante", ignore = true)
    Item mapToItem(ItemCreateDto itemCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurante", ignore = true)
    Item mapToItem(ItemUpdateDto itemUpdateDto);
>>>>>>> 8bf6e47a7f610d7bf2ccf011f2f45fae6aadb6ed

    @Mapping(target = "restauranteId", source = "restaurante.id")
    @Mapping(target = "restaurante", source = "restaurante")
    public abstract ItemResponseDto mapToItemResponseDto(ItemEntity itemEntity);

    @Mapping(target = "restaurante", ignore = true)
    public abstract ItemResponseDto mapToItemResponseDto(Item item);

<<<<<<< HEAD
    @Mapping(target = "id", ignore = true)
    public abstract Endereco mapToEndereco(EnderecoDto enderecoDto);

    public ItemEntity mapToItemEntity(Item item) {
        if (item == null) {
            return null;
        }
        
        RestauranteEntity restaurante = null;
        if (item.getRestauranteId() != null) {
            restaurante = restauranteJpaRepository.findById(item.getRestauranteId()).orElse(null);
        }

        return ItemEntity.builder()
                .id(item.getId())
                .nome(item.getNome())
                .descricao(item.getDescricao())
                .preco(item.getPreco())
                .somenteConsumoLocal(item.getSomenteConsumoLocal())
                .foto(item.getFoto())
                .restaurante(restaurante)
                .build();
    }

    @Mapping(target = "restauranteId", source = "restaurante.id")
    public abstract Item mapToItem(ItemEntity itemEntity);

    public abstract EnderecoEntity mapToEnderecoEntity(Endereco endereco);
=======
    ItemEntity mapToItemEntity(Item item);

    @Mapping(target = "restaurante", ignore = true)
    Item mapToItem(ItemEntity itemEntity);
>>>>>>> 8bf6e47a7f610d7bf2ccf011f2f45fae6aadb6ed
}