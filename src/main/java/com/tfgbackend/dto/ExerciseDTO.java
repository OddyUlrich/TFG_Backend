package com.tfgbackend.dto;

import com.tfgbackend.model.Rule;
import com.tfgbackend.model.Tag;
import java.util.List;

public class ExerciseDTO {

    private String id;
    private String name;
    private String statement;
    private List<Rule> requiredRules;
    private List<Rule> forbiddenRules;
    private List<String> tags;
    private String nameFromBattery;

    public ExerciseDTO(String id, String name, String statement, String nameFromBattery, List<Rule> requiredRules, List<Rule> forbiddenRules, List<String> tags) {
        this.id = id;
        this.name = name;
        this.statement = statement;
        this.nameFromBattery = nameFromBattery;
        this.requiredRules = requiredRules;
        this.forbiddenRules = forbiddenRules;
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

    public List<Rule> getRequiredRules() {
        return requiredRules;
    }

    public void setRequiredRules(List<Rule> requiredRules) {
        this.requiredRules = requiredRules;
    }

    public List<Rule> getForbiddenRules() {
        return forbiddenRules;
    }

    public void setForbiddenRules(List<Rule> forbiddenRules) {
        this.forbiddenRules = forbiddenRules;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getNameFromBattery() {
        return nameFromBattery;
    }

    public void setNameFromBattery(String nameFromBattery) {
        this.nameFromBattery = nameFromBattery;
    }
}
