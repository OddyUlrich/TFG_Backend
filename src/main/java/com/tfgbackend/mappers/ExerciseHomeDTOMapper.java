package com.tfgbackend.mappers;

import com.tfgbackend.dto.ExerciseHomeDTO;
import com.tfgbackend.dto.ExerciseHomeMongoDTO;
import com.tfgbackend.model.*;


public class ExerciseHomeDTOMapper {

    public static ExerciseHomeDTO toEntity(
            ExerciseHomeMongoDTO dto,
            Solution solution
    ) {
        return new ExerciseHomeDTO(
                dto.getId(),
                dto.getName(),
                dto.getTags(),
                dto.getFavorite(),
                dto.getBatteryName(),
                dto.getCreationTimestamp(),
                solution != null ? solution.getNumberErrors() : null,
                solution != null ? solution.getCreationTimestamp() : null,
                solution != null ? solution.getStatus() : null
        );
    }

}
