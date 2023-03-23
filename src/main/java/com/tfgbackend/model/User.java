package com.tfgbackend.model;

import com.tfgbackend.model.enumerators.Rol;
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
    private @Indexed(unique = true)
    @NotBlank String name;
    private @Indexed(unique = true)
    @NotBlank String email;
    private LocalDateTime birthday;
    private @NotNull Rol rol;
    private List<ObjectId> favoriteExercises;


    public User(String id, String name, String email, LocalDateTime birthday, Rol rol, List<ObjectId> favoriteExercises) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.rol = rol;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<ObjectId> getFavoriteExercises() {
        return favoriteExercises;
    }

    public void setFavoriteExercises(List<ObjectId> favoriteExercises) {
        this.favoriteExercises = favoriteExercises;
    }

    @Override
    public String toString() {
        return String.format(
                "User[" + name + "," + email + "," + rol + "]"
        );
    }
}