package com.tfgbackend.repository;

import com.tfgbackend.dto.UserDTO;
import com.tfgbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long>, CustomUserRepository{

    User findUserByUsername(String username);

    Optional<User> findUserById(String id);

    Optional<User> findUserByEmail(String email);

    @Query(value = "{ 'email': ?0 }", fields = "{'username': 1, 'email' : 1, 'creationDate': 1, 'roles':  1}")
    Optional<UserDTO> getUserInfo (String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
