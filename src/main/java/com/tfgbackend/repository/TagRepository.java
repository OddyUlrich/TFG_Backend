package com.tfgbackend.repository;

import com.tfgbackend.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends MongoRepository<Tag, Long> {

    List<Tag> findAll();

    Tag findByName(String tagName);
}
