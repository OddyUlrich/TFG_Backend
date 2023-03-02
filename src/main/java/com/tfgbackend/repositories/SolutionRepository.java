package com.tfgbackend.repositories;

import com.tfgbackend.model.Solution;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends MongoRepository<Solution, Long> {

    Solution findById(ObjectId id);

}
