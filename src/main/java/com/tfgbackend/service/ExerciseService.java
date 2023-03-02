package com.tfgbackend.service;

import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.Subject;
import com.tfgbackend.repositories.ExerciseBatteryRepository;
import com.tfgbackend.repositories.ExerciseRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseService {

    private SubjectService subjectService;
    private ExerciseBatteryRepository ebr;
    private ExerciseRepository er;

    @Autowired
    public ExerciseService(SubjectService subjectService, ExerciseBatteryRepository ebr, ExerciseRepository er){
        this.subjectService = subjectService;
        this.er = er;
        this.ebr = ebr;
    }

    public List<Exercise> allExercisesByStudent(ObjectId studentId){
        List<Subject> subjects = subjectService.subjectsByStudentId(studentId);

        List<ObjectId> subjectIds = new ArrayList<>();
        for (var e : subjects){
            subjectIds.add(e.getId());
        }
        return er.allExerciseByUserSubjects(subjectIds);

        //Debemos retornar una lista de ExerciseSolutionDTO
        //List<ExerciseSolutionDTO> allExerciseSolutions = ebr.allExerciseBatteryByUserSubjects(subjects);
        //return allExerciseSolutions;


        //Ojo, si ya lo ha completado alguna vez el status a mostrar sera "completado"
    }

}
