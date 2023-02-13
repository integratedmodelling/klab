package org.integratedmodelling.klab.hub.users.services;

import java.util.List;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleEntryServiceImpl implements UserRoleEntryService {

    private UserRepository userRepository;

    public UserRoleEntryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsersWithRole(Role role) {
        return userRepository.getUsersByRole(role);
    }
}
