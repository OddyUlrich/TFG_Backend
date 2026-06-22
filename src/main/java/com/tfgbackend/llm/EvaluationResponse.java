package com.tfgbackend.llm;

import com.tfgbackend.model.enumerator.ExerciseEvaluationStatus;

import java.util.List;

public record EvaluationResponse(
        ExerciseEvaluationStatus evaluationStatus,
        List<String> errors

) {
}
