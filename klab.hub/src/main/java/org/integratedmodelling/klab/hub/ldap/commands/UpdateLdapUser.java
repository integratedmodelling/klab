package org.integratedmodelling.klab.hub.ldap.commands;

import org.integratedmodelling.klab.hub.ldap.adapters.LdapUserAdapter;
import org.integratedmodelling.klab.hub.users.commands.UserCommand;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;

public class UpdateLdapUser implements UserCommand {
	
	private User user;
	private LdapUserDetailsManager ldapUserDetailsManager;
	
	
	public UpdateLdapUser(User user, LdapUserDetailsManager ldapUserDetailsManager) {
		super();
		this.user = user;
		this.ldapUserDetailsManager = ldapUserDetailsManager;
	}
	
	@Override
	public User execute() {
		UserDetails ldapUser = new LdapUserAdapter(user).convert();
		ldapUserDetailsManager.updateUser(ldapUser);
		return user;
	}

}
