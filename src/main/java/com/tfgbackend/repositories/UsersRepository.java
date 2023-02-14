package com.tfgbackend.repositories;

import com.tfgbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UsersRepository extends MongoRepository<User, Long> {

    List<User> findUserByName(String name);
}
