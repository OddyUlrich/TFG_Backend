package com.tfgbackend.model;

import com.tfgbackend.annotations.IsTeacher;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("subjects")
@CompoundIndex(def = "{'name': 1, 'year': 1}, unique = true")
public class Subject{

    @Id
    private ObjectId id;
    private String name;
    private int year;
    //No se comprueba que la lista este vacia porque en algun momento podra estarlo
    @DBRef(lazy = true)
    private List<@IsTeacher @Valid User> teachersList;
    @DBRef(lazy = true)
    private List</*@IsStudent*/ @Valid User> studentsList;

    public Subject(ObjectId id, String name, int year, List<User> teachersList, List<User> studentsList) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.teachersList = teachersList;
        this.studentsList = studentsList;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<User> getTeachersList() {
        return teachersList;
    }

    public void setTeachersList(List<User> teachersList) {
        this.teachersList = teachersList;
    }

    public List<User> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(List<User> studentsList) {
        this.studentsList = studentsList;
    }
}