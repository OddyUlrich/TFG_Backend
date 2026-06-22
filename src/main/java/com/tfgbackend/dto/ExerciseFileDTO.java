package com.tfgbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfgbackend.model.EditableMethod;

import java.util.List;

public class ExerciseFileDTO {

    private String id;
    private String name;
    private String path;
    private String text;
    private String idFromSolution;
    private List<EditableMethod> editableMethods; //Este campo ya nos permite saber si este archivo es editable por el alumno o no

    @JsonIgnore
    private byte[] binaryText;

    public ExerciseFileDTO() {
    }

    public ExerciseFileDTO(String id, String name, String path, String text, String idFromSolution, List<EditableMethod> editableMethods, byte[] binaryText) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.text = text;
        this.idFromSolution = idFromSolution;
        this.editableMethods = editableMethods;
        this.binaryText = binaryText;
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

    public byte[] getBinaryText() {
        return binaryText;
    }

    public void setBinaryText(byte[] binaryText) {
        this.binaryText = binaryText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIdFromSolution() {
        return idFromSolution;
    }

    public void setIdFromSolution(String idFromSolution) {
        this.idFromSolution = idFromSolution;
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
