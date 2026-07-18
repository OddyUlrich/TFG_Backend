package com.tfgbackend.service;

import com.github.javaparser.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.mappers.FileMapper;
import com.tfgbackend.model.EditableMethod;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.ExerciseFile;
import com.tfgbackend.model.User;
import com.tfgbackend.repository.ExerciseFileRepository;
import com.tfgbackend.service.wrapper.TemplateAndSolutionFiles;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.tfgbackend.configuration.Util.isEmptyString;

@Service
public class ExerciseFilesService {

    private final UserService us;
    private final ExerciseFileRepository efr;
    private final JavaParser parser;


    @Autowired
    public ExerciseFilesService(UserService us, ExerciseFileRepository efr) {
        this.us = us;
        this.efr = efr;

        ParserConfiguration config = new ParserConfiguration();

        config.setLanguageLevel(
                ParserConfiguration.LanguageLevel.JAVA_21
        );

        this.parser = new JavaParser(config);
    }

    public ExerciseFile findByNameAndSolutionId(String name, String solutionId) {
        return efr.findByNameAndSolutionId(name, new ObjectId(solutionId));
    }

    public List<ExerciseFileDTO> exerciseFilesAndLastSolutionByIdAndStudent(String exerciseId, String email) {
        User user = us.getUserByEmail(email);

        List<ExerciseFileDTO> list = efr.exerciseFilesAndLastSolutionByIdAndStudent(new ObjectId(exerciseId), new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Files about this exercise could not be obtained"));
        for (ExerciseFileDTO file : list) {
            file.setText(new String(file.getBinaryText(), StandardCharsets.UTF_8));
        }

        return list;
    }

    public List<ExerciseFileDTO> templateFilesByExerciseId(String exerciseId) {

        List<ExerciseFileDTO> list = efr.templateFilesByExerciseId(new ObjectId(exerciseId)).orElseThrow(() -> new ResourceNotFoundException("Template files about this exercise could not be obtained"));

        for (ExerciseFileDTO file : list) {
            file.setText(new String(file.getBinaryText(), StandardCharsets.UTF_8));
        }

        return list;
    }

    /*
    From a list of files we take, on one hand, the files to be shown to the student (display) and, on the other
    hand, we take advantage of this to separate the template files in a single loop
     */
    public TemplateAndSolutionFiles filterFiles(List<ExerciseFileDTO> exerciseFiles) {
        HashMap<String, ExerciseFileDTO> filesForDisplay = new HashMap<>();
        List<ExerciseFileDTO> templates = new ArrayList<>();

        /*
        As files with new names arrive, we add them to the hashmap. If one of them is repeated, it means that inside
        the hashmap there is already a solution or template file and its opposite has just appeared. In this case we
        check that the new file is a solution: if it is, we replace the template already in the hashmap by this
        solution file, if it is not, it means that in the hashmap we already have the solution, so we discard this new
        file that would be the template.
         */
        for (ExerciseFileDTO file : exerciseFiles) {
            if (!filesForDisplay.containsKey(file.getName())) {
                filesForDisplay.put(file.getName(), file);
            } else {
                if (!isEmptyString(file.getIdFromSolution())) {
                    filesForDisplay.put(file.getName(), file);
                }
            }

            /*
            We add all the template files (idFromSolution = null/empty) to a List
             */
            if (isEmptyString(file.getIdFromSolution())) {
                templates.add(file);
            }

        }

        return new TemplateAndSolutionFiles(new ArrayList<>(filesForDisplay.values()), templates);
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

    //The boolean just indicates if they are the first files or there are already other ones related to the exercise
    public List<ExerciseFile> saveTemplateFiles(List<ExerciseFileDTO> exerciseFiles, Exercise exercise, boolean firstFiles) {

        if (!firstFiles){
            efr.deleteExerciseFilesByExercise(exercise);
        }

        List<ExerciseFile> newTemplateFiles = new ArrayList<>();

        for (ExerciseFileDTO fileDTO : exerciseFiles) {

            ParseResult<CompilationUnit> result = parser.parse(fileDTO.getText());

            if (!result.isSuccessful() || result.getResult().isEmpty()) {
                continue;
            }

            CompilationUnit cu = result.getResult().get();
            cu.findAll(MethodDeclaration.class).forEach(method -> {
                if (method.getBody().isEmpty() || method.getRange().isEmpty()) {
                    return;
                }

                Range range = method.getRange().get();

                int startLine = range.begin.line;
                int endLine = range.end.line;

                BlockStmt body = method.getBody().get();
                if (body.getStatements().isEmpty()) {
                    fileDTO.getEditableMethods().add(new EditableMethod(method.getName().asString(), startLine+1, endLine-1));
                }

            });


            newTemplateFiles.add(saveFile(FileMapper.toEntityNoId(fileDTO, exercise, null)));
        }

        return newTemplateFiles;
    }

    public ExerciseFile saveFile(ExerciseFile file) {
        return efr.save(file);
    }

}
