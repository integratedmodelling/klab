package org.integratedmodelling.klab.hub.ldap.commands;

import org.integratedmodelling.klab.hub.ldap.adapters.LdapUserAdapter;
import org.integratedmodelling.klab.hub.users.commands.UserCommand;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

public class CreateLdapUser implements UserCommand {
	
	private User user;
	private LdapUserDetailsManager ldapUserDetailsManager;

	public CreateLdapUser(User user,
			LdapUserDetailsManager ldapUserDetailsManager) {
		this.ldapUserDetailsManager = ldapUserDetailsManager;
		this.user = user;
	}

	@Override
	public User execute() {
		UserDetails ldapUser = new LdapUserAdapter(user).convert();
		ldapUserDetailsManager.createUser(ldapUser);
		return user;
	}

}
