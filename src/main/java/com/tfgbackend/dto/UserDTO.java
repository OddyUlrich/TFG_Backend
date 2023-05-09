package com.tfgbackend.dto;

import com.tfgbackend.model.enumerators.Rol;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {

    private String username;
    private String email;
    private LocalDateTime creationDate;
    private List<Rol> roles;

    public UserDTO(String username, String email, LocalDateTime creationDate, List<Rol> roles) {
        this.username = username;
        this.email = email;
        this.creationDate = creationDate;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", creationDate=" + creationDate +
                ", roles=" + roles +
                '}';
    }
}
