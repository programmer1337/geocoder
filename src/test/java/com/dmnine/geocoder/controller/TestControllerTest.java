package com.dmnine.geocoder.controller;

import com.dmnine.geocoder.dto.RestApiError;
import com.dmnine.geocoder.util.TestUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest {

  @LocalServerPort
  Integer port;

  private final TestRestTemplate testRestTemplate = new TestRestTemplate();

  @BeforeEach
  void setUp() {
    System.out.println("setUp");
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("BeforeAll");
  }

  @AfterEach
  void tearDown() {
    System.out.println("tearDown");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("AfterAll");
  }

  @Test
  void integrationTest() {
    ResponseEntity<com.dmnine.geocoder.model.Test> response = testRestTemplate.
      getForEntity("http://localhost:"+port+"/tests/1?name=test",
        com.dmnine.geocoder.model.Test.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    final com.dmnine.geocoder.model.Test body = response.getBody();

    assertEquals(1, body.getId());
    assertEquals("test", body.getName());
    assertEquals(false, body.getDone());
    assertEquals(null, body.getMark());

  }

  @Test
    //without name0
  void integrationTestWhenAnswerIsMap() {
    ResponseEntity<Map<String,String>> response = testRestTemplate.
      exchange("http://localhost:"+port+"/tests/1",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Map<String, String>>(){});

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    final Map <String, String> body = response.getBody();

    assertEquals("400", body.get("status"));
    assertEquals("Bad Request", body.get("error"));
    assertEquals("/tests/1", body.get("path"));

    System.out.println(body);
  }

  @Test
  void integrationTestWhenIdIsString() {
    ResponseEntity<RestApiError> response = testRestTemplate.
      exchange("http://localhost:"+port+"/tests/abc?name=test",
        HttpMethod.GET,
        null,
        RestApiError.class);

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    final RestApiError body = response.getBody();

    assertEquals(400, body.getStatus());
    assertEquals("Bad Request", body.getError());
    assertEquals("/tests/abc", body.getPath());

    System.out.println(body);
  }
}
