package org.integratedmodelling.klab.hub.users.services;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.hub.api.TagEntry;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.MongoTagRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserTagServiceImpl implements UserTagService {

    MongoTagRepository tagRepository;
    UserRepository userRepository;

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

    public UserTagServiceImpl(MongoTagRepository tagRepository, UserRepository userRepository) {
        super();
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
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

}
