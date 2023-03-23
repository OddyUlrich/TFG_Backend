package com.tfgbackend.controllers;

import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.service.dto.ExerciseSolutionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin(origins ="http://localhost:3000/")
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseSolutionDTO>> allExercises(String studentId) {

        //TODO Tendria que comprobar que pasa si vuelve algo vacío y enviar otro status, no? Recogiendo excepciones y eso

        //TODO Ver como hacer el token con los datos del usuario (en marcadores)
        String userID = "635981f6e40c61599e000064";
        List<ExerciseSolutionDTO> lista = exerciseService.allExercisesSolutionsByStudent(userID);

        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<List<ExerciseSolutionDTO>> user(@PathVariable String id) {
//
//        List<ExerciseSolutionDTO> lista = exerciseService.updateExercise(id).orElseThrow(() -> new ResourceNotFoundException("Objeto no encontrado con el ID: " + id));
//
//        return ResponseEntity.status(HttpStatus.OK).body(lista);
//    }
}