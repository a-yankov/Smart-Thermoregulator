package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Energy {
    @Id
    @GeneratedValue
    private int id;
    private int workingTimeInSeconds;
    private Timestamp dateTime;

    public Energy(){

    }
    public Energy(int workingTimeInSeconds, Timestamp dateTime){
        this.workingTimeInSeconds = workingTimeInSeconds;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkingTimeInSeconds() {
        return workingTimeInSeconds;
    }

    public void setWorkingTimeInSeconds(int workingTimeInSeconds) {
        this.workingTimeInSeconds = workingTimeInSeconds;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}
