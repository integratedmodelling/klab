package org.integratedmodelling.klab.hub.users.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.agreements.dto.AgreementEntry;
import org.integratedmodelling.klab.hub.groups.dto.GroupEntry;
import org.integratedmodelling.klab.hub.groups.dto.MongoGroup;
import org.integratedmodelling.klab.hub.users.dto.User.AccountStatus;
import org.integratedmodelling.klab.rest.CustomPropertyRest;
import org.integratedmodelling.klab.rest.Group;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ProfileResource implements OAuth2User {

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

    public List<AgreementEntry> agreements = new ArrayList<>();

    public boolean sendUpdates;

    public String comments;

    public LocalDateTime registrationDate;

    public LocalDateTime lastLogin;

    public LocalDateTime lastConnection;

    public AccountStatus accountStatus;

    private Collection< ? extends GrantedAuthority> authorities;

    private Map<String, Object> attributes;

    public Set<CustomPropertyRest> customProperties = new HashSet<>();
    public Map<String, CustomPropertyRest> customPropertyMap = new HashMap<>();
    
    /**
     * Use to store the jwt token in case of needs
     */
    private String jwtToken;

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(email).append(serverUrl).append(firstName).append(lastName)
                .append(initials).append(address).append(jobTitle).append(phone).append(affiliation).append(agreements)
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
        return new EqualsBuilder().append(name, other.name).append(email, other.email).append(serverUrl, other.serverUrl)
                .append(firstName, other.firstName).append(lastName, other.lastName).append(initials, other.initials)
                .append(address, other.address).append(jobTitle, other.jobTitle).append(phone, other.phone)
                .append(affiliation, other.affiliation).append(agreements, other.agreements)
                .append(sendUpdates, other.sendUpdates).append(comments, other.comments)
                .append(accountStatus, other.accountStatus).isEquals();
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public List<AgreementEntry> getAgreements() {
        return agreements;
    }

    public void setAgreements(List<AgreementEntry> agreements) {
        this.agreements = agreements;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Collection< ? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

//    public void setAttributes(Map<String, Object> attributes) {
//        this.attributes = attributes;
//        this.authorities = Collections.
//                singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//        this.roles.add(Role.ROLE_USER);
//    }

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

    public Set<CustomPropertyRest> getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(Set<CustomPropertyRest> customProperties) {
        this.customProperties = customProperties;
    }

    public List<String> getGroupsIds() {
        List<String> groupsIds = new ArrayList<>();
        // TODO Agreement list
        for(GroupEntry grp : this.getAgreements().get(0).getAgreement().getGroupEntries()) {
            if (grp != null && grp.isValid()) {
                groupsIds.add(grp.getGroup().getName());
            }
        }
        return groupsIds;
    }

    public List<Group> getGroupsList() {
        List<Group> listOfGroups = new ArrayList<>();
        // TODO Agreement list

        for(GroupEntry grp : this.getAgreements().get(0).getAgreement().getGroupEntries()) {
            if (grp != null && grp.isValid()) {

                Group group = new Group();
                MongoGroup mGroup = grp.getGroup();
                group.setId(mGroup.getName());
                group.setName(mGroup.getName());
                group.setDescription(mGroup.getDescription());
                group.setIconUrl(mGroup.getIconUrl());
                group.setMaxUpload(mGroup.getMaxUpload());
                group.setObservables(mGroup.getObservableReferences());
                group.setProjectUrls(mGroup.getProjectUrls());
                group.setSshKey(mGroup.getSshKey());
                group.setMaxUpload(mGroup.getMaxUpload());
                group.setWorldview(mGroup.isWorldview());
                group.setComplimentary(mGroup.isComplimentary());
                group.setOptIn(mGroup.isOptIn());
                group.setCustomProperties(mGroup.getCustomPropertiesRest());

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
        cleanedProfile.agreements = agreements;
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
        cleanedProfile.jwtToken = jwtToken;
        cleanedProfile.name = name;
        // TODO check
        cleanedProfile.agreements = agreements;
        cleanedProfile.customProperties = customProperties;
        return cleanedProfile;
    }

    public ArrayList<GroupEntry> expiredGroupEntries() {
        ArrayList<GroupEntry> expired = new ArrayList<GroupEntry>();

        for(GroupEntry e : getAgreements().get(0).getAgreement().getGroupEntries()) {
            if (e.getExpiration() != null && e.getExpiration().isBefore(LocalDateTime.now())) {
                expired.add(e);
            }
        }
        return expired;
    }

    public ArrayList<GroupEntry> expiringGroupEntries() {
        ArrayList<GroupEntry> expiring = new ArrayList<GroupEntry>();

        for(GroupEntry e : getAgreements().get(0).getAgreement().getGroupEntries()) {
            if (e.getExpiration() != null && !e.getExpiration().isBefore(LocalDateTime.now())
                    && !e.getExpiration().isAfter(LocalDateTime.now().plusDays(30))) {
                expiring.add(e);
            }
        }
        return expiring;
    }

}
