package org.integratedmodelling.klab.hub.users.commands;

import java.time.LocalDateTime;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.dto.User.AccountStatus;

public class CreatePendingUser implements UserCommand {

    private UserRepository userRepository;
    private User user;

    public CreatePendingUser(UserRepository userRepository, User user) {
        super();
        this.userRepository = userRepository;
        this.user = user;
    }

    @Override
    public User execute() {
        user.setAccountStatus(AccountStatus.pendingActivation);
//		user.setRoles(Arrays.asList(Role.ROLE_USER));
        user.setRegistrationDate(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

}
