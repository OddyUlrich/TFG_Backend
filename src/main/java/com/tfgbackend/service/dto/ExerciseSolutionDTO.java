package com.tfgbackend.service.dto;

import com.tfgbackend.model.Tag;
import com.tfgbackend.model.User;
import com.tfgbackend.model.enumerators.StatusExercise;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

public class ExerciseSolutionDTO {

    private ObjectId id;
    private String name;
    private String statement;
    private List<Tag> tags;
    private String batteryName;
    private User teacher;
    private int numberErrorsSolution;
    private LocalDateTime timestampSolution;
    private StatusExercise statusSolution;
    //private List<String> rules; TODO revisar si esto acaba haciendo falta
    //private String successCondition; TODO revisar si esto acaba haciendo falta
    //User student TODO revisar si esto acaba haciendo falta

    public ExerciseSolutionDTO(String name, String statement, List<Tag> tags, String batteryName, User teacher, int numberErrorsSolution, LocalDateTime timestampSolution, StatusExercise statusSolution){
        this.name = name;
        this.statement = statement;
        this.tags = tags;
        this.batteryName = batteryName;
        this.teacher = teacher;
        this.numberErrorsSolution = numberErrorsSolution;
        this.timestampSolution = timestampSolution;
        this.statusSolution = statusSolution;
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

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    public String getBatteryName() {
        return batteryName;
    }

    public void setBatteryName(String batteryName) {
        this.batteryName = batteryName;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public int getNumberErrorsSolution() {
        return numberErrorsSolution;
    }

    public void setNumberErrorsSolution(int numberErrorsSolution) {
        this.numberErrorsSolution = numberErrorsSolution;
    }

    public LocalDateTime getTimestampSolution() {
        return timestampSolution;
    }

    public void setTimestampSolution(LocalDateTime timestampSolution) {
        this.timestampSolution = timestampSolution;
    }

    public StatusExercise getStatusSolution() {
        return statusSolution;
    }

    public void setStatusSolution(StatusExercise statusSolution) {
        this.statusSolution = statusSolution;
    }

    @Override
    public String toString() {
        return String.format(
                "ExerciseSolutionDTO[" + name + ", Statement: " + statement + ", Tags: " + tags.toString() + ", Bateria:" + batteryName + ", Status: " + statusSolution + "]"
        );
    }
}
