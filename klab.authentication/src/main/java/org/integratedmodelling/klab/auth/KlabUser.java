package org.integratedmodelling.klab.auth;

import java.util.Date;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IKlabUserIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.utils.Parameters;
import org.springframework.security.core.GrantedAuthority;

public class KlabUser extends UserIdentity implements IKlabUserIdentity {

	private static final long serialVersionUID = -5902039133869228876L;
	private IIdentity parent;
	protected boolean online;
	private IParameters<String> globalState = Parameters.createSynchronized();
	private View view;
	private Reference actor;
//	private Map<String, BiConsumer<String, Object>> stateChangeListeners = Collections.synchronizedMap(new HashMap<>());

	public KlabUser(String username, INodeIdentity node) {
		super(username);
		this.parent = node;
		this.emailAddress = "";
	}

	public KlabUser(AuthenticatedIdentity userData, NetworkSession networkSession) {
		super(userData.getIdentity());
		this.token = userData.getToken();
		this.groups.addAll(userData.getGroups());
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
    public IIdentity.Type getIdentityType() {
        return IIdentity.Type.IM_USER;
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
		return type == getIdentityType();
	}

	@Override
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean b) {
		this.online = b;
	}

	@Override
	public Reference getActor() {
		// TODO if actor == null, get the user actor from the supervisor
		// find the user.kactor file in ~/.klab and load it if there
		return null;
	}

	@Override
	public void instrument(Reference actor) {
		// TODO Auto-generated method stub
	}

	@Override
	public IParameters<String> getState() {
		return globalState;
	}

	
//	@Override
//	public <V> V getState(String key, Class<V> cls) {
//		return this.globalState.get(key, cls);
//	}
//
//	@Override
//	public void setState(String key, Object value) {
//		this.globalState.put(key, value);
//		for (BiConsumer<String, Object> listener : stateChangeListeners.values()) {
//			listener.accept(key, value);
//		}
//	}

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
    public String load(IBehavior behavior, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean stop(String behaviorId) {
        // TODO Auto-generated method stub
        return false;
    }

//	@Override
////	public void setStateChangeListener(String name, BiConsumer<String, Object> listener) {
//		this.stateChangeListeners.put(name, listener);
//	}
//
//	@Override
//	public void removeStateChangeListener(String name) {
//		this.stateChangeListeners.remove(name);
//	}
}
