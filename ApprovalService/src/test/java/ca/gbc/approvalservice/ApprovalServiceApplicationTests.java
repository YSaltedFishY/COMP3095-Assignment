package ca.gbc.approvalservice;

import ca.gbc.approvalservice.stub.UserServiceClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApprovalServiceApplicationTests {

	@ServiceConnection
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");



	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;

	}

	static {
		postgreSQLContainer.start();
	}

	@Test
	void createApprovalRequestTest() {
		String requestBody = """
                {
                    "userId": 2,
                    "eventId": 456
                }
                """;

		UserServiceClientStub.stubUserApprovalCheck(123L);

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/approval/approve")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("userId", Matchers.equalTo(2))
				.body("eventId", Matchers.equalTo(456))
				.body("status", Matchers.equalTo("APPROVED"));

	}

	@Test
	void getApprovalRequestTest() {
		String requestBody = """
                {
                    "userId": 123,
                    "eventId": 456,
                    "status": "PENDING"
                }
                """;

		var response = RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/approval")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.response()
				.jsonPath();
		var id = response.get("id");

		RestAssured.given()
				.when()
				.get("/api/approval/" + id)
				.then()
				.log().all()
				.statusCode(200)
				.body("id", Matchers.equalTo(id))
				.body("userId", Matchers.equalTo(123))
				.body("eventId", Matchers.equalTo(456))
				.body("status", Matchers.equalTo("PENDING"));
	}

	@Test
	void updateApprovalStatusTest() {
		String createRequestBody = """
                {
                    "userId": 123,
                    "eventId": 456,
                    "status": "PENDING"
                }
                """;

		var response = RestAssured.given()
				.contentType("application/json")
				.body(createRequestBody)
				.when()
				.post("/api/approval")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.response()
				.jsonPath();
		var id = response.get("id");

		String updateRequestBody = """
                {
                    "status": "APPROVED"
                }
                """;

		RestAssured.given()
				.contentType("application/json")
				.body(updateRequestBody)
				.when()
				.put("/api/approval/" + id)
				.then()
				.log().all()
				.statusCode(204);

		RestAssured.given()
				.when()
				.get("/api/approval/" + id)
				.then()
				.log().all()
				.statusCode(200)
				.body("status", Matchers.equalTo("APPROVED"));
	}

	@Test
	void deleteApprovalRequestTest() {
		String requestBody = """
                {
                    "userId": 123,
                    "eventId": 456,
                    "status": "PENDING"
                }
                """;

		var response = RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/approval")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.response()
				.jsonPath();
		var id = response.get("id");

		RestAssured.given()
				.when()
				.delete("/api/approval/" + id)
				.then()
				.log().all()
				.statusCode(204);

		RestAssured.given()
				.when()
				.get("/api/approval/" + id)
				.then()
				.log().all()
				.statusCode(404);
	}

}
