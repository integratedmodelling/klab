package org.integratedmodelling.klab.hub.users;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Document(collection="Users")
public class User implements UserDetails{
	public static final String GLOBAL_GROUP = "REGISTERED";

    private static final long serialVersionUID = -6213593655742083476L;

    @Id 
    String id;

    @Indexed(unique = true)
    String username;

    @Indexed(unique = false)
    String email;

    String affiliation;

    String comments;

    @Indexed
    String firstName;

    @Indexed
    String lastName;

    String initials;

    String address;
    
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    
    private String providerId;

    String jobTitle;

	String phone;

    String serverUrl;

    DateTime registrationDate;

    DateTime lastLogin;

    DateTime lastEngineConnection;

    boolean sendUpdates = true;

    final Set<Role> roles = new HashSet<>(); // LDAP security roles

    @Reference
    private Set<GroupEntry> groupEntries =  new HashSet<>(); // research groups, etc. in web tool

    final Set<String> applications = new HashSet<>();

    AccountStatus accountStatus = AccountStatus.pendingActivation;

    public enum AccountStatus {
        active,
        locked,
        deleted,
        expired,
        pendingActivation,
        verified,
    };

    // @Transient prevents the password from being stored in Mongo.
    // (although it's probably not a huge deal because this will be hashed in production.)
    @Transient
    String passwordHash;

    // TODO reference this from a AbstractSecurityInterceptor for DataSource object security.
    // see "secure object" references at http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle
//    public boolean hasAnyGroups(Set<KlabGroup> groups) {
//        for (KlabGroup group : groups) {
//            if (groups.contains(group)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<Role> getAuthorities() {
        return new HashSet<>(roles);
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountStatus.equals(AccountStatus.expired);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountStatus.equals(AccountStatus.locked);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return accountStatus.equals(AccountStatus.active);
    }

    public boolean isAdmin() {
        return isRole(Role.ROLE_ADMINISTRATOR);
    }

    public boolean isRole(Role role) {
        return roles.contains(role);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void addComments(String additionalComments) {
        if (comments == null) {
            comments = additionalComments;
        } else {
            comments += "\n" + additionalComments;
        }
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public void setSendUpdates(boolean sendUpdates) {
        this.sendUpdates = sendUpdates;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getEmail() {
        return email;
    }

    public void addRoles(Role... roles) {
        this.roles.addAll(Arrays.asList(roles));
    }

    public void setRoles(Collection<Role> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
    }

    public void addGroupEntries(GroupEntry... groups) {
        this.groupEntries.addAll(Arrays.asList(groups));
    }

    public void addGroupEntries(Set<GroupEntry> groups) {
        this.groupEntries.addAll(groups);
    }

    public void setGroupEntries(Set<GroupEntry> groups) {
        this.groupEntries = groups;
    }

    public Set<GroupEntry> getGroupEntries() {
        return groupEntries;
    }

    public Set<String> getApplications() {
        return applications;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String url) {
        serverUrl = url;
    }

    public boolean shouldSendUpdates() {
        return sendUpdates;
    }

    /**
     * Deprecated - use getPasswordHash() instead. this is here for compatibility with UserDetails. The
     * securePassword field should NEVER be persisted anywhere beyond this thread's memory.
     */
    @Override
    @Deprecated
    public String getPassword() {
        return getPasswordHash();
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return username + roles.toString();
    }

    public String getFullName() {
        String first = getFirstName();
        String last = getLastName();
        if (first == null && last == null) {
            return null;
        }
        // return both, or just one if the other is not available
        return String.format("%s %s", first == null ? "" : first, last == null ? "" : last).trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void updateFromProfileResource(ProfileResource resource) {
        address = resource.address;
        affiliation = resource.affiliation;
        comments = resource.comments;
        firstName = resource.firstName;
        initials = resource.initials;
        jobTitle = resource.jobTitle;
        lastName = resource.lastName;
        phone = resource.phone;
        sendUpdates = resource.sendUpdates;
        serverUrl = resource.serverUrl;
    }

    public boolean userGroupsOverlapWith(HashSet<GroupEntry> groups) {
        if (groups == null) {
            // force this to be checked by set intersection, rather than instantly failing (preserves logic)
            groups = new HashSet<>();
        }

        if (groups.contains(User.GLOBAL_GROUP)) {
            return true;
        }

        Set<GroupEntry> list = getGroupEntries(); // returns a copy
        list.retainAll(groups);
        if (list.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * return full name (if available) or some sensible constructed value so that LDAP/Crowd doesn't choke on
     * a null value
     *
     * order of preference: full name, first name, username
     */
    public String getDisplayName() {
        if (getFirstName() != null && getLastName() != null) {
            return getFullName();
        }
        // either firstName or lastName is null.
        if (getFirstName() != null) {
            return getFirstName();
        }
        return getUsername();
    }

    public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public void setRegistrationDate() {
        registrationDate = DateTime.now();
    }
	
	public void setRegistrationDate(DateTime date) {
        registrationDate = date;
    }
	
	public DateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setLastLogin() {
        lastLogin = DateTime.now();
    }
    
    public void setLastLogin(DateTime date) {
        lastLogin = date;
    }
    
    public DateTime getLastLogin() {
    	return lastLogin;
    }

    public void setLastEngineConnection() {
        lastEngineConnection = DateTime.now();
    }
    
    public void setLastEngineConnection(DateTime date) {
        lastEngineConnection = date;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}