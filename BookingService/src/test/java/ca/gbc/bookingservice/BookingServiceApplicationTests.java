package ca.gbc.bookingservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingServiceApplicationTests {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongodb");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mongoDBContainer.start();
    }

    @Test
    void contextLoads() {
        // check what youre running here.
        // log
        //define request body, multiline requesst
        //restassured.given .body .when .post (endpoint yore querying).
        // . endpoint. then whatever the response gives back
        // i need to stup a mock request

        // a service, stubs ar fake endpoint. that is a stun you mock.

    }

}
