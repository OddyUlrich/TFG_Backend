package com.tfgbackend.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tags")
public record Tag(
        @Id
        String id,
        @Indexed(unique = true)
        @NotBlank String name
) {

    public Tag(@NotBlank String name) {
        this(null, name);
    }

    @Override
    public String toString() {
        return String.format(
                "Tag[" + name + "]"
        );
    }
}
