package org.integratedmodelling.klab.hub.users.services;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.DeletedUserRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.AuthProvider;
import org.integratedmodelling.klab.hub.users.User;
import org.integratedmodelling.klab.hub.users.User.AccountStatus;
import org.integratedmodelling.klab.hub.users.commands.DeleteUser;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

public class UserDeletionServiceImpl implements UserDeletionService{
	
	private UserRepository userRepository;
	private LdapUserDetailsManager ldapUserDetailsManager;
	private DeletedUserRepository deletedUserRepository;
	
	public UserDeletionServiceImpl(UserRepository userRepository, LdapUserDetailsManager ldapUserDetailsManager,
			DeletedUserRepository deletedUserRepository) {
		super();
		this.userRepository = userRepository;
		this.ldapUserDetailsManager = ldapUserDetailsManager;
		this.deletedUserRepository = deletedUserRepository;
	}
	
	
	@Override
	public void deleteUser(String username) {
		User user = userRepository.findByUsernameIgnoreCase(username)
				.filter(u -> u.getAccountStatus() != AccountStatus.deleted)
				.orElseThrow(() -> new BadRequestException("User is not present or already deleted"));
		
		if(user.getProvider() == AuthProvider.local) {
			//new DeleteLdapUser(user, ldapUserDetailsManager).execute();
			//we will refrain from deleting ldap users as of now
		}
		new DeleteUser(user, userRepository, deletedUserRepository).execute();
		
	}

}
