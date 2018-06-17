package com.example.demo.repositories;

import com.example.demo.entities.Energy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface EnergyEfficientRepository extends CrudRepository<Energy, Long> {
    //public Iterable<Energy> findByDateTimeBetween(Date fromDate, Date toDate);


    //@Query("select e.id, e.workingTimeInSeconds, e.workingTimeInSeconds from Energy e where e.dateTime between ?1 and  ?2")
    public Iterable<Energy> findAllByDateTimeBetween(Date fromDate, Date toDate);
}
