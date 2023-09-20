package com.tfgbackend.service;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.User;
import com.tfgbackend.repositories.ExerciseFileRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.tfgbackend.configuration.Util.isEmptyString;

@Service
public class ExerciseFilesService {

    private final UserService us;
    private final ExerciseFileRepository efr;

    @Autowired
    public ExerciseFilesService(UserService us, ExerciseFileRepository efr) {
        this.us = us;
        this.efr = efr;
    }

    public List<ExerciseFileDTO> exerciseFilesAndSolutionByIdAndStudent(String exerciseId, String email){
        User user = us.getUser(email);
        return efr.exerciseFilesAndSolutionByIdAndStudent(new ObjectId(exerciseId), new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Files about exercise could not be obtained"));
    }

    //From an array of ExerciseFileDTO we obtain a smaller array by removing the files that are part of the exercise template, replacing them with the version written by the student.
    public List<ExerciseFileDTO> filterFilesForDisplay(List<ExerciseFileDTO> exerciseFiles){
        List<String> solutionFileNames = new ArrayList<>();
        List<ExerciseFileDTO> filesForDisplay = new ArrayList<>();

        for (ExerciseFileDTO file: exerciseFiles) {
            if (!isEmptyString(file.getIdFromSolution())){
                solutionFileNames.add(file.getName());
            }
        }

        for (ExerciseFileDTO file: exerciseFiles){
            if (!solutionFileNames.contains(file.getName()) || !isEmptyString(file.getIdFromSolution())){
                filesForDisplay.add(file);
            }
        }

        return filesForDisplay;
    }

    //From an array of ExerciseFileDTOs it filters out the ExerciseFileDTO's that do not belong to a solution
    public List<ExerciseFileDTO> filterFreshFiles(List<ExerciseFileDTO> exerciseFiles){
        List<ExerciseFileDTO> newList = new ArrayList<>();
        for (ExerciseFileDTO file: exerciseFiles) {
            if (isEmptyString(file.getIdFromSolution())){
                newList.add(file);
            }
        }
        return newList;
    }


}
