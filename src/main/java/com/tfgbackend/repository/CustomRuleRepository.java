package com.tfgbackend.repository;

import com.tfgbackend.dto.RuleSearchDTO;

import java.util.List;

public interface CustomRuleRepository {

    List<RuleSearchDTO> searchByDescriptionOrExerciseName(String rulesFilter);
}
