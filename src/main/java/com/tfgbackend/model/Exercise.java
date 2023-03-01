package com.tfgbackend.model;

import com.tfgbackend.annotations.IsTeacher;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("exercises")
public record Exercise(
        @Id ObjectId id,
        @Indexed(unique = true) @NotBlank String name,
        /*@NotBlank*/ String statement,
        /*@NotEmpty*/ List<String> rules,
        /*@NotBlank*/ String successCondition,
        @DBRef List<Tag> tags,
        @DBRef(lazy = true) @NotNull @Valid ExerciseBattery exerciseBattery,
        @DBRef(lazy = true) @NotNull @IsTeacher @Valid User teacher) {

    @Override
    public String toString() {
        return String.format(
                "Exercise[" + name + ", Statement: " + statement + ", Tags: " + tags.toString() + "]"
        );
    }
}
