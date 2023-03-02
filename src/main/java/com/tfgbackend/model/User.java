package com.tfgbackend.model;

import com.tfgbackend.model.enumerators.Rol;
import jakarta.validation.Valid;
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
    private ObjectId id;
    private @Indexed(unique = true)
    @NotBlank String name;
    private @Indexed(unique = true)
    @NotBlank String email;
    private LocalDateTime birthday;
    private List<@Valid Subject> subjects;
    private @NotNull Rol rol;

    public User(ObjectId id, String name, String email, LocalDateTime birthday, List<Subject> subjects, Rol rol) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.subjects = subjects;
        this.rol = rol;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return String.format(
                "User[" + name + "," + email + "," + rol + "]"
        );
    }
}