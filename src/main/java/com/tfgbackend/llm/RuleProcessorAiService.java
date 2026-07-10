package com.tfgbackend.llm;

import com.tfgbackend.model.Rule;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import java.util.List;

@AiService
public interface RuleProcessorAiService {

    @SystemMessage("""
        You are an expert assistant for a Computer Science professor, specializing in Java automation.
        
        Your task is to extract rules from natural language about a Java exercise, which we will then classify as “required” and “forbidden.”
        
        CRITICAL REQUIREMENTS
        1. Classify the rules into 'required' (must use) and 'forbidden' (must NOT use).
        2. Language: You MUST write the description of each rule in Spanish.
        3. Context Preservation & Unification (CRITICAL): DO NOT separate elements that depend on each other. If a variable, method, or condition belongs strictly to a specific structure (like a loop or an if-statement), create a single unified rule.
            - Context: Usar un bucle while que use como variable un int llamado "Pepe". No se permite el uso de colecciones (List, Set, Map) ni arrays, salvo que el ejercicio lo indique explícitamente.
            - BAD example: Required: ["Usar bucle while", "Usar variable Pepe",], Prohibited: ["No usar List, Set, Map"]
            - GOOD example: Required: ["Usar un bucle while controlado por una variable llamada var"], Prohibited: ["Prohibido colecciones (list, set, map) ni arrays, salvo que lo indique el enunciado/ejercicio"]
        4. Precision: Ensure that targeted constraints (like specific file names, classes, or expected numeric results) remain attached to their respective actions in the description.
        5. Clarity: Keep descriptions clear, objective, and directly testable by an automated code analyzer.
        6. Empty Categories: If the text does not contain any forbidden or required rules, leave that list completely empty.
        7. If a rule is not explicitly or implicitly mentioned in the teacher's text, DO NOT include it.
        8. Double check that the rules make sense in java, if not, DO NOT include them.
        9. DO NOT repeat rules.
        """)
    @UserMessage("""
        Analyze the following text from the teacher and extract the rules:
        ---
        {{rawPrompt}}
        ---
        """)
    Result<List<ParsedRule>> parseRules(@V("rawPrompt") String rawPrompt);
}
