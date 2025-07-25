package br.com.posfiap.restmanager.domain.ports;

import br.com.posfiap.restmanager.domain.entities.Item;

import java.util.Optional;

public interface ItemRepositoryPort {
    Item salvar(Item item);
    Optional<Item> buscarPorId(Long id);
    void excluir(Long id);
}