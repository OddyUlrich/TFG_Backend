package com.tfgbackend.dto;

import java.util.List;

public class SolutionCreationDTO {

    private List<ExerciseFileDTO> solutionFiles;
    private String solutionId;

    public SolutionCreationDTO(List<ExerciseFileDTO> solutionFiles, String solutionId) {
        this.solutionFiles = solutionFiles;
        this.solutionId = solutionId;
    }

    public List<ExerciseFileDTO> getSolutionFiles() {
        return solutionFiles;
    }

    public void setSolutionFiles(List<ExerciseFileDTO> filesForDisplay) {
        this.solutionFiles = filesForDisplay;
    }

    public String getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(String solutionId) {
        this.solutionId = solutionId;
    }
}
