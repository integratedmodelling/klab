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
import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.exception.ActivationTokenFailedException;
import org.integratedmodelling.klab.hub.exception.AuthenticationFailedException;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.exception.ChangePasswordTokenFailedException;
import org.integratedmodelling.klab.hub.exception.GroupRequestTokenFailedException;
import org.integratedmodelling.klab.hub.exception.TokenGenerationException;
import org.integratedmodelling.klab.hub.exception.UserEmailExistsException;
import org.integratedmodelling.klab.hub.exception.UserExistsException;
import org.integratedmodelling.klab.hub.payload.LoginResponse;
import org.integratedmodelling.klab.hub.payload.LogoutResponse;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.service.KlabGroupService;
import org.integratedmodelling.klab.hub.tasks.GroupRequestTask;
import org.integratedmodelling.klab.hub.tasks.Task;
import org.integratedmodelling.klab.hub.tasks.TaskStatus;
import org.integratedmodelling.klab.hub.tasks.services.TaskService;
import org.integratedmodelling.klab.hub.tokens.VerifyAccountClickbackToken;
import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.ChangePasswordClickbackToken;
import org.integratedmodelling.klab.hub.tokens.ClickbackAction;
import org.integratedmodelling.klab.hub.tokens.ClickbackToken;
import org.integratedmodelling.klab.hub.tokens.GroupsClickbackToken;
import org.integratedmodelling.klab.hub.tokens.InviteUserClickbackToken;
import org.integratedmodelling.klab.hub.tokens.LostPasswordClickbackToken;
import org.integratedmodelling.klab.hub.tokens.NewUserClickbackToken;
import org.integratedmodelling.klab.hub.tokens.VerifyEmailClickbackToken;
import org.integratedmodelling.klab.hub.users.GroupEntry;
import org.integratedmodelling.klab.hub.users.ProfileResource;
import org.integratedmodelling.klab.hub.users.Role;
import org.integratedmodelling.klab.hub.users.User;
import org.integratedmodelling.klab.hub.users.User.AccountStatus;
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
	private KlabGroupService klabGroupService;
	
	@Autowired
	private TaskService taskService;

	@Autowired
	private EmailManager emailManager;

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
		result.setCallbackUrl(linkConfig);
		result.setAuthenticated(true);
		tokenRepository.save(result);
		return result;
	}
	
	public ClickbackToken createClickbackToken(String username, String parentToken, Class<? extends ClickbackToken> tokenType) {
		ClickbackToken result = null;
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

	public AuthenticationToken getAuthenticationToken(String username, String password) {
		AuthenticationToken result = null;
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
			User user = klabUserManager.getLoggedInUser();
			klabUserManager.updateLastLogin(user);
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
			User user = klabUserManager.getUser(profile.getUsername());
			klabUserManager.updateLastLogin(user);
		}
		return result;
	}

	public ClickbackToken createNewUser(String username, String email) throws TokenGenerationException {
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
					ClickbackToken clickbackToken = resendActivateAccountClickback(user);
					return clickbackToken;
				}
			}
		}
		ClickbackToken clickbackToken = createNewUserAndToken(username, email);
		return clickbackToken;
	}
	
	
	private ClickbackToken createNewUserAndToken(String username, String email) {
		ClickbackToken clickbackToken = null;
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setEmail(email);
		newUser.setRoles(Arrays.asList(Role.ROLE_USER));
		newUser.setRegistrationDate();
		
		try {
			klabUserManager.createPendingKlabUser(newUser);
			clickbackToken = createClickbackToken(username, VerifyAccountClickbackToken.class);
		} catch (UserExistsException | UserEmailExistsException e) {
			throw new TokenGenerationException(e.getMessage(), e);
		}
		emailManager.sendNewUser(email, username, clickbackToken.getCallbackUrl());
		return clickbackToken;
	}
	
	private ClickbackToken resendActivateAccountClickback(User user) {
		if (!AccountStatus.pendingActivation.equals(user.getAccountStatus())) {
			throw new BadRequestException("An account with this username already exists.\n"
					+ "User may request a new password for their account.");
		}
		List<AuthenticationToken> tokens = tokenRepository.findByUsername(user.getUsername());
		for (AuthenticationToken token : tokens) {
			tokenRepository.delete(token);
		}
		ClickbackToken clickbackToken = createClickbackToken(user.getUsername(), VerifyAccountClickbackToken.class);
		emailManager.sendNewUser(user.getEmail(), user.getUsername(), clickbackToken.getCallbackUrl());
		return clickbackToken;
	}

	public ClickbackToken createNewUserWithGroups(String username, String email, String tokenString, List<String> groups) {
		ClickbackToken token = createNewUser(username, email);
		handleInviteClickbackToken(tokenString, groups);
		return token;		
	}
	
	public AuthenticationToken updateOAuthUserWithGroups(String tokenString, String groupToken,
			List<String> groups) {
		AuthenticationToken token = handleToken(tokenString);
		handleInviteClickbackToken(groupToken, groups);
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
	
	public LostPasswordClickbackToken createlostPasswordClickbackToken(String username) {
		LostPasswordClickbackToken token = (LostPasswordClickbackToken) createClickbackToken(username, LostPasswordClickbackToken.class);
		return token;
	}
	
	public ClickbackToken handleVerificationToken(String userId, String tokenString) {
		//lets only find a clickback token that invited the user
		ClickbackToken activationToken = tokenRepository
			.findByTokenString(tokenString)
			.map(ClickbackToken.class::cast)
			.filter(token -> token.getClickbackAction().equals(ClickbackAction.activate))
			.filter(token -> token.getPrincipal().equals(userId))
			.orElseThrow(() -> new ActivationTokenFailedException("Activation Token no longer active"));
		//lets login to the security context
		SecurityContextHolder.getContext().setAuthentication(activationToken);
		//verify account 
		verifyAccount();
		//deleteToken(tokenString);
		
		if(klabUserManager.getLoggedInUser().getProvider() == null) {
			return createClickbackToken(klabUserManager.getLoggedInUsername(), tokenString, NewUserClickbackToken.class);
		} else {
			activationToken.setDetails("Error");
			return activationToken;
		}
	}
	
	public void verifyAccount() {
		// the user is already authenticated via clickback token header, but we want
		// to do some extra verification because they aren't supplying a password
		Authentication authentication = klabUserManager.getLoggedInAuthentication();
		if (!(authentication instanceof VerifyAccountClickbackToken)) {
			throw new AuthenticationFailedException("The token submitted was not valid for activating an account.");
		}
		// this will also verify that the account started in pendingActivation
		klabUserManager.activateUser(klabUserManager.getLoggedInUsername());
	}
	
	
	public ClickbackToken handleChangePasswordToken(String userId, String tokenString, String newPassword) {
		//lets make sure that this token is for setting the password
		ClickbackToken changePasswordToken = tokenRepository
				.findByTokenString(tokenString)
				.map(ClickbackToken.class::cast)
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
	
	public InviteUserClickbackToken createInviteUserClickbackToken(String email, List<String> groups) {
		InviteUserClickbackToken token = (InviteUserClickbackToken) createClickbackToken(email, InviteUserClickbackToken.class);
		token.setCallbackUrl(linkConfig);
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
		Task task = taskService.getGroupRequestTaskByToken(groupsClickbackToken);
		taskService.changeTaskStatus(task.getId(), TaskStatus.acceptedEmail);
		deleteToken(tokenString);
		return groupsClickbackToken;
	}
	
	public ClickbackToken handleInviteClickbackToken(String tokenString, List<String> groups) {
		ClickbackToken inviteClickToken = tokenRepository
				.findByTokenString(tokenString)
				.map(ClickbackToken.class::cast)
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
					ClickbackToken clickbackToken = createClickbackToken(username, LostPasswordClickbackToken.class);
					emailManager.sendLostPasswordEmail(user.getEmail(), clickbackToken.getCallbackUrl());
				} else {
					ClickbackToken clickbackToken = createClickbackToken(username, VerifyAccountClickbackToken.class);
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
			AuthenticationToken token = getAuthenticationToken(username, password);
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
