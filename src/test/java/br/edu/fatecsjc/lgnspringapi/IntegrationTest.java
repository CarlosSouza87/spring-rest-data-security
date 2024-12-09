package br.edu.fatecsjc.lgnspringapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IntegrationTest {

    private static final MariaDBContainer<?> mariaDbContainer;
    private static String accessToken = null;

    static {
        mariaDbContainer = new MariaDBContainer("mariadb").withPassword("root")
            .withDatabaseName("sample");
        mariaDbContainer.start();
    }

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    static void properties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariaDbContainer::getJdbcUrl);
    }

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    public String getAccessToken() {
        if(accessToken != null) {
            return accessToken;
        }
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            final HttpPost httpPost = new HttpPost("http://localhost:" + port + "/auth/authenticate");
            final String json = "{\"email\":\"admin@mail.com\",\"password\":\"admin123\"}";
            final StringEntity request = new StringEntity(json);
            httpPost.setEntity(request);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {

                HttpEntity entity = response.getEntity();
                TypeReference<HashMap<String,Object>> typeRef = new TypeReference<>() {};

                Map<String,String> result = mapper.readValue(EntityUtils.toString(entity), HashMap.class);
                // Ensure that the stream is fully consumed
                EntityUtils.consume(entity);
                accessToken = "Bearer " + result.get("access_token");
                return accessToken;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
