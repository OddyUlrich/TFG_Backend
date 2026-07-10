package com.tfgbackend.service;

import com.tfgbackend.dto.ExerciseDTO;
import com.tfgbackend.dto.ExerciseHomeMongoDTO;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.mappers.ExerciseHomeDTOMapper;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository er;
    private final UserService us;
    private final ExerciseBatteryService ebs;
    private final TagService ts;
    private final SolutionService ss;
    private final RulesService rs;

    @Autowired
    public ExerciseService(ExerciseRepository er, UserService us, ExerciseBatteryService ebs, TagService ts, SolutionService ss, RulesService rs) {
        this.er = er;
        this.us = us;
        this.ebs = ebs;
        this.ts = ts;
        this.ss = ss;
        this.rs = rs;
    }

    public List<ExerciseHomeDTO> allExercisesWithLastSolutionsByUserId(String email, String exerciseName, String batteryName, List<String> tags) {
        User user = us.getUserByEmail(email);
        return obtainHomeDTOLatestSolution(er.allExercisesWithFilters(new ObjectId(user.getId()), exerciseName, batteryName, tags));
    }

    public Exercise findExerciseById(String exerciseId){
        return er.findExerciseById(exerciseId).orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with that ID"));
    }

    public ExerciseDTO findExerciseForEditorById(String exerciseId){
        return er.findExerciseForEditorById(new ObjectId(exerciseId)).orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with that ID (Data for Editor)"));
    }

    public Exercise createFromDTO(ExerciseDTO exerciseDTO, String teacherEmail){
        String name = exerciseDTO.getName();
        ExerciseBattery battery = ebs.findBatteryByName(exerciseDTO.getNameFromBattery());
        validateUniqueExercise(name, battery);

        return saveExercise(exerciseDTO, teacherEmail, battery);
    }

    public Exercise editFromDTO(ExerciseDTO exerciseDTO, String teacherEmail){
        er.findExerciseById(exerciseDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Exercise does not exist with that ID"));
        ExerciseBattery battery = ebs.findBatteryByName(exerciseDTO.getNameFromBattery());
        if (battery == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have not selected an exercise battery or it does not exist");

        return saveExercise(exerciseDTO, teacherEmail, battery);
    }

    public Exercise saveExercise(ExerciseDTO exerciseDTO, String teacherEmail, ExerciseBattery battery){
        //TODO COMPROBAR QUE SEA TEACHER
        User teacher = us.getUserByEmail(teacherEmail);
        if (teacher == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User creator does not exist");

        List<Tag> tags = ts.findByNameIn(exerciseDTO.getTags().stream().map(Tag::name).toList());
        if (tags == null || tags.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must select at least one tag");
        }

        Exercise newExercise = ExerciseMapper.toEntity(exerciseDTO, battery, tags, teacher, LocalDateTime.now());

        //TODO MANDAR AL SERVICIO DE RULES PARA COMPROBAR QUE NO SE REPITAN, ES DECIR, CREAR NUEVAS O REUTILIZAR
        newExercise.setRules(rs.saveRules(newExercise.getRules()));

        return er.save(newExercise);
    }

    private void validateUniqueExercise(String name, ExerciseBattery battery) {
        if (battery == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have not selected an exercise battery or it does not exist");

        if (er.existsByNameAndExerciseBattery(name, battery)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An exercise with the same name and battery already exists");
        }
    }

    private List<ExerciseHomeDTO> obtainHomeDTOLatestSolution(List<ExerciseHomeMongoDTO> dtoList) {
        List<ExerciseHomeDTO> newList = new ArrayList<>();

        for (ExerciseHomeMongoDTO dto : dtoList) {
            Solution bestSolution = ss.getLatestSolution(dto.getSolutions());
            ExerciseHomeDTO newExercise = ExerciseHomeDTOMapper.toEntity(dto, bestSolution);
            newList.add(newExercise);
        }

        return newList;
    }

}
