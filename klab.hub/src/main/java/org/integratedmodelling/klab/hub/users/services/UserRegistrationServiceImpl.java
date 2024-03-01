package org.integratedmodelling.klab.hub.users.services;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.agreements.services.AgreementService;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.AgreementEntry;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.commands.CreateLdapUser;
import org.integratedmodelling.klab.hub.commands.CreatePendingUser;
import org.integratedmodelling.klab.hub.commands.CreateUserWithRolesAndStatus;
import org.integratedmodelling.klab.hub.commands.SetUserPasswordHash;
import org.integratedmodelling.klab.hub.commands.UpdateLdapUser;
import org.integratedmodelling.klab.hub.commands.UpdateUser;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.exception.UserEmailExistsException;
import org.integratedmodelling.klab.hub.exception.UserExistsException;
import org.integratedmodelling.klab.hub.exception.UserNameOrEmailExistsException;
import org.integratedmodelling.klab.hub.listeners.HubEventPublisher;
import org.integratedmodelling.klab.hub.listeners.NewUserAdded;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.service.implementation.LdapServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;    
    private LdapServiceImpl ldapService;
    private LdapUserDetailsManager ldapUserDetailsManager;
    private HubEventPublisher<NewUserAdded> publisher;
    private AgreementService agreementService;

    @Autowired
    public UserRegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, LdapServiceImpl ldapServiceImpl,
            LdapUserDetailsManager ldapUserDetailsManager, HubEventPublisher<NewUserAdded> publisher,
            AgreementService agreementService) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.ldapUserDetailsManager = ldapUserDetailsManager;
        this.publisher = publisher;
        this.agreementService = agreementService;
        this.ldapService = ldapServiceImpl;
    }

    @Override
    public User registerNewUser(String username, String email) {
        Optional<User> pendingUser = checkIfUserPending(username, email);
        if (pendingUser.isPresent()) {
            return pendingUser.get();
        } else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser = new CreatePendingUser(userRepository, newUser).execute();
            publisher.publish(new NewUserAdded(new Object(), newUser));
            return newUser;
        }

    }

    public User addAgreement(User user, Agreement agreement) {
        AgreementEntry agreementEntry = new AgreementEntry(agreement);
        user.getAgreements().add(agreementEntry);
        user = new UpdateUser(user, userRepository).execute();
        return user;
    }

    @Override
    public User createAndAddAgreement(User user, AgreementType agreementType, AgreementLevel agreementLevel) {        
        List<AgreementEntry> agreements = user.getAgreements().stream().filter((agreement) ->         
            agreement.getAgreement().getAgreementType().equals(agreementType) && 
                    agreement.getAgreement().getAgreementLevel().equals(agreementLevel)).toList();        
        
        if (agreements.isEmpty()) {
            Agreement agreement = agreementService.createAgreement(agreementType, agreementLevel).stream().findFirst().get();
            user = addAgreement(user, agreement);
        }   
        
        return user;
    }

    @Override
    public User registerUser(User user) {
        Optional<User> pendingUser = checkIfUserPending(user.getName(), user.getEmail());
        if (pendingUser.isPresent()) {
            return pendingUser.get();
        } else {
            User newUser = new CreateUserWithRolesAndStatus(user, userRepository, ldapUserDetailsManager).execute();
            return newUser;
        }

    }

    private Optional<User> checkIfUserPending(String username, String email) {
        boolean usernameExists = userRepository.findByNameIgnoreCase(username).isPresent();
        boolean emailExists = userRepository.findByEmailIgnoreCase(email).isPresent();

        if (usernameExists || emailExists) {
            
            boolean ldapExists = false;
            try {
                ldapExists = ldapService.userExists(username, email);
            } catch (BadRequestException bre) {
                throw new UserNameOrEmailExistsException();
            }
            

            if (ldapExists && usernameExists && emailExists) {
                throw new UserExistsException(username);
            }

            if (!ldapExists && usernameExists && emailExists) {
                // we need to return a user who has not activated there account and will be asked to
                // reactivate with an email.
                Optional<User> pendingUser = userRepository.findByNameIgnoreCase(username)
                        .filter(u -> u.getAccountStatus().equals(AccountStatus.pendingActivation));
                pendingUser.orElseThrow(() -> new BadRequestException(
                        "User exists but has not set a password. " + "Please make a forgot password request."));
                return pendingUser;
            }

            if (usernameExists != emailExists) {
                if (usernameExists)
                    throw new UserExistsException(username);
                else
                    throw new UserEmailExistsException(email);
            }

            throw new UserExistsException("How did we get here?");

        } else {
            return Optional.empty();
        }
    }

    @Override
    public User verifyNewUser(String username) {
        User pendingUser = userRepository.findByNameIgnoreCase(username)
                .filter(user -> user.getAccountStatus().equals(AccountStatus.pendingActivation)
                        | user.getAccountStatus().equals(AccountStatus.verified))
                .orElseThrow(() -> new BadRequestException("User is already Activated or does not exist"));

        pendingUser.setAccountStatus(AccountStatus.verified);

        pendingUser = new UpdateUser(pendingUser, userRepository).execute();
        return pendingUser;
    }

    @Override
    public User setPassword(String username, String password, String confirm) {
        if (confirmPassword(password, confirm)) {
            User user = userRepository.findByNameIgnoreCase(username).filter(
                    u -> u.getAccountStatus().equals(AccountStatus.verified) | u.getAccountStatus().equals(AccountStatus.active))
                    .orElseThrow(() -> new BadRequestException("User not active or verified"));

            if (user.getAccountStatus().equals(AccountStatus.verified) | !ldapService.userExists(user.getUsername(), user.getEmail())) {
                user = new SetUserPasswordHash(user, password, passwordEncoder).execute();
                user = new CreateLdapUser(user, ldapUserDetailsManager).execute();
                user.setAccountStatus(AccountStatus.active);
                user = new UpdateUser(user, userRepository).execute();
                return user;
            } else {
                user = new SetUserPasswordHash(user, password, passwordEncoder).execute();
                user = new UpdateLdapUser(user, ldapUserDetailsManager).execute();
                user = new UpdateUser(user, userRepository).execute();
                return user;
            }
        } else {
            throw new BadRequestException("Cannot set password, please confirm that the password and confirmation match");
        }
    }

    private boolean confirmPassword(String password, String confirm) {
        if (password.length() < 8 || password.length() > 24 || password.contains(" ")) {
            throw new BadRequestException("Password not accepted");
        }
        return password.equals(confirm);
    }

}
