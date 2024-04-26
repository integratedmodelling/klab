package org.integratedmodelling.klab.hub.users.services;

import java.util.List;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.dto.User.AccountStatus;
import org.springframework.stereotype.Service;

@Service
public class UserAccountStatusServiceImpl implements UserAccountStatusService {

    private UserRepository userRepository;

    public UserAccountStatusServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public List<String> getUsersByStatus(AccountStatus accountStatus) {
        return userRepository.getUsersByAccountStatus(accountStatus).stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }
}
