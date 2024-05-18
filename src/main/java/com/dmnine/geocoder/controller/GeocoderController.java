package com.dmnine.geocoder.controller;

import com.dmnine.geocoder.model.Place;
import com.dmnine.geocoder.service.PlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("geocoder")
public class GeocoderController {
  private final PlaceService placeService;
  private static final Logger logger = LoggerFactory.getLogger(GeocoderController.class);

  @Autowired
  public GeocoderController(final PlaceService placeService) {
    this.placeService = placeService;
  }

  @GetMapping(value = "/search/{query}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Place> search(final @PathVariable String query) {
    logger.info("Accessing search endpoint with query: {}", query);
    return placeService.search(query).map(a -> ResponseEntity.status(HttpStatus.OK).body(a))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping(value = "/reverse", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Place> reverse(final @RequestParam Double lat, final @RequestParam Double lon) {
    return placeService.reverse(lat, lon).map(p -> ResponseEntity.status(HttpStatus.OK).body(p))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping("/health")
  public ResponseEntity<String> health() {
    logger.info("health");
    return ResponseEntity.ok("Service is up and running");
  }

  @GetMapping("/ready")
  public ResponseEntity<String> ready() {
    logger.info("ready");
    return ResponseEntity.ok("Service is ready to accept requests");
  }
}
