package com.example.demo;

import com.example.demo.entities.Settings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends CrudRepository<Settings, Long> {
}
