package com.tfgbackend.controller;

import com.tfgbackend.dto.*;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Tag;
import com.tfgbackend.service.*;
import com.tfgbackend.service.wrapper.TemplateAndSolutionFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final ExerciseFilesService exerciseFilesService;
    private final SolutionService solutionService;
    private final ExerciseBatteryService exerciseBatteryService;
    private final TagService tagService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, ExerciseFilesService exerciseFilesService, SolutionService solutionService, ExerciseBatteryService exerciseBatteryService, TagService tagService) {
        this.exerciseService = exerciseService;
        this.exerciseFilesService = exerciseFilesService;
        this.solutionService = solutionService;
        this.exerciseBatteryService = exerciseBatteryService;
        this.tagService = tagService;
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
    public ResponseEntity<CodeEditorDataDTO> getExercise(@PathVariable String exerciseId, Authentication auth) {

        try {
            if (auth != null && auth.isAuthenticated()) {

                String email = auth.getName();
                List<ExerciseFileDTO> allExerciseFilesAndLastSolution = exerciseFilesService.exerciseFilesAndLastSolutionByIdAndStudent(exerciseId, email);

                //All the files that the code editor could use (exercise's template files and user's solution files)
                TemplateAndSolutionFiles filteredFiles = exerciseFilesService.filterFiles(allExerciseFilesAndLastSolution);

                //All basic information about the other solutions of this user to allow him/her to change to it
                List<SolutionDTO> solutions = solutionService.allSolutionsByExerciseIdAndStudent(exerciseId, email);

                //ID for the last updated solution (Probably the last one they worked on)
                String currentSolution = exerciseFilesService.obtainSolutionFromExerciseFiles(allExerciseFilesAndLastSolution);

                //All necessary information about the exercise the user is currently working on
                ExerciseDTO exercise = exerciseService.findExerciseForEditorById(exerciseId);

                //DTO for the frontend with all files and information needed
                CodeEditorDataDTO data = new CodeEditorDataDTO(filteredFiles.getFilesForDisplay(), filteredFiles.getTemplateFiles(), solutions, currentSolution, exercise);

                return ResponseEntity.status(HttpStatus.OK).body(data);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/edit/{exerciseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExerciseTemplateDataDTO> editExercise(@PathVariable String exerciseId, Authentication auth) {
        try {
            if (auth != null && auth.isAuthenticated()) {

                ExerciseDTO exercise = exerciseService.findExerciseForEditorById(exerciseId);
                List<ExerciseFileDTO> allTemplateFiles = exerciseFilesService.templateFilesByExerciseId(exerciseId);
                List<ExerciseBattery> allExerciseBatteries = exerciseBatteryService.findAll();
                List<Tag> allTags = tagService.findAll();

                ExerciseTemplateDataDTO data =  new ExerciseTemplateDataDTO(exercise, allTemplateFiles, allExerciseBatteries, allTags);

                return ResponseEntity.status(HttpStatus.OK).body(data);

            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}