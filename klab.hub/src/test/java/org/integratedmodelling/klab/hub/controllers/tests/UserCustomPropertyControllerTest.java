package org.integratedmodelling.klab.hub.controllers.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.api.CustomProperties;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.repository.CustomPropertiesRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.requests.UserCustomPropertyRequest;
import org.integratedmodelling.klab.rest.CustomProperty;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
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
@TestInstance(Lifecycle.PER_CLASS)
public class UserCustomPropertyControllerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomPropertiesRepository customPropertiesRepository;

    @LocalServerPort
    int randomServerPort;

    private RestTemplate restTemplate;
    private String url;
    private String token;
    private HttpHeaders headers;

    final List<String> usernames = List.of("bat", "bi");
    final String nonExistingUser = "inor";
    final List<String> propertyKeys = List.of("first", "second");
    final List<String> propertyValues= List.of("alpha", "betta");

    private void createUser(String username) {
        User user = new User();
        user.setUsername(username);
        userRepository.save(user);
    }

    private void deleteUser(String username) {
        Optional<User> user = userRepository.findByName(username);
        userRepository.delete(user.get());
    }

//    private void deleteProperty(String key) {
//        List<CustomProperties> list = customPropertiesRepository.findAllByNameIn(List.of(key));
//        customPropertiesRepository.deleteAll(list);
//    }

    @BeforeAll
    public void beforeAll() throws URISyntaxException {
        token = AcceptanceTestUtils.getSessionTokenForDefaultAdministrator(randomServerPort);
        createUser(usernames.get(0));
        createUser(usernames.get(1));
    }

    @BeforeEach
    public void beforeEach() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Authentication", token);
    }

    @Test
    public void getAllCustomPropertiesOfUserWithoutProperties() {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + usernames.get(0) + "/custom-properties";
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JSONObject.class);

        assertTrue(responseEntity.getBody().getAsString("Custom Properties").equals("[]"));
    }

    @Test
    public void getAllCustomPropertiesOfNonExistingUserFails() {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + List.of(nonExistingUser) + "/custom-properties";
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            restTemplate.exchange(url, HttpMethod.GET, httpEntity, JSONObject.class);
        });
    }

    @Test
    public void getAllCustomPropertiesOfUserWithProperties() {
        Optional<User> user = userRepository.findByName(usernames.get(1));
        CustomProperty property = new CustomProperty(propertyKeys.get(0), propertyValues.get(0), false);
        user.get().putCustomProperty(property);
        userRepository.save(user.get());
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + usernames.get(1) + "/custom-properties";
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JSONObject.class);

        assertTrue(responseEntity.getBody().getAsString("Custom Properties").contains(propertyKeys.get(0)));
    }

    @Test
    public void addNewCustomPropertyToUsers() {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/custom-properties";
        UserCustomPropertyRequest request = new UserCustomPropertyRequest(usernames, propertyKeys.get(1), propertyValues.get(1), true);
        HttpEntity<UserCustomPropertyRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);

        assertTrue(responseEntity.getStatusCode() == HttpStatus.ACCEPTED);
    }

    @Test
    public void failt_addNewCustomPropertyToNonExistingUsers() {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/custom-properties";
        UserCustomPropertyRequest request = new UserCustomPropertyRequest(List.of(nonExistingUser), propertyKeys.get(1), propertyValues.get(1), true);
        HttpEntity<UserCustomPropertyRequest> httpEntity = new HttpEntity<>(request, headers);

        Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
        });
    }

    @Test
    public void deleteCustomProperty() {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/custom-properties";
        UserCustomPropertyRequest request = new UserCustomPropertyRequest(usernames, propertyKeys.get(1), "", false);
        HttpEntity<UserCustomPropertyRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, JSONObject.class);

        assertTrue(responseEntity.getStatusCode() == HttpStatus.ACCEPTED);
    }

    @Test
    public void fail_deleteCustomPropertyOfNonExisitingUser() {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/custom-properties";
        UserCustomPropertyRequest request = new UserCustomPropertyRequest(List.of(nonExistingUser), propertyKeys.get(1), "", false);
        HttpEntity<UserCustomPropertyRequest> httpEntity = new HttpEntity<>(request, headers);

        Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, JSONObject.class);
        });
    }


    @AfterAll
    public void afterAll() throws URISyntaxException {
        deleteUser(usernames.get(0));
        deleteUser(usernames.get(1));
    }

}
