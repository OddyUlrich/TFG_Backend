package com.tfgbackend.dto;

import java.util.List;

public class ExerciseEditorDataDTO {

    private List<ExerciseFileDTO> filesForDisplay;
    private List<ExerciseFileDTO> freshFiles;
    private List<SolutionDTO> exerciseSolutions;
    private String currentSolution;
    private ExerciseDTO exercise;

    public ExerciseEditorDataDTO(List<ExerciseFileDTO> filesForDisplay, List<ExerciseFileDTO> freshFiles, List<SolutionDTO> exerciseSolutions, String currentSolution, ExerciseDTO exercise) {
        this.filesForDisplay = filesForDisplay;
        this.freshFiles = freshFiles;
        this.exerciseSolutions = exerciseSolutions;
        this.currentSolution = currentSolution;
        this.exercise = exercise;
    }

    public List<ExerciseFileDTO> getFilesForDisplay() {
        return filesForDisplay;
    }

    public void setFilesForDisplay(List<ExerciseFileDTO> filesForDisplay) {
        this.filesForDisplay = filesForDisplay;
    }

    public List<ExerciseFileDTO> getFreshFiles() {
        return freshFiles;
    }

    public void setFreshFiles(List<ExerciseFileDTO> freshFiles) {
        this.freshFiles = freshFiles;
    }

    public List<SolutionDTO> getExerciseSolutions() {
        return exerciseSolutions;
    }

    public void setExerciseSolutions(List<SolutionDTO> exerciseSolutions) {
        this.exerciseSolutions = exerciseSolutions;
    }

    public String getCurrentSolution() {
        return currentSolution;
    }

    public void setCurrentSolution(String currentSolution) {
        this.currentSolution = currentSolution;
    }

    public ExerciseDTO getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDTO exercise) {
        this.exercise = exercise;
    }
}
