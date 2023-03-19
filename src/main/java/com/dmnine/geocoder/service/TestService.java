package com.dmnine.geocoder.service;


import com.dmnine.geocoder.model.Mark;
import com.dmnine.geocoder.model.Test;
import com.dmnine.geocoder.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Тестовый сервис.
 * Создан для работы с данными.
 * Позволяет сохранить и загрузить информацию о человеке.
 */

@Service
public class TestService {

    private final TestRepository repository;

    @Autowired
    public TestService(final TestRepository repository) {
        this.repository = repository;
    }

    public Test build(final Integer id, final String name, final Boolean done) {
        final Test test = new Test();
        test.setId(id);
        test.setName(name);
        test.setDone(done);
        return test;
    }

    public void saveData(final String name, final Mark mark, final Boolean done) {
        final Test test = new Test();
        test.setName(name);
        test.setMark(mark);
        test.setDone(done);

        repository.save(test);
    }

    public Test loadData(final String name) {
      return repository.findByName(name)
        .orElse(null);
    }
}
