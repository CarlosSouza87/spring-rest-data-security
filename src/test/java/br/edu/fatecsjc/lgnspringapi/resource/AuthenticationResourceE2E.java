package br.edu.fatecsjc.lgnspringapi.resource;


import br.edu.fatecsjc.lgnspringapi.IntegrationTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationResourceE2E extends IntegrationTest {

    @Test
    void testShouldRegisterUser() {
        RestAssured.given().contentType("application/json")
            .body("""
                {
                  "firstname": "first_name",
                  "lastname": "last_name",
                  "email": "first.last@email.com",
                  "password": "random_password",
                  "role": "USER"
                }""")
            .when().post("auth/register")
            .then().log().ifValidationFails()
            .statusCode(201)
            .body("access_token", Matchers.notNullValue())
            .body("refresh_token", Matchers.notNullValue());
    }

    @Test
    void testShouldAuthenticationUser() {
        RestAssured.given().contentType("application/json")
            .body("""
                {
                  "email": "user@mail.com",
                  "password": "user123"
                }""")
            .when().post("auth/authenticate")
            .then().log().ifValidationFails()
            .statusCode(200)
            .body("access_token", Matchers.notNullValue())
            .body("refresh_token", Matchers.notNullValue());
    }
}
