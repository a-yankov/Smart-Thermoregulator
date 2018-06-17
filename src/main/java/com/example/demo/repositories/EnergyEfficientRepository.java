package com.example.demo.repositories;

import com.example.demo.entities.Energy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface EnergyEfficientRepository extends CrudRepository<Energy, Long> {
    public Iterable<Energy> findByDateTimeBetween(Date fromDate, Date toDate);
}
