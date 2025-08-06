package br.com.posfiap.restmanager.domain.repository;

import br.com.posfiap.restmanager.domain.model.ItemCardapio;

import java.util.List;
import java.util.Optional;

public interface ItemCardapioRepository {
    ItemCardapio salvar(ItemCardapio itemCardapio);

    Optional<ItemCardapio> buscarPorId(Long id);

    List<ItemCardapio> buscarPorRestauranteId(Long restauranteId);

    void deletarPorId(Long id);

    boolean existePorId(Long id);
}
