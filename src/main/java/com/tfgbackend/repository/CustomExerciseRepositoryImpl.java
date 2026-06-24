package com.tfgbackend.repository;

import com.tfgbackend.dto.ExerciseHomeMongoDTO;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomExerciseRepositoryImpl implements CustomExerciseRepository{

    private final MongoOperations operations;

    @Autowired
    public CustomExerciseRepositoryImpl(MongoOperations operations) {
        this.operations = operations;
    }

    @Override
    public List<ExerciseHomeMongoDTO> allExercisesWithFilters(
            ObjectId studentId,
            String exerciseName,
            String batteryName,
            List<String> tags
    ) {

        List<AggregationOperation> pipeline = new ArrayList<>();

        pipeline.add(Aggregation.lookup(
                "exerciseBatteries",
                "exerciseBattery.$id",
                "_id",
                "exerciseBattery"
        ));

        pipeline.add(Aggregation.unwind("exerciseBattery"));

        pipeline.add(Aggregation.lookup(
                "tags",
                "tags.$id",
                "_id",
                "tags"
        ));

        pipeline.add(context -> new Document("$lookup",
                new Document("from", "solutions")
                        .append("localField", "_id")
                        .append("foreignField", "exercise.$id")
                        .append("pipeline", List.of(
                                new Document("$match",
                                        new Document("$expr",
                                                new Document("$eq", List.of("$student.$id", studentId))
                                        )
                                )
                        ))
                        .append("as", "solution")
        ));

        pipeline.add(context -> new Document("$lookup",
                new Document("from", "users")
                        .append("pipeline", List.of(
                                new Document("$match",
                                        new Document("$expr",
                                                new Document("$eq", List.of("$_id", studentId))
                                        )
                                )
                        ))
                        .append("as", "student")
        ));

        pipeline.add(Aggregation.unwind("student"));



        //FILTROS
        List<Criteria> criteriaList = new ArrayList<>();

        if (exerciseName != null && !exerciseName.isBlank()) {
            criteriaList.add(Criteria.where("name").regex(exerciseName, "i"));
        }

        if (batteryName != null && !batteryName.isBlank()) {
            criteriaList.add(Criteria.where("exerciseBattery.name").regex(batteryName, "i"));
        }

        if (tags != null && !tags.isEmpty()) {
            criteriaList.add(Criteria.where("tags.name").in(tags));
        }

        if (!criteriaList.isEmpty()) {
            pipeline.add(Aggregation.match(new Criteria().andOperator(
                    criteriaList.toArray(new Criteria[0])
            )));
        }

        //ADD FIELDS
        pipeline.add(context -> new Document("$addFields", new Document()
                .append("favorite", new Document("$in", List.of("$_id", "$student.favoriteExercises")))
        ));

        //PROJECT
        pipeline.add(Aggregation.project()
                .andInclude("id", "name", "tags", "favorite", "creationTimestamp")
                .and("exerciseBattery.name").as("batteryName")
                .andInclude("solution")
        );

        Aggregation aggregation = Aggregation.newAggregation(pipeline);

        return operations.aggregate(
                aggregation,
                "exercises",
                ExerciseHomeMongoDTO.class
        ).getMappedResults();
    }
}
