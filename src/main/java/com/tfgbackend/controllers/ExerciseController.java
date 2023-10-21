package com.tfgbackend.controllers;

import com.tfgbackend.dto.*;
import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.service.ExerciseFilesService;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.service.SolutionService;
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
    private final SolutionService solutionService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, ExerciseFilesService exerciseFilesService, SolutionService solutionService) {
        this.exerciseService = exerciseService;
        this.exerciseFilesService = exerciseFilesService;
        this.solutionService = solutionService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseHomeDTO>> allExercises(Authentication auth) {

        try {
            if (auth != null && auth.isAuthenticated()) {
                String email = auth.getName();
                List<ExerciseHomeDTO> lista = exerciseService.allExercisesWithSolutionByUserId(email);
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
    public ResponseEntity<ExerciseEditorDataDTO> getExercise(@PathVariable String exerciseId, Authentication auth) {

        try {
            if (auth != null && auth.isAuthenticated()) {
                String email = auth.getName();
                List<ExerciseFileDTO> exerciseFiles = exerciseFilesService.exerciseFilesAndSolutionByIdAndStudent(exerciseId, email);

                List<ExerciseFileDTO> filesForDisplay = exerciseFilesService.filterFilesForDisplay(exerciseFiles);
                List<ExerciseFileDTO> templateFiles = exerciseFilesService.filterTemplateFiles(exerciseFiles);
                List<SolutionDTO> solutions = solutionService.allSolutionsByExerciseIdAndStudent(exerciseId, email);
                String lastSolution = exerciseFilesService.obtainSolutionFromExerciseFiles(exerciseFiles);
                ExerciseDTO exercise = exerciseService.findExerciseForEditorById(exerciseId);

                ExerciseEditorDataDTO data = new ExerciseEditorDataDTO(filesForDisplay, templateFiles, solutions, lastSolution, exercise);
                return ResponseEntity.status(HttpStatus.OK).body(data);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}