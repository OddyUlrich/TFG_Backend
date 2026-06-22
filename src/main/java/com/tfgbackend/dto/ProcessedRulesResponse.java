package com.tfgbackend.dto;

import com.tfgbackend.model.Rule;

import java.util.List;

public record ProcessedRulesResponse(
        List<Rule> requiredRules,
        List<Rule> forbiddenRules,
        String warning
) {}