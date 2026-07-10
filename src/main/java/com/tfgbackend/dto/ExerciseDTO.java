package com.tfgbackend.dto;

import com.tfgbackend.model.Rule;
import com.tfgbackend.model.Tag;
import java.util.List;

public class ExerciseDTO {

    private String id;
    private String name;
    private String statement;
    private List<Rule> rules;
    private List<Tag> tags;
    private String nameFromBattery;

    public ExerciseDTO(String id, String name, String statement, String nameFromBattery, List<Rule> rules, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.statement = statement;
        this.nameFromBattery = nameFromBattery;
        this.rules = rules;
        this.tags = tags;
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

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public List<Rule> getRules() {return rules;}

    public void setRules(List<Rule> rules) {this.rules = rules;}

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getNameFromBattery() {
        return nameFromBattery;
    }

    public void setNameFromBattery(String nameFromBattery) {
        this.nameFromBattery = nameFromBattery;
    }
}
