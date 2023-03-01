package com.tfgbackend.service;

import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Subject;
import com.tfgbackend.repositories.ExerciseBatteryRepository;
import com.tfgbackend.repositories.ExerciseRepository;
import com.tfgbackend.service.dto.ExerciseSolutionDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<ExerciseBattery> allExercisesByStudent(ObjectId studentId){
        List<Subject> subjects = subjectService.subjectsByStudentId(studentId);
        return ebr.allExerciseBatteryByUserSubjects(subjects);

        //Debemos retornar una lista de ExerciseSolutionDTO
        //List<ExerciseSolutionDTO> allExerciseSolutions = ebr.allExerciseBatteryByUserSubjects(subjects);
        //return allExerciseSolutions;


        //Ojo, si ya lo ha completado alguna vez el status a mostrar sera "completado"
    }
}
