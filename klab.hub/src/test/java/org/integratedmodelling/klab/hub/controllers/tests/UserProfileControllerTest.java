package org.integratedmodelling.klab.hub.controllers.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;
import org.integratedmodelling.klab.api.API;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
public class UserProfileControllerTest {

    @LocalServerPort
    int randomServerPort;

    private RestTemplate restTemplate;
    private String url;
    private String token;
    private HttpHeaders headers;

    final private String paginationParameters = API.HUB.PARAMETERS.PAGE + "=0&" + API.HUB.PARAMETERS.RECORDS + "=3";

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

    @Nested
    @DisplayName("No parameter tests")
    public class NoParameterTest {
        @Test
        @DisplayName("Get all users")
        public void getAllUsers_success() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users";
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
        
        @Test
        @DisplayName("Get all users paginated")
        public void getAllUsersPaginated_success() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users?" + paginationParameters;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
    }

    @Nested
    @DisplayName("User group tests")
    public class UserGroupTest {
        @ParameterizedTest
        @ValueSource(strings = {"IM", "ARIES", "IM,ARIES", "IM&" + paginationParameters})
        @DisplayName("Get users with group")
        public void usersWithGroup_success(String group) {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users?" + API.HUB.PARAMETERS.HAS_GROUP + "=" + group;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
    }

    @Nested
    @DisplayName("User role tests")
    public class UserRoleTest {
        @ParameterizedTest
        @ValueSource(strings = {"ADMINISTRATOR", "MANAGER", "MANAGER,USER", "USER&" + paginationParameters})
        @DisplayName("Get users with role")
        public void usersWithRole_successExistingRoles(String role) {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users?" + API.HUB.PARAMETERS.HAS_ROLES + "=" + role;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @ParameterizedTest
        @ValueSource(strings = {"ROLE_ADMINISTRATOR", "INVALID"})
        @DisplayName("Fail getting users with non existing roles")
        public void usersWithRole_failNonExistingRoles(String role) {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users?" + API.HUB.PARAMETERS.HAS_ROLES + "=" + role;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
                restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            });
        }
    }

    @Nested
    @DisplayName("User account status tests")
    public class UserAccountStatusTest {
        @ParameterizedTest
        @ValueSource(strings = {"active", "locked", "active,deleted", "deleted&" + paginationParameters})
        @DisplayName("Get users with account status")
        public void usersWithAccountStatus_successExistingAccountStatus(String status) {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users?" + API.HUB.PARAMETERS.USER_SET_ACCOUNT_STATUS + "=" + status;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @ParameterizedTest
        @ValueSource(strings = {"ACTIVE", "INVALID"})
        @DisplayName("Fail getting users with non existing account status")
        public void usersWithAccountStatus_failNonExistingAccountStatus(String status) {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users?" + API.HUB.PARAMETERS.USER_SET_ACCOUNT_STATUS + "=" + status;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
                restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            });
        }
    }
}
