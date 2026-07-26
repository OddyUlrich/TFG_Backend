package com.tfgbackend.dto;

import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.ExerciseFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ExerciseTemplateDataDTO {

    ExerciseSimpleDTO exercise;
    List<ExerciseFileDTO> files;

    public ExerciseTemplateDataDTO() {
    }

    public ExerciseTemplateDataDTO(ExerciseSimpleDTO exercise, List<ExerciseFileDTO> files) {
        this.exercise = exercise;
        this.files = files;
    }

    public ExerciseTemplateDataDTO(Exercise exercise, List<ExerciseFile> files) {
        ExerciseSimpleDTO exerciseSimpleDTO = new ExerciseSimpleDTO(exercise.getId(), exercise.getName(), exercise.getStatement(), exercise.getExerciseBattery().getName(), exercise.getRules(), exercise.getTags());
        List<ExerciseFileDTO> exerciseFilesDTO = new ArrayList<>();

        for (ExerciseFile exerciseFile : files) {
            exerciseFilesDTO.add(new ExerciseFileDTO(exerciseFile.getId(), exerciseFile.getName(), exerciseFile.getPath(), new String(exerciseFile.getBinaryText(), StandardCharsets.UTF_8), null, exerciseFile.getEditableMethods(), exerciseFile.getBinaryText()));
        }

        this.exercise = exerciseSimpleDTO;
        this.files = exerciseFilesDTO;
    }

    public ExerciseSimpleDTO getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseSimpleDTO exercise) {
        this.exercise = exercise;
    }

    public List<ExerciseFileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<ExerciseFileDTO> files) {
        this.files = files;
    }
}
