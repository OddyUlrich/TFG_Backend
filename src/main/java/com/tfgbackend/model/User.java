package com.tfgbackend.model;

import com.tfgbackend.model.enumerator.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("users")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    @NotBlank(message = "User's username can not be empty")
    private String username;
    @NotBlank(message = "User's password can not be empty")
    private String password;
    @Indexed(unique = true)
    @Email(message = "Email format is incorrect")
    @NotBlank(message = "User's email can not be empty")
    private String email;
    private LocalDateTime creationDate;
    private @NotNull List<Rol> roles;
    private List<ObjectId> favoriteExercises;


    public User(String id, String username, String password, String email, LocalDateTime creationDate, List<Rol> roles, List<ObjectId> favoriteExercises) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.creationDate = creationDate;
        this.roles = roles;
        this.favoriteExercises = favoriteExercises;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<ObjectId> getFavoriteExercises() {
        return favoriteExercises;
    }

    public void setFavoriteExercises(List<ObjectId> favoriteExercises) {
        this.favoriteExercises = favoriteExercises;
    }

    @Override
    public String toString() {
        return String.format("User[" + username + "," + email + "," + roles + "]");
    }
}