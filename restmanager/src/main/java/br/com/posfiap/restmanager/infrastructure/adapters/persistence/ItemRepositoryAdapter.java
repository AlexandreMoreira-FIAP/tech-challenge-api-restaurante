package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Item;
import br.com.posfiap.restmanager.domain.ports.ItemRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemRepositoryAdapter implements ItemRepositoryPort {

    private final ItemRepository itemRepository;

    @Override
    public Item salvar(Item item) {
        return itemRepository.salvar(item);
    }

    @Override
    public Optional<Item> buscarPorId(Long id) {
        return itemRepository.buscarPorId(id);
    }

    @Override
    public void excluir(Long id) {
        itemRepository.excluir(id);
    }
}