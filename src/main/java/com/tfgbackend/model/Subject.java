package com.tfgbackend.model;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("subjects")
@CompoundIndex(def = "{'name': 1, 'year': 1}, unique = true")
public record Subject(
        String name,
        int year){
}
