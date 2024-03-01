package org.integratedmodelling.klab.hub.service.implementation;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

@Service
public class LdapServiceImpl implements LdapService{
	
	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired
	private LdapUserDetailsManager ldapUserDetailsManager;

	public List<JSONObject> findAllGroups() {
		return ldapTemplate.search(query().where("objectclass").is("groupOfUniqueNames"), new GroupContextMapper());
	}

	public List<String> findAllUsers() {
		return ldapTemplate.search(query().where("objectclass").is("person"), new AttributesMapper<String>() {
			public String mapFromAttributes(Attributes attrs) throws NamingException {
				return attrs.getAll().toString();
			}
		});
	}
	
	public boolean userExists(String username, String email) {
		LdapQuery userNameQuery = query()
				.where("objectclass").is("person")
				.and("uid").is(username);
		List<Object> personByName = ldapTemplate.search(userNameQuery, new UserAttributesMapper());
		
		LdapQuery userEmailQuery = query()
				.where("objectclass").is("person")
				.and("mail").is(email);
		List<Object> personByEmail = ldapTemplate.search(userEmailQuery, new UserAttributesMapper());
		
		if (!personByEmail.equals(personByName)) {
			throw new BadRequestException("Username or email address already in use.");
		} 
		
		if (personByEmail.isEmpty() && personByName.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	private static class GroupContextMapper extends AbstractContextMapper<JSONObject> {
		public JSONObject doMapFromContext(DirContextOperations context) {
			Set<String> membersDn = new HashSet<>();
			Object[] members = context.getObjectAttributes("uniqueMember");
			for (Object member : members) {
				String memberDn = LdapNameBuilder.newInstance(String.valueOf(member)).build().toString();
				membersDn.add(memberDn);
			}
			JSONObject group = new JSONObject();
			group.appendField("dn", context.getStringAttribute("dn"));
			group.appendField("name", context.getStringAttribute("cn"));
			group.appendField("members", members);
			return group;
		}
	}

	public Optional<UserDetails> getUserByCn(String cn) {
			Optional<UserDetails> user = Optional.of(ldapUserDetailsManager.loadUserByUsername(cn));
			return user;
	}

	public void updateUser(UserDetails user) {
		ldapUserDetailsManager.updateUser(user);
	}
	
	public void deleteUser(String username) {
		ldapUserDetailsManager.deleteUser(username);
	}
	
	public void createUser(UserDetails user) {
		ldapUserDetailsManager.createUser(user);
	}

	public Name buildDn(String username ) {
	    return LdapNameBuilder.newInstance()
	            .add("ou", "imusers")
	            .add("uid", username)    
	            .build();
	}
	
	private class UserAttributesMapper implements AttributesMapper<Object> {
		public HashMap<String, String> mapFromAttributes(Attributes attributes) throws NamingException {
			HashMap<String, String> userAttributes = new HashMap<String, String>();
			try {
				userAttributes.put("uid", attributes.get("uid").get().toString());
				userAttributes.put("mail", attributes.get("mail").get().toString());
			} catch (NamingException e){
				return new HashMap<String, String>();
			}
			return userAttributes;
		}
	}
	
	@Override
	public void updateUserEmailAddress(String username, String newEmailAddress) {
	    Name dn = buildDn(username);
	    ModificationItem modificationItem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("mail", newEmailAddress));
	    ldapTemplate.modifyAttributes(dn, new ModificationItem[] { modificationItem });
	}
}
