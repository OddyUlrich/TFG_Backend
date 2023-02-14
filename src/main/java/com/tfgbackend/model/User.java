package com.tfgbackend.model;

import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDateTime;

public record User(
        @Indexed(unique = true) String name,
        @Indexed(unique = true) String email,
        LocalDateTime birthday,
        Rol rol) {

    @Override
    public String toString() {
        return String.format(
                "User[" + name + "," + email + "," + rol + "]"
        );
    }
}
