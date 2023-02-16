package com.tfgbackend.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("ExerciseBatteries")
public record ExerciseBattery (
        @Indexed(unique = true) @NotBlank String name,
        @DBRef(lazy = true) List<Exercise> exerciseList,
        @DBRef(lazy = true) @NotBlank Subject subject){

    @Override
    public String toString() {
        return String.format(
                "User[" + name + "]"
        );
    }
}
