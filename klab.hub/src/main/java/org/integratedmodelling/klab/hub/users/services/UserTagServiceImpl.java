package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.Tag;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserTagServiceImpl implements UserTagService {

    UserRepository userRepository;

    public UserTagServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public void createNewTag(String username, String description) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new BadRequestException("User is not present"));
        user.addNewTag(new Tag(description));
        userRepository.save(user);
    }

    @Override
    public List<Tag> getTagsOfUser(String username) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new BadRequestException("User is not present"));
        return user.getTags();
    }

}
