package org.integratedmodelling.klab.auth;

import java.util.Date;
import java.util.List;

import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IKlabUserIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.springframework.security.core.GrantedAuthority;

public class KlabUser extends UserIdentity implements IKlabUserIdentity {

    private static final long serialVersionUID = -5902039133869228876L;
    private IIdentity parent;
    protected boolean online;

    public KlabUser(String username, INodeIdentity node) {
        super(username);
        this.parent = node;
        this.emailAddress = "";
    }

    public KlabUser(AuthenticatedIdentity userData, NetworkSession networkSession) {
        super(userData.getIdentity());
        this.parent = networkSession;
    }

    public KlabUser(String username, String token, List<GrantedAuthority> authorities) {
        super(username);
        this.token = token;
        this.authorities.addAll(authorities);
    }

    @Override
    public String getServerURL() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFirstName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLastName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getInitials() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAffiliation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getComment() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date getLastLogin() {
        return lastLogin.toDate();
    }

    @Override
    public IIdentity getParentIdentity() {
        return this.parent;
    }

    @Override
    public boolean is(Type type) {
        return type == IKlabUserIdentity.TYPE;
    }

    @Override
    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean b) {
        this.online = b;
    }

}
