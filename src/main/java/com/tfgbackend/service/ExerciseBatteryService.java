package com.tfgbackend.service;

import com.tfgbackend.model.ExerciseBattery;
import com.tfgbackend.repository.ExerciseBatteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseBatteryService {

    private final ExerciseBatteryRepository ebr;

    @Autowired
    public ExerciseBatteryService(ExerciseBatteryRepository ebr) {
        this.ebr = ebr;
    }

    public List<ExerciseBattery> findAll() {
        return this.ebr.findAll();
    }

    public ExerciseBattery findBatteryByName(String batteryName) {
        return ebr.findByName(batteryName);
    }

    public void saveBattery(ExerciseBattery battery){
        ebr.save(battery);
    }
}