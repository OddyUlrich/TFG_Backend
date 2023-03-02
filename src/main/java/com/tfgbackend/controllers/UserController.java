package com.tfgbackend.controllers;

import com.tfgbackend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/alumno")
    public ResponseEntity<User> user(@RequestBody User usuario){

        User ejemplo = new User(null, usuario.getName(), usuario.getEmail(), usuario.getBirthday(), usuario.getSubjects(), usuario.getRol());
        return ResponseEntity.status(HttpStatus.OK).body(ejemplo);
    }
}