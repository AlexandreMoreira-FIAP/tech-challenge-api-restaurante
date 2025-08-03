package br.com.posfiap.restmanager.infrastructure.persistence.jpa;

import br.com.posfiap.restmanager.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByLogin(String login);

    @EntityGraph(attributePaths = "restaurantes")
    Optional<UsuarioEntity> findWithRestaurantesById(Long id);
}
