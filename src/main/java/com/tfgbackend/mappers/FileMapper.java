package com.tfgbackend.mappers;

import com.tfgbackend.dto.ExerciseFileDTO;
import com.tfgbackend.model.*;

import java.nio.charset.StandardCharsets;

public class FileMapper {

    public static ExerciseFile toEntity(
            ExerciseFileDTO dto,
            Exercise exercise,
            Solution solution
    ) {
        return new ExerciseFile(
                dto.getId(),
                dto.getName(),
                dto.getPath(),
                dto.getText().getBytes(StandardCharsets.UTF_8),
                exercise,
                solution,
                dto.getEditableMethods()
        );
    }

    public static ExerciseFile toEntityNoId(
            ExerciseFileDTO dto,
            Exercise exercise,
            Solution solution
    ) {
        return new ExerciseFile(
                null,
                dto.getName(),
                dto.getPath(),
                dto.getText().getBytes(StandardCharsets.UTF_8),
                exercise,
                solution,
                dto.getEditableMethods()
        );
    }
}