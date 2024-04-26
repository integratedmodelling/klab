package org.integratedmodelling.klab.hub.users.services;

import java.util.List;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.springframework.stereotype.Service;

@Service
public class UserRoleEntryServiceImpl implements UserRoleEntryService {

    private UserRepository userRepository;

    public UserRoleEntryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<String> getUsersWithRole(Role role) {
        return userRepository.getUsersByRole(role).stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }
}
