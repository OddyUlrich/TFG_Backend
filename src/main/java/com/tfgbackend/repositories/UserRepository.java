package com.tfgbackend.repositories;

import com.tfgbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {

    User findUserByName(String name);

}
