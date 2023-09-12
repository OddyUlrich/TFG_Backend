package com.tfgbackend.service;

import com.tfgbackend.dto.ExerciseFilesDTO;
import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.User;
import com.tfgbackend.repositories.ExerciseFileRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseFilesService {

    private final UserService us;
    private final ExerciseFileRepository efr;

    @Autowired
    public ExerciseFilesService(UserService us, ExerciseFileRepository efr) {
        this.us = us;
        this.efr = efr;
    }

    public ExerciseFilesDTO exerciseFilesAndSolutionByIdAndStudent(String exerciseId, String email){
        User user = us.getUser(email);
        return efr.exerciseFilesAndSolutionByIdAndStudent(new ObjectId(exerciseId), new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Files about exercise could not be obtained"));
    }
}
