package com.tfgbackend.dto;

import com.tfgbackend.model.Solution;
import com.tfgbackend.model.Tag;

import java.time.LocalDateTime;
import java.util.List;

public class ExerciseHomeMongoDTO {

    private String id;
    private String name;
    private List<Tag> tags;
    private Boolean favorite;
    private String batteryName;
    private LocalDateTime creationTimestamp;
    private List<Solution> solutions;

    public ExerciseHomeMongoDTO(String id, String name, List<Tag> tags, Boolean favorite, String batteryName, LocalDateTime creationTimestamp, List<Solution> solutions) {
        this.id = id;
        this.name = name;
        this.tags = tags;
        this.favorite = favorite;
        this.batteryName = batteryName;
        this.creationTimestamp = creationTimestamp;
        this.solutions = solutions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getBatteryName() {
        return batteryName;
    }

    public void setBatteryName(String batteryName) {
        this.batteryName = batteryName;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    @Override
    public String toString() {
        return String.format(
                "ExerciseHomeDTO[" + name +  ", Tags: " + tags.toString() + ", Bateria:" + batteryName + "]"
        );
    }
}
