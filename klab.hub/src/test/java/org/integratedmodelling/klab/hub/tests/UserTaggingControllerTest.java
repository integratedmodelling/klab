package org.integratedmodelling.klab.hub.tests;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.repository.MongoTagRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;
import java.util.Optional;

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

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("Read tags tests")
    public class GetAllTagsTest {
        @Autowired
        MongoTagRepository tagRepository;
        
        @BeforeAll
        public void beforeAll() {
            MongoTag tag = new MongoTag();
            tag.setName("testTag");
            tagRepository.save(tag);
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
        public void getTagByName_failNonExistingTag() {
            String existingTagName = "nonExistingTag";
            url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_BASE + "/" + existingTagName;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
    
            Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
                restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            });
        }

    }

    @Nested
    @DisplayName("Tag notifications tests")
    public class GetAllTagNotificationsTest {
        @Test
        @DisplayName("Get all notification of tags")
        public void getAllTagNotifications_isOK() {
            url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_NOTIFICATIONS;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
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

    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("Create, update, delete tags tests")
    public class InsertUpdateDeleteTags {
        @Autowired
        MongoTagRepository tagRepository;
        @Autowired
        UserRepository userRepository;

        @BeforeAll
        public void beforeAll() {
            MongoTag tag1 = new MongoTag();
            tag1.setName("testTag1");
            tagRepository.save(tag1);

            MongoTag tag2 = new MongoTag();
            tag2.setName("testTag2");
            tagRepository.save(tag2);

            User user = userRepository.findByName("achilles").get();
            user.addTag(tag1);
            userRepository.save(user);
        }

        @Test
        @DisplayName("Create a new tag")
        public void createNewTag_createsANewTagSuccessfully() {
            url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.TAG_BASE;
            String tagName = String.format("new-tag%d", System.currentTimeMillis());
            String tag = "{\n\"name\": \"" + tagName + "\",\n\"message\": \"Some stuff\",\n\"type\": \"ERROR\"\n}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Add a new tag to a user")
        public void addTagToUser_succesfulAddANewTag() {
            String existingUsername = "achilles";
            url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.USER_BASE + "/" + existingUsername + "/tags";
            String tagName = String.format("new-tag%d", System.currentTimeMillis());
            String tag = "{\n\"name\": \"" + tagName + "\",\n\"message\": \"Some stuff\",\n\"type\": \"ERROR\"\n}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
            assertEquals("Tag sucessfully assigned to user.", responseEntity.getBody());
        }

        @Test
        @DisplayName("Add an existing tag to a user")
        public void addTagToUser_succesfulAddAnExistingTag() {
            String existingUsername = "achilles";
            url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.USER_BASE + "/" + existingUsername + "/tags";
            String tag = "{\n\"name\": \"testTag2\",\n\"message\": \"Some stuff\",\n\"type\": \"ERROR\"\n}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
            assertEquals("Tag sucessfully assigned to user.", responseEntity.getBody());
        }

        @Test
        @DisplayName("Do not add an already assigned tag to a user")
        public void addTagToUser_alreadyHasTag() {
            String existingUsername = "achilles";
            url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.USER_BASE + "/" + existingUsername + "/tags";
            String tag = "{\n\"name\": \"testTag1\",\n\"message\": \"Some stuff\",\n\"type\": \"ERROR\"\n}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
            assertEquals("Tag already exists for the user.", responseEntity.getBody());
        }

        @Test
        @DisplayName("Do not add an already assigned tag to a user")
        public void addTagToUser_failureUserDoesNotExist() {
            String existingUsername = "non-existing";
            url = "http://localhost:" + randomServerPort + "/hub" + API.HUB.USER_BASE + "/" + existingUsername + "/tags";
            String tag = "{\n\"name\": \"testTag\",\n\"message\": \"Some stuff\",\n\"type\": \"ERROR\"\n}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

            Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
                restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
            });
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

        @AfterAll
        public void afterAll() {
            MongoTag tag = tagRepository.findByName("testTag1").get();
            tagRepository.delete(tag);
            tag = tagRepository.findByName("testTag2").get();
            tagRepository.delete(tag);
        }

    }

    @Nested
    @DisplayName("Get tags of user tests")
    public class GetTagsOfUserTest {
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

    }

}
