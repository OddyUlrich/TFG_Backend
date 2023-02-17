package com.tfgbackend.controllers;

import com.tfgbackend.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/alumno")
    public User user(@RequestBody User usuario){
        return new User(null, usuario.name(), usuario.email(), usuario.birthday(), usuario.subjects(), usuario.rol());
    }
}
