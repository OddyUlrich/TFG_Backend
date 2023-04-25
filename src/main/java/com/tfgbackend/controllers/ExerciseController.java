package com.tfgbackend.controllers;

import com.tfgbackend.dto.ExerciseEditorDTO;
import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.ExerciseFiles;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.dto.ExerciseHomeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ExerciseHomeDTO>> allExercises(String studentId) {

        //TODO Ver como hacer el token con los datos del usuario (en marcadores)
        String userId = "635981f6e40c61599e000064";
        try {
            List<ExerciseHomeDTO> lista = exerciseService.allExercisesSolutionsByStudent(userId);
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseEditorDTO> getExercise(@PathVariable String exerciseId, String studentId) {
        //TODO Ver como hacer el token con los datos del usuario (en marcadores)
        String userId = "635981f6e40c61599e000064";

        try{
            ExerciseEditorDTO exercise = exerciseService.exerciseFilesAndSolutionByIdAndStudent(exerciseId, userId);
            return ResponseEntity.status(HttpStatus.OK).body(exercise);
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