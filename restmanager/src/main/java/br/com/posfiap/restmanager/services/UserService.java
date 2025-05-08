package br.com.posfiap.restmanager.services;

import br.com.posfiap.restmanager.dtos.UserDTO;
import br.com.posfiap.restmanager.entities.User;
import br.com.posfiap.restmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private void save(User user) {
        userRepository.save(user);
    }
    public void save(UserDTO userDTO) {
        //TODO:Criar Mapper toEntity
        //this.save(user);
    }

    public void update(UUID userUuid, UserDTO userDTO) {
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new RuntimeException("User not found")); //TODO: Criar Exception para o user
        //validar campos necessarios e setar
        //thia.save()
    }

    public List<User> findAll(int size, int offset) {
        return userRepository.findAll(size, offset);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public Optional<User> validateLogin(String login, String password) {
        return userRepository.validateLogin(login, password);
    }
}
