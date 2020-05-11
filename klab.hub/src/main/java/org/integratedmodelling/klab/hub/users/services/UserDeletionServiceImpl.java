package org.integratedmodelling.klab.hub.users.services;

import java.util.List;

import org.integratedmodelling.klab.hub.api.AuthProvider;
import org.integratedmodelling.klab.hub.api.DeletedUser;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.hub.commands.DeleteUser;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.exception.DeletedUserNotFoundException;
import org.integratedmodelling.klab.hub.repository.DeletedUserRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
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
		User user = userRepository.findByNameIgnoreCase(username)
				.filter(u -> u.getAccountStatus() != AccountStatus.deleted)
				.orElseThrow(() -> new BadRequestException("User is not present or already deleted"));
		
		if(user.getProvider() == AuthProvider.local) {
			//new DeleteLdapUser(user, ldapUserDetailsManager).execute();
			//we will refrain from deleting ldap users as of now
		}
		new DeleteUser(user, userRepository, deletedUserRepository).execute();
		
	}


	@Override
	public List<DeletedUser> getDeletedUsers() {
		return deletedUserRepository.findAll();
	}


	@Override
	public DeletedUser getDeletedUser(String username) {
		DeletedUser user = deletedUserRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new DeletedUserNotFoundException(username));
		return user;
	}


	@Override
	public void deleteUserLdap(String username) {
		DeletedUser user = deletedUserRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new DeletedUserNotFoundException(username));
		ldapUserDetailsManager.deleteUser(user.getUsername());
		
	}

}
