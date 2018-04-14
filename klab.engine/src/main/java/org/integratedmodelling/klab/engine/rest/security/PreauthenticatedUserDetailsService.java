package org.integratedmodelling.klab.engine.rest.security;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.engine.rest.controllers.base.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This service gets the output of {@link PreauthenticationFilter} (unless null)
 * and turns it into the correspondent user details.
 * 
 * @author Ferd
 *
 */
@Service
public class PreauthenticatedUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<String> roles = new ArrayList<>();
		boolean authorize = false;
		String token = "";

		if (Auth.ANONYMOUS_USER_ID.equals(username)) {
			authorize = true;
			roles.add(Roles.ANONYMOUS);
		} else if (Auth.LOCAL_USER_ID.equals(username)) {
			authorize = true;
			roles.add(Roles.ANONYMOUS);
			roles.add(Roles.ADMIN);
		} else {
			
			// TODO
			
		}
		
		if (!authorize) {
			throw new UsernameNotFoundException("User '" + username + "' not found.");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return new User(username, token, authorities);
	}

}
