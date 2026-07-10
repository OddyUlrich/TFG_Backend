package com.tfgbackend.dto;

import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.ExerciseFile;
import com.tfgbackend.model.Tag;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ExerciseTemplateDataDTO {

    ExerciseDTO exercise;
    List<ExerciseFileDTO> files;

    public ExerciseTemplateDataDTO() {
    }

    public ExerciseTemplateDataDTO(ExerciseDTO exercise, List<ExerciseFileDTO> files) {
        this.exercise = exercise;
        this.files = files;
    }

    public ExerciseTemplateDataDTO(Exercise exercise, List<ExerciseFile> files) {
        ExerciseDTO exerciseDTO = new ExerciseDTO(exercise.getId(), exercise.getName(), exercise.getStatement(), exercise.getExerciseBattery().getName(), exercise.getRules(), exercise.getTags());
        List<ExerciseFileDTO> exerciseFilesDTO = new ArrayList<>();

        for (ExerciseFile exerciseFile : files) {
            exerciseFilesDTO.add(new ExerciseFileDTO(exerciseFile.getId(), exerciseFile.getName(), exerciseFile.getPath(), new String(exerciseFile.getBinaryText(), StandardCharsets.UTF_8), null, exerciseFile.getEditableMethods(), exerciseFile.getBinaryText()));
        }

        this.exercise = exerciseDTO;
        this.files = exerciseFilesDTO;
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
