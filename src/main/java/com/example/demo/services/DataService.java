package com.example.demo.services;

import com.example.demo.DataRepository;
import com.example.demo.entities.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Service
public class DataService {
    @Autowired
    DataRepository dataRepository;

    public void save(Data data) {
        Date date = new Date();
        data.setTimestamp(new Timestamp(date.getTime()));
        dataRepository.save(data);
    }
    public Set<Data> findAll(){
        return this.dataRepository.findAll();
    }
}
