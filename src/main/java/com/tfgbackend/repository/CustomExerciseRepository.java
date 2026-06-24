package com.tfgbackend.repository;

import com.tfgbackend.dto.ExerciseHomeMongoDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface CustomExerciseRepository {
    List<ExerciseHomeMongoDTO> allExercisesWithFilters(ObjectId studentId,
                                                                 String exerciseName,
                                                                 String batteryName,
                                                                 List<String> tags);
}