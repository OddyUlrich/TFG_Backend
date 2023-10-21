package com.tfgbackend.service;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.ExerciseFiles;
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

    public ExerciseFiles findByNameAndSolutionId(String name, String solutionId){
        return this.efr.findByNameAndSolutionId(name, new ObjectId(solutionId));
    }

    public List<ExerciseFileDTO> exerciseFilesAndSolutionByIdAndStudent(String exerciseId, String email){
        User user = us.getUser(email);
        return efr.exerciseFilesAndSolutionByIdAndStudent(new ObjectId(exerciseId), new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Files about exercise could not be obtained"));
    }

    /* From an array of ExerciseFileDTO we obtain a smaller array by removing the files that
     are part of the exercise template, replacing them with the version written by the student.
     */
    public List<ExerciseFileDTO> filterFilesForDisplay(List<ExerciseFileDTO> exerciseFiles){
        List<String> solutionFileNames = new ArrayList<>();
        List<ExerciseFileDTO> filesForDisplay = new ArrayList<>();

        /*First we look for all the files that correspond to the student's solution and enter them in the lists
         "solutionFileNames" and "filesForDisplay". This way we get all the student's solution files.
         */
        for (ExerciseFileDTO file: exerciseFiles) {
            if (!isEmptyString(file.getIdFromSolution())){
                solutionFileNames.add(file.getName());
                filesForDisplay.add(file);
            }
        }

        /*Once this is done, if we check the names of the files and one of them is already contained in the
         "solutionFileNames" list, we know that it is either a template corresponding to a file that needs a solution
         or it is the student's own solution file. In either case we know that the solution files were added before to
         "filesForDisplay", so we don't need either case.
         Also, if any of the files are not in the list of solutions it means that they are template files that do
         not have a student version, so we need to show them and add them to "filesForDisplay".
         */
        for (ExerciseFileDTO file: exerciseFiles){
            if (!solutionFileNames.contains(file.getName())){
                filesForDisplay.add(file);
            }
        }
        return filesForDisplay;
    }

    /* From an array of ExerciseFileDTOs it filters to a new list the ExerciseFileDTO's
    that do not belong to a solution (templates) */
    public List<ExerciseFileDTO> filterTemplateFiles(List<ExerciseFileDTO> exerciseFiles){
        List<ExerciseFileDTO> newList = new ArrayList<>();
        for (ExerciseFileDTO file: exerciseFiles) {
            if (isEmptyString(file.getIdFromSolution())){
                newList.add(file);
            }
        }
        return newList;
    }

    /* This function obtains the string of the id from a solution of a list of files. It is assumed that all
    files received belong to the same solution.*/
    public String obtainSolutionFromExerciseFiles(List<ExerciseFileDTO> exerciseFiles){
        for (ExerciseFileDTO file: exerciseFiles) {
            if (!isEmptyString(file.getIdFromSolution())){
                return file.getIdFromSolution();
            }
        }

        return null;
    }

    public void saveFile(ExerciseFiles file){
        efr.save(file);
    }

}
