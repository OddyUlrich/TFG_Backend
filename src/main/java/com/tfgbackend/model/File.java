package com.tfgbackend.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("files")
@CompoundIndex(def = "{'name': 1, 'exercise': 1}", unique = true)
public record File(
        @Id ObjectId id,
        @NotBlank String name,
        @NotBlank String path,
        @DBRef(lazy = true) @NotNull @Valid Exercise exercise
) {


}
