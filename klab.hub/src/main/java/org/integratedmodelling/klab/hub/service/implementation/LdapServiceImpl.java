package org.integratedmodelling.klab.hub.service.implementation;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.integratedmodelling.klab.hub.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
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

	@Override
	public List<JSONObject> findAllGroups() {
		return ldapTemplate.search(query().where("objectclass").is("groupOfUniqueNames"), new GroupContextMapper());
	}

	@Override
	public List<String> findAllUsers() {
		return ldapTemplate.search(query().where("objectclass").is("person"), new AttributesMapper<String>() {
			public String mapFromAttributes(Attributes attrs) throws NamingException {

				return attrs.get("cn").get().toString();
			}
		});
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

	@Override
	public Optional<UserDetails> getUserByCn(String cn) {
			Optional<UserDetails> user = Optional.of(ldapUserDetailsManager.loadUserByUsername(cn));
			return user;
	}

	@Override
	public void updateUser(UserDetails user) {
		ldapUserDetailsManager.updateUser(user);
	}
	
	@Override
	public void deleteUser(String username) {
		ldapUserDetailsManager.deleteUser(username);
	}
	
	@Override
	public void createUser(UserDetails user) {
		ldapUserDetailsManager.createUser(user);
	}

	@Override
	public Name buildDn(String username ) {
	    return LdapNameBuilder.newInstance()
	            .add("ou", "imusers")
	            .add("uid", username)    
	            .build();
	}
}
