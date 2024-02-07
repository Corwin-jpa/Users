package kz.corwin.users.controller;

import io.restassured.RestAssured;
import kz.corwin.users.entity.User;
import kz.corwin.users.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

//    @BeforeAll
//    static void beforeAll() {
//        postgres.start();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        postgres.stop();
//    }
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }
//
//    @BeforeEach
//    void setUp() {
//        RestAssured.baseURI = "http://localhost:" + port;
//        userRepository.deleteAll();
//    }

    @Test
    public void testUserEndpoints() {

        // Create a new User
        User user = new User();
        user.setUsername("John");
        user.setSurname("Snow");

        ResponseEntity<User> createResponse =
                restTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        User savedUser = createResponse.getBody();

        assert savedUser != null;

        // Retrieve
        ResponseEntity<User> getResponse =
                restTemplate.getForEntity("/users/" + savedUser.getId(), User.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        User userFromGet = getResponse.getBody();

        assert userFromGet != null;

        assertEquals("John", userFromGet.getUsername());
        assertEquals("Snow", userFromGet.getSurname());

        // Retrieve All
        ResponseEntity<User[]> getAllResponse =
                restTemplate.getForEntity("/users", User[].class);
        assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());

        User[] userFromGetAll = getAllResponse.getBody();
        assert userFromGetAll != null;

        assertEquals(1, userFromGetAll.length);
    }
}