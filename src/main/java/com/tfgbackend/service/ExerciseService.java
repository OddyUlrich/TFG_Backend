package com.tfgbackend.service;

import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.repositories.ExerciseRepository;
import com.tfgbackend.service.dto.ExerciseSolutionDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository er;

    @Autowired
    public ExerciseService(ExerciseRepository er){
        this.er = er;
    }

    public List<ExerciseSolutionDTO> allExercisesSolutionsByStudent(String studentId){

        return er.allExerciseSolutionsByUserId(new ObjectId(studentId)).orElseThrow(() -> new ResourceNotFoundException("Data could not be obtained"));


        //Ojo, si ya lo ha completado alguna vez el status a mostrar sera "completado"
    }

    public Optional<Exercise> findExerciseById(String exerciseId){
        return er.findExerciseById(exerciseId);
    }


}
