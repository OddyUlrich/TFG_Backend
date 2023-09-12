package com.tfgbackend.dto;

import com.tfgbackend.model.EditableMethod;
import com.tfgbackend.model.Exercise;

import java.util.List;

public class ExerciseFilesDTO {

    private String id;
    private String name;
    private String path;
    private String content;
    private Exercise exercise;
    //private Solution solution;
    private List<EditableMethod> editableMethods; //Este campo ya nos permite saber si este archivo es editable por el alumno o no

    public ExerciseFilesDTO (String id, String name, String path, String content, Exercise exercise, /*Solution solution,*/ List<EditableMethod> editableMethods) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.content = content;
        this.exercise = exercise;
        //this.solution = solution;
        this.editableMethods = editableMethods;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public List<EditableMethod> getEditableMethods() {
        return editableMethods;
    }

    public void setEditableMethods(List<EditableMethod> editableMethods) {
        this.editableMethods = editableMethods;
    }

    @Override
    public String toString() {
        return "ExerciseFilesDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
