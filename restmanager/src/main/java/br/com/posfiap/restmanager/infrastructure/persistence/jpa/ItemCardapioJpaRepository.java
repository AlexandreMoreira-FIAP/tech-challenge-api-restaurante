package br.com.posfiap.restmanager.infrastructure.persistence.jpa;

import br.com.posfiap.restmanager.infrastructure.persistence.entity.ItemCardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCardapioJpaRepository extends JpaRepository<ItemCardapioEntity, Long> {
    List<ItemCardapioEntity> findByRestauranteId(Long restauranteId);
}
