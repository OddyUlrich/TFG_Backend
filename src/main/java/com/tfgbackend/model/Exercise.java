package com.tfgbackend.model;

import com.tfgbackend.annotations.isTeacher;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("exercises")
public record Exercise(
        @Indexed(unique = true) @NotBlank String name,
        @NotBlank String statement,
        @NotBlank List<String> rules,
        @NotBlank String successCondition,
        @DBRef List<Tag> tags,
        @DBRef(lazy = true) @NotBlank @isTeacher User teacher) {

    @Override
    public String toString() {
        return String.format(
                "User[" + name + ", Statement: " + statement + /*", Tags: " + tags.toString() +*/ "]"
        );
    }
}
