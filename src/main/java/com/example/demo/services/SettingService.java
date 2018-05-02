package com.example.demo.services;

import com.example.demo.SettingsRepository;
import com.example.demo.entities.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {
    @Autowired
    SettingsRepository settingsRepository;

    public void saveSettings(List<Settings> settings){
        this.settingsRepository.save(settings);
    }

    public List<Settings> getSettings (){
        return (List<Settings>) this.settingsRepository.findAll();
    }
}
