package com.tfgbackend.repository;

import com.tfgbackend.model.Solution;
import com.tfgbackend.model.User;
import com.tfgbackend.model.enumerator.StatusExercise;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class CustomSolutionRepositoryImpl implements CustomSolutionRepository {

    private final MongoOperations operations;

    @Autowired
    public CustomSolutionRepositoryImpl(MongoOperations operations) {

        Assert.notNull(operations, "MongoOperations must not be null!");
        this.operations = operations;
    }

    @Override
    public void updateSolutionStatus(String solutionId, StatusExercise status){

        Query query = new Query().addCriteria(Criteria.where("_id").is(new ObjectId(solutionId)));
        Update updateDefinition = new Update().set("status", status.toString());
        FindAndModifyOptions options = new FindAndModifyOptions().upsert(false);

        operations.findAndModify(query, updateDefinition, options, Solution.class);
    }
}
