package org.integratedmodelling.klab.hub.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.integratedmodelling.klab.rest.Group;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ProfileResource implements OAuth2User{

	public String id;
	
    @Size(max = Constraints.USERNAME_LENGTH)
    @Pattern(regexp = Constraints.USERNAME_PATTERN)
    public String name;

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

    public List<GroupEntry> groupEntries = new ArrayList<>(); // research groups, etc. in web tool

    public boolean sendUpdates;

    public String comments;

    public LocalDateTime registrationDate;

    public LocalDateTime lastLogin;

	public LocalDateTime lastConnection;

    public AccountStatus accountStatus;
    
    // public String Token;

    private Collection<? extends GrantedAuthority> authorities;
    
    private Map<String, Object> attributes;
    
    /**
     * Use to store the jwt token in case of needs
     */
    private String jwtToken;
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(email).append(serverUrl).append(firstName).append(lastName)
                .append(initials).append(address).append(jobTitle).append(phone).append(affiliation).append(groupEntries)
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
        return new EqualsBuilder().append(name, other.name).append(email, other.email)
                .append(serverUrl, other.serverUrl).append(firstName, other.firstName).append(lastName, other.lastName)
                .append(initials, other.initials).append(address, other.address).append(jobTitle, other.jobTitle)
                .append(phone, other.phone).append(affiliation, other.affiliation).append(groupEntries, other.groupEntries)
                .append(sendUpdates, other.sendUpdates).append(comments, other.comments)
                .append(accountStatus, other.accountStatus).isEquals();
    }
    
    public String getUsername() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}
	
    public List<GroupEntry> getGroups() {
		return groupEntries;
	}

	public void setGroups(List<GroupEntry> groups) {
		this.groupEntries = groups;
	}

	public List<Role> getRoles() {
		return roles;
	}

//	public void setToken(String token) {
//		this.Token = token;
//	}

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
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
	
    public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public LocalDateTime getLastConnection() {
		return lastConnection;
	}

	public void setLastConnection(LocalDateTime lastConnection) {
		this.lastConnection = lastConnection;
	}

	public List<String> getGroupsIds() {
	    List<String> groupsIds = new ArrayList<>();
	    for (GroupEntry grp : this.getGroups()) {
            if(grp != null && grp.getExpiration() != null && grp.getExpiration().isAfter(LocalDateTime.now())) {
                groupsIds.add(grp.getGroup().getName());
            }
        }
	    return groupsIds;
	}
	
	public List<Group> getGroupsList() {
		List<Group> listOfGroups = new ArrayList<>();
		for (GroupEntry grp : this.getGroups()) {
			if(grp != null && grp.getExpiration() != null && grp.getExpiration().isAfter(LocalDateTime.now())) {
				Group group = new Group();
				MongoGroup mGroup = grp.getGroup();
				group.setName(mGroup.getName());
				group.setDescription(mGroup.getDescription());
				group.setIconUrl(mGroup.getIconUrl());
				group.setMaxUpload(mGroup.getMaxUpload());
				group.setObservables(mGroup.getObservableReferences());
				group.setProjectUrls(mGroup.getProjectUrls());
				group.setSshKey(mGroup.getSshKey());
				group.setMaxUpload(mGroup.getMaxUpload());
				group.setWorldview(mGroup.isWorldview());
				
				
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
		cleanedProfile.groupEntries = groupEntries;
		cleanedProfile.id = id;
		cleanedProfile.initials = initials;
		cleanedProfile.jobTitle = jobTitle;
		cleanedProfile.lastName = lastName;
		cleanedProfile.lastConnection = lastConnection;
		cleanedProfile.lastLogin = lastLogin;
		cleanedProfile.phone = phone;
		cleanedProfile.registrationDate = registrationDate;
		cleanedProfile.roles = roles;
		cleanedProfile.sendUpdates = sendUpdates;
		cleanedProfile.serverUrl = serverUrl;
		//cleanedProfile.Token = Token;
		cleanedProfile.jwtToken = jwtToken;
		cleanedProfile.name = name;
		List<GroupEntry> safeGroups = new ArrayList<>();
		for (GroupEntry entry : cleanedProfile.getGroups()) {
			if(entry != null) {
				MongoGroup cleanGroup = new MongoGroup();
				MongoGroup unsafeGroup = entry.getGroup();
				cleanGroup.setIconUrl(unsafeGroup.getIconUrl());
				cleanGroup.setName(unsafeGroup.getName());
				cleanGroup.setDependsOn(unsafeGroup.getDependsOn());
				cleanGroup.setWorldview(unsafeGroup.isWorldview());
				entry.setGroup(cleanGroup);
				safeGroups.add(entry);
			}
		}
		cleanedProfile.groupEntries = cleanedProfile.getGroups();
		return cleanedProfile;
	}


    public ArrayList<GroupEntry> expiredGroupEntries() {
        ArrayList<GroupEntry> expired = new ArrayList<GroupEntry>();
        for (GroupEntry e : getGroups()) {
            if(e.getExpiration() != null && e.getExpiration().isBefore(LocalDateTime.now())) {
                expired.add(e);
            }
        }
        return expired;
    }
    
    public ArrayList<GroupEntry> expiringGroupEntries() {
        ArrayList<GroupEntry> expiring = new ArrayList<GroupEntry>();
        for (GroupEntry e : getGroups()) {
            if(e.getExpiration() != null && !e.getExpiration().isBefore(LocalDateTime.now()) && !e.getExpiration().isAfter(LocalDateTime.now().plusDays(30))) {
                expiring.add(e);
            }
        }
        return expiring;
    }
	
}
