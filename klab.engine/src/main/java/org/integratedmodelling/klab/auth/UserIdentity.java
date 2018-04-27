package org.integratedmodelling.klab.auth;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class UserIdentity implements IUserIdentity, UserDetails {

    private static final long serialVersionUID = -5670348187596399293L;

    protected String username;
    protected String emailAddress;
    protected String token;
    protected DateTime lastLogin = DateTime.now();
    protected DateTime expiryDate;
    protected Set<String> groups = new HashSet<>();
    protected Set<GrantedAuthority> authorities = new HashSet<>();

    public UserIdentity(String username) {
        this.username = username;
    }

    public UserIdentity(UserIdentity user) {
        this.username = user.username;
        this.emailAddress = user.emailAddress;
        this.token = user.token;
        this.groups.addAll(user.groups);
        this.authorities.addAll(user.authorities);
    }

    public UserIdentity(AuthenticatedIdentity identity) {
        this(identity.getIdentity());
        this.token = identity.getToken();
        for (Group group : identity.getGroups()) {
            this.groups.add(group.getId());
        }
        this.expiryDate = DateTime.parse(identity.getExpiry());
    }

    public UserIdentity(IdentityReference identity) {
        this.username = identity.getId();
        this.emailAddress = identity.getEmail();
        this.lastLogin = DateTime.parse(identity.getLastLogin());
    }

    @Override
    public boolean isAnonymous() {
        return username.equals(Auth.ANONYMOUS_USER_ID);
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return getId();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO
        return true;
    }

    @Override
    public String getId() {
        return token;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<T> type) {
        return IIdentity.findParent(this, type);
    }

    @Override
    public Set<String> getGroups() {
        return groups;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserIdentity other = (UserIdentity) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
