package com.dmnine.geocoder.service;


import com.dmnine.geocoder.model.Mark;
import com.dmnine.geocoder.model.Test;
import com.dmnine.geocoder.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private TestRepository repository;

    @Autowired
    public TestService(TestRepository repository) {
        this.repository = repository;
    }

    public Test build(Integer id, String name, Boolean done){
        Test test = new Test();
        test.setId(id);
        test.setName(name);
        test.setDone(done);
        return test;
    }

    public void saveData(String name, Mark mark, Boolean done){
        Test test =  new Test();
        test.setName(name);
        test.setMark(mark);
        test.setDone(done);

        repository.save(test);
    }

    public Test loadData(String name) {
      return repository.findByName(name)
        .orElse(null);
    }
}
