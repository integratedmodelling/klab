package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.ldap.commands.CreateLdapUser;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.security.oauth2.AuthProvider;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

public class CreateUserWithRolesAndStatus implements UserCommand {
	
	private UserRepository userRepository;
	private User user;
	private LdapUserDetailsManager ldapUserDetailsManager;

	public CreateUserWithRolesAndStatus(User user, 
			UserRepository userRepository, LdapUserDetailsManager ldapUserDetailsManager) {
		super();
		this.userRepository = userRepository;
		this.user = user;
		this.ldapUserDetailsManager = ldapUserDetailsManager;
	}

	@Override
	public User execute() {
		User newUser = userRepository.insert(user); 
		if (newUser.getProvider() != null && newUser.getProvider() != AuthProvider.local) {
			return newUser;
		}
		new CreateLdapUser(newUser, ldapUserDetailsManager).execute();
		return newUser;
		
	}

}
