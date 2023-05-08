package org.integratedmodelling.klab.hub.users.services;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.stereotype.Service;
import org.integratedmodelling.klab.auth.Role;

/*
 * This is needed for login authentication.  It is used to load the user and
 * populate the password hash from the ldap.  It is than compared to password
 * in the request.
 */
@Service
@Primary
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
			    
	    User user = userRepository.findByNameIgnoreCaseOrderByNameAsc(username).get();
	    
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
