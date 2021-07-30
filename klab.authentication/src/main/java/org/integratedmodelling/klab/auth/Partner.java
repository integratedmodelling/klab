package org.integratedmodelling.klab.auth;

import java.util.Date;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.utils.Parameters;
import org.springframework.security.core.userdetails.UserDetails;

public class Partner extends UserIdentity implements IPartnerIdentity, UserDetails {

	private static final long serialVersionUID = -129699145554376751L;

	private IParameters<String> globalState = Parameters.createSynchronized();
	private View view;
	private Reference actor;
//	private Map<String, BiConsumer<String, Object>> stateChangeListeners = Collections.synchronizedMap(new HashMap<>());

	public Partner(String partnerName) {
		super(partnerName);
	}

	public Partner(IdentityReference owningPartner) {
		super(owningPartner);
	}

	@Override
	public IIdentity getParentIdentity() {
		// the only legitimate null.
		return null;
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
	public boolean is(Type type) {
		return type == IPartnerIdentity.TYPE;
	}

	@Override
	public boolean isOnline() {
		// partners are an offline entity.
		return false;
	}

	@Override
	public String getName() {
		return getUsername();
	}

	@Override
	public Reference getActor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void instrument(Reference actor) {
		// TODO Auto-generated method stub

	}
//
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
	public String load(IBehavior behavior, IContextualizationScope scope) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public IParameters<String> getState() {
		return globalState;
	}

	
//	@Override
//	public void setStateChangeListener(String name, BiConsumer<String, Object> listener) {
//		this.stateChangeListeners.put(name, listener);
//	}
//
//	@Override
//	public void removeStateChangeListener(String name) {
//		this.stateChangeListeners.remove(name);
//	}
}
