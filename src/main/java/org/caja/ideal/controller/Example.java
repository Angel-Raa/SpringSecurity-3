package org.caja.ideal.controller;

import jakarta.validation.Valid;
import org.caja.ideal.controller.requestDTO.UserDTo;
import org.caja.ideal.models.Role;
import org.caja.ideal.models.Roles;
import org.caja.ideal.models.User;
import org.caja.ideal.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class Example {
    @Autowired
    private IUserRepository repository;
    @GetMapping("/hello")
    public String hello() {
        return """
                Hello world
                """;
    }

    @PostMapping("/add")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTo userDTo) {
        Set<Role> role = userDTo.getRole()
                .stream().map(rol -> Role.builder()
                        .name(Roles.valueOf(rol))
                        .build())
                .collect(Collectors.toSet());
        User user = User.builder()
                .email(userDTo.getEmail())
                .passaword(userDTo.getPassword())
                .username(userDTo.getUsername())
                .roles(role)
                .build();
        repository.save(user);
        return new ResponseEntity<>("Usuario creado con exitos ", HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> detele(@PathVariable Long id){
        repository.findById(id);
        return ResponseEntity.ok("Usuario eliminado con exitos");
    }
}
