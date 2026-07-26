package com.tfgbackend.mappers;

import com.tfgbackend.dto.ExerciseSimpleDTO;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.model.Tag;
import com.tfgbackend.model.User;
import java.time.LocalDateTime;
import java.util.List;

public class ExerciseMapper {

    public static Exercise toEntity(
            ExerciseSimpleDTO dto,
            ExerciseBattery battery,
            List<Tag> tags,
            User teacher,
            LocalDateTime creationDate
    ) {
        return new Exercise(
                dto.getId(),
                dto.getName(),
                dto.getStatement(),
                battery,
                dto.getRules(),
                tags,
                teacher,
                creationDate
        );
    }
}