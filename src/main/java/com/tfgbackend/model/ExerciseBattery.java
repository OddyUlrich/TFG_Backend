package com.tfgbackend.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("exerciseBatteries")
public record ExerciseBattery (
        @Id ObjectId id,
        @Indexed(unique = true) @NotBlank String name,
        @DBRef(lazy = true) List<@Valid Exercise> exerciseList /*confirmar que debe seguir aqui*/,
        @DBRef(lazy = true) @NotNull @Valid Subject subject){

    @Override
    public String toString() {
        return String.format(
                "ExerciseBattery[" + name + "]"
        );
    }
}
