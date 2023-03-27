package com.dmnine.geocoder.controller;

import com.dmnine.geocoder.dto.NominatimPlace;
import com.dmnine.geocoder.model.Place;
import com.dmnine.geocoder.repository.PlaceRepository;
import com.dmnine.geocoder.service.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GeocoderControllerTest {
  @LocalServerPort
  Integer port;
  private final TestRestTemplate testRestTemplate = new TestRestTemplate();

  @Autowired
  private PlaceRepository placeRepository;

  @MockBean
  private PlaceService placeService;

  @BeforeEach
  void setup(){
    placeRepository.deleteAll();
  }

  @Test
  void searchWhenNominatimNotResponse() {
    final String query = "kubgu";
    when(placeService.search(anyString()))
      .thenReturn(Optional.empty());

    ResponseEntity<NominatimPlace> response = testRestTemplate.
      getForEntity(
        "http://localhost:"+ port +"/geocoder/search/" + query, NominatimPlace.class);

    assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    assertNull(response.getBody());
  }

  private static NominatimPlace buildTestPlace(){
    return new NominatimPlace("Кубгу","university",45.046580, 38.978289);
  }

  private static Place buildTestAddress(final String query){
    return Place.of(buildTestPlace(), query);
  }

  @Test
  void searchWhenNominatimResponse() {
    final String query = "kubgu";
    final Place testPlace = buildTestAddress(query);
    when(placeService.search(anyString()))
      .thenReturn(Optional.of(buildTestAddress(query)));

    ResponseEntity<Place> response = testRestTemplate.
      getForEntity(
        "http://localhost:"+ port +"/geocoder/search/" + query, Place.class);

    assertEquals(HttpStatus.OK,response.getStatusCode());
    final Place body = response.getBody();
    assertEquals(testPlace,body);
  }

  @Test
  void reverseWhenNominatimNotResponse() {
    when(placeService.reverse(anyDouble(), anyDouble()))
      .thenReturn(Optional.empty());

    ResponseEntity<Place> response = testRestTemplate.
      getForEntity(
        "http://localhost:"+ port +"/geocoder/reverse/45.046580&38.978289",
        Place.class);

    assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    assertNull(response.getBody());
  }

  @Test
  void reverseWhenNominatimResponse() {
    final String query = "kubgu";
    final Place testPlace = buildTestAddress(query);
    when(placeService.reverse(anyDouble(), anyDouble()))
      .thenReturn(Optional.of(buildTestAddress(query)));

    ResponseEntity<Place> response = testRestTemplate.
      getForEntity(
        "http://localhost:"+port+"/geocoder/reverse/45.02036085&39.03099994504268",
        Place.class);

    System.out.println(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    final Place body = response.getBody();
    assertEquals(testPlace,body);
  }
}
