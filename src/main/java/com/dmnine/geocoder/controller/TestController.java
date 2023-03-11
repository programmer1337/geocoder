package com.dmnine.geocoder.controller;

import com.dmnine.geocoder.client.NominatimClient;
import com.dmnine.geocoder.dto.NominatimPlace;
import com.dmnine.geocoder.model.Mark;
import com.dmnine.geocoder.model.Test;
import com.dmnine.geocoder.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("tests")
public class TestController {

  private TestService service;
  private NominatimClient nominatimClient;

  @Autowired
  public TestController(TestService service, NominatimClient nominatimClient) {
    this.service = service;
    this.nominatimClient = nominatimClient;
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

  @GetMapping(value="/searchPlace", produces = APPLICATION_JSON_VALUE)
  public NominatimPlace searchPlace() {
    return nominatimClient.search("кубгу", "json").get(0);
  }

  @GetMapping(value="/reverse", produces = APPLICATION_JSON_VALUE)
  public String reverse() {
    return nominatimClient.reverse("45.019634", "39.031161").getDisplayName();
  }
}
