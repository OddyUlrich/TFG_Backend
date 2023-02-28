package com.tfgbackend.repositories;

import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseBatteryRepository extends MongoRepository<ExerciseBattery, Long> {

    @Query("{'subject': { $in: $0 } }")
    List<ExerciseBattery> allExerciseBatteryByUserSubjects(List<Subject> subjects);

}