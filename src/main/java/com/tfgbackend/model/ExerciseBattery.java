package com.tfgbackend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("ExerciseBatteries")
public record ExerciseBattery (
        @Id ObjectId id,
        @Indexed(unique = true) @NotBlank String name,
        @DBRef(lazy = true) List<Exercise> exerciseList,
        @DBRef(lazy = true) @NotNull Subject subject){

    @Override
    public String toString() {
        return String.format(
                "User[" + name + "]"
        );
    }
}
