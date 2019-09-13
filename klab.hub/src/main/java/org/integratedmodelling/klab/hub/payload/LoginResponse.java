package org.integratedmodelling.klab.hub.payload;

import java.util.ArrayList;
import java.util.Collection;

import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.integratedmodelling.klab.hub.models.Role;
import org.integratedmodelling.klab.hub.models.tokens.AuthenticationToken;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;

public class LoginResponse {
    public String username;

    public String authToken;

    public DateTime expiration;

    public ProfileResource profile;

    public Collection<Role> roles;

    public LoginResponse(AuthenticationToken token, ProfileResource profile) {
        username = token.getPrincipal();
        authToken = token.getCredentials();
        expiration = token.getExpiration();
        this.profile = profile;
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        roles = new ArrayList<>(authorities.size());
        for (GrantedAuthority authority : authorities) {
            roles.add((Role) authority);
        }
    }

    public LoginResponse() {
        // Jackson JSON mapping requires a no-arg constructor
    }

    @Override
    public String toString() {
        return String.format("[%s|%s]", username, authToken);
    }

}
