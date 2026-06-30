package com.tfgbackend.service;

import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Rule;
import com.tfgbackend.repository.RulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RulesService {

    private final RulesRepository rr;

    @Autowired
    public RulesService(RulesRepository rr) {
        this.rr = rr;
    }
    public void saveRules(List<Rule> rules){
        rr.saveAll(rules);
    }

}
