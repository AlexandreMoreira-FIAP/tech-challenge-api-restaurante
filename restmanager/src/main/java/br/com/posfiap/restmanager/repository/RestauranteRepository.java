package br.com.posfiap.restmanager.repository;

import br.com.posfiap.restmanager.domain.Restaurante;


import java.util.Optional;

public interface RestauranteRepository {

    Restaurante salvar(Restaurante restaurante);

    Optional<Restaurante> buscarPorId(Long id);

    void excluir(Long id);

    Optional<Restaurante> buscarPorLogin(String login);
}