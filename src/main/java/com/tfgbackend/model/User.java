package com.tfgbackend.model;

import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("users")
public record User(
        @Id ObjectId id,
        @Indexed(unique = true) @NotBlank String name,
        @Indexed(unique = true) @NotBlank String email,
        LocalDateTime birthday,
        List<Subject> subjects,
        @NotBlank Rol rol) {

    @Override
    public String toString() {
        return String.format(
                "User[" + name + "," + email + "," + rol + "]"
        );
    }
}
