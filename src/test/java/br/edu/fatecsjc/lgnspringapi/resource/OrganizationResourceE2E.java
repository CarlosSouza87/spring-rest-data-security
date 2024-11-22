package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizationResourceE2E extends IntegrationTest {

    @Test
    void testGetAllOrganization() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .when().get("/organization")
            .then().statusCode(200);
    }

    @Test
    void testGetOrganizationById() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .pathParam("id", "1")
            .when().get("/organization/{id}")
            .then().statusCode(200);
    }

    @Test
    void testRegisterOrganization() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .contentType("application/json")
            .body("""
                {
                   "name": "org-test",
                   "institutionName": "org-inst-test",
                   "street": "street-test",
                   "city": "city-test",
                   "state": "st",
                   "country": "country-test",
                   "zipcode": "123456"
                }""")
            .when().post("/organization")
            .then().statusCode(201)
            .body("name", equalTo("org-test"))
            .body("institutionName", equalTo("org-inst-test"))
            .body("street", equalTo("street-test"))
            .body("city", equalTo("city-test"))
            .body("state", equalTo("st"))
            .body("country", equalTo("country-test"))
            .body("zipcode", equalTo("123456"));
    }

    @Test
    void testUpdateOrganization() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .contentType("application/json")
            .pathParam("id", "1")
            .body("""
                 {
                   "name": "new-org-test",
                   "institutionName": "new-org-inst-test",
                   "street": "new-street-test",
                   "city": "new-city-test",
                   "state": "new-st",
                   "country": "new-country-test",
                   "zipcode": "1234567"
                }""")
            .when().put("/organization/{id}")
            .then().statusCode(201)
            .body("name", equalTo("new-org-test"))
            .body("institutionName", equalTo("new-org-inst-test"))
            .body("street", equalTo("new-street-test"))
            .body("city", equalTo("new-city-test"))
            .body("state", equalTo("new-st"))
            .body("country", equalTo("new-country-test"))
            .body("zipcode", equalTo("1234567"));
    }

    @Test
    void testDeleteOrganization() {
        RestAssured.given().header(new Header("Authorization", getAccessToken()))
            .pathParam("id", "2")
            .when().delete("/organization/{id}")
            .then().statusCode(204);
    }

}
