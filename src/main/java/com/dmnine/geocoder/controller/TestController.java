package com.dmnine.geocoder.controller;

import com.dmnine.geocoder.model.Mark;
import com.dmnine.geocoder.model.Test;
import com.dmnine.geocoder.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("tests")
public class TestController {

  private TestService service;

  @Autowired
  public TestController(TestService service) {
    this.service = service;
  }

  @GetMapping(value="/{id}", produces = APPLICATION_JSON_VALUE)
  public Test getTest(@PathVariable Integer id,
                      @RequestParam String name) {

    if(id>100) {
      return service.build(id, name, true);
    }else return service.build(id, name, false);
  }

  @GetMapping(value="/save", produces = APPLICATION_JSON_VALUE)
  public void save(@RequestParam String name) {

    service.saveData(name, Mark.A, true);
  }

  @GetMapping(value="/load/{name}", produces = APPLICATION_JSON_VALUE)
  public Test load(@PathVariable String name) {
    return service.loadData(name);
  }

}
