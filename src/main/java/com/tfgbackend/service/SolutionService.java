package com.tfgbackend.service;

import com.tfgbackend.repositories.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SolutionService {

    private final SolutionRepository sr;

    @Autowired
    public SolutionService(SolutionRepository sr){
        this.sr = sr;
    }


}
