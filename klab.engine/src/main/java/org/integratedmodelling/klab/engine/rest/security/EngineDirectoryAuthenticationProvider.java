package org.integratedmodelling.klab.engine.rest.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * This is the auth provider for regularly logged in engine users (using
 * username/password login). Should reflect configuration and either redirect to
 * k-network or use locally configured directories.
 * 
 * @author Ferd
 *
 */
@Component
public class EngineDirectoryAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		System.out.println("XIO PETARDO auth " + username);

		// UserIdentity user = null; // userDao.getUser(username);
		// if (user == null) {// should have proper handling of Exception
		// throw new UsernameNotFoundException("User '" + username + "' not found.");
		// }

		List<GrantedAuthority> roles = new ArrayList<>();
		// for (String role : user.getRoles()) {
		// roles.add(new SimpleGrantedAuthority(role));
		// }
		UserDetails details = new org.springframework.security.core.userdetails.User(username, "Porchiddio", roles);
		return details;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// TODO
	}
}