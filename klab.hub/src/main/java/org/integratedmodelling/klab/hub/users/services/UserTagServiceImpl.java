package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.hub.api.TagEntry;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.MongoTagRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.rest.HubNotificationMessage.Type;
import org.springframework.stereotype.Service;

@Service
public class UserTagServiceImpl implements UserTagService {

    MongoTagRepository tagRepository;
    UserRepository userRepository;

    private User findUserByName(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new BadRequestException("User is not present."));
    }

    public UserTagServiceImpl(MongoTagRepository tagRepository, UserRepository userRepository) {
        super();
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createNewTag(String username, MongoTag tag) {
        User user = findUserByName(username);
        user.addNewTag(tag);
        tagRepository.save(tag);
        userRepository.save(user);
    }

    @Override
    public List<TagEntry> getTagsOfUser(String username) {
        return findUserByName(username).getTags();
    }

    @Override
    public List<TagEntry> getTagsOfUserWithType(String username, Type type) {
        return findUserByName(username).getTagsOfType(type);
    }

    @Override
    public List<TagEntry> getUnsentTagsOfUser(String username) {
        return findUserByName(username).getUnsentTags();
    }

    @Override
    public List<TagEntry> getUnsentTagsOfUserWithType(String username, Type type) {
        return findUserByName(username).getUnsentTagsOfType(type);
    }

}
