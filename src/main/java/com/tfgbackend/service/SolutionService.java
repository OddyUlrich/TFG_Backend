package com.tfgbackend.service;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.dto.SolutionCreationDTO;
import com.tfgbackend.dto.SolutionDTO;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.*;
import com.tfgbackend.model.enumerator.ExerciseEvaluationStatus;
import com.tfgbackend.model.enumerator.StatusExercise;
import com.tfgbackend.repository.ExerciseRepository;
import com.tfgbackend.repository.SolutionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static com.tfgbackend.configuration.Util.isEmptyString;

@Repository
public class SolutionService {

    private final SolutionRepository sr;
    private final UserService us;
    private final ExerciseFilesService efs;

    @Autowired
    public SolutionService(SolutionRepository sr, UserService us, ExerciseFilesService efs) {
        this.sr = sr;
        this.us = us;
        this.efs = efs;
    }

    public List<SolutionDTO> allSolutionsByExerciseIdAndStudent(String exerciseId, String email){
        User user = us.getUserByEmail(email);
        return sr.allSolutionsByExerciseIdAndStudent(new ObjectId(exerciseId), new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Solutions about exercise could not be obtained"));
    }

    public Solution findSolutionById(String solutionId){
        return sr.findById(solutionId).orElseThrow(() -> new ResourceNotFoundException("Solutions with that ID cannot be found"));
    }

    public ResponseEntity<Solution> saveSolution(SolutionCreationDTO data, User user, Exercise exercise){

        List<ExerciseFileDTO> solutionFiles = data.getSolutionFiles();
        String solutionId = data.getSolutionId();

        Solution solution;
        HttpStatus status;

        if (solutionId == null) {
            solution = new Solution(LocalDateTime.now(), null, StatusExercise.IN_PROGRESS, user, exercise, 0);
            status = HttpStatus.CREATED;

            /* With this saveSolution we will make sure there is no possible null in solution.getId() when we save
            the new content of the files so no template file could be overwritten or "created" by error*/
            solution = sr.save(solution);

        } else {
            /* Looking for the solution by ID: if it is found, then we just update the last update timestamp. If not,
            we will receive an exception and warn the frontend about it.
            * */
            solution = findSolutionById(solutionId);
            solution.setUpdateTimestamp(LocalDateTime.now());
            solution = sr.save(solution);
            status = HttpStatus.OK;
        }

         /* While trying to save the new solution files we should check first if there is already a solution file in
        the database. If it exists already we use the data it already has except for the new text. If not, we create
        a new file with a solution field filled
        * */
        for (ExerciseFileDTO file : solutionFiles) {

            //We should check that we don't add any file that is not from a solution
            if (!file.getEditableMethods().isEmpty()) {
                efs.updateEditableMethodRange(file);

                ExerciseFile fileInDatabase = efs.findByNameAndSolutionId(file.getName(), solution.getId());
                if (fileInDatabase != null) {
                    fileInDatabase.setBinaryText(file.getText().getBytes(StandardCharsets.UTF_8));
                    fileInDatabase.setEditableMethods(file.getEditableMethods());
                    efs.saveFile(fileInDatabase);
                } else {
                    efs.saveFile(new ExerciseFile(file.getName(), file.getPath(), file.getText().getBytes(StandardCharsets.UTF_8), exercise, solution, file.getEditableMethods()));
                }
            }
        }

        return ResponseEntity.status(status).body(solution);
    }

    /* This function gets the string of the id from solution files. It is assumed that all
    files received belong to the same solution.*/
    public String obtainSolutionFromExerciseFiles(List<ExerciseFileDTO> exerciseFiles) {
        for (ExerciseFileDTO file : exerciseFiles) {
            if (!isEmptyString(file.getIdFromSolution())) {
                return file.getIdFromSolution();
            }
        }

        return null;
    }

    public Solution getLatestSolution(List<Solution> solutions) {

        if (solutions == null || solutions.isEmpty()) {
            return null;
        }

        return solutions.stream()
                .max((a, b) -> {

                    // 1. COMPLETED tiene prioridad
                    if (a.getStatus() == StatusExercise.COMPLETED && b.getStatus() != StatusExercise.COMPLETED) return 1;
                    if (b.getStatus() == StatusExercise.COMPLETED && a.getStatus() != StatusExercise.COMPLETED) return -1;

                    // 2. si ambos son COMPLETED o ambos no:
                    return a.getUpdateTimestamp()
                            .compareTo(b.getUpdateTimestamp());
                })
                .orElse(null);
    }

    public void assignStatusEvaluation(String solutionId, ExerciseEvaluationStatus evaluationStatus) {

        if (evaluationStatus == ExerciseEvaluationStatus.PASS) {
            sr.updateSolutionStatus(solutionId, StatusExercise.COMPLETED);
        }else {
            sr.updateSolutionStatus(solutionId, StatusExercise.IN_PROGRESS);
        }
    }


    public Solution save(Solution solution) {
        return sr.save(solution);
    }

}
