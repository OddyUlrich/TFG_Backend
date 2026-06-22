package com.tfgbackend.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("files")
public class ExerciseFile {
    @Id
    private String id;
    private @NotBlank String name;
    private @NotBlank String path;
    private @NotBlank byte[] binaryText;

    @DBRef(lazy = true)
    private @NotNull @Valid Exercise exercise;
    @DBRef(lazy = true)
    private @Valid Solution solution;

    private List<EditableMethod> editableMethods;

    public ExerciseFile() {
    }

    public ExerciseFile(String name, String path, byte[] binaryText, Exercise exercise, Solution solution, List<EditableMethod> editableMethods) {
        this.name = name;
        this.path = path;
        this.binaryText = binaryText;
        this.exercise = exercise;
        this.solution = solution;
        this.editableMethods = editableMethods;
    }

    public ExerciseFile(String id, String name, String path, byte[] binaryText, Exercise exercise, Solution solution, List<EditableMethod> editableMethods) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.binaryText = binaryText;
        this.exercise = exercise;
        this.solution = solution;
        this.editableMethods = editableMethods;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getBinaryText() {
        return binaryText;
    }

    public void setBinaryText(byte[] binaryText) {
        this.binaryText = binaryText;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public List<EditableMethod> getEditableMethods() {
        return editableMethods;
    }

    public void setEditableMethods(List<EditableMethod> editableMethods) {
        this.editableMethods = editableMethods;
    }
}



