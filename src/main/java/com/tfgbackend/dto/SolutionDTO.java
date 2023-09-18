package com.tfgbackend.dto;

import com.tfgbackend.model.enumerators.StatusExercise;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class SolutionDTO {

    private String id;
    private String name;
    private LocalDateTime lastUpdate;
    private @NotNull StatusExercise status;
    private Integer numberErrors;

    public SolutionDTO(String id, String name, LocalDateTime lastUpdate, StatusExercise status, Integer numberErrors) {
        this.id = id;
        this.name = name;
        this.lastUpdate = lastUpdate;
        this.status = status;
        this.numberErrors = numberErrors;
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

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public StatusExercise getStatus() {
        return status;
    }

    public void setStatus(StatusExercise status) {
        this.status = status;
    }

    public Integer getNumberErrors() {
        return numberErrors;
    }

    public void setNumberErrors(Integer numberErrors) {
        this.numberErrors = numberErrors;
    }
}
