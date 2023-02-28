package com.tfgbackend.service;

import com.tfgbackend.model.Subject;
import com.tfgbackend.repositories.ExerciseBatteryRepository;
import com.tfgbackend.service.dto.ExerciseSolutionDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    private SubjectService subjectService;
    private ExerciseBatteryRepository ebr;

    @Autowired
    public ExerciseService(SubjectService subjectService, ExerciseBatteryRepository ebr){
        this.subjectService = subjectService;
        this.ebr = ebr;
    }

    public List<ExerciseSolutionDTO> allExercisesByStudent(ObjectId studentId){
        List<Subject> subjects = subjectService.subjectsByStudentId(studentId);
        ebr.allExerciseBatteryByUserSubjects(subjects);


        //Ojo, si ya lo ha completado alguna vez el status a mostrar sera "completado"
    }
}
