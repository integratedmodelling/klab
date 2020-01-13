package org.integratedmodelling.klab.hub.models.tokens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.integratedmodelling.klab.hub.models.Role;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "AuthenticationTokens")
public class AuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -639057954057823267L;

    private static final int TOKEN_TTL_SECONDS = 60 * 60 * 24 * 7 * 4; // 4 weeks
    
    @Id
    private String id;

    @Indexed(unique = true)
    private String tokenString;

    @Indexed
    private String username;
    
    private String paretToken;

	protected DateTime expiration;

    // this essentially overrides this.authorities in the parent class,
    // so that we can have a mongo-friendly no-arg constructor (i.e. mutable state)
    // NOTE: the parent class recommends against mutable state.
    private final Collection<Role> roles = new HashSet<>();

    // this needs to be here for Mongo (username=null to start)
    public AuthenticationToken() {
        this(null);
    }

    public AuthenticationToken(String username) {
        super(null); // no authorities by default
        this.username = username;
        this.tokenString = UUID.randomUUID().toString();
        this.expiration = DateTime.now().plusSeconds(TOKEN_TTL_SECONDS);
    }

	/**
     * add an expiration check to the default isAuthenticated() implementation
     */
    @Override
    public boolean isAuthenticated() {
        return super.isAuthenticated() && !isExpired();
    }

    public String getTokenString() {
        return tokenString;
    }

    @Override
    public String getCredentials() {
        return tokenString;
    }

    @Override
    public String getPrincipal() {
        return username;
    }

    // this needs to be here for Mongo (username=null to start)
    public void setUsername(String username) {
        this.username = username;
    }

    public DateTime getExpiration() {
        return expiration;
    }

    public boolean isExpired() {
        DateTime now = new DateTime();
        return now.isAfter(expiration);
    }

    @Override
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

}

