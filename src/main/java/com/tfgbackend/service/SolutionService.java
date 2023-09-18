package com.tfgbackend.service;

import com.tfgbackend.dto.SolutionDTO;
import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.User;
import com.tfgbackend.repositories.SolutionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SolutionService {

    private final SolutionRepository sr;
    private final UserService us;

    @Autowired
    public SolutionService(SolutionRepository sr, UserService us){
        this.sr = sr;
        this.us = us;
    }

    public List<SolutionDTO> allSolutionsByExerciseIdAndStudent(String exerciseId, String email){
        User user = us.getUser(email);
        return sr.allSolutionsByExerciseIdAndStudent(new ObjectId(exerciseId), new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Solutions about exercise could not be obtained"));
    }

}
