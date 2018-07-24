package org.integratedmodelling.klab.auth;

import java.util.Date;

import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class EngineUser extends UserIdentity implements IEngineUserIdentity {

    private static final long serialVersionUID = -134196454400472128L;
    private IEngineIdentity parent;

    public EngineUser(String username, IEngineIdentity parent) {
        super(username);
        this.parent = parent;
        this.authorities.add(new SimpleGrantedAuthority(Roles.ENGINE_USER));
    }

    public EngineUser(UserIdentity owner, IEngineIdentity parent) {
        super(owner);
        this.parent = parent;
    }

    /**
       * Create the default engine user from the engine's owner.
       * 
       * @param engine an engine identity. Must have an owner.
       * @return the default engine user for the passed owner and engine
       */
    public static EngineUser promote(IEngineIdentity engine) {
        KlabUser owner = engine.getParentIdentity(KlabUser.class);
        if (owner == null) {
            throw new IllegalArgumentException("engine does not have an owner: cannot create default engine user");
        }
        return null;
    }

    @Override
    public boolean isAnonymous() {
        // TODO Auto-generated method stub
        return false;
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
    public boolean isEngineOwner() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IEngineIdentity getParentIdentity() {
        return parent;
    }

    @Override
    public boolean is(Type type) {
        return type == IEngineUserIdentity.TYPE;
    }

    @Override
    public boolean isOnline() {
        // TODO Auto-generated method stub
        return false;
    }

}
