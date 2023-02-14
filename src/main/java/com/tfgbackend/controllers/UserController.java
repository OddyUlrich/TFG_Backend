package com.tfgbackend.controllers;

import com.tfgbackend.model.Rol;
import com.tfgbackend.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class UserController {

    private static final String template = "Hello, %s!";

    @GetMapping("/alumno")
    public User user(
            @RequestParam(value = "name", defaultValue = "Manuel") String name,
            @RequestParam(value = "email", defaultValue = "andresoubina@hotmail.es") String email,
            @RequestParam(value = "birthday", defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime birthday,
            @RequestParam(value = "rol", defaultValue = "STUDENT") Rol rol){
        return new User(name, email, birthday, rol);
    }
}
