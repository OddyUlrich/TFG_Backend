package com.tfgbackend.controller;

import com.tfgbackend.dto.UserDTO;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.forms.SignUpForm;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.User;
import com.tfgbackend.model.enumerator.Rol;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.time.Duration;
import java.util.List;

import static com.tfgbackend.configuration.Util.successfulCookieAuthentication;

@RestController
public class UserController {

    private final UserService userService;
    private final ExerciseService exerciseService;
    private final Key key;

    @Autowired
    public UserController(UserService userService, ExerciseService exerciseService, Key key) {
        this.userService = userService;
        this.exerciseService = exerciseService;
        this.key = key;
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

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpForm user, HttpServletResponse response) {

        try {

            //Trying creating the new user and adding it to the database
            User newUser = userService.create(user);

            //Creating the userDTO for the frontend with the new created user
            UserDTO userLogin = new UserDTO(newUser.getUsername(), newUser.getEmail(), newUser.getCreationDate(), newUser.getRoles());

            //Creating the cookie so that the user has access to the application after the signup without having to log in.
            Cookie cookie = successfulCookieAuthentication(List.of(Rol.STUDENT.toString()), Duration.ofMillis(0), userLogin.getEmail(), key);

            //Returning the response with cookie
            response.addCookie(cookie);
            return ResponseEntity.status(HttpStatus.CREATED).body(userLogin);

        }catch (Exception e){
            //If the email or username is already in use we must warn the user instead of add a new user in the database
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PostMapping("/close-session")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response){

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authentication")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }

        return ResponseEntity.ok().build();
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