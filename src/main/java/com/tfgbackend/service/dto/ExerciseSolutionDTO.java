package com.tfgbackend.service.dto;

import com.tfgbackend.model.Tag;
import com.tfgbackend.model.enumerators.StatusExercise;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ExerciseSolutionDTO {

    private String id;
    private String name;
    private String statement;
    private List<Tag> tags;
    private Boolean favorite;
    private String batteryName;
    private Integer numberErrorsSolution;
    private LocalDateTime timestampSolution;
    private StatusExercise statusSolution;

    public ExerciseSolutionDTO(String name, String statement, List<Tag> tags, Boolean favorite, String batteryName, Integer numberErrorsSolution, LocalDateTime timestampSolution, StatusExercise statusSolution) {
        this.name = name;
        this.statement = statement;
        this.tags = tags;
        this.favorite = favorite;
        this.batteryName = batteryName;
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

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
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
        favorite = favorite;
    }

    public String getBatteryName() {
        return batteryName;
    }

    public void setBatteryName(String batteryName) {
        this.batteryName = batteryName;
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
                "ExerciseSolutionDTO[" + name + ", Statement: " + statement + ", Tags: " + tags.toString() + ", Bateria:" + batteryName + ", Status: " + statusSolution + "]"
        );
    }
}
