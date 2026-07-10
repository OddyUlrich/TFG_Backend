package com.tfgbackend.mappers;

import com.tfgbackend.llm.ParsedRule;
import com.tfgbackend.model.Rule;

public class RuleMapper {

    public static Rule fromParsedRule(ParsedRule parsedRule) {
        return new Rule(
                null,
                parsedRule.description(),
                parsedRule.type()
        );
    }
}
