package org.integratedmodelling.klab.hub.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.integratedmodelling.klab.hub.config.dev.MongoConfigDev;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.listeners.HubEventPublisher;
import org.integratedmodelling.klab.hub.repository.MongoTagRepository;
import org.integratedmodelling.klab.hub.repository.TagNotificationRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tags.dto.MongoTag;
import org.integratedmodelling.klab.hub.tags.dto.TagEntry;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.services.UserTagService;
import org.integratedmodelling.klab.hub.users.services.UserTagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mongodb.assertions.Assertions;

@SpringBootTest(classes = {MongoConfigDev.class, HubEventPublisher.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "development")
public class UserTaggingServiceTest {

    @Autowired
    MongoTagRepository tagRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagNotificationRepository notificationRepository;

    private UserTagService tagService;

    private final String exisingUsername = "achilles";
    private final String exisingTagName = "tag";

    @BeforeEach
    public void setup() {
        tagRepository.deleteAll();
        MongoTag tag = new MongoTag();
        tag.setName(exisingTagName);
        tagRepository.save(tag);

        userRepository.deleteAll();
        User user = new User();
        user.setName(exisingUsername);
        userRepository.insert(user);
    }

    @Test
    @Order(0)
    public void test_constructor_works() {

        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);
    }

    private MongoTag generateTag(String tagName) {
        MongoTag mongoTag = new MongoTag();
        mongoTag.setName(tagName);

        return mongoTag;
    }

    private User getExistingUser() {
        return userRepository.findByName(exisingUsername).get();
    }

    private void addTagsToUserAndSave(User user, Collection<MongoTag> tags) {
        tagRepository.saveAll(tags);
        user.addTags(tags);
        userRepository.save(user);
    }

    @Test
    public void test_assignTagToUser_UserExistsAndTagIsNew() {
        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);
        final String tagName = "test";
        MongoTag tag = generateTag(tagName);

        tagService.assignTagToUser(exisingUsername, tag);

        Optional<User> user = userRepository.findByName(exisingUsername);
        Assertions.assertTrue(user.get().hasTag(tagName));
    }

    @Test
    public void test_assignTagToUser_UserDoesNotExist() {
        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);
        final String tagName = "test";
        MongoTag tag = generateTag(tagName);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            tagService.assignTagToUser("noname", tag);
        });

        Assertions.assertTrue(thrown.getMessage().contentEquals("User is not present."));
    }

    @Test
    public void test_assignTagToUser_assignExistingTag() {
        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);
        MongoTag tag = generateTag(exisingTagName);

        tagService.assignTagToUser(exisingUsername, tag);

        Optional<User> user = userRepository.findByName(exisingUsername);
        Assertions.assertTrue(user.get().hasTag(exisingTagName));
    }

    @Test
    public void test_assignTagToUser_tagAlreadyInUser() {
        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);
        MongoTag tag = generateTag(exisingTagName);
        tagService.assignTagToUser(exisingUsername, tag);
        final int sizeBefore = userRepository.findByName(exisingUsername).get().getTags().size();

        tagService.assignTagToUser(exisingUsername, tag);
        final int sizeAfter = userRepository.findByName(exisingUsername).get().getTags().size();

        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    public void getTagsOfUser_withoutTags() {
        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);

        List<TagEntry> tagsOfUser = tagService.getTagsOfUser(exisingUsername);

        Assertions.assertTrue(tagsOfUser.isEmpty());
    }

    @Test
    public void getTagsOfUser_withMultipleTags() {
        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);
        Set<MongoTag> tags = Set.of(generateTag("newTag1"), generateTag("newTag2"), generateTag("newTag3"));
        addTagsToUserAndSave(getExistingUser(), tags);

        List<TagEntry> tagsOfUser = tagService.getTagsOfUser(exisingUsername);

        Assertions.assertFalse(tagsOfUser.isEmpty());
    }

    @Test
    public void test_getUnsentTagsOfUser() {
        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);
        final Set<MongoTag> tags = Set.of(generateTag("newTag1"), generateTag("newTag2"),
                generateTag("newTag3"));
        addTagsToUserAndSave(getExistingUser(), tags);

        List<TagEntry> tagsOfUser = tagService.getUnsentTagsOfUser(exisingUsername);

        Assertions.assertFalse(tagsOfUser.isEmpty());
    }

    @Test
    public void test_getAllTags() {
        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);
        final Set<MongoTag> tags = Set.of(generateTag("newTag1"), generateTag("newTag2"),
                generateTag("newTag3"));
        addTagsToUserAndSave(getExistingUser(), tags);

        List<MongoTag> alltags = tagService.getAllTags();

        Assertions.assertFalse(alltags.isEmpty());
    }

    @Test
    public void test_insertTag_duplicateTagThrowsException() {
        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);
        MongoTag tag = new MongoTag();
        tag.setName(exisingTagName);

        DuplicateKeyException thrown = assertThrows(DuplicateKeyException.class, () -> {
            tagService.insertTag(tag);
        });

        Assertions.assertTrue(thrown instanceof DuplicateKeyException);
    }

    @Test
    public void test_insertTag_normalBehavior() {
        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);
        MongoTag tag = new MongoTag();
        tag.setName("newTag");

        assertDoesNotThrow(() -> {
            tagService.insertTag(tag);
        });
    }

    @Test
    public void test_insertOrUpdateTag_doesNotCareAboutDuplicatedKeys() {
        tagService = new UserTagServiceImpl(tagRepository, userRepository, notificationRepository);
        MongoTag tag = new MongoTag();
        tag.setName(exisingTagName);

        assertDoesNotThrow(() -> {
            tagService.insertOrUpdateTag(tag);
        });
    }

}
