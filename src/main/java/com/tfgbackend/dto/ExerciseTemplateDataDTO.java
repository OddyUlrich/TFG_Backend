package com.tfgbackend.dto;

import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Tag;

import java.util.List;

public class ExerciseTemplateDataDTO {

    ExerciseDTO exercise;
    List<ExerciseFileDTO> files;

    public ExerciseTemplateDataDTO(ExerciseDTO exercise, List<ExerciseFileDTO> files) {
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
