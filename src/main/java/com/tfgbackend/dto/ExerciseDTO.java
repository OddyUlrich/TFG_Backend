package com.tfgbackend.dto;

import com.tfgbackend.model.Rule;
import com.tfgbackend.model.Tag;
import java.util.List;

public class ExerciseDTO {

    private String id;
    private String name;
    private String statement;
    private List<Rule> rules;
    private String successCondition;
    private List<Tag> tags;
    private String idFromBattery;
    private String nameFromBattery;

    public ExerciseDTO(String id, String name, String statement, List<Rule> rules, String successCondition, List<Tag> tags, String nameFromBattery) {
        this.id = id;
        this.name = name;
        this.statement = statement;
        this.rules = rules;
        this.successCondition = successCondition;
        this.tags = tags;
        this.nameFromBattery = nameFromBattery;
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

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public String getSuccessCondition() {
        return successCondition;
    }

    public void setSuccessCondition(String successCondition) {
        this.successCondition = successCondition;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getIdFromBattery() {
        return idFromBattery;
    }

    public void setIdFromBattery(String idFromBattery) {
        this.idFromBattery = idFromBattery;
    }

    public String getNameFromBattery() {
        return nameFromBattery;
    }

    public void setNameFromBattery(String nameFromBattery) {
        this.nameFromBattery = nameFromBattery;
    }
}
