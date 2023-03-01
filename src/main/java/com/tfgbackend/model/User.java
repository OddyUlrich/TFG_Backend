package com.tfgbackend.model;

import com.tfgbackend.model.enumerators.Rol;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
        List<@Valid Subject> subjects,
        @NotNull Rol rol) {

    @Override
    public String toString() {
        return String.format(
                "User[" + name + "," + email + "," + rol + "]"
        );
    }
}