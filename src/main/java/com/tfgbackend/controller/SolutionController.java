package com.tfgbackend.controller;

import com.tfgbackend.dto.*;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.ExerciseFile;
import com.tfgbackend.model.Solution;
import com.tfgbackend.model.User;
import com.tfgbackend.model.enumerator.StatusExercise;
import com.tfgbackend.service.ExerciseFilesService;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.service.SolutionService;
import com.tfgbackend.service.UserService;
import com.tfgbackend.service.wrapper.TemplateAndSolutionFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@RestController

@RequestMapping("/exercises/{exerciseId}/solutions")

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CodeEditorDataDTO> getSolution(@PathVariable String exerciseId, Authentication auth) {

        try {
            if (auth != null && auth.isAuthenticated()) {

                String email = auth.getName();
                List<ExerciseFileDTO> allExerciseFilesAndLastSolution = exerciseFilesService.exerciseFilesAndLastSolutionByIdAndStudent(exerciseId, email);

                //All the files that the code editor could use (exercise's template files and user's solution files)
                TemplateAndSolutionFiles filteredFiles = exerciseFilesService.filterFiles(allExerciseFilesAndLastSolution);

                //All basic information about the other solutions of this user to allow him/her to change to it
                List<SolutionDTO> solutions = solutionService.allSolutionsByExerciseIdAndStudent(exerciseId, email);

                //ID for the last updated solution (Probably the last one they worked on)
                String currentSolution = exerciseFilesService.obtainSolutionFromExerciseFiles(allExerciseFilesAndLastSolution);

                //All necessary information about the exercise the user is currently working on
                ExerciseDTO exercise = exerciseService.findExerciseForEditorById(exerciseId);

                //DTO for the frontend with all files and information needed
                CodeEditorDataDTO data = new CodeEditorDataDTO(filteredFiles.getFilesForDisplay(), filteredFiles.getTemplateFiles(), solutions, currentSolution, exercise);

                return ResponseEntity.status(HttpStatus.OK).body(data);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


    //TODO ¿Esto debería de ser @Transactional para que se haga todo de una, no vaya a ser que guardemos una nueva solucion y luego los archivos fallen al guardarse y quede la solución sin nada. Ademas avisamos al frontend de ello?
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Solution> saveSolution(@RequestBody SolutionCreationDTO data, Authentication auth) {

        List<ExerciseFileDTO> filesForDisplay = data.getFilesForDisplay();
        String exerciseId = data.getExerciseId();
        String solutionId = data.getSolutionId();
        String email = auth.getName();

        User user = userService.getUserByEmail(email);
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
                System.out.println("Solution not found with that ID, not updated");
                status = HttpStatus.NOT_FOUND;
                return ResponseEntity.status(status).build();
            }
        }

        /* While trying to save the new solution files we should check first if there is already a solution file in
        the database. If it exists already we use the data it already has except for the new text. If not, we create
        a new file with a solution field filled
        * */
        for (ExerciseFileDTO file : filesForDisplay) {
            ExerciseFile fileInDatabase = exerciseFilesService.findByNameAndSolutionId(file.getName(), solution.getId());
            if (fileInDatabase != null) {
                fileInDatabase.setBinaryText(file.getText().getBytes(StandardCharsets.UTF_8));
                exerciseFilesService.saveFile(fileInDatabase);
            } else {
                exerciseFilesService.saveFile(new ExerciseFile(file.getName(), file.getPath(), file.getText().getBytes(StandardCharsets.UTF_8), exercise, solution, file.getEditableMethods()));
            }
        }

        System.out.println("Solution saved with ID: " + solution.getId());

        return ResponseEntity.status(status).body(solution);
    }
}
