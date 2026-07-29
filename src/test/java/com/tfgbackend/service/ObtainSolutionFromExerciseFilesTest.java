package com.tfgbackend.service;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.model.*;
import com.tfgbackend.service.wrapper.TemplateAndSolutionFiles;
import org.junit.jupiter.api.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ObtainSolutionFromExerciseFilesTest {

    @BeforeEach
    protected void setUp() {

    }

    @AfterEach
    protected void tearDown() {

    }

    @Test
    @DisplayName("Filtrado de archivos plantilla y archivos solución")
    protected void shouldReturnTemplatesAndSolutionsWhenBothExist() {
        EditableMethod metodosEjercicio = new EditableMethod("metodoVacio", 1, 2);
        String content = "Esto es un test";

        ExerciseFileDTO exerciseFile1 = new ExerciseFileDTO("1","archivo1", "/Ejercicio/archivo1", content, "1", List.of(metodosEjercicio), content.getBytes(StandardCharsets.UTF_8));
        ExerciseFileDTO exerciseFile2 = new ExerciseFileDTO("2","archivo2", "/Ejercicio/archivo2", content, "1", List.of(metodosEjercicio), content.getBytes(StandardCharsets.UTF_8));
        ExerciseFileDTO exerciseFile3 = new ExerciseFileDTO("3","archivo3", "/Ejercicio/archivo3", content, "1", List.of(metodosEjercicio), content.getBytes(StandardCharsets.UTF_8));
        ExerciseFileDTO exerciseFile4 = new ExerciseFileDTO("4","archivo4", "/Ejercicio/archivo4", content, "1", List.of(metodosEjercicio), content.getBytes(StandardCharsets.UTF_8));
        ExerciseFileDTO exerciseFile5 = new ExerciseFileDTO("5","archivo5", "/Ejercicio/archivo5", content, "1", List.of(metodosEjercicio), content.getBytes(StandardCharsets.UTF_8));
        ExerciseFileDTO exerciseFile6 = new ExerciseFileDTO("6","archivo1", "/Ejercicio/archivo1", content, null, null, content.getBytes(StandardCharsets.UTF_8));
        ExerciseFileDTO exerciseFile7 = new ExerciseFileDTO("7","archivo2", "/Ejercicio/archivo2", content, null, null, content.getBytes(StandardCharsets.UTF_8));
        ExerciseFileDTO exerciseFile8 = new ExerciseFileDTO("8","archivo3", "/Ejercicio/archivo3", content, null, null, content.getBytes(StandardCharsets.UTF_8));
        ExerciseFileDTO exerciseFile9 = new ExerciseFileDTO("9","archivo4", "/Ejercicio/archivo4", content, null, null, content.getBytes(StandardCharsets.UTF_8));
        ExerciseFileDTO exerciseFile10 = new ExerciseFileDTO("10","archivo5", "/Ejercicio/archivo5", content, null, null, content.getBytes(StandardCharsets.UTF_8));

        List<ExerciseFileDTO> exerciseFiles = new ArrayList<>();
        exerciseFiles.add(exerciseFile1);
        exerciseFiles.add(exerciseFile2);
        exerciseFiles.add(exerciseFile3);
        exerciseFiles.add(exerciseFile4);
        exerciseFiles.add(exerciseFile5);
        exerciseFiles.add(exerciseFile6);
        exerciseFiles.add(exerciseFile7);
        exerciseFiles.add(exerciseFile8);
        exerciseFiles.add(exerciseFile9);
        exerciseFiles.add(exerciseFile10);

        ExerciseFilesService service = new ExerciseFilesService(null, null);
        TemplateAndSolutionFiles filteredFiles = service.filterFiles(exerciseFiles);

        Assertions.assertEquals(5, filteredFiles.getTemplateFiles().size());
        Assertions.assertEquals(5, filteredFiles.getFilesForDisplay().size());
    }

}
