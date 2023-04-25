package com.tfgbackend.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("files")
@CompoundIndex(def = "{'name': 1, 'exercise': 1}", unique = true)
public class ExerciseFiles {
    @Id
    private String id;
    private @NotBlank String name;
    private @NotBlank String path;
    private @NotBlank byte[] content;
    @DBRef(lazy = true)
    private @NotNull @Valid Exercise exercise;

    public ExerciseFiles(String id, String name, String path, byte[] content, Exercise exercise) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.content = content;
        this.exercise = exercise;
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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}


