package org.integratedmodelling.klab.hub.tests;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles(profiles = "production")
public class UserTaggingControllerTest {

    @LocalServerPort
    int randomServerPort;

    private RestTemplate restTemplate;
    private String url;
    private String token;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() throws URISyntaxException {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        ResponseEntity<JSONObject> response = loginResponse("system", "password");
        JSONObject body = response.getBody();//.toJSONString();//("Authentication");
        token = ((Map) body.get("Authentication")).get("tokenString").toString();
        headers.add("Authentication", token);
    }

    @Test
    @DisplayName("Get all tags successfully")
    public void getAllTags_isOK() {
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_BASE;
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
        
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Create a new tag successfully")
    public void createNewTag_createsANewTagSuccessfully() {
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_BASE;
        String tagName = String.format("tag%d", System.currentTimeMillis()/1000);
        String tag = "{\n\"name\": \"" + tagName + "\",\n\"message\": \"Some stuff\",\n\"type\": \"ERROR\"\n}";
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);
        assertEquals(tag, httpEntity.getBody());

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

    private ResponseEntity<JSONObject> loginResponse(String username, String password) throws URISyntaxException {
        final String baseUrl = "http://localhost:"+ randomServerPort + "/hub" + API.HUB.AUTHENTICATE_USER;
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        UserAuthenticationRequest auth= new UserAuthenticationRequest();
        auth.setPassword(password);
        auth.setUsername(username);
        HttpEntity<?> request = new HttpEntity<>(auth, headers);
        ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, request, JSONObject.class);
        return result;
    }

}
