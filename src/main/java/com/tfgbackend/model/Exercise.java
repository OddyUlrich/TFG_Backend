package com.tfgbackend.model;

import com.tfgbackend.annotation.IsTeacher;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("exercises")
@CompoundIndex(def = "{'name': 1, 'exerciseBattery': 1}", unique = true)
public class Exercise {
    @Id
    private String id;
    private @NotBlank String name;
    private /*@NotBlank*/ String statement;
    private /*@NotEmpty*/ List<String> rules;
    private /*@NotBlank*/ String successCondition;
    @DBRef
    private List<Tag> tags;
    @DBRef(lazy = true)
    private /*@NotNull*/ @Valid ExerciseBattery exerciseBattery;
    @DBRef(lazy = true)
    private @NotNull @IsTeacher @Valid User teacher;
    private @NotNull LocalDateTime creationTimestamp;

    public Exercise(String id, String name, String statement, List<String> rules, String successCondition, List<Tag> tags, ExerciseBattery exerciseBattery, User teacher, LocalDateTime creationTimestamp) {
        this.id = id;
        this.name = name;
        this.statement = statement;
        this.rules = rules;
        this.successCondition = successCondition;
        this.tags = tags;
        this.exerciseBattery = exerciseBattery;
        this.teacher = teacher;
        this.creationTimestamp = creationTimestamp;
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

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
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

    public ExerciseBattery getExerciseBattery() {
        return exerciseBattery;
    }

    public void setExerciseBattery(ExerciseBattery exerciseBattery) {
        this.exerciseBattery = exerciseBattery;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "Exercise[" + name + ", Statement: " + statement + ", Tags: " + tags.toString() + "]"
        );
    }
}
