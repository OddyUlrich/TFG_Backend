package com.tfgbackend.controller;

import com.tfgbackend.dto.ProcessedRulesResponse;
import com.tfgbackend.dto.RuleSearchDTO;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.llm.ParsedRule;
import com.tfgbackend.llm.RuleProcessorAiService;
import com.tfgbackend.model.Rule;
import com.tfgbackend.service.RulesService;
import dev.langchain4j.exception.InternalServerException;
import dev.langchain4j.service.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

@RequestMapping("/rules")
public class RuleController {

    private final RuleProcessorAiService ruleProcessorAiService;
    private final RulesService rulesService;

    @Autowired
    public RuleController(RulesService rulesService, RuleProcessorAiService ruleProcessorAiService){
        this.rulesService = rulesService;
        this.ruleProcessorAiService = ruleProcessorAiService;
    }

    @GetMapping
    public ResponseEntity<List<RuleSearchDTO>> allExercises(
            @RequestParam(required = false) String rulesFilter,
            Authentication auth) {

        try {
            if (auth != null && auth.isAuthenticated()) {
                List<RuleSearchDTO> lista = rulesService.searchByNameAndDescription(rulesFilter);
                return ResponseEntity.status(HttpStatus.OK).body(lista);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessedRulesResponse> processingRules(@RequestBody String rulesText, Authentication auth) {

        try {

            if (auth == null || !auth.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Result<List<ParsedRule>> result = ruleProcessorAiService.parseRules(rulesText);
            List<Rule> rules = rulesService.mapToRule(result.content().stream().toList());

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ProcessedRulesResponse(
                            rules,
                            null)
            );

        }catch (InternalServerException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ProcessedRulesResponse(
                            List.of(),
                            "Esta IA no está temporalmente disponible"
                    ));
        }
    }
}
