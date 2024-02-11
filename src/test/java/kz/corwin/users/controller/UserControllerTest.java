package kz.corwin.users.controller;

import io.restassured.RestAssured;
import kz.corwin.users.entity.User;
import kz.corwin.users.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/schema.sql")
@Testcontainers
class UserControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    //
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
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
        user.setDeleted(Boolean.FALSE);
        user.setDateOfBirdth(LocalDate.now());
        user.setCountry("KZ");

        ResponseEntity<String> createResponse =
                restTemplate.postForEntity("/users", user, String.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        String savedUser = createResponse.getBody();

        assert savedUser != null;
        org.assertj.core.api.Assertions.assertThat(savedUser).isEqualTo("Пользователь %s %s успешно добавлен", user.getSurname(), user.getUsername());

    }
}