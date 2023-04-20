package com.tfgbackend.dto;

import com.tfgbackend.model.Tag;
import com.tfgbackend.model.enumerators.StatusExercise;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ExerciseHomeDTO {

    private String id;
    private String name;
    private List<Tag> tags;
    private Boolean favorite;
    private String batteryName;
    private LocalDateTime creationTimestamp;
    private Integer numberErrorsSolution;
    private LocalDateTime timestampSolution;
    private StatusExercise statusSolution;

    public ExerciseHomeDTO(String id, String name, List<Tag> tags, Boolean favorite, String batteryName, LocalDateTime creationTimestamp, Integer numberErrorsSolution, LocalDateTime timestampSolution, StatusExercise statusSolution) {
        this.id = id;
        this.name = name;
        this.tags = tags;
        this.favorite = favorite;
        this.batteryName = batteryName;
        this.creationTimestamp = creationTimestamp;
        this.numberErrorsSolution = numberErrorsSolution;
        this.timestampSolution = timestampSolution;
        this.statusSolution = Objects.requireNonNullElse(statusSolution, StatusExercise.PENDING);
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

    public Integer getNumberErrorsSolution() {
        return numberErrorsSolution;
    }

    public void setNumberErrorsSolution(Integer numberErrorsSolution) {
        this.numberErrorsSolution = numberErrorsSolution;
    }

    public LocalDateTime getTimestampSolution() {
        return timestampSolution;
    }

    public void setTimestampSolution(LocalDateTime timestampSolution) {
        this.timestampSolution = timestampSolution;
    }

    public StatusExercise getStatusSolution() {
        return statusSolution;
    }

    public void setStatusSolution(StatusExercise statusSolution) {
        this.statusSolution = statusSolution;
    }

    @Override
    public String toString() {
        return String.format(
                "ExerciseHomeDTO[" + name +  ", Tags: " + tags.toString() + ", Bateria:" + batteryName + ", Status: " + statusSolution + "]"
        );
    }
}
