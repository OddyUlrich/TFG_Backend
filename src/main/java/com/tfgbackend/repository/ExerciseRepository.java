package com.tfgbackend.repository;

import com.tfgbackend.dto.ExerciseDTO;
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
                        'initialValue': null,
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
                    "'numberErrorsSolution': '$latestSolution.numberErrors'," +
                    "'timestampSolution': '$latestSolution.creationTimestamp'," +
                    "'statusSolution': '$latestSolution.status'} }",

    })
    Optional<List<ExerciseHomeDTO>> allExercisesWithSolutionByUserId(ObjectId studentId);

    @Aggregation(pipeline = {
            "{$match: {'_id': ?0}}",

            "{$lookup:" +
                "{" +
                    "from: 'exerciseBatteries'," +
                    "localField: 'exerciseBattery.$id'," +
                    "foreignField: '_id'," +
                    "as: 'exerciseBattery'," +
                "}" +
            "}",

            "{$unwind: { path: '$exerciseBattery'}}",

            "{$project:" +
                    "{" +
                    "   _id: 1," +
                    "   name: 1," +
                    "   statement: 1," +
                    "   rules: 1," +
                    "   successConditions: 1," +
                    "   tags: 1," +
                    "   idFromBattery: '$exerciseBattery._id'," +
                    "   nameFromBattery: '$exerciseBattery.name'" +
                    "}" +
            "}"
    })
    Optional<ExerciseDTO> findExerciseForEditorById(ObjectId id);

    Optional<Exercise> findExerciseById(String id);
}



