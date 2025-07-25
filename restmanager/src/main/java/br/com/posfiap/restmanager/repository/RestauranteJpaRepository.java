package br.com.posfiap.restmanager.repository;

import br.com.posfiap.restmanager.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RestauranteJpaRepository extends JpaRepository<RestauranteEntity, Long> {

    Optional<RestauranteEntity> findByLogin(String login);
}


