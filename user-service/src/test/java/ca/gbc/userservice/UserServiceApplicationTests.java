package ca.gbc.userservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.TestcontainersConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;


//import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceApplicationTests {
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port=port;


        //clears the initscript data and data from other tests
            RestAssured.given()
                    .contentType("application/json")
                    .when()
                    .delete("/api/user/deleteeverything")//base URI is local host but need to add extra URI
                    .then()
                    .log().all();


    }
    @BeforeAll
    static void startContainers(){
        postgreSQLContainer.start();

    }
    @AfterAll
    static void closeContainers(){
        postgreSQLContainer.close();


    }
    @Test
    void createUserTest() {

        String requestBody = """
                {
                        "name":"Audrey Tjandra",
                        "email":"tjandraaudrey@hotmail.com",
                        "role":"student",
                         "user_type": "Student"
                         }
                        
                """;

        //BDD - Behavioral Driven Development (given when, do this
        var response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/user")//base URI is local host but need to add extra URI
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Audrey Tjandra"))
                .body("email", Matchers.equalTo("tjandraaudrey@hotmail.com"))
                .body("role", Matchers.equalTo("student"))
                .body("userType", Matchers.equalTo("Student"))
                        ;

    }
    @Test
    void getUserTest() {
        String requestBody = """
                {
                        "name":"Audrey Tjandra",
                        "email":"tjandraaudrey@hotmail.com",
                        "role":"student",
                         "user_type": "Student"
                         }
                        
                """;
        //BDD - Behavioral Driven Development (given when, do this
        var response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/user")//base URI is local host but need to add extra URI
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Audrey Tjandra"))
                .body("email", Matchers.equalTo("tjandraaudrey@hotmail.com"))
                .body("role", Matchers.equalTo("student"))
                .body("userType", Matchers.equalTo("Student"));

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .get("/api/user")//base URI is local host but need to add extra URI
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", Matchers.greaterThan(0))
                .body("[0].id", Matchers.notNullValue())
                .body("[0].name", Matchers.equalTo("Audrey Tjandra"))
                .body("[0].email", Matchers.equalTo("tjandraaudrey@hotmail.com"))
                .body("[0].role", Matchers.equalTo("student"))
                .body("[0].userType", Matchers.equalTo("Student"));

        ;
    } @Test
    void updateUserTest() {
        String requestBody = """
                {
                        "name":"Audrey Tjandra",
                        "email":"tjandraaudrey@hotmail.com",
                        "role":"student",
                         "user_type": "Student"
                         }
                        
                """;
        String requestBody2 = """
                {
                        "name":"Audrey Tjandra",
                        "email":"audrey.tjandra@georgebrown.ca",
                        "role":"graduate",
                         "user_type": "Student"
                         }
                        
                """;
        //BDD - Behavioral Driven Development (given when, do this
        var response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/user")//base URI is local host but need to add extra URI
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Audrey Tjandra"))
                .body("email", Matchers.equalTo("tjandraaudrey@hotmail.com"))
                .body("role", Matchers.equalTo("student"))
                .body("userType", Matchers.equalTo("Student"))
                .extract()
                .response()
                .jsonPath();
        var id = response.get("id");

         RestAssured.given()
                .contentType("application/json")
                .body(requestBody2)
                .when()
                .put("/api/user/"+id.toString())//base URI is local host but need to add extra URI
                .then()
                .log().all()
                .statusCode(204);
         RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .get("/api/user")//base URI is local host but need to add extra URI
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                 .body("[0].name", Matchers.equalTo("Audrey Tjandra"))
                 .body("[0].email", Matchers.equalTo("audrey.tjandra@georgebrown.ca"))
                 .body("[0].role", Matchers.equalTo("graduate"))
                 .body("[0].userType", Matchers.equalTo("Student"));


    }
    @Test
    void deleteUserTest() {
        String requestBody = """
                {
                        "name":"Audrey Tjandra",
                        "email":"tjandraaudrey@hotmail.com",
                        "role":"student",
                         "user_type": "Student"
                         }
                        
                """;
        //BDD - Behavioral Driven Development (given when, do this
        var response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/user")//base URI is local host but need to add extra URI
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Audrey Tjandra"))
                .body("email", Matchers.equalTo("tjandraaudrey@hotmail.com"))
                .body("role", Matchers.equalTo("student"))
                .body("userType", Matchers.equalTo("Student"))
                .extract()
                .response()
                .jsonPath();
        var id = response.get("id");

        RestAssured.given()
                .contentType("application/json")
                .when()
                .delete("/api/user/"+id.toString())//base URI is local host but need to add extra URI
                .then()
                .log().all()
                .statusCode(204);

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .get("/api/user")//base URI is local host but need to add extra URI
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", Matchers.equalTo(0));
    }
    @Test
    void checkIfStaffTrue() {
        String requestBody = """
                 {
                        "name":"Audrey Tjandra",
                        "email":"tjandraaudrey@hotmail.com",
                        "role":"student",
                         "user_type": "Staff"
                         }
              
                """;

        //BDD - Behavioral Driven Development (given when, do this
        var response=RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/user")//base URI is local host but need to add extra URI
                .then()
                .log().all()
                .statusCode(201)
                 .body("id", Matchers.notNullValue())
                 .body("name", Matchers.equalTo("Audrey Tjandra"))
                 .body("email", Matchers.equalTo("tjandraaudrey@hotmail.com"))
                 .body("role", Matchers.equalTo("student"))
                 .body("userType", Matchers.equalTo("Staff"))
                 .extract()
                 .response()
                .jsonPath();
        var id=response.get("id");
        var isStaff = RestAssured.given()
                        .contentType("application/json")
                        .when()
                .get("/api/user/approve/"+id.toString())
                .then()
                .log().all()
                .extract()
                .response()
                .as(Boolean.class);

                assertThat(isStaff, Matchers.equalTo(true));

           }
    @Test
    void checkIfStaffFalse() {
        String requestBody = """
                 {
                        "name":"Audrey Tjandra",
                        "email":"tjandraaudrey@hotmail.com",
                        "role":"student",
                         "user_type": "Student"
                         }
              
                """;

        //BDD - Behavioral Driven Development (given when, do this
        var response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/user")//base URI is local host but need to add extra URI
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Audrey Tjandra"))
                .body("email", Matchers.equalTo("tjandraaudrey@hotmail.com"))
                .body("role", Matchers.equalTo("student"))
                .body("userType", Matchers.equalTo("Student"))
                .extract()
                .response()
                .jsonPath();
        var id=response.get("id");
        var isStaff = RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/api/user/approve/"+id.toString())
                .then()
                .log().all()
                .extract()
                .response()
                .as(Boolean.class);

        assertThat(isStaff, Matchers.equalTo(false));

    }


}
