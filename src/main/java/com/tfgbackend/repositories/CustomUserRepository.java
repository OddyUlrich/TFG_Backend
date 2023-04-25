package com.tfgbackend.repositories;

import org.bson.types.ObjectId;

import java.util.List;

public interface CustomUserRepository {

    void updateUserFavorites(String userId, List<ObjectId> favoriteList);
}
