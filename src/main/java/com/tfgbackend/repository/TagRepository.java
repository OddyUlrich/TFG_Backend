package com.tfgbackend.repository;

import com.tfgbackend.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TagRepository extends MongoRepository<Tag, Long> {

    List<Tag> findAll();

    List<Tag> findByNameIn(Collection<String> names);

    Tag findByName(String name);
}
