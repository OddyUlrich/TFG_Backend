package com.tfgbackend.model;

import dev.langchain4j.model.output.structured.Description;

public record Rule(
        @Description("A brief and concise description of the rule students must follow when completing the exercise")
        String description
) {

        @Override
        public String toString() {
                return description;
        }

}
