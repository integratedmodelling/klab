package org.integratedmodelling.klab.hub.users.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.TagNotificationRepository;
import org.integratedmodelling.klab.hub.repository.MongoTagRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tags.dto.MongoTag;
import org.integratedmodelling.klab.hub.tags.dto.TagEntry;
import org.integratedmodelling.klab.hub.tags.dto.TagNotification;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.springframework.stereotype.Service;

@Service
public class UserTagServiceImpl implements UserTagService {

    MongoTagRepository tagRepository;
    UserRepository userRepository;
    TagNotificationRepository notificationRepository;

    private User findUserByName(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new BadRequestException("User is not present."));
    }

    private boolean doesTagExistInTheDatabase(MongoTag tag) {
        Optional<MongoTag> tagInTheDatabase = tagRepository.findByName(tag.getName());
        if(tagInTheDatabase.isPresent()) {
            tag = tagInTheDatabase.get();
            return true;
        }
        return false;
    }

    public UserTagServiceImpl(MongoTagRepository tagRepository, UserRepository userRepository, TagNotificationRepository notificationRepository) {
        super();
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public boolean assignTagToUser(String username, MongoTag tag) {
        User user = findUserByName(username);
        if (user.hasTag(tag.getName())) {
            return false;
        }
        if (!doesTagExistInTheDatabase(tag)) {
            tagRepository.save(tag);
        }
        user.addTag(tag);
        userRepository.save(user);
        return true;
    }

    @Override
    public List<TagEntry> getTagsOfUser(String username) {
        return findUserByName(username).getTags();
    }

    @Override
    public List<TagEntry> getUnsentTagsOfUser(String username) {
        return findUserByName(username).getUnsentTags();
    }

    @Override
    public List<MongoTag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public void insertTag(MongoTag tag) {
        tagRepository.insert(tag);
    }

    @Override
    public void insertOrUpdateTag(MongoTag tag) {
        tagRepository.save(tag);
    }

    @Override
    public Optional<MongoTag> getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public void insertOrUpdateTagNotification(TagNotification tagNotification) {
        notificationRepository.save(tagNotification);
    }

    @Override
    public List<TagNotification> getAllTagNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<TagNotification> getTagNotificationsByTag(MongoTag tag) {
        return notificationRepository.findByTag(tag);
    }
    
    @Override
    public List<TagNotification> getTagNotificationsByUser(String username) {
        List<TagEntry> unsentTags = findUserByName(username).getUnsentTags();
        List<TagNotification> tagNotifications = unsentTags.stream().map(t -> notificationRepository.findByTag(t.getTag()))
                .filter(n -> n.isPresent())
                .map(n -> n.get())
                .collect(Collectors.toList());

        return tagNotifications;
    }

}
