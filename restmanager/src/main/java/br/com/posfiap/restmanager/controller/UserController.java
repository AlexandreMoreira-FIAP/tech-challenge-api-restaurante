package br.com.posfiap.restmanager.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.posfiap.restmanager.entity.User;
import br.com.posfiap.restmanager.service.UserService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

   
    
    @PostMapping
    public ResponseEntity<Void> save(
             @RequestBody User user
    ) {
        
        this.service.save(user);
        return ResponseEntity.status(201).build();
    }
    
    @GetMapping
    public List<User> getAll(int size, int offset) {
        return service.findAll(size, offset);
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Long id) {
        return service.findById(id);
    }

//    @PutMapping("/{id}")
//    public Integer update(@PathVariable Long id, @RequestBody User user) {
//        user.setId(id);
//        return service.save(user);
//    }

    
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/login")
    public Optional<User> login(@RequestBody User user) {
        return service.validateLogin(user.getLogin(), user.getPassword());
    }
}
