package com.tfgbackend.controllers;

import com.tfgbackend.dto.ExerciseEditorDTO;
import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.dto.ExerciseHomeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseHomeDTO>> allExercises(Authentication auth) {

        try {
            if (auth != null && auth.isAuthenticated()) {
                String email = auth.getName();
                List<ExerciseHomeDTO> lista = exerciseService.allExercisesSolutionsByStudent(email);
                return ResponseEntity.status(HttpStatus.OK).body(lista);
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping( value = "/{exerciseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExerciseEditorDTO> getExercise(@PathVariable String exerciseId, Authentication auth) {

        try{
            if (auth != null && auth.isAuthenticated()) {
                String email = auth.getName();
                ExerciseEditorDTO exercise = exerciseService.exerciseFilesAndSolutionByIdAndStudent(exerciseId, email);
                return ResponseEntity.status(HttpStatus.OK).body(exercise);
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<List<ExerciseSolutionDTO>> user(@PathVariable String id) {
//
//        List<ExerciseSolutionDTO> lista = exerciseService.updateExercise(id).orElseThrow(() -> new ResourceNotFoundException("Objeto no encontrado con el ID: " + id));
//
//        return ResponseEntity.status(HttpStatus.OK).body(lista);
//    }
}