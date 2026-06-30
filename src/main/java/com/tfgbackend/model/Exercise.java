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
    private String statement;
    @DBRef(lazy = true)
    private @Valid ExerciseBattery exerciseBattery;
    private List<Rule> rules;
    @DBRef(lazy = true)
    private List<Tag> tags;
    @DBRef(lazy = true)
    private @NotNull
    @IsTeacher
    @Valid User teacher;
    private @NotNull LocalDateTime creationTimestamp;

    public Exercise(String id, String name, String statement, ExerciseBattery exerciseBattery, List<Rule> rules, List<Tag> tags, User teacher, LocalDateTime creationTimestamp) {
        this.id = id;
        this.name = name;
        this.statement = statement;
        this.exerciseBattery = exerciseBattery;
        this.rules = rules;
        this.tags = tags;
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

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
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
        return String.format("Exercise[" + name + ", Statement: " + statement + ", Tags: " + tags.toString() + "]");
    }
}
