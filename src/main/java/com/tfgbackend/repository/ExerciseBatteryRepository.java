package com.tfgbackend.repository;

import com.tfgbackend.model.ExerciseBattery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseBatteryRepository extends MongoRepository<ExerciseBattery, Long> {

    List<ExerciseBattery> findAll();

    Optional<ExerciseBattery> findByName(String name);
}