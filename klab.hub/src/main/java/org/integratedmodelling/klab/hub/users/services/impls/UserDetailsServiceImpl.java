package org.integratedmodelling.klab.hub.users.services.impls;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.Role;
import org.integratedmodelling.klab.hub.users.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private UserRepository userRepository;
	
	private LdapUserDetailsManager ldapUserDetailsManager;
	
	public UserDetailsServiceImpl(UserRepository userRepository, 
			LdapUserDetailsManager ldapUserDetailsManager) {
		super();
		this.userRepository = userRepository;
		this.ldapUserDetailsManager = ldapUserDetailsManager;
	}


	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsernameIgnoreCase(username)
			.orElseThrow(() -> new UsernameNotFoundException("Unable to find user - " + username));
		
		UserDetails ldapUser = ldapUserDetailsManager.loadUserByUsername(username);
		
		
		Set<Role> roles = new HashSet<>();
		
		ldapUser.getAuthorities().forEach(role -> roles.add(Role.valueOf(role.getAuthority())));
		
		user.setPasswordHash(ldapUser.getPassword());
		if (!roles.isEmpty()) {
			user.setRoles(roles);
		}
		
		if(user.getRoles().isEmpty()) {
			//can not have a user with no role, or else what are we?
			throw new UsernameNotFoundException("Unable to find user  " + username + " roles!");
		}
		
		return user;
	}

}
