package com.tfgbackend.service;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.ExerciseFiles;
import com.tfgbackend.model.User;
import com.tfgbackend.repository.ExerciseFileRepository;
import com.tfgbackend.service.wrapper.TemplateAndSolutionFiles;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
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

        List<ExerciseFileDTO> list = efr.exerciseFilesAndSolutionByIdAndStudent(new ObjectId(exerciseId), new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Files about exercise could not be obtained"));
        for (ExerciseFileDTO file : list){
            file.setContent(new String(file.getContentBinary(), StandardCharsets.UTF_8));
        }

        return list;
    }

    /*
    From a list of files we take, on the one hand, the files to be shown to the student (display) and, on the other
    hand, we take advantage of this to separate the template files in a single loop
     */
    public TemplateAndSolutionFiles filterFiles(List<ExerciseFileDTO> exerciseFiles){
        HashMap<String, ExerciseFileDTO> filesForDisplay = new HashMap<>();
        List<ExerciseFileDTO> templates = new ArrayList<>();

        /*
        As files with new names arrive, we add them to the hashmap. If one of them is repeated, it means that inside
        the hashmap there is already a solution or template file and its opposite has just appeared. In this case we
        check that the new file is a solution: if it is, we replace the template already in the hashmap by this
        solution file, if it is not, it means that in the hashmap we already have the solution, so we discard this new
        file that would be the template.
         */
        for (ExerciseFileDTO file: exerciseFiles) {
            if (!filesForDisplay.containsKey(file.getName())){
                filesForDisplay.put(file.getName(), file);
            }else{
                if (!isEmptyString(file.getIdFromSolution())){
                    filesForDisplay.put(file.getName(), file);
                }
            }

            /*
            We add all the template files (idFromSolution = null/empty) to a List
             */
            if (isEmptyString(file.getIdFromSolution())){
                templates.add(file);
            }

        }

        return new TemplateAndSolutionFiles(new ArrayList<>(filesForDisplay.values()), templates);
    }

    /* This function gets the string of the id from solution files. It is assumed that all
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
