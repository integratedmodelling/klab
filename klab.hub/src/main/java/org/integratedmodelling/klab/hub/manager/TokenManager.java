package org.integratedmodelling.klab.hub.manager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.config.TokenClickbackConfig;
import org.integratedmodelling.klab.hub.exception.AuthenticationFailedException;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.exception.TokenGenerationException;
import org.integratedmodelling.klab.hub.exception.UserEmailExistsException;
import org.integratedmodelling.klab.hub.exception.UserExistsException;
import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.User;
import org.integratedmodelling.klab.hub.models.User.AccountStatus;
import org.integratedmodelling.klab.hub.models.tokens.ActivateAccountClickbackToken;
import org.integratedmodelling.klab.hub.models.tokens.AuthenticationToken;
import org.integratedmodelling.klab.hub.models.tokens.ChangePasswordClickbackToken;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackAction;
import org.integratedmodelling.klab.hub.models.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.models.tokens.GroupsClickbackToken;
import org.integratedmodelling.klab.hub.models.tokens.InviteUserClickbackToken;
import org.integratedmodelling.klab.hub.models.tokens.VerifyEmailClickbackToken;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.service.KlabGroupService;
import org.integratedmodelling.klab.hub.service.KlabUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//Maybe make a service for the tokens, which the manager can call
//this may make it easier to follow
@Component
public class TokenManager {
	
	@Autowired
	private KlabUserDetailsService klabUserDetailsService;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private TokenClickbackConfig tokenClickbackConfig;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private KlabGroupService klabGroupService;

	@Autowired
	private EmailManager emailManager;
	
	@Autowired
	LicenseManager licenseManager;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private KlabUserManager klabUserManager;

	public ClickbackToken createClickbackToken(String username, Class<? extends ClickbackToken> tokenType) {
		ClickbackToken result = null;
		try {
			result = tokenType.getConstructor(String.class).newInstance(username);
		} catch (Exception e) {
			// mainly this is to throw RuntimeException instead of a checked exception
			// (shouldn't get here though)
			throw new KlabAuthorizationException("Unable to get token constructor method.", e);
		}
		result.setCallbackUrl(tokenClickbackConfig);
		result.setAuthenticated(true);
		tokenRepository.save(result);
		return result;
	}

	public void deleteToken(String tokenString) {
		Optional<AuthenticationToken> dbToken = tokenRepository.findByTokenString(tokenString);
		if (dbToken.isPresent()) {
			tokenRepository.delete(dbToken.get());
		} else {
			throw new KlabException(
					"Something happened with the delete, for some reason there was no token present in database");
		}
	}

	public void deleteExpiredTokens(String username) {
		List<AuthenticationToken> dbTokens = tokenRepository.findByUsername(username);
		for (AuthenticationToken dbToken : dbTokens) {
			if (dbToken.isExpired()) {
				tokenRepository.delete(dbToken);
			}
		}
	}

	public AuthenticationToken handleToken(String tokenString) {
		AuthenticationToken token = 
				tokenRepository
					.findByTokenString(tokenString)
					.orElseThrow(() ->new KlabException("The Token:" + tokenString + ", was not found"));	
		return token;
	}

	public AuthenticationToken authenticate(String username, String password) {
		AuthenticationToken result = null;
		try {
			Authentication authRequest = new UsernamePasswordAuthenticationToken(username, password);
			Authentication authResult = authenticationManager.authenticate(authRequest);
			if (!authResult.isAuthenticated()) {
				String msg = "Something went wrong with authentication. Result.isAuthenticated() == false, but no exception was thrown.";
				throw new KlabException(msg) {
					private static final long serialVersionUID = -8517575154826926750L;
				};
			} else {
				deleteExpiredTokens(username);
				result = createAuthenticationToken(username, AuthenticationToken.class);
				SecurityContextHolder.getContext().setAuthentication(result);
				klabUserManager.updateLastLogin(username);
			}
		} catch (KlabException e) {
			String msg = "Login failed for user: " + username;
			throw new KlabException(msg, e);
		}
		return result;
	}
	
	public AuthenticationToken createAuthTokenForOAuth(Authentication authentication) {
		AuthenticationToken result = null;
		ProfileResource profile = (ProfileResource) authentication.getPrincipal();
		if (!authentication.isAuthenticated()) {
			String msg = "Something went wrong with authentication. Result.isAuthenticated() == false, but no exception was thrown.";
			throw new KlabException(msg) {
				private static final long serialVersionUID = -8517575154826926750L;
			};
		} else {
			deleteExpiredTokens(profile.getUsername());
			result = createAuthenticationToken(profile.getUsername(), AuthenticationToken.class);
			SecurityContextHolder.getContext().setAuthentication(result);
			klabUserManager.updateLastLogin(profile.getUsername());
		}
		return result;
	}

	public ClickbackToken createNewUser(String username, String email) throws TokenGenerationException {
		User user = klabUserDetailsService.loadUserByUsername(username);
		if (user != null) {
			if (!AccountStatus.pendingActivation.equals(user.getAccountStatus())
					|| !email.equals(user.getEmail())) {
				throw new BadRequestException("An account with this username already exists.\n"
						+ "If you are re-sending a registration email, please use the same email address as before.");
			}
		} else {
			User newUser = new User();
			newUser.setUsername(username);
			newUser.setEmail(email);
			newUser.setRoles(Arrays.asList(Role.ROLE_USER));
			newUser.setRegistrationDate();
			try {
				klabUserDetailsService.createMongoUser(newUser);
			} catch (UserExistsException | UserEmailExistsException e) {
				throw new BadRequestException(e.getMessage(), e);
			}
		}

		ClickbackToken clickbackToken = createClickbackToken(username, ActivateAccountClickbackToken.class);
		emailManager.sendNewUser(email, username, clickbackToken.getCallbackUrl());
		return clickbackToken;
	}
	
	public ClickbackToken createNewUserWithGroups(String username, String email, String tokenString, List<String> groups) {
		ClickbackToken token = createNewUser(username, email);
		handleInviteClickbackToken(username, tokenString, groups);
		return token;		
	}
	
	public AuthenticationToken updateOAuthUserWithGroups(String tokenString, String groupToken,
			List<String> groups) {
		AuthenticationToken token = handleToken(tokenString);
		handleInviteClickbackToken(token.getPrincipal(), groupToken, groups);
		return token;
	}

	public VerifyEmailClickbackToken createVerifyEmailClickbackToken(String username, String email) {
		VerifyEmailClickbackToken token = (VerifyEmailClickbackToken) createClickbackToken(username,
				VerifyEmailClickbackToken.class);
		token.setNewEmailAddress(email);
		tokenRepository.save(token);
		return token;
	}
	
	public ChangePasswordClickbackToken createNewPasswordClickbackToken(String username) {
		ChangePasswordClickbackToken token = (ChangePasswordClickbackToken) createClickbackToken(username, ChangePasswordClickbackToken.class);
		return token;
	}
	
	public ClickbackToken handleVerificationToken(String userId, String tokenString) {
		//lets only find a clickback token that invited the user
		ClickbackToken activationToken = tokenRepository
			.findByTokenString(tokenString)
			.map(ClickbackToken.class::cast)
			.filter(token -> token.getClickbackAction().equals(ClickbackAction.activate))
			.filter(token -> token.getPrincipal().equals(userId))
			.orElseThrow(IllegalArgumentException::new);
		//lets login to the security context
		SecurityContextHolder.getContext().setAuthentication(activationToken);
		//activate 
		verifyAccount();
		deleteToken(tokenString);
		
		if(klabUserManager.getLoggedInUser().getProvider() == null) {
			return createClickbackToken(klabUserManager.getLoggedInUsername(), ChangePasswordClickbackToken.class);
		} else {
			activationToken.setDetails("Deleted");
			return activationToken;
		}
	}
	
	public void verifyAccount() {
		// the user is already authenticated via clickback token header, but we want
		// to do some extra verification because they aren't supplying a password
		Authentication authentication = klabUserDetailsService.getLoggedInAuthentication();
		if (!(authentication instanceof ActivateAccountClickbackToken)) {
			throw new AuthenticationFailedException("The token submitted was not valid for activating an account.");
		}
		// this will also verify that the account started in pendingActivation
		klabUserDetailsService.activateUser(klabUserManager.getLoggedInUsername());
	}
	
	
	public ClickbackToken handleChangePasswordToken(String userId, String tokenString, String newPassword) {
		//lets make sure that this token is for setting the password
		ClickbackToken changePasswordToken = tokenRepository
				.findByTokenString(tokenString)
				.map(ClickbackToken.class::cast)
				.filter(token -> token.getClickbackAction().equals(ClickbackAction.password))
				.filter(token -> token.getPrincipal().equals(userId))
				.filter(token -> !token.isExpired())
				.orElseThrow(IllegalArgumentException::new);
		//lets login
		SecurityContextHolder.getContext().setAuthentication(changePasswordToken);
		setPasswordAndSendVerificationEmail(newPassword);
		changePasswordToken.setDetails("Deleted");
		return changePasswordToken;
	}
	
	
	private void setPasswordAndSendVerificationEmail(String newPassword) {
		// The user will have logged in via PasswordChangeClickbackToken or
		// username/password.
		User persistedUser = klabUserManager.getLoggedInUser();
		if (persistedUser == null) {
			throw new BadRequestException("Could not find a user with the token that was submitted.");
		} else if (!AccountStatus.active.equals(persistedUser.getAccountStatus())) {
			throw new BadRequestException("An active user could not be found with the token that was submitted.");
		} else {
			// in case the user is changing their password to solve a broken state (i.e.
			// Mongo but no LDAP)
			// this will prevent the missing LDAP record from breaking the process
			if (!klabUserDetailsService.ldapUserExists(persistedUser.getUsername())) {
				klabUserDetailsService.createLdapUser(persistedUser);
			}
			persistedUser.setPasswordHash(passwordEncoder.encode((newPassword)));
			klabUserDetailsService.updateUser(persistedUser);
			// send an email notifying the user their password was changed
			emailManager.sendPasswordChangeConfirmation(persistedUser.getEmail());
		}
	}
	
	public AuthenticationToken createAuthenticationToken(String username,
			Class<? extends AuthenticationToken> tokenType) {
		if (ClickbackToken.class.isAssignableFrom(tokenType)) {
			throw new KlabAuthorizationException(
					"ClickbackTokens must be generated by createClickbackToken(), not createAuthenticationToken()",
					null);
		}
		AuthenticationToken result = null;
		try {
			result = tokenType.getConstructor(String.class).newInstance(username);
		} catch (Exception e) {
			// mainly this is to throw RuntimeException instead of a checked exception
			// (shouldn't get here though)
			throw new KlabAuthorizationException("Unable to get token constructor method.", e);
		}
		User user = klabUserDetailsService.loadUserByUsername(username);
		result.setAuthorities(user.getAuthorities());
		result.setAuthenticated(true);
		tokenRepository.save(result);
		return result;
	}

	public GroupsClickbackToken createGroupsClickbackToken(String username, List<String> groups) {
		GroupsClickbackToken token = (GroupsClickbackToken) createClickbackToken(username,GroupsClickbackToken.class);
		token.setCallbackUrl(tokenClickbackConfig);
		return token;
	}
	
	public void sendGroupClickbackToken(String username, List<String> groups) {
		GroupsClickbackToken token = createGroupsClickbackToken(username, groups);
		String grpString = groups.stream().collect(Collectors.joining(","));
		URL clickbackWithGroups;
		try {
			clickbackWithGroups = new URL(
					String.format("%s&addGroups=%s",token.getCallbackUrl().toString(),grpString));
		} catch (MalformedURLException e) {
			throw new KlabException(e);
		}
		emailManager.sendGroupRequestEmail(username, clickbackWithGroups, grpString);
	}
	
	public InviteUserClickbackToken createInviteUserClickbackToken(String email, List<String> groups) {
		InviteUserClickbackToken token = (InviteUserClickbackToken) createClickbackToken(email, InviteUserClickbackToken.class);
		token.setCallbackUrl(tokenClickbackConfig);
		return token;
	}
	
	public void sendInviteUserClickbackToken(String email, List<String> groups) throws MessagingException {
		InviteUserClickbackToken token = createInviteUserClickbackToken(email, groups);
		String grpString = groups.stream().collect(Collectors.joining(","));
		URL clickbackWithGroups;
		try {
			clickbackWithGroups = new URL(
					String.format("%s&addGroups=%s",token.getCallbackUrl().toString(),grpString));
		} catch (MalformedURLException e) {
			throw new KlabException(e);
		}
		emailManager.sendInviteWithGroupsEmail(email, clickbackWithGroups);
	}
	
	public void inviteNewUserWithGroups(String email, List<String> groups) throws MessagingException {
		User user = klabUserDetailsService.loadUserByUsername(email);
		if (user != null) {
			if (!AccountStatus.pendingActivation.equals(user.getAccountStatus())
					|| !email.equals(user.getEmail())) {
				throw new BadRequestException("An account with this username already exists.\n");
			}
		} else {
			sendInviteUserClickbackToken(email, groups);
		}
	}
	
	public ClickbackToken handleGroupsClickbackToken(String userId, String tokenString, Collection<String> groups) {
		ClickbackToken groupsClickbackToken = tokenRepository
			.findByTokenString(tokenString)
			.map(ClickbackToken.class::cast)
			.filter(token -> token.getClickbackAction().equals(ClickbackAction.groups))
			.filter(token -> token.getPrincipal().equals(userId))
			.orElseThrow(IllegalArgumentException::new);
		//lets login to the security context
		SecurityContextHolder.getContext().setAuthentication(groupsClickbackToken);
		klabUserDetailsService.getLoggedInAuthentication();
		User user = klabUserDetailsService.loadUserByUsername(userId);
		Collection<String> available = klabGroupService.getGroupNames();
		//groups.addAll(user.getGroups());
		if(!available.containsAll(groups)) {
			throw new KlabException("A Group was included that does not exist in the database");
		}
		user.setGroups(groups);
		klabUserDetailsService.updateUser(user);
		deleteToken(tokenString);
		return groupsClickbackToken;
	}
	
	public ClickbackToken handleInviteClickbackToken(String userId, String tokenString, Collection<String> groups) {
		ClickbackToken inviteClickToken = tokenRepository
				.findByTokenString(tokenString)
				.map(ClickbackToken.class::cast)
				.filter(token -> token.getClickbackAction().equals(ClickbackAction.invite))
				.orElseThrow(IllegalArgumentException::new);
		SecurityContextHolder.getContext().setAuthentication(inviteClickToken);
		klabUserDetailsService.getLoggedInAuthentication();
		User user = klabUserDetailsService.loadUserByUsername(userId);
		Collection<String> available = klabGroupService.getGroupNames();
		//groups.addAll(user.getGroups());
		if(!available.containsAll(groups)) {
			throw new KlabException("A Group was included that does not exist in the database");
		}
		user.setGroups(groups);
		klabUserDetailsService.updateUser(user);
		deleteToken(tokenString);
		return inviteClickToken;
	}

}
