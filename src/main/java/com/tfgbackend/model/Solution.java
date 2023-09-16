package com.tfgbackend.model;

import com.tfgbackend.model.enumerators.StatusExercise;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("solutions")
public class Solution {
    @Id
    private String id;
    private @NotNull LocalDateTime creationTimestamp;
    private LocalDateTime updateTimestamp;
    private @NotNull StatusExercise status;
    @DBRef(lazy = true)
    private @NotNull  /*IsStudent*/ @Valid User student;
    @DBRef(lazy = true)
    private @NotNull @Valid Exercise exercise;
    private Integer numberErrors;

    public Solution(LocalDateTime creationTimestamp, StatusExercise status, User student, Exercise exercise, Integer numberErrors) {
        this.creationTimestamp = creationTimestamp;
        this.updateTimestamp = creationTimestamp;
        this.status = status;
        this.student = student;
        this.exercise = exercise;
        this.numberErrors = numberErrors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public StatusExercise getStatus() {
        return status;
    }

    public void setStatus(StatusExercise status) {
        this.status = status;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getNumberErrors() {
        return numberErrors;
    }

    public void setNumberErrors(Integer numberErrors) {
        this.numberErrors = numberErrors;
    }

    @Override
    public String toString() {
        return String.format(
                "Solution[" + id + "," + student.getUsername() + "," + exercise.getName() + "]"
        );
    }

}
