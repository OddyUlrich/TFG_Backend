package com.tfgbackend.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("exerciseBatteries")
public class ExerciseBattery {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private @NotBlank String name;
    @DBRef(lazy = true)
    private List<@Valid Exercise> exerciseList;/*confirmar que debe seguir aqui*/

    public ExerciseBattery(ObjectId id, String name, List<Exercise> exerciseList) {
        this.id = id;
        this.name = name;
        this.exerciseList = exerciseList;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @Override
    public String toString() {
        return String.format(
                "ExerciseBattery[" + name + "]"
        );
    }
}
