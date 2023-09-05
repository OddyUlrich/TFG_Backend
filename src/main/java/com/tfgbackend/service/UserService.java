package com.tfgbackend.service;

import com.tfgbackend.dto.UserDTO;
import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.forms.SignUpForm;
import com.tfgbackend.model.User;
import com.tfgbackend.model.enumerators.Rol;
import com.tfgbackend.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;

@Service
public class UserService {

    private final UserRepository ur;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository ur, PasswordEncoder encoder) {
        this.ur = ur;
        this.encoder = encoder;
    }

    public User create(SignUpForm userData) throws Exception {

        if (ur.existsByUsername(userData.getUsername())){
            throw new Exception("This username is already in use");
        }

        if (ur.existsByEmail(userData.getEmail())){
            throw new Exception("This email is already in use");
        }

        User finalUser = new User(null, userData.getUsername(), encoder.encode(userData.getPassword()), userData.getEmail(), LocalDateTime.now(), List.of(Rol.STUDENT), List.of());
        return ur.insert(finalUser);

    }

    public void updateUserFavorites(String email, String exerciseId) throws ResourceNotFoundException {
        User updateUser = ur.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User does not exist with that ID"));

        List<ObjectId> favoriteList = updateUser.getFavoriteExercises();
        ObjectId exerciseObjectId = new ObjectId(exerciseId);

        if (favoriteList.contains(exerciseObjectId)) {
            favoriteList.remove(exerciseObjectId);
            ur.updateUserFavorites(updateUser.getId(), favoriteList);
        } else {
            favoriteList.add(exerciseObjectId);
            ur.updateUserFavorites(updateUser.getId(), favoriteList);
        }
    }

    public User getUser(String email) throws ResourceNotFoundException {
        return ur.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User does not exist with that email"));
    }

    public UserDTO getUserInfo(String email) {
        return ur.getUserInfo(email).orElseThrow(() -> new ResourceNotFoundException("User does not exist with that email"));
    }

}
