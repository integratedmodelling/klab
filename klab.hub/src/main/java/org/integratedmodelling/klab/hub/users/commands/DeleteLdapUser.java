package org.integratedmodelling.klab.hub.users.commands;

import org.integratedmodelling.klab.hub.users.LdapUserAdapter;
import org.integratedmodelling.klab.hub.users.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

public class DeleteLdapUser implements UserCommand {
	
	private User user;
	private LdapUserDetailsManager ldapUserDetailsManager;

	public DeleteLdapUser(User user,
			LdapUserDetailsManager ldapUserDetailsManager) {
		this.ldapUserDetailsManager = ldapUserDetailsManager;
		this.user = user;
	}

	@Override
	public User execute() {
		UserDetails ldapUser = new LdapUserAdapter(user).convert();
		ldapUserDetailsManager.deleteUser(ldapUser.getUsername());
		return user;
	}

}
