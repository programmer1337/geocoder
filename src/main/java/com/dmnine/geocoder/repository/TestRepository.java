package com.dmnine.geocoder.repository;

import com.dmnine.geocoder.model.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends CrudRepository<Test, Integer> {
    Optional <Test> findByName(String name);
}
