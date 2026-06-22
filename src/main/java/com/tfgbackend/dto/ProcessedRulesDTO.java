package com.tfgbackend.dto;

import com.tfgbackend.model.Rule;
import dev.langchain4j.model.output.structured.Description;

import java.util.List;

public record ProcessedRulesDTO(
    @Description("List of mandatory rules taken STRICTLY from the professor's text. If there are none, leave this section blank.")
    List<Rule> requiredRules,
    @Description("List of forbidden rules taken STRICTLY from the professor's text. If there are none, leave this section blank.")
    List<Rule> forbiddenRules
) {}
