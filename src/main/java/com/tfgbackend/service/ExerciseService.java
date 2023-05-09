package com.tfgbackend.service;

import com.tfgbackend.dto.ExerciseEditorDTO;
import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.User;
import com.tfgbackend.repositories.ExerciseRepository;
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

    public List<ExerciseHomeDTO> allExercisesSolutionsByStudent(String email){
        User user = us.getUser(email);
        return er.allExerciseSolutionsByUserId(new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Data could not be obtained"));
    }

    public ExerciseEditorDTO exerciseFilesAndSolutionByIdAndStudent(String exerciseId, String email){
        User user = us.getUser(email);
        return er.exerciseFilesAndSolutionByIdAndStudent(new ObjectId(exerciseId), new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Files about exercise could not be obtained"));
    }

    public Exercise findExerciseById(String exerciseId){
        return er.findExerciseById(exerciseId).orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with that ID"));
    }

}
