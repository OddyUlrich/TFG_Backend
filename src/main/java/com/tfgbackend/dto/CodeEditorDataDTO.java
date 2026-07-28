package com.tfgbackend.dto;

import java.util.List;

public class CodeEditorDataDTO {

    private List<ExerciseFileDTO> filesForDisplay;
    private List<ExerciseFileDTO> templateFiles;
    private List<SolutionDTO> exerciseSolutions;
    private String currentSolution;
    private ExerciseSimpleDTO exercise;

    public CodeEditorDataDTO(List<ExerciseFileDTO> filesForDisplay, List<ExerciseFileDTO> templateFiles, List<SolutionDTO> exerciseSolutions, String currentSolution, ExerciseSimpleDTO exercise) {
        this.filesForDisplay = filesForDisplay;
        this.templateFiles = templateFiles;
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

    public List<ExerciseFileDTO> getTemplateFiles() {
        return templateFiles;
    }

    public void setTemplateFiles(List<ExerciseFileDTO> templateFiles) {
        this.templateFiles = templateFiles;
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

    public ExerciseSimpleDTO getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseSimpleDTO exercise) {
        this.exercise = exercise;
    }
}
