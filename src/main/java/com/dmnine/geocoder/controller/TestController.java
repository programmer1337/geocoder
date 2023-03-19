package com.dmnine.geocoder.controller;

import com.dmnine.geocoder.dto.NominatimPlace;
import com.dmnine.geocoder.dto.RestApiError;
import com.dmnine.geocoder.model.Mark;
import com.dmnine.geocoder.model.Test;
import com.dmnine.geocoder.service.TestService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Управление запросами к тесту.
 */

@RestController
@RequestMapping("tests")
public class TestController {

  private final TestService service;

  @Autowired
  public TestController(final TestService service) {
    this.service = service;
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  public Test getTest(final @PathVariable Integer id,
                      final @RequestParam String name) {
    final boolean whatMark = id > 100;
    if (whatMark) {
      return service.build(id, name, true);
    } else {
      return service.build(id, name, false);
    }
  }

  @GetMapping(value = "/save", produces = APPLICATION_JSON_VALUE)
  public void save(final @RequestParam String name) {
    service.saveData(name, Mark.A, true);
  }

  @GetMapping(value = "/load/{name}", produces = APPLICATION_JSON_VALUE)
  public Test load(final @PathVariable String name) {
    return service.loadData(name);
  }

  @GetMapping(value = "/status", produces = APPLICATION_JSON_VALUE)
  @SuppressWarnings("PWD.AvoidLiteralsInIfCondition")
  @SuppressFBWarnings("DMI_RANDOM_USED_ONLY_ONCE")
  public ResponseEntity<Object> hello() {
    final Random random = new Random();
    final Double half = 0.5;
    if (random.nextDouble() > half) {
      final NominatimPlace place = new NominatimPlace();
      return
        ResponseEntity
          .status(HttpStatus.OK)
          .body(place);

    } else {
      final RestApiError error = new RestApiError();
      return
        ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(error);
    }
  }
}
