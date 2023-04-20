package com.tfgbackend.service;

import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.User;
import com.tfgbackend.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository ur;
    private final ExerciseService es;

    @Autowired
    public UserService(UserRepository ur, ExerciseService es) {
        this.ur = ur;
        this.es = es;
    }

    public void updateUserFavorites(String userId, String exerciseId) throws ResourceNotFoundException {
        User updateUser = ur.findUserById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exist with that ID"));
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
