package org.integratedmodelling.klab.hub.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONObject;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles(profiles = "production")
public class UserRoleEntryControllerTest {

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
        JSONObject body = response.getBody();
        token = ((Map<?,?>) body.get("Authentication")).get("tokenString").toString();
        headers.add("Authentication", token);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ROLE_ADMINISTRATOR", "ROLE_DATA_MANAGER", "ROLE_MANAGER", "ROLE_USER"})
    @DisplayName("Get users with role")
    public void usersWithRole_successExistingRoles(String role) {
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.USER_BASE + "?" + API.HUB.PARAMETERS.HAS_ROLES + "=" + role;
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ADMINISTRATOR", "ROLE_INVALID", ""})
    @DisplayName("Fail getting users with non existing roles")
    public void usersWithRole_failNonExistingRoles(String role) {
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.USER_BASE + "?" + API.HUB.PARAMETERS.HAS_ROLES + "=" + role;
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        });
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
