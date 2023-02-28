package com.tfgbackend.service;

import com.tfgbackend.model.Subject;
import com.tfgbackend.repositories.SubjectRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> subjectsByStudentId(ObjectId studentId){
        return subjectRepository.findAllByStudentID(studentId);
    }
}
