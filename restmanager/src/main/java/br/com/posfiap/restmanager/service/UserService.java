package br.com.posfiap.restmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.posfiap.restmanager.entity.User;
import br.com.posfiap.restmanager.repository.UserRepository;

@Service
public class UserService {

    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    
    
    public Integer save(User user) {
        user.setLastUpdated(java.time.LocalDate.now());
        return userRepository.save(user);
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
