package com.tfgbackend.service;

import com.tfgbackend.dto.SolutionDTO;
import com.tfgbackend.exception.ResourceNotFoundException;
import com.tfgbackend.model.Solution;
import com.tfgbackend.model.User;
import com.tfgbackend.model.enumerator.StatusExercise;
import com.tfgbackend.repository.SolutionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SolutionService {

    private final SolutionRepository sr;
    private final UserService us;

    @Autowired
    public SolutionService(SolutionRepository sr, UserService us){
        this.sr = sr;
        this.us = us;
    }

    public List<SolutionDTO> allSolutionsByExerciseIdAndStudent(String exerciseId, String email){
        User user = us.getUserByEmail(email);
        return sr.allSolutionsByExerciseIdAndStudent(new ObjectId(exerciseId), new ObjectId(user.getId())).orElseThrow(() -> new ResourceNotFoundException("Solutions about exercise could not be obtained"));
    }

    public Solution findSolutionById(String solutionId){
        return sr.findById(solutionId).orElseThrow(() -> new ResourceNotFoundException("Solutions with that ID cannot be found"));
    }

    public void saveSolution(Solution solution){
        sr.save(solution);
    }

    public Solution getLatestSolution(List<Solution> solutions) {

        if (solutions == null || solutions.isEmpty()) {
            return null;
        }

        return solutions.stream()
                .max((a, b) -> {

                    // 1. COMPLETED tiene prioridad
                    if (a.getStatus() == StatusExercise.COMPLETED && b.getStatus() != StatusExercise.COMPLETED) return 1;
                    if (b.getStatus() == StatusExercise.COMPLETED && a.getStatus() != StatusExercise.COMPLETED) return -1;

                    // 2. si ambos son COMPLETED o ambos no:
                    return a.getUpdateTimestamp()
                            .compareTo(b.getUpdateTimestamp());
                })
                .orElse(null);
    }

}
