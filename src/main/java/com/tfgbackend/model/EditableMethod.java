package com.tfgbackend.model;

public class EditableMethod {

    private String name;
    private int line;

    public EditableMethod(String name, int line) {
        this.name = name;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
