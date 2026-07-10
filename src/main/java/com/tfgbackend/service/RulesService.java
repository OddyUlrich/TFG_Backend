package com.tfgbackend.service;

import com.tfgbackend.dto.RuleSearchDTO;
import com.tfgbackend.llm.ParsedRule;
import com.tfgbackend.mappers.RuleMapper;
import com.tfgbackend.model.Rule;
import com.tfgbackend.repository.RulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RulesService {

    private final RulesRepository rr;

    @Autowired
    public RulesService(RulesRepository rr) {
        this.rr = rr;
    }

    public List<RuleSearchDTO> searchByNameAndDescription(String rulesFilter) {
        return rr.searchByDescriptionOrExerciseName(rulesFilter);
    }

    public List<Rule> saveRules(List<Rule> rules){
        return rr.saveAll(rules);
    }

    public List<Rule> mapToRule(List<ParsedRule> parsedRules){
        ArrayList<Rule> rules = new ArrayList<>();

        for(ParsedRule parsedRule : parsedRules){
            rules.add(RuleMapper.fromParsedRule(parsedRule));
        }

        return rules;
    }
}
