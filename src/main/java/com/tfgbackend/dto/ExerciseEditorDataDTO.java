package com.tfgbackend.dto;

import java.util.List;

public class ExerciseEditorDataDTO {

    private List<ExerciseFileDTO> files;
    private List<SolutionDTO> exerciseSolutions;
    private ExerciseDTO exercise;

    public ExerciseEditorDataDTO(List<ExerciseFileDTO> files, List<SolutionDTO> exerciseSolutions, ExerciseDTO exercise) {
        this.files = files;
        this.exerciseSolutions = exerciseSolutions;
        this.exercise = exercise;
    }

    public List<ExerciseFileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<ExerciseFileDTO> files) {
        this.files = files;
    }

    public List<SolutionDTO> getExerciseSolutions() {
        return exerciseSolutions;
    }

    public void setExerciseSolutions(List<SolutionDTO> exerciseSolutions) {
        this.exerciseSolutions = exerciseSolutions;
    }

    public ExerciseDTO getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDTO exercise) {
        this.exercise = exercise;
    }
}
