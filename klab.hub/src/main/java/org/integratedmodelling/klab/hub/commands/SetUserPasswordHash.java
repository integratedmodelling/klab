package org.integratedmodelling.klab.hub.commands;

import org.integratedmodelling.klab.hub.api.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SetUserPasswordHash implements UserCommand {

	private User user;
	private String password;
	private PasswordEncoder passwordEncoder;

	public SetUserPasswordHash(User user, String password, PasswordEncoder passwordEncoder) {
		this.user = user;
		this.password = password;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User execute() {
		user.setPasswordHash(passwordEncoder.encode(password));
		return user;
	}

}
