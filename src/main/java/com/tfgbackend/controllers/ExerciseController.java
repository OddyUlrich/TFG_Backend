package com.tfgbackend.controllers;

import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.repositories.ExerciseBatteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExerciseController() {

    private final ExerciseBatteryRepository eBR;

    @Autowired
    public ExerciseController(ExerciseBatteryRepository eBR){
        this.eBR = eBR;
    }

    @GetMapping("/{studentId}/exercises")
    public ResponseEntity<List<ExerciseBattery>> user(@PathVariable String studentId){

        //Cambiar para que no acceda al repositorio sino a un servicio. Esto quiere decir que el atributo de la clase será el servicio, no esto.
        //Sería el servicio el que accedería al otro controlador para, a partir del ID del alumno, conseguir las asignaturas y luego hacer esto, no?
        //Queda la duda de si, en este punto tendríamos guardado al estudiante mientras que entramos en diferentes paginas y no haria falta buscarlo ya -> Axel
        List<ExerciseBattery> exerciseBatteryFromStudent = eBR.allExerciseBatteryByUserSubjects();
        return ResponseEntity.status(HttpStatus.OK).body(ejemplo);
    }
}