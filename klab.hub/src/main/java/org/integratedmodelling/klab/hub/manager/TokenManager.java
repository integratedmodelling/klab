package org.integratedmodelling.klab.hub.manager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.api.TokenAuthentication;
import org.integratedmodelling.klab.hub.api.TokenChangePasswordClickback;
import org.integratedmodelling.klab.hub.api.ClickbackAction;
import org.integratedmodelling.klab.hub.api.TokenClickback;
import org.integratedmodelling.klab.hub.api.GroupEntry;
import org.integratedmodelling.klab.hub.api.GroupRequestTask;
import org.integratedmodelling.klab.hub.api.GroupsClickbackToken;
import org.integratedmodelling.klab.hub.api.TokenInviteUserClickback;
import org.integratedmodelling.klab.hub.api.TokenLostPasswordClickback;
import org.integratedmodelling.klab.hub.api.TokenNewUserClickback;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.Role;
import org.integratedmodelling.klab.hub.api.Task;
import org.integratedmodelling.klab.hub.api.TaskStatus;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.TokenVerifyAccountClickback;
import org.integratedmodelling.klab.hub.api.TokenVerifyEmailClickback;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.exception.ActivationTokenFailedException;
import org.integratedmodelling.klab.hub.exception.AuthenticationFailedException;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.exception.ChangePasswordTokenFailedException;
import org.integratedmodelling.klab.hub.exception.GroupRequestTokenFailedException;
import org.integratedmodelling.klab.hub.exception.TokenGenerationException;
import org.integratedmodelling.klab.hub.exception.UserEmailExistsException;
import org.integratedmodelling.klab.hub.exception.UserExistsException;
import org.integratedmodelling.klab.hub.groups.services.MongoGroupService;
import org.integratedmodelling.klab.hub.payload.LoginResponse;
import org.integratedmodelling.klab.hub.payload.LogoutResponse;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tasks.services.TaskService;
import org.joda.time.DateTime;
import org.integratedmodelling.klab.hub.manager.KlabUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

//Maybe make a service for the tokens, which the manager can call
//this may make it easier to follow
@Component
public class TokenManager {
	

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private LinkConfig linkConfig;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private MongoGroupService klabGroupService;
	
	@Autowired
	private TaskService taskService;

	@Autowired
	private EmailManager emailManager;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private KlabUserManager klabUserManager;

	public TokenClickback createClickbackToken(String username, Class<? extends TokenClickback> tokenType) {
		TokenClickback result = null;
		try {
			result = tokenType.getConstructor(String.class).newInstance(username);
		} catch (Exception e) {
			// mainly this is to throw RuntimeException instead of a checked exception
			// (shouldn't get here though)
			throw new KlabAuthorizationException("Unable to get token constructor method.", e);
		}
		result.setCallbackUrl(linkConfig);
		result.setAuthenticated(true);
		tokenRepository.save(result);
		return result;
	}
	
	public TokenClickback createClickbackToken(String username, String parentToken, Class<? extends TokenClickback> tokenType) {
		TokenClickback result = null;
		try {
			result = tokenType.getConstructor(String.class).newInstance(username);
		} catch (Exception e) {
			// mainly this is to throw RuntimeException instead of a checked exception
			// (shouldn't get here though)
			throw new KlabAuthorizationException("Unable to get token constructor method.", e);
		}
		result.setParetToken(parentToken);
		result.setCallbackUrl(linkConfig);
		result.setAuthenticated(true);
		tokenRepository.save(result);
		return result;
	}

	public void deleteToken(String tokenString) {
		Optional<TokenAuthentication> dbToken = tokenRepository.findByTokenString(tokenString);
		if (dbToken.isPresent()) {
			tokenRepository.delete(dbToken.get());
		} else {
			throw new KlabException(
					"Something happened with the delete, for some reason there was no token present in database");
		}
	}

	public void deleteExpiredTokens(String username) {
		List<TokenAuthentication> dbTokens = tokenRepository.findByUsername(username);
		for (TokenAuthentication dbToken : dbTokens) {
			if (dbToken.isExpired()) {
				tokenRepository.delete(dbToken);
			}
		}
	}

	public TokenAuthentication handleToken(String tokenString) {
		TokenAuthentication token = 
				tokenRepository
					.findByTokenString(tokenString)
					.orElseThrow(() ->new KlabException("The Token:" + tokenString + ", was not found"));	
		return token;
	}

	public TokenAuthentication getAuthenticationToken(String username, String password) {
		TokenAuthentication result = null;
		Authentication authRequest = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authResult = authenticationManager.authenticate(authRequest);
		if (!authResult.isAuthenticated()) {
			String msg = "Something went wrong with authentication. Result.isAuthenticated() == false, but no exception was thrown.";
			throw new KlabException(msg) {
				private static final long serialVersionUID = -8517575154826926750L;
			};
		} else {
			deleteExpiredTokens(username);
			result = createAuthenticationToken(username, TokenAuthentication.class);
			SecurityContextHolder.getContext().setAuthentication(result);
			User user = klabUserManager.getLoggedInUser();
			klabUserManager.updateLastLogin(user);
		}
		return result;
	}
	
	public TokenAuthentication createAuthTokenForOAuth(Authentication authentication) {
		TokenAuthentication result = null;
		ProfileResource profile = (ProfileResource) authentication.getPrincipal();
		if (!authentication.isAuthenticated()) {
			String msg = "Something went wrong with authentication. Result.isAuthenticated() == false, but no exception was thrown.";
			throw new KlabException(msg) {
				private static final long serialVersionUID = -8517575154826926750L;
			};
		} else {
			deleteExpiredTokens(profile.getUsername());
			result = createAuthenticationToken(profile.getUsername(), TokenAuthentication.class);
			SecurityContextHolder.getContext().setAuthentication(result);
			User user = klabUserManager.getUser(profile.getUsername());
			klabUserManager.updateLastLogin(user);
		}
		return result;
	}

	public TokenClickback createNewUser(String username, String email) throws TokenGenerationException {
		boolean ldapEntry = klabUserManager.ldapUserExists(username, email);
		boolean userExist = klabUserManager.mongUserExists(username, email);
		
		if (ldapEntry) {
			throw new UserExistsException("User name or email already registered");
		}
		
		if (userExist) {
			User user = klabUserManager.loadUserByUsername(username);
			if (user == null ) {
				throw new UserEmailExistsException(email);
			} else {
				if (!user.getEmail().equals(email)) {
					throw new UserExistsException(username);
				} else {
					TokenClickback clickbackToken = resendActivateAccountClickback(user);
					return clickbackToken;
				}
			}
		}
		TokenClickback clickbackToken = createNewUserAndToken(username, email);
		return clickbackToken;
	}
	
	
	private TokenClickback createNewUserAndToken(String username, String email) {
		TokenClickback clickbackToken = null;
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setEmail(email);
		newUser.setRoles(Arrays.asList(Role.ROLE_USER));
		newUser.setRegistrationDate(DateTime.now());
		
		try {
			klabUserManager.createPendingKlabUser(newUser);
			clickbackToken = createClickbackToken(username, TokenVerifyAccountClickback.class);
		} catch (UserExistsException | UserEmailExistsException e) {
			throw new TokenGenerationException(e.getMessage(), e);
		}
		emailManager.sendNewUser(email, username, clickbackToken.getCallbackUrl());
		return clickbackToken;
	}
	
	private TokenClickback resendActivateAccountClickback(User user) {
		if (!AccountStatus.pendingActivation.equals(user.getAccountStatus())) {
			throw new BadRequestException("An account with this username already exists.\n"
					+ "User may request a new password for their account.");
		}
		List<TokenAuthentication> tokens = tokenRepository.findByUsername(user.getUsername());
		for (TokenAuthentication token : tokens) {
			tokenRepository.delete(token);
		}
		TokenClickback clickbackToken = createClickbackToken(user.getUsername(), TokenVerifyAccountClickback.class);
		emailManager.sendNewUser(user.getEmail(), user.getUsername(), clickbackToken.getCallbackUrl());
		return clickbackToken;
	}

	public TokenClickback createNewUserWithGroups(String username, String email, String tokenString, List<String> groups) {
		TokenClickback token = createNewUser(username, email);
		handleInviteClickbackToken(tokenString, groups);
		return token;		
	}
	
	public TokenAuthentication updateOAuthUserWithGroups(String tokenString, String groupToken,
			List<String> groups) {
		TokenAuthentication token = handleToken(tokenString);
		handleInviteClickbackToken(groupToken, groups);
		return token;
	}

	public TokenVerifyEmailClickback createVerifyEmailClickbackToken(String username, String email) {
		TokenVerifyEmailClickback token = (TokenVerifyEmailClickback) createClickbackToken(username,
				TokenVerifyEmailClickback.class);
		token.setNewEmailAddress(email);
		tokenRepository.save(token);
		return token;
	}
	
	public TokenChangePasswordClickback createNewPasswordClickbackToken(String username) {
		TokenChangePasswordClickback token = (TokenChangePasswordClickback) createClickbackToken(username, TokenChangePasswordClickback.class);
		return token;
	}
	
	public TokenLostPasswordClickback createlostPasswordClickbackToken(String username) {
		TokenLostPasswordClickback token = (TokenLostPasswordClickback) createClickbackToken(username, TokenLostPasswordClickback.class);
		return token;
	}
	
	public TokenClickback handleVerificationToken(String userId, String tokenString) {
		//lets only find a clickback token that invited the user
		TokenClickback activationToken = tokenRepository
			.findByTokenString(tokenString)
			.map(TokenClickback.class::cast)
			.filter(token -> token.getClickbackAction().equals(ClickbackAction.activate))
			.filter(token -> token.getPrincipal().equals(userId))
			.orElseThrow(() -> new ActivationTokenFailedException("Activation Token no longer active"));
		//lets login to the security context
		SecurityContextHolder.getContext().setAuthentication(activationToken);
		//verify account 
		verifyAccount();
		//deleteToken(tokenString);
		
		if(klabUserManager.getLoggedInUser().getProvider() == null) {
			return createClickbackToken(klabUserManager.getLoggedInUsername(), tokenString, TokenNewUserClickback.class);
		} else {
			activationToken.setDetails("Error");
			return activationToken;
		}
	}
	
	public void verifyAccount() {
		// the user is already authenticated via clickback token header, but we want
		// to do some extra verification because they aren't supplying a password
		Authentication authentication = klabUserManager.getLoggedInAuthentication();
		if (!(authentication instanceof TokenVerifyAccountClickback)) {
			throw new AuthenticationFailedException("The token submitted was not valid for activating an account.");
		}
		// this will also verify that the account started in pendingActivation
		klabUserManager.activateUser(klabUserManager.getLoggedInUsername());
	}
	
	
	public TokenClickback handleChangePasswordToken(String userId, String tokenString, String newPassword) {
		//lets make sure that this token is for setting the password
		TokenClickback changePasswordToken = tokenRepository
				.findByTokenString(tokenString)
				.map(TokenClickback.class::cast)
				.filter(token -> (token.getClickbackAction().equals(ClickbackAction.password) |
						token.getClickbackAction().equals(ClickbackAction.lostPassword) |
						token.getClickbackAction().equals(ClickbackAction.newUser)))
				.filter(token -> token.getPrincipal().equals(userId))
				.filter(token -> !token.isExpired())
				.orElseThrow(()->new ChangePasswordTokenFailedException("Token inactive.  Make another request for changing user password"));
		//lets login
		SecurityContextHolder.getContext().setAuthentication(changePasswordToken);
		setPasswordAndSendVerificationEmail(newPassword);
		deleteToken(tokenString);
		if (changePasswordToken.getClickbackAction().equals(ClickbackAction.newUser)) {
			deleteToken(changePasswordToken.getParetToken());
		}
		changePasswordToken.setDetails("Deleted");
		return changePasswordToken;
	}
	
	
	private void setPasswordAndSendVerificationEmail(String newPassword) {
		// The user will have logged in via PasswordChangeClickbackToken or
		// username/password.
		if(newPassword.length() < 8 || newPassword.length() > 24 || newPassword.contains(" ")) {
			throw new BadRequestException("Password not accepted");
		}
		User persistedUser = klabUserManager.getLoggedInUser();
		if (persistedUser == null) {
			throw new BadRequestException("Could not find a user with the token that was submitted.");
		}
		AccountStatus status = persistedUser.getAccountStatus();
		if (!status.equals(AccountStatus.active) & !status.equals(AccountStatus.verified)) {
			throw new BadRequestException("An verified or active user could not be found with the token that was submitted.");
		} else {
			// in case the user is changing their password to solve a broken state (i.e.
			// Mongo but no LDAP)
			// this will prevent the missing LDAP record from breaking the process
			if (!klabUserManager.ldapUserExists(persistedUser.getUsername(), persistedUser.getEmail())) {
				klabUserManager.createLdapUser(persistedUser);
				persistedUser.setPasswordHash(passwordEncoder.encode(newPassword));
				klabUserManager.updateKlabUser(persistedUser);
				klabUserManager.activateUser(persistedUser.getUsername());
			} else {
				persistedUser.setPasswordHash(passwordEncoder.encode((newPassword)));
				klabUserManager.updateKlabUser(persistedUser);
			}
			if (!klabUserManager.ldapUserExists(persistedUser.getUsername(),persistedUser.getEmail())) {
				klabUserManager.createLdapUser(persistedUser);
			}
			persistedUser.setPasswordHash(passwordEncoder.encode((newPassword)));
			klabUserManager.updateKlabUser(persistedUser);
			try {
				// send an email notifying the user their password was changed
				emailManager.sendPasswordChangeConfirmation(persistedUser.getEmail());
			} catch (Exception e) {
				// some problem sending mail, we override the exception to inform front end part
				throw new BadRequestException("Error sending email", e);
			}
		}
	}
		
	
	public TokenAuthentication createAuthenticationToken(String username,
			Class<? extends TokenAuthentication> tokenType) {
		if (TokenClickback.class.isAssignableFrom(tokenType)) {
			throw new KlabAuthorizationException(
					"ClickbackTokens must be generated by createClickbackToken(), not createAuthenticationToken()",
					null);
		}
		TokenAuthentication result = null;
		try {
			result = tokenType.getConstructor(String.class).newInstance(username);
		} catch (Exception e) {
			// mainly this is to throw RuntimeException instead of a checked exception
			// (shouldn't get here though)
			throw new KlabAuthorizationException("Unable to get token constructor method.", e);
		}
		User user = klabUserManager.loadUserByUsername(username);
		result.setAuthorities(user.getAuthorities());
		result.setAuthenticated(true);
		tokenRepository.save(result);
		return result;
	}

	public GroupsClickbackToken createGroupsClickbackToken(String username, List<GroupEntry> groups) {
		GroupsClickbackToken token = (GroupsClickbackToken) createClickbackToken(username,GroupsClickbackToken.class);
		token.setGroups(groups);
		token.setCallbackUrl(linkConfig);
		tokenRepository.save(token);
		return token;
	}
	/*
	public GroupsClickbackToken sendGroupClickbackToken(String username, List<GroupEntry> groupEntries) {
		GroupsClickbackToken token = createGroupsClickbackToken(username, groupEntries);
		String grpString = groupEntries.stream()
				.map(grpEntry -> grpEntry.getGroupName())
				.collect(Collectors.joining(","));
		GroupRequestTask task = (GroupRequestTask) taskService.createTask(username, GroupRequestTask.class);
		task.setToken(token);
		taskService.saveTask(task);
		URL clickbackWithGroups;
		try {
			clickbackWithGroups = new URL(
					String.format("%s&addGroups=%s",token.getCallbackUrl().toString(),grpString));
		} catch (MalformedURLException e) {
			throw new KlabException(e);
		}
		emailManager.sendGroupRequestEmail(username, clickbackWithGroups, grpString);
		return token;
	}
	*/
	public TokenInviteUserClickback createInviteUserClickbackToken(String email, List<String> groups) {
		TokenInviteUserClickback token = (TokenInviteUserClickback) createClickbackToken(email, TokenInviteUserClickback.class);
		token.setCallbackUrl(linkConfig);
		return token;
	}
	
	public void sendInviteUserClickbackToken(String email, List<String> groups) throws MessagingException {
		TokenInviteUserClickback token = createInviteUserClickbackToken(email, groups);
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
		User user = klabUserManager.loadUserByUsername(email);
		if (user != null) {
			if (!AccountStatus.pendingActivation.equals(user.getAccountStatus())
					|| !email.equals(user.getEmail())) {
				throw new BadRequestException("An account with this username already exists.\n");
			}
		} 
		if (user != null) {
			sendInviteUserClickbackToken(email, groups);
		}
	}
	/*
	public ClickbackToken handleGroupsClickbackToken(String tokenString, Set<String> groups) {
		User user = klabUserManager.getLoggedInUser();
		ClickbackToken groupsClickbackToken = tokenRepository
			.findByTokenString(tokenString)
			.map(ClickbackToken.class::cast)
			.filter(token -> token.getClickbackAction().equals(ClickbackAction.groups))
			.filter(token -> token.getPrincipal().equals(user.getUsername()))
			.orElseThrow(() -> new GroupRequestTokenFailedException("Token no longer valid"));
		//lets login to the security context
		SecurityContextHolder.getContext().setAuthentication(groupsClickbackToken);
		klabUserManager.getLoggedInAuthentication();
		Collection<String> available = klabGroupService.getGroupNames();
		//groups.addAll(user.getGroups());
		if(!available.containsAll(groups)) {
			throw new BadRequestException("A Group was included that does not exist in the database");
		}
		klabUserManager.addUserGroupsFromNames(user.getUsername(), groups, null);
		Task task = taskService.getTaskByToken(GroupRequestTask.class, groupsClickbackToken).get();
		taskService.changeTaskStatus(task.getId(), TaskStatus.accepted);
		deleteToken(tokenString);
		return groupsClickbackToken;
	}
	*/
	public TokenClickback handleInviteClickbackToken(String tokenString, List<String> groups) {
		TokenClickback inviteClickToken = tokenRepository
				.findByTokenString(tokenString)
				.map(TokenClickback.class::cast)
				.filter(token -> token.getClickbackAction().equals(ClickbackAction.invite))
				.orElseThrow(IllegalArgumentException::new);
		SecurityContextHolder.getContext().setAuthentication(inviteClickToken);
		klabUserManager.getLoggedInAuthentication();
		User user = klabUserManager.getLoggedInUser();
		klabUserManager.setUserGroupsFromNames(user.getUsername(), groups, null);
		deleteToken(tokenString);
		return inviteClickToken;
	}

	public void sendLostPasswordToken(String username) {
		try {
			User user = klabUserManager.loadUserByUsername(username);
			if (user != null) {
				if(klabUserManager.ldapUserExists(user.getUsername(), user.getEmail())) {
					TokenClickback clickbackToken = createClickbackToken(username, TokenLostPasswordClickback.class);
					emailManager.sendLostPasswordEmail(user.getEmail(), clickbackToken.getCallbackUrl());
				} else {
					TokenClickback clickbackToken = createClickbackToken(username, TokenVerifyAccountClickback.class);
					emailManager.sendNewUser(user.getEmail(), username, clickbackToken.getCallbackUrl());
				}
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
				//throw new KlabException("Username was not found");
			}
		} catch (UsernameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		} catch (MessagingException e) {
			throw new KlabException("There was a problem sending the Lost Password Email.  Contact System Admin.");
		}
	}

	public LoginResponse authenticate(String username, String password) {
		try {
			TokenAuthentication token = getAuthenticationToken(username, password);
			ProfileResource profile = klabUserManager.getLoggedInUserProfile();
			LoginResponse loginResponse = new LoginResponse(token, profile);
			return loginResponse;
		} catch (AuthenticationException e) {
			return new LoginResponse();
		}
	}
	
	public LogoutResponse logout(String username, String token) {
		try {
			User user = klabUserManager.getLoggedInUser();
			if (user.getUsername().equals(username)) {
				deleteToken(token);
				LogoutResponse resp = new LogoutResponse(username);
				return resp;
			} else {
				return new LogoutResponse();
			}
		} catch  (UsernameNotFoundException e){
			return new LogoutResponse();
		}
	}

}
