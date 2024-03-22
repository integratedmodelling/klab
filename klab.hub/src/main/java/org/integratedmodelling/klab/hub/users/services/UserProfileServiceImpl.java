package org.integratedmodelling.klab.hub.users.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.ldap.LdapServiceImpl;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tags.dto.MongoTag;
import org.integratedmodelling.klab.hub.tags.dto.TagNotification;
import org.integratedmodelling.klab.hub.tags.enums.TagNameEnum;
import org.integratedmodelling.klab.hub.tags.services.TagNotificationService;
import org.integratedmodelling.klab.hub.tokens.dto.TokenVerifyEmailClickback;
import org.integratedmodelling.klab.hub.tokens.enums.TokenType;
import org.integratedmodelling.klab.hub.tokens.services.RegistrationTokenService;
import org.integratedmodelling.klab.hub.users.commands.UpdateUser;
import org.integratedmodelling.klab.hub.users.dto.ProfileResource;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.exceptions.UserByEmailDoesNotExistException;
import org.integratedmodelling.klab.hub.users.exceptions.UserDoesNotExistException;
import org.integratedmodelling.klab.rest.HubNotificationMessage.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    protected static final Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private UserRepository userRepository;
    private LdapServiceImpl ldapServiceImpl;

    private ObjectMapper objectMapper;

    private EmailManager emailManager;

    private RegistrationTokenService tokenService;
    private TagNotificationService tagNotificationService;

    public UserProfileServiceImpl(UserRepository userRepository, ObjectMapper objectMapper, EmailManager emailManager,
            RegistrationTokenService tokenService, LdapServiceImpl ldapServiceImpl,
            TagNotificationService tagNotificationService) {
        super();
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.emailManager = emailManager;
        this.tokenService = tokenService;
        this.ldapServiceImpl = ldapServiceImpl;
        this.tagNotificationService = tagNotificationService;
    }

    @Override
    public ProfileResource updateUserByProfile(ProfileResource profile) {
        User user = updateUserFromProfileResource(profile);
        if (!user.getEmail().equals(profile.getEmail())) {
            if (userRepository.existsByEmailIgnoreCase(profile.getEmail())) {
                throw new KlabException("Duplicated key. Email is already exists");
            }
            user.setEmail(profile.getEmail());
        }
        User updatedUser = new UpdateUser(user, userRepository).execute();
        ProfileResource updatedProfile = objectMapper.convertValue(updatedUser, ProfileResource.class);
        return updatedProfile.getSafeProfile();
    }

    @Override
    public ProfileResource getUserSafeProfile(User user) {
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
        return profile.getSafeProfile();
    }

    @Override
    public ProfileResource getUserProfile(String username) {
        User user = userRepository.findByNameIgnoreCase(username).orElseThrow(() -> new UserDoesNotExistException());
        return getUserSafeProfile(user);
    }

    @Override
    public ProfileResource getCurrentUserProfile(boolean remote) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByNameIgnoreCase(username).orElseThrow(() -> new UserDoesNotExistException());
        ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
        if (remote) {
            return profile;
        }
        return profile.getSafeProfile();
    }

    @Override
    public Set<ProfileResource> getAllUserProfiles() {
        Set<ProfileResource> profiles = new HashSet<>();
        userRepository.findAll()
                .forEach(user -> profiles.add(objectMapper.convertValue(user, ProfileResource.class).getSafeProfile()));
        return profiles;
    }

    @Override
    public ProfileResource getRawUserProfile(String username) {
        User user = userRepository.findByNameIgnoreCase(username).orElseThrow(() -> new UserDoesNotExistException());
        ProfileResource profile = objectMapper.convertValue(user, ProfileResource.class);
        return profile;
    }

    @Override
    public ProfileResource getUserProfileByEmail(String email) {
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UserByEmailDoesNotExistException(email));
        return getUserSafeProfile(user);
    }

    /**
     * Create TokenVerifyEmailClickback if email is changed. If email changes send
     * and email to the user to verified this action
     * 
     * @param user
     * @param email
     * @throws MessagingException
     */
    @Override
    public ProfileResource createNewEmailRequest(String username, String email) throws MessagingException {
        ProfileResource profile = getUserProfile(username);
        if (profile.getEmail().equals(email)) {
            throw new KlabException("Not modified email.");
        }
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new KlabException("Duplicated key. Email is already exists");
        }
        TokenVerifyEmailClickback token = (TokenVerifyEmailClickback) tokenService.createToken(username, email,
                TokenType.verifyEmail);

        try {
            emailManager.sendVerifyEmailClickback(email, token.getCallbackUrl());
        } catch (Exception e) {
            logger.error(e.getMessage());
            tokenService.deleteToken(token.getTokenString());
            throw new KlabException("Error sending mail.");
        }

        return profile;

    }

    @Override
    public ProfileResource updateUserEmail(String username, String email) {
        User user = userRepository.findByNameIgnoreCase(username)
                .orElseThrow(() -> new KlabException("Current User doesn't exist"));
        user.setEmail(email);

        /* update mongo repository */
        User updatedUser;

        try {
            updatedUser = new UpdateUser(user, userRepository).execute();
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage());
            throw new KlabException("Duplicated key, email is already in use.");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new KlabException("Error updating mongo user: " + e.getMessage(), e);
        }

        /* update ldap */
        try {
            ldapServiceImpl.updateUserEmailAddress(username, email);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new KlabException("Error updating ldap user: " + e.getMessage(), e);
        }

        try {
            TagNotification tagNotification = tagNotificationService.createWarningUserTagNotification(updatedUser,
                    TagNameEnum.downloadCertificateChangeEmail.toString(), false, "", "");

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new KlabException(e.getMessage(), e);
        }

        ProfileResource updatedProfile = objectMapper.convertValue(updatedUser, ProfileResource.class);
        return updatedProfile.getSafeProfile();
    }

    private User updateUserFromProfileResource(ProfileResource profile) {
        User user = userRepository.findByNameIgnoreCase(profile.getUsername())
                .filter(u -> u.getUsername().equals(profile.getUsername()))
                .orElseThrow(() -> new KlabException("Current User context does match updated profile username"));
        user.updateFromProfileResource(profile);
        return user;
    }

    @Override
    public Page<User> getPage(Query query, Pageable pageable) {
        return userRepository.findAll(query, pageable);
    }

    @Override
    public List<User> getQuery(Query query) {
        return userRepository.findAll(query);
    }

}
