package com.dmnine.geocoder.controller;

import com.dmnine.geocoder.dto.RestApiError;
import com.dmnine.geocoder.model.Mark;
import com.dmnine.geocoder.repository.TestRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
  @Autowired
  TestRepository testRepository;

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
    assertEquals(false, body.isDone());
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

    assertEquals(400, body.status());
    assertEquals("Bad Request", body.error());
    assertEquals("/tests/abc", body.path());

    System.out.println(body);
  }

  @Test
  void integrationTestForSavePositive(){
    ResponseEntity<com.dmnine.geocoder.model.Test> response = testRestTemplate.
      getForEntity("http://localhost:"+port+"/tests/save?name=test",
        com.dmnine.geocoder.model.Test.class);

    final com.dmnine.geocoder.model.Test body = response.getBody();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(body);
  }

  @Test
  void integrationTestForSaveVoid(){
    ResponseEntity<Void> response = testRestTemplate.
      getForEntity("http://localhost:"+port+"/tests/save?name=test",
        Void.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void integrationTestForSaveWithoutName() {
    ResponseEntity<com.dmnine.geocoder.model.Test> response = testRestTemplate.
      getForEntity("http://localhost:"+port+"/tests/save",
        com.dmnine.geocoder.model.Test.class);

    final com.dmnine.geocoder.model.Test body = response.getBody();

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(body);
  }

  @Test
  void integrationTestForLoadNameNotCreated() {
    ResponseEntity<com.dmnine.geocoder.model.Test> response = testRestTemplate.
      getForEntity("http://localhost:"+port+"/tests/load/test2",
        com.dmnine.geocoder.model.Test.class);

    final com.dmnine.geocoder.model.Test body = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(body);
  }

  @Test
  void integrationTestForLoadWithoutName() {
    ResponseEntity<com.dmnine.geocoder.model.Test> response = testRestTemplate.
      getForEntity("http://localhost:"+port+"/tests/load",
        com.dmnine.geocoder.model.Test.class);

    final com.dmnine.geocoder.model.Test body = response.getBody();

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(body);
  }

  @Test
  void integrationTestForLoadWithTestRepository() {
    com.dmnine.geocoder.model.Test test = new com.dmnine.geocoder.model.Test();
    test.setId(1);
    test.setName("Mario");
    test.setMark(Mark.A);
    test.setDone(true);
    testRepository.save(test);

    ResponseEntity<com.dmnine.geocoder.model.Test> response = testRestTemplate.
      getForEntity("http://localhost:"+port+"/tests/load/Mario",
        com.dmnine.geocoder.model.Test.class);

    final com.dmnine.geocoder.model.Test body = response.getBody();

    System.out.println(body);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1,body.getId());
    assertEquals("Mario",body.getName());
    assertEquals(Mark.A,body.getMark());
    assertEquals(true,body.isDone());
  }
}
