package br.com.posfiap.restmanager.infrastructure.persistence.jpa;

import br.com.posfiap.restmanager.infrastructure.persistence.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteJpaRepository extends JpaRepository<RestauranteEntity, Long> {
}
