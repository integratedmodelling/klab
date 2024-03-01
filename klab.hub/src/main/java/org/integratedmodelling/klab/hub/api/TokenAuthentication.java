package org.integratedmodelling.klab.hub.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.integratedmodelling.klab.auth.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "Tokens")
@TypeAlias("Authentication")
public class TokenAuthentication {

    private static final int TOKEN_TTL_SECONDS = 60 * 60 * 24 * 7 * 4; // 4 weeks
    
    @Id
    private String id;

    @Indexed(unique = true)
    private String tokenString;

    @Indexed
    private String username;
    
    private String paretToken;

	protected LocalDateTime expiration;
	
	boolean authenticated;

	// this essentially overrides this.authorities in the parent class,
    // so that we can have a mongo-friendly no-arg constructor (i.e. mutable state)
    // NOTE: the parent class recommends against mutable state.
    private Collection<Role> roles = new HashSet<>();

    // this needs to be here for Mongo (username=null to start)
    public TokenAuthentication() {
        this(null);
    }

    public TokenAuthentication(String username) {
        this.username = username;
        this.tokenString = UUID.randomUUID().toString();
        this.expiration = LocalDateTime.now().plusSeconds(TOKEN_TTL_SECONDS);
    }

	/**
     * add an expiration check to the default isAuthenticated() implementation
     */
    public boolean isAuthenticated() {
    	if (authenticated) {
    		if(!isExpired()) {
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }

    public String getTokenString() {
        return tokenString;
    }

    public String getCredentials() {
        return tokenString;
    }

    public String getPrincipal() {
        return username;
    }

    // this needs to be here for Mongo (username=null to start)
    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expiration);
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>(roles);
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        // always keep it a hash set
        roles.clear();
        for (GrantedAuthority authority : authorities) {
            Role role = Role.valueOf(authority.getAuthority());
            if (role != null) {
                // silently ignore unknown authorities - lets other applications do what they want
                roles.add(role);
            }
        }
    }

    public boolean isAdministrator() {
        return roles.contains(Role.ROLE_ADMINISTRATOR);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getTokenString();
    }

	public String getParetToken() {
		return paretToken;
	}

	public void setParetToken(String paretToken) {
		this.paretToken = paretToken;
	}
	
    public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
}

