package com.tfgbackend.controller;

import com.tfgbackend.dto.SolutionCreationDTO;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Solution;
import com.tfgbackend.service.ExerciseBatteryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

@RequestMapping("/exerciseBatteries")
public class ExerciseBatteryController {

    private final ExerciseBatteryService exerciseBatteryService;

    public ExerciseBatteryController(ExerciseBatteryService exerciseBatteryService) {
        this.exerciseBatteryService = exerciseBatteryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExerciseBattery>> allExerciseBatteries(Authentication auth) {

        if (auth != null && auth.isAuthenticated()) {
            List<ExerciseBattery> allExerciseBatteries = exerciseBatteryService.findAll();

            return ResponseEntity.status(HttpStatus.OK).body(allExerciseBatteries);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExerciseBattery> saveBattery(@RequestBody ExerciseBattery data, Authentication auth) {

        String batteryName = data.getName();

        if (auth != null && auth.isAuthenticated()) {

            ExerciseBattery batteryInDatabase = exerciseBatteryService.findBatteryByName(batteryName);

            if (batteryInDatabase != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }

            ExerciseBattery newBattery = new ExerciseBattery(batteryName);
            exerciseBatteryService.saveBattery(newBattery);

            return ResponseEntity.status(HttpStatus.CREATED).body(newBattery);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
