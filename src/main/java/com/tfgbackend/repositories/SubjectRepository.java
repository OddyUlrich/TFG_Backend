package com.tfgbackend.repositories;

import com.tfgbackend.model.Subject;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends MongoRepository<Subject, Long> {

    @Query("{ 'studentsList.$id': ?0 }")
    List<Subject> findAllByStudentID(ObjectId studentId);

}
