package org.integratedmodelling.klab.hub.tests;

import org.integratedmodelling.klab.api.API;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;

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
        token = AcceptanceTestUtils.getSessionTokenForDefaultAdministrator(randomServerPort);
        headers.add("Authentication", token);
    }

    @Test
    @DisplayName("Get all tags")
    public void getAllTags_isOK() {
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_BASE;
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Create a new tag")
    public void createNewTag_createsANewTagSuccessfully() {
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_BASE;
        String tagName = String.format("new-tag%d", System.currentTimeMillis()/1000);
        String tag = "{\n\"name\": \"" + tagName + "\",\n\"message\": \"Some stuff\",\n\"type\": \"ERROR\"\n}";
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Get all notification of tags")
    public void getAllTagNotifications_isOK() {
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_NOTIFICATIONS;
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Get all tags of a user")
    public void getTagsOfUser_successfulUserExists() {
        String existingUsername = "achilles";
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.USER_BASE + "/" + existingUsername + "/tags";
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Get unsent tags of a user")
    public void getUnsentTagsOfUser_successfulUserExists() {
        String existingUsername = "achilles";
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_UNSENT + "/" + existingUsername;
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Get an existing tag by name")
    public void getTagByName_successfulExistingTag() {
        String existingTagName = "testTag";
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_BASE + "/" + existingTagName;
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Fail geting a non exisiting tag by name")
    @Disabled("TODO responseEntity does not get values from @ExceptionHandler")
    public void getTagByName_failNonExistingTag() {
        String existingTagName = "nonExistingTag";
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_BASE + "/" + existingTagName;
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Get tag notifications by username")
    public void getTagNotificationsByUsername_succesful() {
        String existingUsername = "achilles";
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.USER_BASE + "/" + existingUsername + "/tag-notifications";
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Create or update a tag")
    public void createOrUpdateTag_successful() {
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_BASE;
        String tagName = String.format("tag%d", System.currentTimeMillis()/1000);
        String tag = "{\n\"name\": \"" + tagName + "\",\n\"message\": \"Some stuff\",\n\"type\": \"ERROR\"\n}";
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Add a tag to a user")
    public void addTagToUser_succesful() {
        String existingUsername = "achilles";
        url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.USER_BASE + "/" + existingUsername + "/tags";
        String tag = "{\n\"name\": \"tagName\",\n\"message\": \"Some stuff\",\n\"type\": \"ERROR\"\n}";
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

}
