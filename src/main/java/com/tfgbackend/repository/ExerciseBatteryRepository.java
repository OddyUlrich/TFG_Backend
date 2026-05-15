package com.tfgbackend.repository;

import com.tfgbackend.model.ExerciseBattery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseBatteryRepository extends MongoRepository<ExerciseBattery, Long> {

    List<ExerciseBattery> findAll();

    ExerciseBattery findByName(String name);
}