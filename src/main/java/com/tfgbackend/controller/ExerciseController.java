package com.tfgbackend.controller;

import com.tfgbackend.dto.*;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.ExerciseFile;
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
    public ResponseEntity<List<ExerciseHomeDTO>> allExercises(
            @RequestParam(required = false) String exerciseName,
            @RequestParam(required = false) String batteryName,
            @RequestParam(required = false) List<String> tags,
            Authentication auth) {

        try {
            if (auth != null && auth.isAuthenticated()) {
                String email = auth.getName();
                List<ExerciseHomeDTO> lista = exerciseService.allExercisesWithLastSolutionsByUserId(email, exerciseName, batteryName, tags);
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

                ExerciseTemplateDataDTO data = new ExerciseTemplateDataDTO(exercise, allTemplateFiles);

                return ResponseEntity.status(HttpStatus.OK).body(data);

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExerciseTemplateDataDTO> newExercise(@RequestBody ExerciseTemplateDataDTO data, Authentication auth) {

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ExerciseDTO exerciseDTO = data.getExercise();
        List<ExerciseFileDTO> templateFilesDTO = data.getFiles();

        Exercise newExercise = exerciseService.createFromDTO(exerciseDTO, auth.getName());
        List<ExerciseFile> newTemplateFiles = exerciseFilesService.saveTemplateFiles(templateFilesDTO, newExercise, true);

        //TODO guardar los ficheros -> ¿transaccional guardando todos a la vez?

        ExerciseTemplateDataDTO newData = new ExerciseTemplateDataDTO(newExercise, newTemplateFiles);

        return ResponseEntity.status(HttpStatus.CREATED).body(newData);
    }

    @PutMapping(value = "/{exerciseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExerciseTemplateDataDTO> editExercise(@PathVariable String exerciseId, @RequestBody ExerciseTemplateDataDTO data, Authentication auth) {

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (exerciseId == null || exerciseId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "exerciseId is required");
        }

        ExerciseDTO exerciseDTO = data.getExercise();
        List<ExerciseFileDTO> templateFilesDTO = data.getFiles();

        if (!exerciseId.equals(exerciseDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID from path variable and the ID from the exercise don't match");
        }

        Exercise newExercise = exerciseService.editFromDTO(exerciseDTO, auth.getName());
        List<ExerciseFile> newTemplateFiles = exerciseFilesService.saveTemplateFiles(templateFilesDTO, newExercise, false);

        //TODO guardar los ficheros -> ¿transaccional guardando todos a la vez?

        ExerciseTemplateDataDTO newData = new ExerciseTemplateDataDTO(newExercise, newTemplateFiles);

        return ResponseEntity.status(HttpStatus.CREATED).body(newData);
    }
}