package org.integratedmodelling.klab.hub.payload;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.groups.dto.GroupEntry;
import org.integratedmodelling.klab.hub.users.dto.ProfileResource;

public class EngineProfileResource {

    
    private String name;
    private String email;
    private List<Role> roles;
    
    private Set<GroupEntry> groupEntries;
    
    private String jwtToken;

    public EngineProfileResource(ProfileResource profile) {
        if (profile != null) {
            this.name = profile.getUsername();
            this.email = profile.getEmail();
            this.roles = profile.getRoles();
            this.groupEntries = profile.getAgreements().get(0).getAgreement().getGroupEntries();
            this.jwtToken = profile.getJwtToken();
        }
    }
    
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GroupEntry> getGroupEntries() {
        return groupEntries;
    }

    public void setGroupEntries(Set<GroupEntry> groupEntries) {
        this.groupEntries = groupEntries;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
