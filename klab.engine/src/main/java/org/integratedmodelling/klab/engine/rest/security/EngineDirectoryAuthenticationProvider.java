package org.integratedmodelling.klab.engine.rest.security;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IUserCredentials;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * This is the auth provider for regularly logged in engine users (using
 * username/password login). Uses the authenticate method in the engine, which
 * should reflect configuration and either redirect to the k-network or use
 * locally configured directories.
 * 
 * @author Ferd
 *
 */
@Component
public class EngineDirectoryAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		Engine engine = Authentication.INSTANCE.getAuthenticatedIdentity(Engine.class);
		IEngineUserIdentity user = engine.authenticateUser(new IUserCredentials() {

			@Override
			public String getUsername() {
				return username;
			}

			@Override
			public String getPassword() {
				return authentication.getCredentials().toString();
			}
		});

		if (user != null && !(user instanceof UserDetails)) {
			throw new KlabInternalErrorException(
					"internal: user was authenticated successfully but is not understandable by the security stack");
		}

		return (UserDetails) user;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// TODO
	}
}