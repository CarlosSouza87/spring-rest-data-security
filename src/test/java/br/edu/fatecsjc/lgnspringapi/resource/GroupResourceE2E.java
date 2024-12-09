package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupResourceE2E extends IntegrationTest {

    @Test
    void testGetAllGroups() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .when().get("/group")
            .then().statusCode(200);
    }

    @Test
    void testGetGroupById() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .pathParam("id", "1")
            .when().get("/group/{id}")
            .then().statusCode(200);
    }

    @Test
    void testRegisterGroup() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .contentType("application/json")
            .body("""
                {
                   "name": "test-name",
                   "members": [
                     {
                       "name": "test-member-name",
                       "age": 10
                     }
                   ],
                   "organization": 1
                }""")
            .when().post("/group")
            .then().statusCode(201)
            .body("name", equalTo("test-name"))
            .body("members[0].name", equalTo("test-member-name"))
            .body("members[0].age", equalTo(10));
    }

    @Test
    void testUpdateGroup() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .contentType("application/json")
            .pathParam("id", "1")
            .body("""
                {
                   "name": "test-update-name",
                   "members": [
                     {
                       "name": "test-updated-member-name",
                       "age": 10
                     }
                   ]
                }""")
            .when().put("/group/{id}")
            .then().statusCode(201)
            .body("name", equalTo("test-update-name"))
            .body("members[0].name", equalTo("test-updated-member-name"))
            .body("members[0].age", equalTo(10));
    }

    @Test
    void testDeleteGroup() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .pathParam("id", "2")
            .when().delete("/group/{id}")
            .then().statusCode(204);
    }

}
