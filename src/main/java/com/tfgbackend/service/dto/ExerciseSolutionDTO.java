package com.tfgbackend.service.dto;

import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.ExerciseBattery;
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
    private ExerciseBattery exerciseBattery;
    private User teacher;
    int numberErrorsSolution;
    LocalDateTime timestampSolution;
    StatusExercise statusSolution;
    //private List<String> rules; TODO revisar si esto acaba haciendo falta
    //private String successCondition; TODO revisar si esto acaba haciendo falta
    //User student TODO revisar si esto acaba haciendo falta

    public ExerciseSolutionDTO(String name, String statement, List<Tag> tags, ExerciseBattery exerciseBattery, User teacher, int numberErrorsSolution, LocalDateTime timestampSolution, StatusExercise statusSolution){
        this.name = name;
        this.statement = statement;
        this.tags = tags;
        this.exerciseBattery = exerciseBattery;
        this.teacher = teacher;
        this.numberErrorsSolution = numberErrorsSolution;
        this.timestampSolution = timestampSolution;
        this.statusSolution = statusSolution;
    }

    @Override
    public String toString() {
        return String.format(
                "ExerciseSolutionDTO[" + name + ", Statement: " + statement + ", Tags: " + tags.toString() + ", Bateria:" + exerciseBattery.name() + ", Status: " + statusSolution + "]"
        );
    }
}
