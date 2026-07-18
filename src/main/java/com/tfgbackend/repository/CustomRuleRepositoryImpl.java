package com.tfgbackend.repository;

import com.tfgbackend.dto.RuleSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomRuleRepositoryImpl implements CustomRuleRepository {

    private final MongoOperations operations;

    @Autowired
    public CustomRuleRepositoryImpl(MongoOperations operations) {

        Assert.notNull(operations, "MongoOperations must not be null!");
        this.operations = operations;
    }

    @Override
    public List<RuleSearchDTO> searchByDescriptionOrExerciseName(String rulesFilter) {

        List<AggregationOperation> pipeline = new ArrayList<>();

        pipeline.add(Aggregation.lookup(
                "exercises",
                "_id",
                "rules.$id",
                "exercise"
        ));

        pipeline.add(Aggregation.unwind("exercise", true));

        pipeline.add(Aggregation.lookup(
                "exerciseBatteries",
                "exercise.exerciseBattery.$id",
                "_id",
                "exerciseBattery"
        ));

        pipeline.add(Aggregation.unwind("exerciseBattery", true));

        //FILTROS
        List<Criteria> criteriaList = new ArrayList<>();

        if (rulesFilter != null && !rulesFilter.isBlank()) {
            criteriaList.add(new Criteria().orOperator(
                    Criteria.where("description").regex(rulesFilter, "i"),
                    Criteria.where("exercise.name").regex(rulesFilter, "i")
            ));
        }

        if (!criteriaList.isEmpty()) {
            pipeline.add(Aggregation.match(new Criteria().andOperator(
                    criteriaList.toArray(new Criteria[0])
            )));
        }

        //PROJECT
        pipeline.add(Aggregation.project()
                .andInclude("id", "description", "type")
                .and("exercise.name").as("exerciseName")
                .and("exerciseBattery.name").as("batteryName")
        );

        Aggregation aggregation = Aggregation.newAggregation(pipeline);

        return operations.aggregate(
                aggregation,
                "rules",
                RuleSearchDTO.class
        ).getMappedResults();
    }
}
