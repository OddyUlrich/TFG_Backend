package com.tfgbackend.controllers;

import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.User;
import com.tfgbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/alumno")
    public ResponseEntity<User> user(@RequestBody User usuario) {

        User ejemplo = new User(null, usuario.getName(), usuario.getEmail(), usuario.getBirthday(), usuario.getRol(), usuario.getFavoriteExercises());
        return ResponseEntity.status(HttpStatus.OK).body(ejemplo);
    }

    @PutMapping("/exercises/{exerciseId}/favorite")
    public ResponseEntity<User> user(@PathVariable String exerciseId) {

        //TODO Ver como hacer el token con los datos del usuario (en marcadores)
        String userID = "635981f6e40c61599e000064";

        try {
            User user = userService.updateUserFavorites(userID, exerciseId);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}