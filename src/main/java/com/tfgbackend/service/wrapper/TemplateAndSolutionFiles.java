package com.tfgbackend.service.wrapper;

import com.tfgbackend.dto.ExerciseFileDTO;

import java.util.List;

public class TemplateAndSolutionFiles {

    private List<ExerciseFileDTO> filesForDisplay;
    private List<ExerciseFileDTO> templateFiles;

    public TemplateAndSolutionFiles(List<ExerciseFileDTO> filesForDisplay, List<ExerciseFileDTO> templateFiles) {
        this.filesForDisplay = filesForDisplay;
        this.templateFiles = templateFiles;
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
}
