package com.dmnine.geocoder.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.dmnine.geocoder.client.NominatimClient;
import com.dmnine.geocoder.dto.NominatimPlace;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GeocoderControllerTest {
  @LocalServerPort
  Integer port;
  private final TestRestTemplate testRestTemplate = new TestRestTemplate();

  @MockBean
  private NominatimClient nominatimClient;

  @Test
  void searchWhenNominatimNotResponse() {
    when(nominatimClient.search(anyString())).thenReturn(Optional.empty());
    ResponseEntity<NominatimPlace> response = testRestTemplate.
      getForEntity(
        "http://localhost:"+ port +"/geocoder/search/kubgu", NominatimPlace.class);

    assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    assertNull(response.getBody());
  }

  private static NominatimPlace buildTestPlace(){
    return new NominatimPlace("Кубгу","university",45.046580, 38.978289);
  }

  @Test
  void searchWhenNominatimResponse() {
    final NominatimPlace testPlace = buildTestPlace();
    when(nominatimClient.search(anyString())).thenReturn(Optional.of(buildTestPlace()));
    ResponseEntity<NominatimPlace> response = testRestTemplate.
      getForEntity(
        "http://localhost:"+ port +"/geocoder/search/kubgu", NominatimPlace.class);

    assertEquals(HttpStatus.OK,response.getStatusCode());
    final NominatimPlace body = response.getBody();
    assertEquals(testPlace,body);
  }

  @Test
  void reverseWhenNominatimNotResponse() {
    when(nominatimClient.reverse(anyString(),anyString())).thenReturn(Optional.empty());
    ResponseEntity<NominatimPlace> response = testRestTemplate.
      getForEntity(
        "http://localhost:"+ port +"/geocoder/reverse/45.046580&38.978289",
        NominatimPlace.class);

    assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    assertNull(response.getBody());
  }

  @Test
  void reverseWhenNominatimResponse() {
    final NominatimPlace testPlace = buildTestPlace();
    when(nominatimClient.reverse(anyString(),anyString())).thenReturn(Optional.of(buildTestPlace()));
    ResponseEntity<NominatimPlace> response = testRestTemplate.
      getForEntity(
        "http://localhost:"+port+"/geocoder/reverse/45.02036085&39.03099994504268",
        NominatimPlace.class);

    System.out.println(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    final NominatimPlace body = response.getBody();
    assertEquals(testPlace,body);
  }
}
