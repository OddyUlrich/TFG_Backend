package com.tfgbackend.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("users")
public record User(
        @Indexed(unique = true) @NotBlank String name,
        @Indexed(unique = true) @NotBlank String email,
        LocalDateTime birthday,
        Rol rol) {

    @Override
    public String toString() {
        return String.format(
                "User[" + name + "," + email + "," + rol + "]"
        );
    }
}
