package com.tfgbackend.repositories;

import com.tfgbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long>, CustomUserRepository{

    User findUserByName(String name);

    Optional<User> findUserById(String id);

    boolean existsByEmail(String email);

}
