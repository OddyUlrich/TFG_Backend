package com.tfgbackend.model;

import com.tfgbackend.model.enumerator.RuleType;
import dev.langchain4j.model.output.structured.Description;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Rule(
        @Description("A brief and concise description of the rule students must follow when completing the exercise")
        String description,

        @Description("Rule classification. Since it is an ENUM, it can only have the value \"FORBIDDEN\" or \"REQUIRED\"")
        RuleType type
) {
        @Override
        public String toString() {
                return description;
        }

}
