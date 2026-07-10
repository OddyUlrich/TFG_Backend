package com.tfgbackend.repository;

import com.tfgbackend.model.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulesRepository extends MongoRepository<Rule, Long>, CustomRuleRepository {

}