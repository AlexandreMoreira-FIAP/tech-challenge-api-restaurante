package br.com.posfiap.restmanager.domain.ports;

import br.com.posfiap.restmanager.domain.entities.TipoUsuarioEntity;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioRepositoryPort {
    List<TipoUsuarioEntity> findAll();
    Optional<TipoUsuarioEntity> findById(Long id);
    Optional<TipoUsuarioEntity> findByCodigo(String codigo);
    TipoUsuarioEntity save(TipoUsuarioEntity tipoUsuario);
    void deleteById(Long id);
    boolean existsByCodigo(String codigo);
}