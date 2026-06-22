package com.tfgbackend.service;

import com.tfgbackend.dto.ExerciseDTO;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.mappers.ExerciseMapper;
import com.tfgbackend.model.*;
import com.tfgbackend.repository.ExerciseRepository;
import com.tfgbackend.dto.ExerciseHomeDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExerciseService {

    private final ExerciseRepository er;
    private final UserService us;
    private final ExerciseBatteryService ebs;
    private final TagService ts;

    @Autowired
    public ExerciseService(ExerciseRepository er, UserService us, ExerciseBatteryService ebs, TagService ts) {
        this.er = er;
        this.us = us;
        this.ebs = ebs;
        this.ts = ts;
    }

    public List<ExerciseHomeDTO> allExercisesWithLastSolutionsByUserId(String email){
        User user = us.getUserByEmail(email);
        return er.allExercisesWithLastSolutionsByUserId(new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Data could not be obtained"));
    }

    public Exercise findExerciseById(String exerciseId){
        return er.findExerciseById(exerciseId).orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with that ID"));
    }

    public ExerciseDTO findExerciseForEditorById(String exerciseId){
        return er.findExerciseForEditorById(new ObjectId(exerciseId)).orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with that ID (Data for Editor)"));
    }

    public Exercise createFromDTO(ExerciseDTO exerciseDTO, String teacherEmail){

        ExerciseBattery battery = ebs.findBatteryByName(exerciseDTO.getNameFromBattery());
        if (battery == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have not selected an exercise battery or it does not exist");
        validateUniqueExercise(exerciseDTO.getName(), battery);

        //TODO COMPROBAR QUE SEA TEACHER
        User teacher = us.getUserByEmail(teacherEmail);
        List<Tag> tags = ts.findByNameIn(exerciseDTO.getTags());

        Exercise exercise = ExerciseMapper.toEntity(exerciseDTO, battery, tags, teacher, LocalDateTime.now());

        return er.save(exercise);

    }

    private void validateUniqueExercise(String name, ExerciseBattery battery) {
        if (er.existsByNameAndExerciseBattery(name, battery)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An exercise with the same name and battery already exists");
        }
    }

}
