package com.tfgbackend.dto;

import java.util.List;

public class ExerciseTemplateFilesDTO {

    ExerciseDTO exercise;
    List<ExerciseFileDTO>  files;

    public ExerciseTemplateFilesDTO(ExerciseDTO exercise, List<ExerciseFileDTO> files) {
        this.exercise = exercise;
        this.files = files;
    }

    public ExerciseDTO getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDTO exercise) {
        this.exercise = exercise;
    }

    public List<ExerciseFileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<ExerciseFileDTO> files) {
        this.files = files;
    }
}
