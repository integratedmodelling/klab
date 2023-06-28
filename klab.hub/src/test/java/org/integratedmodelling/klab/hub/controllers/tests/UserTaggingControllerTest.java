package org.integratedmodelling.klab.hub.controllers.tests;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.hub.api.TagNotification;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.MongoTagRepository;
import org.integratedmodelling.klab.hub.repository.TagNotificationRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
@TestInstance(Lifecycle.PER_CLASS)
public class UserTaggingControllerTest {
    @Autowired
    MongoTagRepository tagRepository;

    @LocalServerPort
    int randomServerPort;

    private RestTemplate restTemplate;
    private String url;
    private String token;
    private HttpHeaders headers;

    final String existingUsername = "achilles";
    final String nonExistingUsername = "ezezagun";

    public MongoTag createMongoTag(String tagName) {
        MongoTag tag = new MongoTag();
        tag.setName(tagName);
        return tagRepository.save(tag);
    }

    public void deleteMongoTag(String tagName) {
        Optional<MongoTag> tag = tagRepository.findByName(tagName);
        if (tag.isPresent()) {
            tagRepository.delete(tag.get());
        }
    }

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
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("Read tags tests")
    public class GetAllTagsTest {
        @Autowired
        MongoTagRepository tagRepository;

        final String existingTagName = "existing-tag";
        final String nonExistingTagName = "non-existing-tag";

        @BeforeAll
        public void beforeAll() {
            MongoTag tag = new MongoTag();
            tag.setName("existing-tag");
            tagRepository.save(tag);
        }

        @Test
        @DisplayName("Get all tags")
        public void getAllTags_isOK() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tags";
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Get an existing tag by name")
        public void getTagByName_successfulExistingTag() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tags/" + existingTagName;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Fail geting a non exisiting tag by name")
        public void getTagByName_failNonExistingTag() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tags/" + nonExistingTagName;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
                restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            });
        }
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("Tag notifications tests")
    public class GetAllTagNotificationsTest {
        @Autowired
        TagNotificationRepository notificationRepository;
        
        final String tagWithoutNotification = "tag-without-notification";
        final String tagToHaveNotification = "tag-to-have-notification";
        final String tagWithNotification = "tag-with-notification";
        final String nonExistingTag = "non-existing-tag";
        JSONObject tagNotificationBody;

        private void createMongoTagWithNotification(String tagName) {
            MongoTag tag = createMongoTag(tagName);
            TagNotification notification = new TagNotification();
            notification.setTag(tag);
            notificationRepository.save(notification);
        }

        private void deleteMongoTagWithNotification(String tagName) {
            Optional<MongoTag> tag = tagRepository.findByName(tagName);
            if (tag.isEmpty()) {
                return;
            }
            notificationRepository.deleteByTag(tag.get());
            tagRepository.delete(tag.get());
        }

        @BeforeAll
        public void beforeAll() throws JSONException, URISyntaxException {
            createMongoTag(tagWithoutNotification);
            createMongoTag(tagToHaveNotification);
            createMongoTagWithNotification(tagWithNotification);

            tagNotificationBody = new JSONObject();
            tagNotificationBody.put("type", "ERROR");
            tagNotificationBody.put("message", "lorem ipsum");
        }

        @Test
        @DisplayName("Get all notification of tags")
        public void getAllTagNotifications_isOK() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tag-notifications";
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Get tag notifications by username")
        public void getTagNotificationsByUsername_succesful() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + existingUsername + "/tag-notifications";
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Get tag notification by tag name")
        public void getTagNotificationByTagName_succesful() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tag-notifications/" + tagWithNotification;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Fail to get a tag notification by tag name with a non existing tag")
        public void getTagNotificationByTagName_failNoTag() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tag-notifications/" + nonExistingTag;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
                restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            });
        }

        @Test
        @DisplayName("Fail to get a tag notification for a tag without tag notification")
        public void getTagNotificationByTagName_failNoTagNotification() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tag-notifications/" + tagWithoutNotification;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Bind a notification to a tag")
        public void bindNotificationToATag_succesful() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tag-notifications/" + tagToHaveNotification;
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tagNotificationBody.toString(), headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Fild to bind a notification to a non existing tag")
        public void bindNotificationToATag_failTagDoesNotExist() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tag-notifications/" + nonExistingTag;
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tagNotificationBody.toString(), headers);

            Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
                restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            });
        }

        @AfterAll
        public void afterAll() {
            deleteMongoTag(tagWithoutNotification);
            deleteMongoTagWithNotification(tagToHaveNotification);
            deleteMongoTagWithNotification(tagWithNotification);
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

        final String nonAssignedTag = "non-assigned-tag";
        final String assignedTag = "assigned-tag";
        final String newTag = "new-tag";
        JSONObject tagBody;

        @BeforeAll
        public void beforeAll() throws URISyntaxException, JSONException {
            createMongoTag(nonAssignedTag);
            MongoTag tag = createMongoTag(assignedTag);
            User user = userRepository.findByName("achilles").get();
            user.addTag(tag);
            userRepository.save(user);

            tagBody = new JSONObject();
            tagBody.put("type", "ERROR");
            tagBody.put("message", "lorem ipsum");
        }
        
        @BeforeEach
        public void beforeEach() {
            restTemplate = new RestTemplate();
            headers = new HttpHeaders();
            headers.add("Authentication", token);
        }

        @Test
        @DisplayName("Create a new tag")
        public void createNewTag_createsANewTagSuccessfully() throws JSONException {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tags";
            tagBody.put("name", newTag);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tagBody.toString(), headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Fail creating a duplicated tag")
        public void createNewTag_failureCreatingADuplicatedTag() throws JSONException {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tags";
            tagBody.put("name", nonAssignedTag);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tagBody.toString(), headers);

            Assertions.assertThrows(HttpClientErrorException.Conflict.class, () -> {
                restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            });
        }

        @Test
        @DisplayName("Add a new tag to a user")
        public void addTagToUser_succesfulAddANewTag() throws JSONException {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + existingUsername + "/tags";
            tagBody.put("name", newTag);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tagBody.toString(), headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
            assertEquals("Tag sucessfully assigned to user.", responseEntity.getBody());
        }

        @Test
        @DisplayName("Add an existing tag to a user")
        public void addTagToUser_succesfulAddAnExistingTag() throws JSONException {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + existingUsername + "/tags";
            tagBody.put("name", nonAssignedTag);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tagBody.toString(), headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Do not add an already assigned tag to a user")
        public void addTagToUser_alreadyHasTag() throws JSONException {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + existingUsername + "/tags";
            tagBody.put("name", assignedTag);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tagBody.toString(), headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Fail adding a tag to a non existing user")
        public void addTagToUser_failureUserDoesNotExist() throws JSONException {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + nonExistingUsername+ "/tags";
            tagBody.put("name", newTag);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tagBody.toString(), headers);

            Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
                restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
            });
        }

        @Test
        @DisplayName("Create or update a tag")
        public void createOrUpdateTag_successful() throws JSONException {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tags";
            tagBody.put("name", newTag);
            headers.setContentType(MediaType.APPLICATION_JSON);
            String a = tagBody.toString();
            HttpEntity<String> httpEntity = new HttpEntity<>(tagBody.toString(), headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        }

        @AfterAll
        public void afterAll() {
            deleteMongoTag(assignedTag);
            deleteMongoTag(nonAssignedTag);
        }

        @AfterEach
        public void afterEach() {
            deleteMongoTag(newTag);
            tagBody.remove("name");
        }
    }

    @Nested
    @DisplayName("Get tags of user tests")
    public class GetTagsOfUserTest {
        @Test
        @DisplayName("Get all tags of a user")
        public void getTagsOfUser_successfulUserExists() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + existingUsername + "/tags";
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Get unsent tags of a user")
        public void getUnsentTagsOfUser_successfulUserExists() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tags-unsent/" + existingUsername;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Fail getting tags of non existing user")
        public void getTagsOfUser_failUserDoesNotExist() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + nonExistingUsername + "/tags";
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
                restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            });
        }

        @Test
        @DisplayName("Fail getting unsent tags of non existing user")
        public void getUnsentTagsOfUser_failUserDoesNotExist() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tags-unsent/" + nonExistingUsername;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

            Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
                restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            });
        }
    }
}
