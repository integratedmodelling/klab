package org.integratedmodelling.klab.hub.users.services;

import java.util.List;
import org.integratedmodelling.klab.hub.api.Tag;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.rest.HubNotificationMessage.Type;
import org.springframework.stereotype.Service;

@Service
public class UserTagServiceImpl implements UserTagService {

    UserRepository userRepository;
    
    private User findUserByName(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new BadRequestException("User is not present."));
    }

    public UserTagServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public void createNewTag(String username, Tag tag) {
        User user = findUserByName(username);
        user.addNewTag(tag);
        userRepository.save(user);
    }

    @Override
    public List<Tag> getTagsOfUser(String username) {
        return findUserByName(username).getTags();
    }

    @Override
    public List<Tag> getTagsOfUserWithType(String username, Type type) {
        return findUserByName(username).getTagsOfType(type);
    }

    @Override
    public List<Tag> getUnsentTagsOfUser(String username) {
        return findUserByName(username).getUnsentTags();
    }

    @Override
    public List<Tag> getUnsentTagsOfUserWithType(String username, Type type) {
        return findUserByName(username).getUnsentTagsOfType(type);
    }


}
