package br.com.posfiap.restmanager.repositories;

import br.com.posfiap.restmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findById(Long id);
    Optional<User> findByUuid(UUID uuiid);
    List<User> findAll(int size, int offset);
    User save(User user);
    Integer update(User user, Long id);
    Integer delete(Long id);
    Optional<User> validateLogin(String login, String password);

}
