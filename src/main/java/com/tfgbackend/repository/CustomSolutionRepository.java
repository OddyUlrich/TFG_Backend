package com.tfgbackend.repository;

import com.tfgbackend.model.enumerator.StatusExercise;

public interface CustomSolutionRepository {

   void updateSolutionStatus(String solutionId, StatusExercise status);

}
