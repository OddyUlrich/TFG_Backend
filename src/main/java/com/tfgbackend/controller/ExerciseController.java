package com.tfgbackend.controller;

import com.tfgbackend.dto.*;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.Tag;
import com.tfgbackend.service.*;
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
    private final ExerciseFilesService exerciseFilesService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, ExerciseFilesService exerciseFilesService) {
        this.exerciseService = exerciseService;
        this.exerciseFilesService = exerciseFilesService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseHomeDTO>> allExercises(Authentication auth) {

        try {
            if (auth != null && auth.isAuthenticated()) {
                String email = auth.getName();
                List<ExerciseHomeDTO> lista = exerciseService.allExercisesWithLastSolutionsByUserId(email);
                return ResponseEntity.status(HttpStatus.OK).body(lista);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/{exerciseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExerciseTemplateDataDTO> getExercise(@PathVariable String exerciseId, Authentication auth) {
        try {
            if (auth != null && auth.isAuthenticated()) {

                ExerciseDTO exercise = exerciseService.findExerciseForEditorById(exerciseId);
                List<ExerciseFileDTO> allTemplateFiles = exerciseFilesService.templateFilesByExerciseId(exerciseId);

                ExerciseTemplateDataDTO data =  new ExerciseTemplateDataDTO(exercise, allTemplateFiles);

                return ResponseEntity.status(HttpStatus.OK).body(data);

            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(value = "/{exerciseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExerciseTemplateDataDTO> newExercise(@RequestBody ExerciseTemplateDataDTO data, Authentication auth) {

        System.out.println(data);

        return null;
    }

    @PatchMapping(value = "/{exerciseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExerciseTemplateDataDTO> editExercise(@RequestBody ExerciseTemplateDataDTO data, Authentication auth) {

        System.out.println(data);

        return null;
    }
}