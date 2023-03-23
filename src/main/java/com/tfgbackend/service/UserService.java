package com.tfgbackend.service;

import com.tfgbackend.exceptions.ResourceNotFoundException;
import com.tfgbackend.model.Exercise;
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
    public UserService(UserRepository ur, ExerciseService es){
        this.ur = ur;
        this.es = es;
    }

    public User updateUserFavorites(String userId, String exerciseId) throws ResourceNotFoundException{
        User updateUser = ur.findUserById(userId);
        Exercise exercise = es.findExerciseById(exerciseId);

        if (updateUser == null) {
            throw new ResourceNotFoundException("User does not exist with that ID");
        }

        if (exercise == null){
            throw new ResourceNotFoundException("Exercise does not exist with that ID");
        }

        List<ObjectId> favoriteList = updateUser.getFavoriteExercises();
        for (int i = 0; i < favoriteList.size(); i++){
            if (favoriteList.get(i).toString().equals(exerciseId)){
                favoriteList.remove(i);
                ur.save(updateUser);
                return updateUser;
            }
        }
        favoriteList.add(new ObjectId(exerciseId));
        ur.save(updateUser);
        return updateUser;
    }
}
