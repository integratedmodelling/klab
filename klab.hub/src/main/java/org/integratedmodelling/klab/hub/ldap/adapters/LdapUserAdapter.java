package org.integratedmodelling.klab.hub.ldap.adapters;

import javax.naming.Name;

import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

public class LdapUserAdapter {
	
	private User user;

	public LdapUserAdapter(User user) {
		super();
		this.user = user;
	}
	
	public Name buildDn(String username ) {
	    return LdapNameBuilder.newInstance()
	            .add("ou", "imusers")
	            .add("uid", username)    
	            .build();
	}
	
	public UserDetails convert() {
		InetOrgPerson.Essence person = new InetOrgPerson.Essence();
		person.setCn(new String[] { this.user.getUsername() });
		person.setDn(buildDn(this.user.getUsername()).toString());
		person.setUid(this.user.getUsername());
		person.setUsername(this.user.getUsername());
		person.setPassword(this.user.getPasswordHash());
		person.setMail(this.user.getEmail());
		person.setDisplayName(this.user.getDisplayName());
		if (StringUtils.isEmpty(this.user.getLastName())) {
			person.setSn("<unknown>");
		} else {
			person.setSn(this.user.getLastName());
		}
		person.setAuthorities(this.user.getAuthorities());
		LdapUserDetails userDetails = person.createUserDetails();
		return userDetails;
	}
}
