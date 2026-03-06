package com.tfgbackend.dto;

import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Tag;

import java.util.List;

public class ExerciseTemplateDataDTO {

    ExerciseDTO exercise;
    List<ExerciseFileDTO>  files;
    List<ExerciseBattery> batteries;
    List<Tag> tags;

    public ExerciseTemplateDataDTO(ExerciseDTO exercise, List<ExerciseFileDTO> files, List<ExerciseBattery> batteries, List<Tag> tags) {
        this.exercise = exercise;
        this.files = files;
        this.batteries = batteries;
        this.tags = tags;
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

    public List<ExerciseBattery> getBatteries() {
        return batteries;
    }

    public void setBatteries(List<ExerciseBattery> batteries) {
        this.batteries = batteries;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
