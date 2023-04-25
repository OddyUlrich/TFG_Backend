package com.tfgbackend.model;

import com.tfgbackend.model.enumerators.Rol;
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
    @NotBlank(message = "User's name can not be empty")
    private String name;
    @NotBlank(message = "User's password can not be empty")
    private String password;
    @Indexed(unique = true)
    @Email(message = "Email format is incorrect")
    @NotBlank(message = "User's email can not be empty")
    private String email;
    private LocalDateTime birthday;
    private @NotNull List<Rol> rols;
    private List<ObjectId> favoriteExercises;


    public User(String id, String name, String password, String email, LocalDateTime birthday, List<Rol> rols, List<ObjectId> favoriteExercises) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.rols = rols;
        this.favoriteExercises = favoriteExercises;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public List<Rol> getRols() {
        return rols;
    }

    public void setRols(List<Rol> rols) {
        this.rols = rols;
    }

    public List<ObjectId> getFavoriteExercises() {
        return favoriteExercises;
    }

    public void setFavoriteExercises(List<ObjectId> favoriteExercises) {
        this.favoriteExercises = favoriteExercises;
    }

    @Override
    public String toString() {
        return String.format("User[" + name + "," + email + "," + rols + "]");
    }
}