package com.tfgbackend.repositories;

import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Subject;
import com.tfgbackend.service.dto.ExerciseSolutionDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseBatteryRepository extends MongoRepository<ExerciseBattery, Long> {

    //@Query("{'subject': { $in: $0 } }")


    @Aggregation(pipeline = {
            "{'$match': {'subject':  { $in:  $0} } }",
            "{'$unwind': 'exerciseList'}"

    })
    List<ExerciseBattery> allExerciseBatteryByUserSubjects(List<Subject> subjects);
    //List<ExerciseSolutionDTO> allExerciseBatteryByUserSubjects(List<Subject> subjects);

    //"{'$lookup' : {from: 'solutions', 'localfield': 'exercise'}} "


}