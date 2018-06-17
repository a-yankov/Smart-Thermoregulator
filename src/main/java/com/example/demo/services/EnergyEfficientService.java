package com.example.demo.services;

import com.example.demo.entities.Energy;
import com.example.demo.repositories.EnergyEfficientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;


@Service
public class EnergyEfficientService {

    @Autowired
    EnergyEfficientRepository energyEfficientRepository;


    public Iterable<Energy> findAll(){
        return this.energyEfficientRepository.findAll();
    }


    public int getWorkingTime(){
        Iterable<Energy> energySet = this.findAll();

        int workTime = 0;

        for (Energy energy : energySet) {
            workTime += energy.getWorkingTimeInSeconds();
        }
        return workTime;
    }

    public int getWorkingTime(Date fromDate, Date toDate){
        Iterable<Energy> energySet = this.energyEfficientRepository.findByDateTimeBetween(fromDate, toDate);
        System.out.println("hello");
        int workTime = 0;

        for (Energy energy : energySet) {
            workTime += energy.getWorkingTimeInSeconds();
        }
        return workTime;
    }
}
