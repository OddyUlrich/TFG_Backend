package com.tfgbackend.controllers;

import com.tfgbackend.dto.UserDTO;
import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.forms.SignUpForm;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.User;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    private final UserService userService;
    private final ExerciseService exerciseService;

    @Autowired
    public UserController(UserService userService, ExerciseService exerciseService) {
        this.userService = userService;
        this.exerciseService = exerciseService;
    }

    @GetMapping("/users/check")
    public ResponseEntity<UserDTO> checkAuth(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            UserDTO userLogin = userService.getUserInfo(email);
            return ResponseEntity.status(HttpStatus.OK).body(userLogin);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> checkAuth(@RequestBody SignUpForm user) {

        User newUser = userService.create(user);
        if (newUser != null) {
            UserDTO userLogin = userService.getUserInfo(newUser.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(userLogin);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body();
        }
    }

    @PatchMapping("/users/favorites/{exerciseId}")
    public ResponseEntity<User> changeFavorite(@PathVariable String exerciseId, Authentication auth) {

        try {
            if (auth != null && auth.isAuthenticated()) {
                //Obtenemos el email del usuario que realiza la peticion
                String email = auth.getName();

                //Comprobamos si el ejercicio existe buscando por su ID
                Exercise exercise = exerciseService.findExerciseById(exerciseId);

                //Con el email del usuario y el ID del ejercicio procedemos a agregar o quitar el ejercicio de favoritos
                userService.updateUserFavorites(email, exercise.getId());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}