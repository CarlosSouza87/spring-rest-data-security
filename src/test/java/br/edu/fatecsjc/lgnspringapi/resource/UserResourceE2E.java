package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceE2E extends IntegrationTest {

    @Test
    void testChangeUserPassword() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .contentType("application/json")
            .body("""
                {
                  "currentPassword": "admin123",
                  "newPassword": "admin456",
                  "confirmationPassword": "admin456"
                }""")
            .when().patch("/user")
            .then().statusCode(200);
    }
}
