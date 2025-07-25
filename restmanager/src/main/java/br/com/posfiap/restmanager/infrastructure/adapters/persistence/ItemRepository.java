package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Item;

import java.util.Optional;

public interface ItemRepository {

    Item salvar(Item item);

    Optional<Item> buscarPorId(Long id);

    void excluir(Long id);

}


