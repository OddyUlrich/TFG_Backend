package com.tfgbackend.service;

import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.User;
import com.tfgbackend.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository ur;
    private final ExerciseService es;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository ur, ExerciseService es, PasswordEncoder encoder) {
        this.ur = ur;
        this.es = es;
        this.encoder = encoder;
    }

    public Optional<User> create(User user){
        if(!ur.existsByEmail(user.getEmail())) {
            user.setPassword(encoder.encode(user.getPassword()));
            return Optional.of(ur.insert(user));
        } else {
            return Optional.empty();
        }
    }

    public void updateUserFavorites(String userId, String exerciseId) throws ResourceNotFoundException {
        User updateUser = ur.findUserById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exist with that ID"));

        //This method is only used here to check if the exercise exists. If not, an exception is thrown
        es.findExerciseById(exerciseId);

        List<ObjectId> favoriteList = updateUser.getFavoriteExercises();
        ObjectId exerciseObjectId = new ObjectId(exerciseId);

        if (favoriteList.contains(exerciseObjectId)) {
            favoriteList.remove(exerciseObjectId);
            ur.updateUserFavorites(userId, favoriteList);
        }else{
            favoriteList.add(exerciseObjectId);
            ur.updateUserFavorites(userId, favoriteList);
        }
    }
}
