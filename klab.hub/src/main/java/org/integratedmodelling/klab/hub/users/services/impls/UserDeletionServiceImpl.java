package org.integratedmodelling.klab.hub.users.services.impls;

import java.util.List;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.DeletedUserRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.AuthProvider;
import org.integratedmodelling.klab.hub.users.DeletedUser;
import org.integratedmodelling.klab.hub.users.User;
import org.integratedmodelling.klab.hub.users.User.AccountStatus;
import org.integratedmodelling.klab.hub.users.commands.DeleteUser;
import org.integratedmodelling.klab.hub.users.services.UserDeletionService;
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
		User user = userRepository.findByUsernameIgnoreCase(username)
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
				.orElseThrow(() -> new BadRequestException("No deleted user by that username found"));
		return user;
	}

}
