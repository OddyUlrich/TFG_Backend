package com.tfgbackend.repositories;

import com.tfgbackend.model.ExerciseFiles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseFileRepository extends MongoRepository<ExerciseFiles, Long> {
}
