package org.integratedmodelling.klab.hub.users.commands;

import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.klab.hub.users.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

public abstract class LdapUserCommand implements UserCommand {
	
	private UserDetails convertUsertoLdapUser(User user) {
		InetOrgPerson.Essence person = new InetOrgPerson.Essence();
		person.setCn(new String[] { user.getUsername() });
		person.setDn(ldapService.buildDn(user.getUsername()).toString());
		person.setUid(user.getUsername());
		person.setUsername(user.getUsername());
		person.setPassword(user.getPasswordHash());
		person.setMail(user.getEmail());
		person.setDisplayName(user.getDisplayName());
		if (StringUtils.isEmpty(user.getLastName())) {
			person.setSn("<unknown>");
		} else {
			person.setSn(user.getLastName());
		}
		person.setAuthorities(user.getAuthorities());
		LdapUserDetails userDetails = person.createUserDetails();
		return userDetails;
	}
	
}
