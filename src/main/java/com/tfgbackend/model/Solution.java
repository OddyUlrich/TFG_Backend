package com.tfgbackend.model;

import com.tfgbackend.model.enumerators.StatusExercise;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("solutions")
public record Solution(
        @Id String id,
        @NotNull LocalDateTime creationTimestamp,
        @NotNull StatusExercise status,
        @DBRef(lazy = true) @NotNull  /*IsStudent*/ @Valid User student,
        @DBRef(lazy = true) @NotNull @Valid Exercise exercise,
        Integer numberErrors
        //"String codigo" y "@DBRef(lazy = true) @NotNull File file" o "File file" con el mismo nombre que en el template para que pueda ser sustituido
) {
    public Solution(LocalDateTime creationTimestamp, User student, Exercise exercise, Integer numberErrors) {
        this(null, creationTimestamp, StatusExercise.PENDING, student, exercise,0);
    }

    @Override
    public String toString() {
        return String.format(
                "Solution[" + id + "," + student.getUsername() + "," + exercise.getName() + "]"
        );
    }
}
