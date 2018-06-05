package org.integratedmodelling.klab.engine.runtime;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Engine session. Implements UserDetails to be directly usable as a principal
 * in Spring security.
 * 
 * @author ferdinando.villa
 *
 */
public class Session implements ISession, UserDetails {

	private static final long serialVersionUID = -1571090827271892549L;

	Monitor monitor;
	String token = "s" + NameGenerator.shortUUID();
	IEngineUserIdentity user;
	List<Listener> listeners = new ArrayList<>();
	boolean closed = false;
	Set<GrantedAuthority> authorities = new HashSet<>();
	long lastActivity = System.currentTimeMillis();
	SpatialExtent regionOfInterest = null;

	public interface Listener {
		void onClose(ISession session);
	}

	public Session(Engine engine, IEngineUserIdentity user) {
		this.user = user;
		this.monitor = ((Monitor) engine.getMonitor()).get(this);
		this.authorities.add(new SimpleGrantedAuthority(Roles.SESSION));
		Auth.INSTANCE.registerSession(this);
	}

	void touch() {
		this.lastActivity = System.currentTimeMillis();
	}

	public void addListener(Listener listener) {
		this.listeners.add(listener);
	}

	@Override
	public String getId() {
		return token;
	}

	@Override
	public boolean is(Type type) {
		return type == Type.MODEL_SESSION;
	}

	@Override
	public <T extends IIdentity> T getParentIdentity(Class<T> type) {
		return IIdentity.findParent(this, type);
	}

	@Override
	public IEngineUserIdentity getParentIdentity() {
		return user;
	}

	@Override
	public Monitor getMonitor() {
		return monitor;
	}

	@Override
	public void close() throws IOException {
		for (Listener listener : listeners) {
			listener.onClose(this);
		}
		this.closed = true;
	}

	@Override
	public Future<ISubject> observe(String urn, String... scenarios) throws KlabException {
		touch();
		IKimObject object = Resources.INSTANCE.getModelObject(urn);
		if (!(object instanceof Observer)) {
			throw new KlabContextualizationException("URN " + urn + " does not specify an observation");
		}
		return new ObserveContextTask(this, (Observer) object, CollectionUtils.arrayToList(scenarios));
	}

	public String toString() {
		// TODO add user
		return "<session " + getId() + ">";
	}

	@Override
	public Set<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return getId();
	}

	@Override
	public String getUsername() {
		return getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !closed;
	}

	@Override
	public boolean isEnabled() {
		return !closed;
	}

	@Override
	public IGeometry getRegionOfInterest() {
		
		if (regionOfInterest == null) {
			return Geometry.empty();
		}
		return Geometry.create("S1").withBoundingBox(regionOfInterest.getEast(), regionOfInterest.getWest(),
				regionOfInterest.getSouth(), regionOfInterest.getNorth());
	}

	/*
	 * ------------------------------------------------------------------------
	 * handlers for messages
	 * ------------------------------------------------------------------------
	 */

	@MessageHandler
	private void setRegionOfInterest(SpatialExtent extent) {
		// TODO change to monitor.debug
		System.out.println("setting ROI = " + extent);
		this.regionOfInterest = extent;
	}

	@Override
	public IScript run(URL url) throws KlabException {
		IScript ret = null;
		if (url.toString().endsWith(".kim")) {
			return new Script(this, url);
		}
		return ret;

	}

}
