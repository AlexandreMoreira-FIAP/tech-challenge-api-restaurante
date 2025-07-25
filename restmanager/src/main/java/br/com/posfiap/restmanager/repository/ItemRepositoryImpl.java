package br.com.posfiap.restmanager.repository;

import br.com.posfiap.restmanager.domain.Item;
import br.com.posfiap.restmanager.mapper.ItemMapper;
import br.com.posfiap.restmanager.repository.ItemJpaRepository;
import br.com.posfiap.restmanager.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
class ItemRepositoryImpl implements ItemRepository {

    private final ItemJpaRepository itemJpaRepository;
    private final ItemMapper itemMapper;

    @Override
    public Item salvar(Item item) {

        var itemEntity = itemJpaRepository.save(itemMapper.mapToItemEntity(item));

        return itemMapper.mapToItem(itemEntity);
    }

    @Override
    public Optional<Item> buscarPorId(Long id) {

        return itemJpaRepository.findById(id)
                .map(itemMapper::mapToItem);
    }

    @Override
    public void excluir(Long id) {

        itemJpaRepository.deleteById(id);
    }

}