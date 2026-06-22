package com.tfgbackend.controller;

import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.User;
import com.tfgbackend.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController

@RequestMapping("/users/favorites/{exerciseId}")
public class FavoritesController {

    private final FavoritesService favoritesService;

    @Autowired
    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    @PatchMapping
    public ResponseEntity<User> changeFavorite(@PathVariable String exerciseId, Authentication auth) {

        try {
            if (auth == null || !auth.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            //Con el email del usuario y el ID del ejercicio procedemos a agregar o quitar el ejercicio de favoritos
            favoritesService.updateUserFavorites(auth.getName(), exerciseId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


}
