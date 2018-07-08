package com.example.demo.services;

import com.example.demo.entities.SchedulerSettings;
import com.example.demo.repositories.SchedulerSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerSettingsService {
    @Autowired
    SchedulerSettingsRepository schedulerSettingsRepository;

    public void addSchedulerSettingsLine(SchedulerSettings schedulerSettings) {
        this.schedulerSettingsRepository.save(schedulerSettings);
    }

    public List<SchedulerSettings> findAll(){
        return (List<SchedulerSettings>) this.schedulerSettingsRepository.findAll();
    }

    public void delete(Long id){
        this.schedulerSettingsRepository.delete(id);
    }
}
