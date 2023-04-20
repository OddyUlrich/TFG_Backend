package com.tfgbackend.repositories;

import com.tfgbackend.model.Exercise;
import com.tfgbackend.dto.ExerciseHomeDTO;
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
                    "'pipeline': [{'$match': {'$expr': {'$eq': ['$student.$id', ?0]}}}], " +
                    "'as': 'solution'} }",
            "{'$lookup': { " +
                    "'from': 'users', " +
                    "'pipeline': [{'$match': {'$expr': {'$eq': ['$_id', ?0]}}}], " +
                    "'as': 'student'}}",
            "{'$unwind': {path: '$student'}, }",
            """
            {'$addFields': {
                'favorite': {'$in': ['$_id', '$student.favoriteExercises']},
                'latestSolution': {
                    '$reduce': {
                    'input': '$solution',
                    'initialValue': None,
                    'in': {
                    '$cond': {
                        'if': {
                            '$eq': ['$$this.status', 'COMPLETED']
                        },
                        'then': '$$this',
                        'else': {
                            '$cond': {
                                'if': {
                                    '$and': [
                                        {
                                        '$ne': ['$$value.status', 'COMPLETED']
                                        }, {
                                        '$eq': ['$$this.status', 'PENDING']
                                        }, {
                                        '$gt': ['$$this.creationTimestamp', '$$value.creationTimestamp']
                                        }
                                            ]
                                        },
                                        'then': '$$this',
                                        'else': '$$value'
                                    }
                                }
                            }
                        }
                    }
                }
            }
            }
            """,
            "{'$project': {" +
                    "'id': 1, " +
                    "'name': 1, " +
                    "'tags': 1, " +
                    "'favorite': 1,"+
                    "'creationTimestamp': 1," +
                    "'batteryName': '$exerciseBattery.name'," +
                    "'numberErrorsSolution': {$ifNull: ['$latestSolution.numberErrors', 0]}," +
                    "'timestampSolution': '$latestSolution.creationTimestamp'," +
                    "'statusSolution': '$latestSolution.status'} }",

    })
    Optional<List<ExerciseHomeDTO>> allExerciseSolutionsByUserId(ObjectId studentId);

    Optional<Exercise> findExerciseById(String id);
}



