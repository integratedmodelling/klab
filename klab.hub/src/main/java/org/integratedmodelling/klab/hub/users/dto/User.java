package org.integratedmodelling.klab.hub.users.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.agreements.dto.AgreementEntry;
import org.integratedmodelling.klab.hub.api.IdentityModel;
import org.integratedmodelling.klab.hub.security.oauth2.AuthProvider;
import org.integratedmodelling.klab.hub.tags.dto.ITagElement;
import org.integratedmodelling.klab.hub.tags.dto.MongoTag;
import org.integratedmodelling.klab.hub.tags.dto.TagEntry;
import org.integratedmodelling.klab.rest.CustomProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Document(collection = "Users")
@TypeAlias("MongoUser")
@CompoundIndexes({@CompoundIndex(name = "username_idx", unique = true, def = "{'name' : 1, 'username' : 1}")})
public class User extends IdentityModel implements UserDetails, ITagElement {

    public static final String GLOBAL_GROUP = "REGISTERED";

    private static final long serialVersionUID = -6213593655742083476L;

    String affiliation;

    String comments;

    @Indexed(unique = true)
    String email;

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

    LocalDateTime lastLogin;

    boolean sendUpdates = true;

    private Set<Role> roles = new HashSet<>();;

    @Reference
    private Set<AgreementEntry> agreements = new HashSet<>();

    private List<TagEntry> tags = new ArrayList<>();

    private Set<String> applications = new HashSet<>();

    AccountStatus accountStatus = AccountStatus.pendingActivation;

    Set<CustomProperty> customProperties = new HashSet<>();

    public enum AccountStatus {
        active, locked, deleted, expired, pendingActivation, verified
    };

    // @Transient prevents the password from being stored in Mongo.
    // (although it's probably not a huge deal because this will be hashed in production.)
    @Transient
    String passwordHash;

    // TODO reference this from a AbstractSecurityInterceptor for DataSource object security.
    // see "secure object" references at
    // http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle
//    public boolean hasAnyGroups(Set<KlabGroup> groups) {
//        for (KlabGroup group : groups) {
//            if (groups.contains(group)) {
//                return true;
//            }
//        }
//        return false;
//    }

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public String getId() {
        return super.getId();
    }

    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getUsername() {
        return getName();
    }

    public void setUsername(String username) {
        setName(username);
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

    public boolean isManager() {
        return isRole(Role.ROLE_MANAGER);
    }

    public boolean isRole(Role role) {
        return roles.contains(role);
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

    public void addRoles(Role... roles) {
        this.roles.addAll(Arrays.asList(roles));
    }

    public void setRoles(Collection<Role> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void removeRoles(Collection<Role> rolesToRemove) {
        this.roles.removeAll(rolesToRemove);
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
        return super.getName() + roles.toString();
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
        setLastConnection(resource.lastConnection);
        lastLogin = resource.lastLogin;
        customProperties = resource.customProperties;
    }

//	public boolean userGroupsOverlapWith(HashSet<GroupEntry> groups) {
//        if (groups == null) {
//            // force this to be checked by set intersection, rather than instantly failing (preserves logic)
//            groups = new HashSet<>();
//        }
//
//        if (groups.contains(User.GLOBAL_GROUP)) {
//            return true;
//        }
//
//        Set<GroupEntry> list = getGroupEntries(); // returns a copy
//        list.retainAll(groups);
//        if (list.size() > 0) {
//            return true;
//        }
//
//        return false;
//    }

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

    public void setLastLogin() {
        lastLogin = LocalDateTime.now();
    }

    public void setLastLogin(LocalDateTime date) {
        lastLogin = date;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void addAgreements(AgreementEntry agreements) {
        this.agreements.addAll(Arrays.asList(agreements));
    }

    public void addAgreements(Set<AgreementEntry> agreements) {
        this.agreements.addAll(agreements);
    }

    public Set<AgreementEntry> getAgreements() {
        return agreements;
    }

    public void setAgreements(Set<AgreementEntry> agreements) {
        this.agreements = agreements;
    }

    public boolean hasTag(String tagName) {
        return tags.stream().anyMatch(t -> t.getTag() != null && t.getTag().getName().equals(tagName));
    }

    public void addTag(MongoTag mongoTag) {
        TagEntry tagEntry = new TagEntry(mongoTag);
        this.tags.add(tagEntry);
    }

    public void addTags(Collection<MongoTag> tags) {
        for(MongoTag t : tags) {
            addTag(t);
        }
    }

    public List<TagEntry> getTags() {
        return tags;
    }

    public List<TagEntry> getUnsentTags() {
        return tags.stream().filter(t -> !t.isSent()).collect(Collectors.toList());
    }

    public Set<CustomProperty> getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(Set<CustomProperty> customProperties) {
        this.customProperties = customProperties;
    }

    public void putCustomProperty(CustomProperty customProperty) {
        this.customProperties.add(customProperty);
    }

    public void putCustomProperties(Collection<CustomProperty> customProperties) {
        this.customProperties.addAll(customProperties);
    }

    public Optional<CustomProperty> findCustomProperty(String key) {
        return customProperties.stream().filter(cp -> cp.getKey().equals(key)).findFirst();
    }

    public void removeCustomProperty(String name) {
        this.customProperties.removeIf(cp -> cp.getKey().equals(name));
    }
}