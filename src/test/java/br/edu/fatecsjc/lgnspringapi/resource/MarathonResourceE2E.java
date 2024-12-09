package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MarathonResourceE2E extends IntegrationTest {

    @Test
    void testGetAllMarathon() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .when().get("/marathon")
            .then().statusCode(200);
    }

    @Test
    void testGetMarathonById() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .pathParam("id", "1")
            .when().get("/marathon/{id}")
            .then().statusCode(200);
    }

    @Test
    void testRegisterMarathon() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .contentType("application/json")
            .body("""
                {
                  "name": "test-name-1",
                  "weight": "test-weight-1",
                  "score": "test-score-1"
                }""")
            .when().post("/marathon")
            .then().statusCode(201)
            .body("name", equalTo("test-name-1"))
            .body("weight", equalTo("test-weight-1"))
            .body("score", equalTo("test-score-1"));
    }

    @Test
    void testUpdateMarathon() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .contentType("application/json")
            .pathParam("id", "1")
            .body("""
                 {
                  "name": "new-test-name-1",
                  "weight": "new-test-weight-1",
                  "score": "new-test-score-1"
                }""")
            .when().put("/marathon/{id}")
            .then().statusCode(201)
            .body("name", equalTo("new-test-name-1"))
            .body("weight", equalTo("new-test-weight-1"))
            .body("score", equalTo("new-test-score-1"));
    }

    @Test
    void testDeleteMarathon() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .pathParam("id", "2")
            .when().delete("/marathon/{id}")
            .then().statusCode(204);
    }

}
