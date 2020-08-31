package org.integratedmodelling.klab.auth;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior;
import org.integratedmodelling.klab.components.runtime.actors.UserActor;
import org.integratedmodelling.klab.engine.runtime.ViewImpl;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.rest.Layout;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import akka.actor.typed.ActorRef;

public class EngineUser extends UserIdentity implements IEngineUserIdentity {

	private static final long serialVersionUID = -134196454400472128L;
	private IEngineIdentity parent;
	private ActorRef<KlabMessage> actor;
	private Map<String, Object> globalState = Collections.synchronizedMap(new HashMap<>());
	private View view;

	public Map<String, Object> getState() {
		return globalState;
	}

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

	@Override
	public ActorRef<KlabMessage> getActor() {
		if (this.actor == null) {
			this.actor = Actors.INSTANCE.createActor(UserActor.create(this), this);
		}
		return this.actor;
	}

	// TODO pass new SimpleRuntimeScope(Klab.INSTANCE.getRootMonitor())
	@Override
	public String load(IBehavior behavior, IContextualizationScope scope) {
		// TODO this gets a sucky runtime scope that is used to run main messages.
		getActor().tell(new SystemBehavior.Load(behavior.getId(), getId(), (IRuntimeScope)scope));
		return getId();
	}

	@Override
	public void instrument(ActorRef<KlabMessage> actor) {
		this.actor = actor;
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public void setLayout(Layout layout) {
		this.view = new ViewImpl(layout);
	}

	@Override
	public boolean stop(String behaviorId) {
		// TODO Auto-generated method stub
		return false;
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

}
