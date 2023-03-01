package com.tfgbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tags")
public record Tag(
        @Id String name) {

    @Override
    public String toString() {
        return String.format(
                "Tag[" + name + "]"
        );
    }
}
