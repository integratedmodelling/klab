package org.integratedmodelling.klab.hub.users.commands;

import java.time.LocalDateTime;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.dto.User.AccountStatus;

public class CreateUser implements UserCommand {

    private UserRepository userRepository;
    private User user;
    private AccountStatus accountStatus;

    public CreateUser(UserRepository userRepository, User user, AccountStatus accountStatus) {
        super();
        this.userRepository = userRepository;
        this.user = user;
        this.accountStatus = accountStatus;
    }

    @Override
    public User execute() {
        user.setAccountStatus(accountStatus);
//		user.setRoles(Arrays.asList(Role.ROLE_USER));
        user.setRegistrationDate(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

}
