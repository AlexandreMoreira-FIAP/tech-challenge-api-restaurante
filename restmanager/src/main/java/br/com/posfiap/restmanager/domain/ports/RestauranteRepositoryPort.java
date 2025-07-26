package br.com.posfiap.restmanager.domain.ports;

import br.com.posfiap.restmanager.domain.entities.Restaurante;

import java.util.Optional;

public interface RestauranteRepositoryPort {
    Restaurante salvar(Restaurante restaurante);
    Optional<Restaurante> buscarPorId(Long id);
    Optional<Restaurante> buscarPorLogin(String login);
    void excluir(Long id);
}