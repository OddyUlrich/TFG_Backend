package com.tfgbackend.model;

import org.springframework.data.annotation.Id;

public record Tag(
        @Id String name) {

    @Override
    public String toString() {
        return String.format(
                "Tag[" + name + "]"
        );
    }
}
