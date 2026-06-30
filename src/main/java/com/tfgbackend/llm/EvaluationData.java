package com.tfgbackend.llm;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.model.Rule;

import java.util.List;

public record EvaluationData (

    String exerciseStatement,
    List<Rule> rules,
    List<ExerciseFileDTO> exerciseFiles
){

}
