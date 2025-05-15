package com.tfgbackend.service;

import com.tfgbackend.dto.ExerciseDTO;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.User;
import com.tfgbackend.repository.ExerciseRepository;
import com.tfgbackend.dto.ExerciseHomeDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository er;
    private final UserService us;

    @Autowired
    public ExerciseService(ExerciseRepository er, UserService us){
        this.er = er;
        this.us = us;
    }

    public List<ExerciseHomeDTO> allExercisesWithSolutionByUserId(String email){
        User user = us.getUser(email);
        return er.allExercisesWithSolutionByUserId(new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Data could not be obtained"));
    }

    public Exercise findExerciseById(String exerciseId){
        return er.findExerciseById(exerciseId).orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with that ID"));
    }

    public ExerciseDTO findExerciseForEditorById(String exerciseId){
        return er.findExerciseForEditorById(new ObjectId(exerciseId)).orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with that ID (Data for Editor)"));
    }

}
