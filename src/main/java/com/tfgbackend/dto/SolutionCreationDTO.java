package com.tfgbackend.dto;

import java.util.List;

public class SolutionCreationDTO {

    private List<ExerciseFileDTO> filesForDisplay;
    private String exerciseId;
    private String solutionId;

    public SolutionCreationDTO(List<ExerciseFileDTO> filesForDisplay, String exerciseId, String solutionId) {
        this.filesForDisplay = filesForDisplay;
        this.exerciseId = exerciseId;
        this.solutionId = solutionId;
    }

    public List<ExerciseFileDTO> getFilesForDisplay() {
        return filesForDisplay;
    }

    public void setFilesForDisplay(List<ExerciseFileDTO> filesForDisplay) {
        this.filesForDisplay = filesForDisplay;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(String solutionId) {
        this.solutionId = solutionId;
    }
}
