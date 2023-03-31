package com.tfgbackend.repositories;

import com.tfgbackend.model.Exercise;
import com.tfgbackend.service.dto.ExerciseSolutionDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends MongoRepository<Exercise, Long> {

    @Aggregation(pipeline = {
            "{ '$lookup': {" +
                    "'from': 'exerciseBatteries'," +
                    "'localField': 'exerciseBattery.$id'," +
                    "'foreignField': '_id'," +
                    "'as': 'exerciseBattery'} }",
            "{'$unwind': {path: '$exerciseBattery'} }",
            "{'$lookup': { " +
                    "'from': 'solutions'," +
                    "'localField': '_id'," +
                    "'foreignField': 'exercise.$id'," +
                    "'as': 'solution'} }",
            "{'$unwind': {path: '$solution', preserveNullAndEmptyArrays: true}, }",
            "{'$lookup': { " +
                    "'from': 'users', " +
                    "'pipeline': [{'$match': {'$expr': {'$eq': ['$_id', ?0]}}}], " +
                    "'as': 'user'}}",
            "{'$unwind': {path: '$user'}, }",
            "{'$addFields': {" +
                    "'favorite': {'$in': ['$_id', '$user.favoriteExercises']}}}",
            "{'$project': {" +
                    "'id': 1, " +
                    "'name': 1, " +
                    "'statement': 1, " +
                    "'tags': 1, " +
                    "'favorite': 1,"+
                    "'batteryName': '$exerciseBattery.name'," +
                    "'numberErrorsSolution': '$solution.numberErrors'," +
                    "'timestampSolution': '$solution.timestamp'," +
                    "'statusSolution': {$ifNull: ['$solution.status','$false']} } }",

    })
    Optional<List<ExerciseSolutionDTO>> allExerciseSolutionsByUserId(ObjectId studentId);

    Optional<Exercise> findExerciseById(String id);
}
