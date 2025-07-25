package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.domain.entities.Restaurante;


import java.util.Optional;

public interface RestauranteRepository {

    Restaurante salvar(Restaurante restaurante);

    Optional<Restaurante> buscarPorId(Long id);

    void excluir(Long id);

    Optional<Restaurante> buscarPorLogin(String login);
}