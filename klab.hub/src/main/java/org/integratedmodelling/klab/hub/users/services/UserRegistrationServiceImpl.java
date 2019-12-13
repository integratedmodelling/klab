package org.integratedmodelling.klab.hub.users.services;

import java.util.Optional;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.exception.UserExistsException;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.service.LdapService;
import org.integratedmodelling.klab.hub.users.User;
import org.integratedmodelling.klab.hub.users.User.AccountStatus;
import org.integratedmodelling.klab.hub.users.commands.CreateLdapUser;
import org.integratedmodelling.klab.hub.users.commands.CreatePendingUser;
import org.integratedmodelling.klab.hub.users.commands.SetUserPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService{
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private UserRepository userRepository;
	private LdapService ldapService;

	public UserRegistrationServiceImpl(UserRepository userRepository, LdapService ldapService) {
		super();
		this.userRepository = userRepository;
		this.ldapService = ldapService;
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
			return newUser;
		}
		
	}
	
	private Optional<User> checkIfUserPending(String username, String email) {
		boolean existInMongo = userRepository.
				findByUsernameIgnoreCaseOrEmailIgnoreCase(username, email)
				.isPresent();
		
		if(existInMongo) {
			boolean usernameExists = userRepository
				.findByUsernameIgnoreCase(username)
				.isPresent();
			
			boolean emailExists = userRepository
					.findByEmailIgnoreCase(email)
					.isPresent();
			
			boolean ldapExists = ldapService.userExists(username, email);
			
			if(ldapExists && usernameExists && emailExists) {
				throw new UserExistsException(username);
			}
			
			if(!ldapExists && usernameExists && emailExists) {
				//we need to return a user who has not activated there account and will be asked to
				//reactivate with an email.
				Optional<User> pendingUser = userRepository.findByUsernameIgnoreCase(username)
						.filter(u -> u.getAccountStatus().equals(AccountStatus.pendingActivation));
				pendingUser
					.orElseThrow(()-> new BadRequestException("User Exists but has not set a password. "
							+ "Please make a forgot password request."));
				return pendingUser;
			}
			
			if(usernameExists != emailExists) {
				throw new UserExistsException(username);
			}
			
			throw new UserExistsException("How did we get here?"); 
				
		} else {
			return Optional.empty();
		}
	}

	@Override
	public User activateNewUser(String username) {
		User pendingUser = userRepository.findByUsernameIgnoreCase(username)
			.filter(user -> 
				user.getAccountStatus().equals(AccountStatus.pendingActivation) |
				user.getAccountStatus().equals(AccountStatus.verified))
			.orElseThrow(() -> 
				new BadRequestException("User is already Activated or does not exist"));
		
		pendingUser.setAccountStatus(AccountStatus.verified);
		return userRepository.save(pendingUser);
	}

	@Override
	public User setNewPassword(String username, String password, String confirm) {
		if (confirmPassword(password, confirm)) {
			User user = userRepository.findByUsernameIgnoreCase(username)
				.filter(u -> 
					u.getAccountStatus().equals(AccountStatus.verified) |
					u.getAccountStatus().equals(AccountStatus.active))
				.orElseThrow(() ->
					new BadRequestException("User not active or verified"));
			
			if(user.getAccountStatus().equals(AccountStatus.verified) |
					!ldapService.userExists(user.getUsername(), user.getEmail())) {
				user = new SetUserPassword(user, password, passwordEncoder).execute();
				new CreateLdapUser(ldapService, user);
				//user.setPasswordHash(PasswordEncoder);
				//new CreateLdapUser(ldapService, user, password).execute();
			}
		} else {
			throw new BadRequestException("Cannot set password, please confirm that the password and confirmation match");
		}
	}
	
	private boolean confirmPassword(String password, String confirm) {
		if(password.length() < 8 || password.length() > 24 || password.contains(" ")) {
			throw new BadRequestException("Password not accepted");
		}
		return password.equals(confirm);
	}
	

}
