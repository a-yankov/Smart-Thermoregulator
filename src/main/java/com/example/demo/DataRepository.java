package com.example.demo;

import com.example.demo.entities.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface DataRepository extends CrudRepository<Data, Long>{

    @Override
    Set<Data> findAll();
}
