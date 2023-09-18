package com.tfgbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfgbackend.model.EditableMethod;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ExerciseFileDTO {

    private String id;
    private String name;
    private String path;
    private String idFromSolution;
    private List<EditableMethod> editableMethods; //Este campo ya nos permite saber si este archivo es editable por el alumno o no

    @JsonIgnore
    private byte[] contentBinary;
    private String content;

    public ExerciseFileDTO(String id, String name, String path, byte[] contentBinary, String idFromSolution, List<EditableMethod> editableMethods) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.contentBinary = contentBinary;
        this.content = new String(contentBinary, StandardCharsets.US_ASCII);
        this.idFromSolution = idFromSolution;
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

    public byte[] getContentBinary() {
        return contentBinary;
    }

    public void setContentBinary(byte[] contentBinary) {
        this.contentBinary = contentBinary;
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
