package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tags.dto.ITagElement;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByNameIgnoreCase(username).get();

    }

    public ITagElement getUserById(String id) {
        return userRepository.findById(id).get();
    }

}
