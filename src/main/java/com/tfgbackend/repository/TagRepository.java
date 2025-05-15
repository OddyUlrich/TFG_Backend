package com.tfgbackend.repository;

import com.tfgbackend.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<Tag, Long> {

    Tag findTagByName(String name);

}
