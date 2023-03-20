package org.integratedmodelling.klab.auth;

import java.util.Date;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Services;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IActorsService;
import org.integratedmodelling.klab.utils.Parameters;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class EngineUser extends UserIdentity implements IEngineUserIdentity {

    private static final long serialVersionUID = -134196454400472128L;
    private IEngineIdentity parent;
    private IActorIdentity.Reference actor;
    private IParameters<String> globalState = Parameters.createSynchronized();
    private IActorIdentity.View view;
    private IContextScope scope;

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
    public IIdentity.Type getIdentityType() {
        return IIdentity.Type.ENGINE_USER;
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
        return type == getIdentityType();
    }

    @Override
    public boolean isOnline() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Reference getActor() {
        if (this.actor == null && Services.INSTANCE.getService(IActorsService.class) != null) {
            this.actor = Services.INSTANCE.getService(IActorsService.class).createUserActor(this);
        }
        return this.actor;
    }

    @Override
    public void instrument(Reference actor) {
        this.actor = actor;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setView(View layout) {
        this.view = layout;
    }

    @Override
    public boolean stop() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IMonitor getMonitor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IParameters<String> getState() {
        return globalState;
    }

    @Override
    public IContextScope getScope() {
        return this.scope;
    }

    /**
     * TODO have the engine implementation call this for each user upon authentication.
     * 
     * @param scope
     */
    public void setObservationScope(IContextScope scope) {
        this.scope = scope;
    }

    @Override
    public String load(IBehavior behavior, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean stop(String behaviorId) {
        // TODO Auto-generated method stub
        return false;
    }

}
