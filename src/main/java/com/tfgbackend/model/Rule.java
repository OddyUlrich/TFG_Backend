package com.tfgbackend.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Rule(
        @Id
        String id,
        @Indexed(unique = true)
        @NotBlank
        String name,
        @NotBlank
        String type) {

}
