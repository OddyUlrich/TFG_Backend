package com.tfgbackend.repositories;

import com.tfgbackend.dto.ExerciseFilesDTO;
import com.tfgbackend.model.ExerciseFiles;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseFileRepository extends MongoRepository<ExerciseFiles, Long> {

    @Aggregation(pipeline = {
            "{'$match': {'$expr': {'$eq': ['$_id', ?0]}}}"
    })
    Optional<ExerciseFilesDTO> exerciseFilesAndSolutionByIdAndStudent(ObjectId studentId, ObjectId exerciseId);
}
