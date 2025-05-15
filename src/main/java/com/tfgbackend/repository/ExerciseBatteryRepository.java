package com.tfgbackend.repository;

import com.tfgbackend.model.ExerciseBattery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseBatteryRepository extends MongoRepository<ExerciseBattery, Long> {

    //@Query("{'subject.$id': { $in : ?0 } }")
    //List<ExerciseSolutionDTO> allExerciseBatteryByUserSubjects(List<Subject> subjects);

    //"{'$lookup' : {from: 'solutions', 'localfield': 'exercise'}} "


}