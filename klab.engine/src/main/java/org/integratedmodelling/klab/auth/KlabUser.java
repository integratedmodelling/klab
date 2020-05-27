package org.integratedmodelling.klab.auth;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IKlabUserIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.springframework.security.core.GrantedAuthority;

import akka.actor.typed.ActorRef;

public class KlabUser extends UserIdentity implements IKlabUserIdentity {

	private static final long serialVersionUID = -5902039133869228876L;
	private IIdentity parent;
	protected boolean online;
	private Map<String, Object> globalState = Collections.synchronizedMap(new HashMap<>());

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

	@Override
	public ActorRef<KlabMessage> getActor() {
		// TODO if actor == null, get the user actor from the supervisor
		// find the user.kactor file in ~/.klab and load it if there
		return null;
	}

	@Override
	public void load(IBehavior behavior, IRuntimeScope scope) {
		// TODO Auto-generated method stub

	}

	@Override
	public void instrument(ActorRef<KlabMessage> actor) {
		// TODO Auto-generated method stub
	}

	public Map<String, Object> getState() {
		return globalState;
	}

}
