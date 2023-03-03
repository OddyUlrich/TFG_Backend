package com.tfgbackend.service;

import com.tfgbackend.repositories.ExerciseRepository;
import com.tfgbackend.service.dto.ExerciseSolutionDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    private ExerciseRepository er;

    @Autowired
    public ExerciseService(ExerciseRepository er){
        this.er = er;
    }

    public List<ExerciseSolutionDTO> allExercisesByStudent(ObjectId studentId){

        return er.allExerciseByUserSubjects(studentId);

        //Debemos retornar una lista de ExerciseSolutionDTO
        //List<ExerciseSolutionDTO> allExerciseSolutions = ebr.allExerciseBatteryByUserSubjects(subjects);
        //return allExerciseSolutions;


        //Ojo, si ya lo ha completado alguna vez el status a mostrar sera "completado"
    }

}
