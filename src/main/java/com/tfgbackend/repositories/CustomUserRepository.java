package com.tfgbackend.repositories;

import com.tfgbackend.model.User;
import org.bson.types.ObjectId;

import java.util.List;

public interface CustomUserRepository {

    public void updateUserFavorites(String userId, List<ObjectId> favoriteList);
}
