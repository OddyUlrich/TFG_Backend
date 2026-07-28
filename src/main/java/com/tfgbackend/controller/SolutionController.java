package com.tfgbackend.controller;

import com.tfgbackend.dto.*;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.llm.*;
import com.tfgbackend.model.*;
import com.tfgbackend.model.enumerator.ExerciseEvaluationStatus;
import com.tfgbackend.model.enumerator.StatusExercise;
import com.tfgbackend.service.ExerciseFilesService;
import com.tfgbackend.service.ExerciseService;
import com.tfgbackend.service.SolutionService;
import com.tfgbackend.service.UserService;
import com.tfgbackend.service.wrapper.TemplateAndSolutionFiles;
import dev.langchain4j.exception.InternalServerException;
import dev.langchain4j.service.Result;
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
    private final CorrectorAiService correctorAiService;

    @Autowired
    public SolutionController(ExerciseFilesService exerciseFilesService, ExerciseService exerciseService, SolutionService solutionService, UserService userService, CorrectorAiService correctorAiService) {
        this.exerciseFilesService = exerciseFilesService;
        this.exerciseService = exerciseService;
        this.solutionService = solutionService;
        this.userService = userService;
        this.correctorAiService = correctorAiService;
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

                //ID for the last updated solution (Probably the last one they worked on), if it doesn't exist we should create the first solution
                String currentSolution = solutionService.obtainSolutionFromExerciseFiles(allExerciseFilesAndLastSolution);
                if (currentSolution == null) {
                    User user = userService.getUserByEmail(email);
                    Exercise exercise = exerciseService.findExerciseById(exerciseId);
                    Solution solution = solutionService.save(new Solution(LocalDateTime.now(), "intento_1", StatusExercise.PENDING, user, exercise, 0));
                    currentSolution = solution.getId();
                }

                //All necessary information about the exercise the user is currently working on
                ExerciseSimpleDTO exercise = exerciseService.findExerciseForEditorById(exerciseId);

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Solution> saveSolution(@PathVariable String exerciseId, @RequestBody SolutionCreationDTO data, Authentication auth) {

        String email = auth.getName();
        User user = userService.getUserByEmail(email);
        Exercise exercise = exerciseService.findExerciseById(exerciseId);

        try {

            return solutionService.saveSolution(data, user, exercise);

        } catch (ResourceNotFoundException e) {
            System.out.println("Solution not found with that ID, not updated");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value = "/evaluate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EvaluationResponse> evaluation(@PathVariable String exerciseId, @RequestBody EvaluationData data, Authentication auth) {

        try {
            if (auth == null || !auth.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String email = auth.getName();
            User user = userService.getUserByEmail(email);
            Exercise exercise = exerciseService.findExerciseById(exerciseId);

            SolutionCreationDTO solutionCreationDTO = new SolutionCreationDTO(data.filesForEvaluation(), data.solutionId());
            solutionService.saveSolution(solutionCreationDTO, user, exercise);

            EvaluationResponse result = correctorAiService.evaluate(data.filesForEvaluation(), data.statement(), data.rules());

            solutionService.assignStatusEvaluation(data.solutionId(), result.evaluationStatus());

            return ResponseEntity.status(HttpStatus.OK).body(result);

        }catch (ResourceNotFoundException e) {
            System.out.println("Solution not found with that ID, not updated/evaluated");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch (InternalServerException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new EvaluationResponse(
                            ExerciseEvaluationStatus.UNCERTAIN,
                            "Esta IA no está temporalmente disponible",
                            List.of()
                    ));
        }
    }
}
