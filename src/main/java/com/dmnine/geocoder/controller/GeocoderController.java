package com.dmnine.geocoder.controller;

import com.dmnine.geocoder.model.Place;
import com.dmnine.geocoder.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Управление запросами к геокодеру.
 * Работа с сервисами.
 */

@RestController
@RequestMapping("geocoder")
public class GeocoderController {
  private final PlaceService placeService;

  @Autowired
  public GeocoderController(final PlaceService placeService) {
    this.placeService = placeService;
  }

  @GetMapping(value = "/search/{query}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Place> search(final @PathVariable String query) {
    return placeService.search(query).map(a -> ResponseEntity.status(HttpStatus.OK).body(a))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping(value = "/reverse", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Place> reverse(final @RequestParam Double lat, final @RequestParam Double lon) {
    return placeService.reverse(lat, lon).map(p -> ResponseEntity.status(HttpStatus.OK).body(p))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
