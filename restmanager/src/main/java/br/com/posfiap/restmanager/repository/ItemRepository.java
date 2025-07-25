package br.com.posfiap.restmanager.repository;

import br.com.posfiap.restmanager.domain.Item;

import java.util.Optional;

public interface ItemRepository {

    Item salvar(Item item);

    Optional<Item> buscarPorId(Long id);

    void excluir(Long id);

}


