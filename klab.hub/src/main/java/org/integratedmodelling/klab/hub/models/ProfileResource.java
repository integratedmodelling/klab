package org.integratedmodelling.klab.hub.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.integratedmodelling.klab.hub.models.User.AccountStatus;
import org.integratedmodelling.klab.rest.Group;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ProfileResource implements OAuth2User{

	public String id;
	
    @Size(max = Constraints.USERNAME_LENGTH)
    @Pattern(regexp = Constraints.USERNAME_PATTERN)
    public String username;

    @Email
    public String email;

	public String serverUrl;

    public String firstName;

    public String lastName;

    public String initials;

	public String address;

    public String jobTitle;

    public String phone;

    public String affiliation;

    public List<Role> roles = new ArrayList<>(); // LDAP security roles or OAuth

    public List<KlabGroup> groups = new ArrayList<>(); // research groups, etc. in web tool

    public boolean sendUpdates;

    public String comments;

    public DateTime registrationDate;

    public DateTime lastLogin;

	public DateTime lastEngineConnection;

    public AccountStatus accountStatus;
    
    public String Token;

    private Collection<? extends GrantedAuthority> authorities;
    
    private Map<String, Object> attributes;
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(username).append(email).append(serverUrl).append(firstName).append(lastName)
                .append(initials).append(address).append(jobTitle).append(phone).append(affiliation).append(groups)
                .append(sendUpdates).append(comments).append(accountStatus).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        ProfileResource other = (ProfileResource) obj;
        return new EqualsBuilder().append(username, other.username).append(email, other.email)
                .append(serverUrl, other.serverUrl).append(firstName, other.firstName).append(lastName, other.lastName)
                .append(initials, other.initials).append(address, other.address).append(jobTitle, other.jobTitle)
                .append(phone, other.phone).append(affiliation, other.affiliation).append(groups, other.groups)
                .append(sendUpdates, other.sendUpdates).append(comments, other.comments)
                .append(accountStatus, other.accountStatus).isEquals();
    }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
    public List<KlabGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<KlabGroup> groups) {
		this.groups = groups;
	}

	public void setToken(String token) {
		this.Token = token;
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        this.roles.add(Role.ROLE_USER);
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    public DateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(DateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public List<Group> getGroupsList() {
		List<Group> listOfGroups = new ArrayList<>();
		for (KlabGroup grp : this.getGroups()) {
			if(grp != null) {
				Group group = new Group();
				group.setId(grp.getId());
				group.setProjectUrls(grp.getProjectUrls());
				group.setSshKey(grp.getSshKey());
				group.setObservables(grp.getObservables());
				group.setWorldview(grp.getWorldview());
				listOfGroups.add(group);
			}
		}
		return listOfGroups;
	}
	
	public ProfileResource getSafeProfile() {
		ProfileResource cleanedProfile = new ProfileResource();
		cleanedProfile.accountStatus = accountStatus;
		cleanedProfile.address = address;
		cleanedProfile.affiliation = affiliation;
		cleanedProfile.attributes = attributes;
		cleanedProfile.authorities = authorities;
		cleanedProfile.comments = comments;
		cleanedProfile.email = email;
		cleanedProfile.firstName = firstName;
		cleanedProfile.groups = groups;
		cleanedProfile.id = id;
		cleanedProfile.initials = initials;
		cleanedProfile.jobTitle = jobTitle;
		cleanedProfile.lastName = lastName;
		cleanedProfile.lastEngineConnection = lastEngineConnection;
		cleanedProfile.lastLogin = lastLogin;
		cleanedProfile.phone = phone;
		cleanedProfile.registrationDate = registrationDate;
		cleanedProfile.roles = roles;
		cleanedProfile.sendUpdates = sendUpdates;
		cleanedProfile.serverUrl = serverUrl;
		cleanedProfile.Token = Token;
		cleanedProfile.username = username;
		
		List<KlabGroup> safeGroups = new ArrayList<>();
		for (KlabGroup grp : cleanedProfile.getGroups()) {
			if(grp != null) {
				KlabGroup group = new KlabGroup();
				group.setId(grp.getId());
				safeGroups.add(group);
			}
		}
		cleanedProfile.groups = safeGroups;
		return cleanedProfile;
	}
}