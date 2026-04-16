package com.tfgbackend.dto;

import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Tag;

import java.util.List;

public class ExerciseCommonDataDTO {

    List<ExerciseBattery> batteries;
    List<Tag> tags;

    public ExerciseCommonDataDTO(List<ExerciseBattery> batteries, List<Tag> tags) {
        this.batteries = batteries;
        this.tags = tags;
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
