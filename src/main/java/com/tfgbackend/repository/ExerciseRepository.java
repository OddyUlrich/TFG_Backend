package com.tfgbackend.repository;

import com.tfgbackend.dto.ExerciseDTO;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.ExerciseBattery;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends MongoRepository<Exercise, String>, CustomExerciseRepository {

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

            "{$lookup:" +
                "{" +
                    "from: 'tags'," +
                    "localField: 'tags.$id'," +
                    "foreignField: '_id'," +
                    "as: 'tags'," +
                "}" +
            "}",

            "{$project:" +
                    "{" +
                    "   _id: 1," +
                    "   name: 1," +
                    "   statement: 1," +
                    "   rules: 1," +
                    "   successConditions: 1," +
                    "   tags: { " +
                            " $map: { " +
                                " input: '$tags', " +
                                " as: 'tag', " +
                                " in: '$$tag.name', " +
                            "}" +
                        "}," +
                    "   idFromBattery: '$exerciseBattery._id'," +
                    "   nameFromBattery: '$exerciseBattery.name'" +
                    "}" +
            "}"
    })
    Optional<ExerciseDTO> findExerciseForEditorById(ObjectId id);

    Optional<Exercise> findExerciseById(String id);

    boolean existsByNameAndExerciseBattery(String name, ExerciseBattery exerciseBattery);
}



