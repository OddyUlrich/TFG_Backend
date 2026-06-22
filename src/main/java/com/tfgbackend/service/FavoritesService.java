package com.tfgbackend.service;

import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.Exercise;
import com.tfgbackend.model.User;
import com.tfgbackend.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritesService {

    private final ExerciseService es;
    private final UserRepository ur;

    @Autowired
    public FavoritesService(UserRepository ur, ExerciseService es) {
        this.ur = ur;
        this.es = es;
    }

    public void updateUserFavorites(String email, String exerciseId) throws ResourceNotFoundException {

        //Comprobamos si el ejercicio existe buscando por su ID
        Exercise exercise = es.findExerciseById(exerciseId);

        User userToUpdate = ur.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User does not exist with that ID"));

        List<ObjectId> favoriteList = userToUpdate.getFavoriteExercises();
        ObjectId exerciseObjectId = new ObjectId(exercise.getId());

        if (favoriteList.contains(exerciseObjectId)) {
            favoriteList.remove(exerciseObjectId);
            ur.updateUserFavorites(userToUpdate.getId(), favoriteList);
        } else {
            favoriteList.add(exerciseObjectId);
            ur.updateUserFavorites(userToUpdate.getId(), favoriteList);
        }
    }
}
