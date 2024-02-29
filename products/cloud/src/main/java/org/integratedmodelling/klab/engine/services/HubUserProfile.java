package org.integratedmodelling.klab.engine.services;

import java.util.List;

import org.integratedmodelling.klab.rest.GroupEntry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author steve
 * 
 * This is what happens when you make a mistake and keep having to
 * eat it over and over again.  The Hub ProfileResource class should
 * have been moved out of the application to the api or made into something
 * much more rational.  Now it is a PITA.  This has to be here because I 
 * wanted a jackson serialization, fyi static class for inner is important,
 * but means it needs to be in services when it is called.  This is dumb.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HubUserProfile {
    private String name;
    private String email;
    private List<String> roles;

    private List<GroupEntry> groupEntries;
    @JsonInclude(Include.NON_NULL)
    private String jwtToken;
    @JsonInclude(Include.NON_NULL)
    private String authToken;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
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

    public List<GroupEntry> getGroupEntries() {
        return groupEntries;
    }

    public void setGroupEntries(List<GroupEntry> groupEntries) {
        this.groupEntries = groupEntries;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
