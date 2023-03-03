package com.tfgbackend.controllers;

import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.service.dto.ExerciseSolutionDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    public ResponseEntity<List<ExerciseBattery>> user() {

        //TODO Provisional, lo suyo es que lo recibe por argumento
        ObjectId studentId = new ObjectId("635981f6e40f61599e000064");

        //TODO Tendria que comprobar que pasa si vuelve algo vacío y enviar otro status, no? Recogiendo excepciones y eso
        //List<ExerciseSolutionDTO> listStudentExercises = exerciseService.allExercisesByStudent(studentId);
        //List<ExerciseBattery> lista = exerciseService.allExercisesByStudent(studentId);

        ExerciseBattery list = new ExerciseBattery(null, "Bateria131", null);
        ArrayList<ExerciseBattery> lista = new ArrayList(List.of(list));

        for (ExerciseBattery eb : lista){
            System.out.println("ATIENDE: " + eb.getName() + ", Ejercicios: " + eb.getExerciseList());
        }

        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }
}