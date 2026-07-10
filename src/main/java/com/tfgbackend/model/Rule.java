package com.tfgbackend.model;

import com.tfgbackend.model.enumerator.RuleType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("rules")
public record Rule(
        @Id
        String id,
        String description,
        RuleType type
) {
        @Override
        public String toString() {
                return description;
        }

}
