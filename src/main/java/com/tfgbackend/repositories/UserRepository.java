package com.tfgbackend.repositories;

import com.tfgbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    User findUserByName(String name);

    User findUserById(String id);

}
