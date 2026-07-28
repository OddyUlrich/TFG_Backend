package com.tfgbackend.llm;

import com.tfgbackend.model.enumerator.ExerciseEvaluationStatus;
import dev.langchain4j.model.output.structured.Description;

import java.util.List;

public record EvaluationResponse(

        @Description("Global result of exercise's evaluation. Since it is an ENUM, it can only have the value \"PASS\", \"FAIL\" or \"UNCERTAIN\"")
        ExerciseEvaluationStatus evaluationStatus,

        @Description("Brief summary of the evaluation in Spanish, if the evaluationStatus it is a PASS it should have a line break and then the result of the execution of the program with the student solution")
        String response,

        @Description("List of the errors found in the student's code. If there is no error it should be an empty list. Each one 1 or 2 lines each maximum.")
        List<String> errors
) {
}
