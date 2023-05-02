package com.tfgbackend.controllers;

import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.forms.LoginForm;
import com.tfgbackend.model.User;
import com.tfgbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/alumno")
    public ResponseEntity<User> user(@RequestBody User usuario) {

        User ejemplo = new User(null, usuario.getName(), usuario.getPassword(), usuario.getEmail(), usuario.getBirthday(), usuario.getRols(), usuario.getFavoriteExercises());
        return ResponseEntity.status(HttpStatus.OK).body(ejemplo);
    }

    //TODO Cambiar para que no dependa de un "userId" que puede poner cualquiera, sino "/addFavorite/{exerciseId}, checkear para saber como se podria poner el exerciseID en el body
    @PatchMapping("/users/{userId}/favorites/{exerciseId}")
    public ResponseEntity<User> changeFavorite(@PathVariable String exerciseId) {

        //TODO Ver como hacer el token con los datos del usuario (en marcadores)
        String userID = "635981f6e40c61599e000064";

        try {
            userService.updateUserFavorites(userID, exerciseId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> get(@RequestBody LoginForm form) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}