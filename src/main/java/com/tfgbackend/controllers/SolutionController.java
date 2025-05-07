package com.tfgbackend.controllers;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.dto.SolutionCreationDTO;
import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.ExerciseFiles;
import com.tfgbackend.model.Solution;
import com.tfgbackend.model.User;
import com.tfgbackend.model.enumerators.StatusExercise;
import com.tfgbackend.service.ExerciseFilesService;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.service.SolutionService;
import com.tfgbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@RestController

@RequestMapping("/solutions")

public class SolutionController {

    private final ExerciseFilesService exerciseFilesService;
    private final ExerciseService exerciseService;
    private final SolutionService solutionService;
    private final UserService userService;

    @Autowired
    public SolutionController(ExerciseFilesService exerciseFilesService, ExerciseService exerciseService, SolutionService solutionService, UserService userService) {
        this.exerciseFilesService = exerciseFilesService;
        this.exerciseService = exerciseService;
        this.solutionService = solutionService;
        this.userService = userService;
    }

    @PutMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Solution> save(@RequestBody SolutionCreationDTO data, Authentication auth) {

        List<ExerciseFileDTO> filesForDisplay = data.getFilesForDisplay();
        String exerciseId = data.getExerciseId();
        String solutionId = data.getSolutionId();
        String email = auth.getName();

        User user = userService.getUser(email);
        Exercise exercise = exerciseService.findExerciseById(exerciseId);

        Solution solution;
        HttpStatus status;

        if (solutionId == null) {
            solution = new Solution(LocalDateTime.now(), null, StatusExercise.PENDING, user, exercise, 0);
            status = HttpStatus.CREATED;

            /* With this saveSolution we will make sure there is no possible null in solution.getId() when we save
            the new content of the files so no template file could be overwritten or "created" by error*/
            solutionService.saveSolution(solution);

        } else {
            /* Looking for the solution by ID: if it is found, then we just update the last update timestamp. If not,
            we will receive an exception and warn the frontend about it.
            * */
            try {
                solution = solutionService.findSolutionById(solutionId);
                solution.setUpdateTimestamp(LocalDateTime.now());
                solutionService.saveSolution(solution);
                status = HttpStatus.OK;

            } catch (ResourceNotFoundException e) {
                System.out.println("Solution not found, not updated");
                status = HttpStatus.NOT_FOUND;
                return ResponseEntity.status(status).build();
            }
        }

        for (ExerciseFileDTO file : filesForDisplay) {
            ExerciseFiles fileInDatabase = exerciseFilesService.findByNameAndSolutionId(file.getName(), solution.getId());
            if (fileInDatabase != null) {
                fileInDatabase.setContent(file.getContent().getBytes(StandardCharsets.UTF_8));
                exerciseFilesService.saveFile(fileInDatabase);
            } else {
                exerciseFilesService.saveFile(new ExerciseFiles(file.getName(), file.getPath(), file.getContent().getBytes(StandardCharsets.UTF_8), exercise, solution, file.getEditableMethods()));
            }
        }

        System.out.println("Solution saved with ID: " + solution.getId());

        return ResponseEntity.status(status).body(solution);

    }
}
