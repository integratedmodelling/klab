package org.integratedmodelling.klab.auth;

import java.util.Date;
import org.integratedmodelling.klab.api.auth.IKlabUserIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;

public class KlabUserIdentity extends UserIdentity implements IKlabUserIdentity {

    private static final long serialVersionUID = -5902039133869228876L;
    private INodeIdentity parent;
    protected boolean online;

    public KlabUserIdentity(String username, INodeIdentity node) {
        super(username);
        this.parent = node;
    }

    @Override
    public String getServerURL() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getEmailAddress() {
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public INodeIdentity getParentIdentity() {
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
