package com.tfgbackend.dto;

public record RuleSearchDTO(
        String id,
        String description,
        String type,
        String exerciseName,
        String batteryName
) {}
