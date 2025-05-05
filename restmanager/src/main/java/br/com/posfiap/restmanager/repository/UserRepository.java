package br.com.posfiap.restmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.posfiap.restmanager.entity.User;

@Repository
public interface UserRepository {

    Optional<User> findById(Long id);
    List<User> findAll(int size, int offset);
    Integer save(User user);
    Integer update(User user, Long id);
    Integer delete(Long id);
    Optional<User> validateLogin(String login, String password);
    
}