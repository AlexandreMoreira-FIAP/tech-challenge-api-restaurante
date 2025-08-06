package br.com.posfiap.restmanager.domain.repository;

import br.com.posfiap.restmanager.domain.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteRepository {

    Restaurante salvar(Restaurante restaurante);

    Optional<Restaurante> buscarPorId(Long id);

    List<Restaurante> listarPorProprietario(Long idProprietario);

    void deletarPorId(Long id);
}
