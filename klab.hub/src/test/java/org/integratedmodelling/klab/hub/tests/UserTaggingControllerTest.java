package org.integratedmodelling.klab.hub.tests;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.hub.api.TagNotification;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.repository.MongoTagRepository;
import org.integratedmodelling.klab.hub.repository.TagNotificationRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
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
        public void beforeAll() {
            createMongoTag(tagWithoutNotification);
            createMongoTag(tagToHaveNotification);
            createMongoTagWithNotification(tagWithNotification);
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
            String tagNotification = "{\n"
                    + "    \"type\": \"ERROR\",\n"
                    + "    \"message\": \"lorem ipsum\"\n"
                    + "}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tagNotification, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Fild to bind a notification to a non existing tag")
        public void bindNotificationToATag_failTagDoesNotExist() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tag-notifications/" + nonExistingTag;
            String tagNotification = "{\n"
                    + "    \"type\": \"ERROR\",\n"
                    + "    \"message\": \"lorem ipsum\"\n"
                    + "}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tagNotification, headers);

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

        @BeforeAll
        public void beforeAll() {
            createMongoTag(nonAssignedTag);
            MongoTag tag = createMongoTag(assignedTag);
            User user = userRepository.findByName("achilles").get();
            user.addTag(tag);
            userRepository.save(user);
        }

        @Test
        @DisplayName("Create a new tag")
        public void createNewTag_createsANewTagSuccessfully() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tags";
            String tag = "{\n"
                    + "\"name\": \"" + newTag + "\",\n"
                    + "\"message\": \"Some stuff\",\n"
                    + "\"type\": \"ERROR\"\n"
                    + "}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Fail creating a duplicated tag")
        public void createNewTag_failureCreatingADuplicatedTag() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tags";
            String tag = "{\n"
                    + "\"name\": \""+ nonAssignedTag + "\",\n"
                    + "\"message\": \"Some stuff\",\n"
                    + "\"type\": \"ERROR\"\n"
                    + "}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

            Assertions.assertThrows(HttpClientErrorException.Conflict.class, () -> {
                restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            });
        }

        @Test
        @DisplayName("Add a new tag to a user")
        public void addTagToUser_succesfulAddANewTag() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + existingUsername + "/tags";
            String tag = "{\n"
                    + "\"name\": \"" + newTag + "\",\n"
                    + "\"message\": \"Some stuff\",\n"
                    + "\"type\": \"ERROR\"\n"
                    + "}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
            assertEquals("Tag sucessfully assigned to user.", responseEntity.getBody());
        }

        @Test
        @DisplayName("Add an existing tag to a user")
        public void addTagToUser_succesfulAddAnExistingTag() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + existingUsername + "/tags";
            String tag = "{\n"
                    + "\"name\": \"" + nonAssignedTag + "\",\n"
                    + "\"message\": \"Some stuff\",\n"
                    + "\"type\": \"ERROR\"\n"
                    + "}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Do not add an already assigned tag to a user")
        public void addTagToUser_alreadyHasTag() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + existingUsername + "/tags";
            String tag = "{\n"
                    + "\"name\": \"" + assignedTag + "\",\n"
                    + "\"message\": \"Some stuff\",\n"
                    + "\"type\": \"ERROR\"\n"
                    + "}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

            assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        }

        @Test
        @DisplayName("Fail adding a tag to a non existing user")
        public void addTagToUser_failureUserDoesNotExist() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/users/" + nonExistingUsername+ "/tags";
            String tag = "{\n"
                    + "\"name\": \"" + newTag + "\",\n"
                    + "\"message\": \"Some stuff\",\n"
                    + "\"type\": \"ERROR\"\n"
                    + "}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

            Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
                restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
            });
        }

        @Test
        @DisplayName("Create or update a tag")
        public void createOrUpdateTag_successful() {
            url = "http://localhost:" + randomServerPort + "/hub/api/v2/tags";
            String tag = "{\n"
                    + "\"name\": \"" + newTag + "\",\n"
                    + "\"message\": \"Some stuff\",\n"
                    + "\"type\": \"ERROR\"\n"
                    + "}";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(tag, headers);

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
