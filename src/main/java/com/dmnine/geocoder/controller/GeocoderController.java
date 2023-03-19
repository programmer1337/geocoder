package com.dmnine.geocoder.controller;

import com.dmnine.geocoder.client.NominatimClient;
import com.dmnine.geocoder.dto.NominatimPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Управление запросами к геокодеру.
 */

@RestController
@RequestMapping("geocoder")
public class GeocoderController {

  private final NominatimClient nominatimClient;
  @Autowired
  public GeocoderController(final NominatimClient nominatimClient) {
    this.nominatimClient = nominatimClient;
  }

  @GetMapping(value = "/search/{place}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<NominatimPlace> search(final @PathVariable String place) {
    return nominatimClient.search(place)
      .map(a -> ResponseEntity.status(HttpStatus.OK).body(a))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping(value = "/reverse/{lat}&{lon}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<NominatimPlace> reverse(final @PathVariable String lat, final @PathVariable String lon) {
    return nominatimClient.reverse(lat, lon).map(p -> ResponseEntity.status(HttpStatus.OK).body(p))
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
