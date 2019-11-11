package org.integratedmodelling.klab.hub.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.BadRequestException;

import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.exception.UserEmailExistsException;
import org.integratedmodelling.klab.hub.exception.UserExistsException;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.models.User.AccountStatus;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Service;

@Service
public class KlabUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	LdapService ldapService;

	@Override
	public User loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = getUserFromMongo(usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		Set<Role> roles = new HashSet<>();
		
		getLdapUser(usernameOrEmail)
				.map(UserDetails::getAuthorities)
				.ifPresent(authorities -> 
					authorities.forEach(role -> roles.add(Role.valueOf(role.getAuthority()))));
		
		getLdapUser(usernameOrEmail)
			.map(UserDetails::getPassword)
			.ifPresent(password -> user.setPasswordHash(password));
		
		if(!roles.isEmpty()) {
			user.setRoles(roles);	
		}
		return user;
	}

	private Optional<UserDetails> getLdapUser(String username) {
		try {
			return ldapService.getUserByCn(username);
		} catch (Exception e) {
			Optional<UserDetails> emptyLdapUser = Optional.empty();
			return emptyLdapUser;
		}
	}

	private Optional<User> getUserFromMongo(String usernameOrEmail) {
		Optional<User> user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(usernameOrEmail, usernameOrEmail);
		return user;
	}

	public boolean ldapUserExists(String username) {
		Optional<UserDetails> user = null;
		try {
			user = getLdapUser(username);
			return user.isPresent();
		} catch (AuthenticationException e) {
			return false;
		}
	}

	public User createMongoUser(User user) throws KlabException {
		return createMongoUser(user, AccountStatus.pendingActivation);
	}

	public User createMongoUser(User user, AccountStatus accountStatus) throws KlabException {
		if (usernameExists(user.getUsername())) {
			throw new UserExistsException(user.getUsername());
		}
		if (emailExists(user.getEmail())) {
			throw new UserEmailExistsException(user.getEmail());
		}
		if(user.getAccountStatus().equals(null)) {
			user.setAccountStatus(accountStatus);	
		}
		user = userRepository.save(user);
		Logging.INSTANCE.info("Created Mongo User: " + user.toString());
		return user;
	}

	public boolean usernameExists(String username) {
		Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
		return user.isPresent();
	}

	public boolean emailExists(String email) {
		Optional<User> user = userRepository.findByEmailIgnoreCase(email);
		return user.isPresent();
	}
	
	public void activateUser(String username) {
		User user = loadUserByUsername(username);
		if (AccountStatus.active.equals(user.getAccountStatus())) {
			// if already active, return silently (don't want a repeat-click to overwrite a
			// changed password)
		} else if (AccountStatus.verified.equals(user.getAccountStatus())) {
			// update the account status and create the LDAP user
			user.setAccountStatus(AccountStatus.active);
			userRepository.save(user);
		} else {
			throw new BadRequestException("Account was not in a valid state for activation.");
		}
	}
	
	public void verifyUser(String username) {
		User user = loadUserByUsername(username);
		if (AccountStatus.verified.equals(user.getAccountStatus())) {
			// if already active, return silently (don't want a repeat-click to overwrite a
			// changed password)
		} else if (AccountStatus.pendingActivation.equals(user.getAccountStatus())) {
			// update the account status and create the LDAP user
			user.setAccountStatus(AccountStatus.verified);
			userRepository.save(user);
		} else {
			throw new BadRequestException("Account was not in a valid state for activation.");
		}
	}

	public User updateUser(User user) {
		getUserFromMongo(user.getUsername())
			.map(User::getId)
			.ifPresent(id -> user.setId(id));
		
		userRepository.save(user);
		try {
			updateLdapUser(user);
		} catch (Throwable e) {
			// return success if only LDAP fails - sometimes users aren't activated before
			// updates happen
			Logging.INSTANCE.warn((String.format("Mongo update succeeded but Spring/LDAP user update failed for '%s'.",
					user.getUsername())));
		}
		return user;
	}

	public void createLdapUser(User user) {
		UserDetails ldapUser = convertUsertoLdapUser(user);
		ldapService.createUser(ldapUser);
		Logging.INSTANCE.info("Created Ldap User:" + user.toString());
	}

	public void updateLdapUser(User user) {
		UserDetails ldapUser = convertUsertoLdapUser(user);
		ldapService.updateUser(ldapUser);
		Logging.INSTANCE.info("Updated Ldap User:" + user.toString());
	}

	private UserDetails convertUsertoLdapUser(User user) {
		InetOrgPerson.Essence person = new InetOrgPerson.Essence();
		person.setCn(new String[] { user.getUsername() });
		person.setDn(ldapService.buildDn(user.getUsername()).toString());
		person.setUid(user.getUsername());
		person.setUsername(user.getUsername());
		person.setPassword(user.getPasswordHash());
		person.setMail(user.getEmail());
		person.setDisplayName(user.getDisplayName());
		if (StringUtils.isEmpty(user.getLastName())) {
			person.setSn("<unknown>");
		} else {
			person.setSn(user.getLastName());
		}
		person.setAuthorities(user.getAuthorities());
		LdapUserDetails userDetails = person.createUserDetails();
		return userDetails;
	}

	public Authentication getLoggedInAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public void changePassword(String oldPassword, String newPassword) {
	}
	
    public void deleteUser(String username) {
        User user = getUserFromMongo(username)
        				.filter(foundUser -> foundUser.getAccountStatus().equals(AccountStatus.active))
        				.orElseThrow(IllegalArgumentException::new);
        user.setAccountStatus(AccountStatus.deleted);
        userRepository.save(user);
        ldapService.deleteUser(username);
    }

}
