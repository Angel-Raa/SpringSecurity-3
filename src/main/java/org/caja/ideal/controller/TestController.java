package org.caja.ideal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String accessAdmin(){
        return "Hola, Rol de Admin";
    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String accessUser(){
        return "Hola, Rol de User";
    }
    @GetMapping("/invited")
    @PreAuthorize("hasRole('INVITED')")
    public String accessInvited(){
        return "Hola, Rol de Invited";
    }
}
