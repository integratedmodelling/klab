package org.integratedmodelling.klab.hub.users.exceptions;

import org.springframework.security.core.AuthenticationException;

public class LoginFailedExcepetion extends AuthenticationException{

	private static final long serialVersionUID = 6557424992980919957L;

	public LoginFailedExcepetion(String user) {
		super(String.format("User with name %s not found or password incorrect.", user));
	}

}
