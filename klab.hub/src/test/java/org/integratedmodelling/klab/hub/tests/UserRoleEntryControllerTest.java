package org.integratedmodelling.klab.hub.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;
import org.integratedmodelling.klab.api.API;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles(profiles = "production")
@TestInstance(Lifecycle.PER_CLASS)
public class UserRoleEntryControllerTest {

    @LocalServerPort
    int randomServerPort;

    private RestTemplate restTemplate;
    private String url;
    private String token;
    private HttpHeaders headers;

    @BeforeAll
    public void beforeAll() throws URISyntaxException {
        token = AcceptanceTestUtils.getSessionTokenForDefaultAdministrator(randomServerPort);
    }

    @BeforeEach
    void beforeEach() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
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

}
