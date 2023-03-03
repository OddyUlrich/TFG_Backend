package com.tfgbackend.repositories;

import com.tfgbackend.model.Exercise;
import com.tfgbackend.service.dto.ExerciseSolutionDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends MongoRepository<Exercise, Long> {

    @Aggregation(pipeline = {
            "{ '$lookup': {" +
                    "'from': 'exerciseBatteries'," +
                    "'localField': 'exerciseBattery.$id'," +
                    "'foreignField': '_id'," +
                    "'as': 'exerciseBattery'} }",
            "{'$unwind': {path: '$exerciseBattery'} }",
            "{'$match': {'exerciseBattery.subject.$id': { $in: ?0} } }",
            "{'$lookup': { " +
                    "'from': 'solutions'," +
                    "'localField': '_id'," +
                    "'foreignField': 'exercise.$id'," +
                    "'as': 'solution'} }",
            "{'$unwind': {path: '$solution'} }",
            "{'$project': {" +
                    "'id': 1, " +
                    "'name': 1, " +
                    "'statement': 1, " +
                    "'tags': 1, " +
                    "'subject': '$exerciseBattery.subject', " +
                    "'batteryName': '$exerciseBattery.name'," +
                    "'teacher': 1," +
                    "'numberErrorsSolution': '$solution.numberErrors'," +
                    "'timestampSolution': '$solution.timestamp'," +
                    "'statusSolution': '$solution.status'} }",
    })
    List<ExerciseSolutionDTO> allExerciseByUserSubjects(List<ObjectId> subjects);

}
