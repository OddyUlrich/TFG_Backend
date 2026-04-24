package com.tfgbackend.controller;

import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.service.ExerciseBatteryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

        try {
            if (auth != null && auth.isAuthenticated()) {
                List<ExerciseBattery> allExerciseBatteries = exerciseBatteryService.findAll();

                return ResponseEntity.status(HttpStatus.OK).body(allExerciseBatteries);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }


    }

}
