package com.tfgbackend.repository;

import com.tfgbackend.model.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository{

    private final MongoOperations operations;

    @Autowired
    public CustomUserRepositoryImpl(MongoOperations operations) {

        Assert.notNull(operations, "MongoOperations must not be null!");
        this.operations = operations;
    }

    public void updateUserFavorites(String userId, List<ObjectId> favoriteList){

        Query query = new Query().addCriteria(Criteria.where("_id").is(userId));
        Update updateDefinition = new Update().set("favoriteExercises", favoriteList);
        FindAndModifyOptions options = new FindAndModifyOptions().upsert(false);

        operations.findAndModify(query, updateDefinition, options, User.class);
    }
}
