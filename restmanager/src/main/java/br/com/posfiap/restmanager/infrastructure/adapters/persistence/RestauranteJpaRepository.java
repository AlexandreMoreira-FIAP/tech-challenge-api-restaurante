package br.com.posfiap.restmanager.infrastructure.adapters.persistence;

import br.com.posfiap.restmanager.infrastructure.entities.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestauranteJpaRepository extends JpaRepository<RestauranteEntity, Long> {

    Optional<RestauranteEntity> findByLogin(String login);
}


