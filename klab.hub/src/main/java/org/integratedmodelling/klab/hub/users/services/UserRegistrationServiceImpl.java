package org.integratedmodelling.klab.hub.users.services;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.integratedmodelling.klab.hub.agreements.services.AgreementService;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.commands.CreateLdapUser;
import org.integratedmodelling.klab.hub.commands.CreatePendingUser;
import org.integratedmodelling.klab.hub.commands.CreateUserWithRolesAndStatus;
import org.integratedmodelling.klab.hub.commands.SetUserPasswordHash;
import org.integratedmodelling.klab.hub.commands.UpdateLdapUser;
import org.integratedmodelling.klab.hub.commands.UpdateUser;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.exception.UserEmailExistsException;
import org.integratedmodelling.klab.hub.exception.UserExistsException;
import org.integratedmodelling.klab.hub.listeners.HubEventPublisher;
import org.integratedmodelling.klab.hub.listeners.NewUserAdded;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService{
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private LdapTemplate ldapTemplate;
	private LdapUserDetailsManager ldapUserDetailsManager;
	private HubEventPublisher<NewUserAdded> publisher;
	
	public UserRegistrationServiceImpl(UserRepository userRepository, 
			PasswordEncoder passwordEncoder, 
			LdapTemplate ldapTemplate,
			LdapUserDetailsManager ldapUserDetailsManager,
			HubEventPublisher<NewUserAdded> publisher, AgreementService agreementService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.ldapTemplate = ldapTemplate;
		this.ldapUserDetailsManager = ldapUserDetailsManager;
		this.publisher = publisher;		
	}


	@Override
	public User registerNewUser(String username, String email, Agreement agreement) {
		Optional<User> pendingUser = checkIfUserPending(username, email);
		if (pendingUser.isPresent()) {
			return pendingUser.get();
		} else {
			User newUser = new User();			
			newUser.setUsername(username);
			newUser.setEmail(email);
			newUser.getAgreements().add(agreement);
			newUser = new CreatePendingUser(userRepository, newUser).execute();			
			publisher.publish(new NewUserAdded(new Object(), newUser));
			return newUser;
		}
		
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
		boolean existInMongo = userRepository.
				findByNameIgnoreCaseOrEmailIgnoreCase(username, email)
				.isPresent();
		
		if(existInMongo) {
			boolean usernameExists = userRepository
				.findByNameIgnoreCase(username)
				.isPresent();
			
			boolean emailExists = userRepository
					.findByEmailIgnoreCase(email)
					.isPresent();
			
			boolean ldapExists = ldapUserExists(username, email);
			
			if(ldapExists && usernameExists && emailExists) {
				throw new UserExistsException(username);
			}
			
			if(!ldapExists && usernameExists && emailExists) {
				//we need to return a user who has not activated there account and will be asked to
				//reactivate with an email.
				Optional<User> pendingUser = userRepository.findByNameIgnoreCase(username)
						.filter(u -> u.getAccountStatus().equals(AccountStatus.pendingActivation));
				pendingUser
					.orElseThrow(()-> new BadRequestException("User exists but has not set a password. "
							+ "Please make a forgot password request."));
				return pendingUser;
			}
			
			if(usernameExists != emailExists) {
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
			.filter(user -> 
				user.getAccountStatus().equals(AccountStatus.pendingActivation) |
				user.getAccountStatus().equals(AccountStatus.verified))
			.orElseThrow(() -> 
				new BadRequestException("User is already Activated or does not exist"));
		
		pendingUser.setAccountStatus(AccountStatus.verified);
		
		pendingUser = new UpdateUser(pendingUser, userRepository).execute();
		return pendingUser;
	}

	@Override
	public User setPassword(String username, String password, String confirm) {
		if (confirmPassword(password, confirm)) {
			User user = userRepository.findByNameIgnoreCase(username)
				.filter(u -> 
					u.getAccountStatus().equals(AccountStatus.verified) |
					u.getAccountStatus().equals(AccountStatus.active))
				.orElseThrow(() ->
					new BadRequestException("User not active or verified"));
			
			if(user.getAccountStatus().equals(AccountStatus.verified) |
					!ldapUserExists(user.getUsername(), user.getEmail())) {
				user = new SetUserPasswordHash(user, password, passwordEncoder).execute();
				user= new CreateLdapUser(user, ldapUserDetailsManager).execute();
				user.setAccountStatus(AccountStatus.active);
				user = new UpdateUser(user, userRepository).execute();
				return user;
			} else {
				user = new SetUserPasswordHash(user, password, passwordEncoder).execute();
				user= new UpdateLdapUser(user, ldapUserDetailsManager).execute();
				user = new UpdateUser(user, userRepository).execute();
				return user;
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
	
	private boolean ldapUserExists(String username, String email) {
		LdapQuery userNameQuery = query()
				.where("objectclass").is("person")
				.and("uid").is(username);
		List<Object> personByName = ldapTemplate.search(userNameQuery, new UserAttributesMapper());
		
		LdapQuery userEmailQuery = query()
				.where("objectclass").is("person")
				.and("mail").is(email);
		List<Object> personByEmail = ldapTemplate.search(userEmailQuery, new UserAttributesMapper());
		
		if (!personByEmail.equals(personByName)) {
			throw new BadRequestException("Username or email address already in use.");
		} 
		
		if (personByEmail.isEmpty() && personByName.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	private class UserAttributesMapper implements AttributesMapper<Object> {
		public HashMap<String, String> mapFromAttributes(Attributes attributes) throws NamingException {
			HashMap<String, String> userAttributes = new HashMap<String, String>();
			try {
				userAttributes.put("uid", attributes.get("uid").get().toString());
				userAttributes.put("mail", attributes.get("mail").get().toString());
			} catch (NamingException e){
				return new HashMap<String, String>();
			}
			return userAttributes;
		}
	}

}
