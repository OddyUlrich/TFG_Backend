package com.tfgbackend.model;

import com.tfgbackend.annotations.IsTeacher;
import jakarta.validation.Valid;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("subjects")
@CompoundIndex(def = "{'name': 1, 'year': 1}, unique = true")
public record Subject(
        String name,
        int year,
        //No se comprueba que la lista este vacia porque en algun momento podra estarlo
        @DBRef(lazy = true) List<@IsTeacher @Valid User> teachersList,
        @DBRef(lazy = true) List</*@IsStudent*/ @Valid User> studentsList){
}
