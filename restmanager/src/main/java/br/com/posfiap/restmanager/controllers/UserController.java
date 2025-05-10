package br.com.posfiap.restmanager.controllers;

import br.com.posfiap.restmanager.dtos.UserDTO;
import br.com.posfiap.restmanager.entities.User;
import br.com.posfiap.restmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    //TODO: Mudar User para UserDTO
    @Autowired
    private UserService service;


    @PostMapping
    public ResponseEntity<Void> save(
            @RequestBody UserDTO userDTO
    ) {

        this.service.save(userDTO);
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

    //TODO: Criar controller de autenticaçao
//    @PostMapping("/login")
//    public Optional<User> login(@RequestBody User user) {
//        return service.validateLogin(user.getLogin(), user.getPassword());
//    }

}