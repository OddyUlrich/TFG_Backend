package com.tfgbackend.model;

import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("exerciseBatteries")
public class ExerciseBattery {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private @NotBlank String name;
//    @DBRef(lazy = true)
//    private List<@Valid Exercise> exerciseList;/*confirmar que debe seguir aqui*/

    public ExerciseBattery(ObjectId id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return String.format(
                "ExerciseBattery[" + name + "]"
        );
    }
}
