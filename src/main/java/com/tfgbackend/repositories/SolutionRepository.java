package com.tfgbackend.repositories;

import com.tfgbackend.dto.SolutionDTO;
import com.tfgbackend.model.Solution;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface SolutionRepository extends MongoRepository<Solution, String> {

    @Aggregation(pipeline = {
            "{$match: {" +
                    "$and: [{" +
                    "          'exercise.$id': ?0" +
                    "        }," +
                    "        {" +
                    "          'student.$id': ?1" +
                    "        }" +
                    "]" +
            "}}",
            "{$project:" +
                    "{" +
                    "   _id: 1," +
                    "   name: 1," +
                    "   status: 1," +
                    "   lastUpdate: '$updateTimestamp'," +
                    "   numberErrors: 1," +
                    "}" +
            "}"
    })
    Optional<List<SolutionDTO>> allSolutionsByExerciseIdAndStudent(Object studentId, ObjectId exerciseId);

    Optional<Solution> findById(String id);
}
