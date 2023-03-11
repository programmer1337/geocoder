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

  @GetMapping(value="/searchPlace/{place}", produces = APPLICATION_JSON_VALUE)
  public NominatimPlace searchPlace(@PathVariable String place) {
    return nominatimClient.search(place, "json").get(0);
  }

  @GetMapping(value="/reverse/{lat}&{lon}", produces = APPLICATION_JSON_VALUE)
  public NominatimPlace reverse(@PathVariable String lat, @PathVariable String lon) {
    return nominatimClient.reverse(lat, lon, "json");
  }
}
