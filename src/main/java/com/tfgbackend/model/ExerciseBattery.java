package com.tfgbackend.model;

import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("exerciseBatteries")
public class ExerciseBattery {
    @Id
    private String id;
    @Indexed(unique = true)
    private @NotBlank String name;

    public ExerciseBattery(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ExerciseBattery(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "ExerciseBattery[" + name + "]"
        );
    }
}
