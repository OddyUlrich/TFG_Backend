package com.tfgbackend.llm;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.model.EditableMethod;
import com.tfgbackend.model.Rule;

import java.util.List;

public record EvaluationData (

    List<ExerciseFileDTO> filesForEvaluation,
    String statement,
    List<Rule> rules,
    String solutionId
){

}
