package org.integratedmodelling.klab.engine.events;

 public class GenericUserEvent<P, S> {

	private P profile;
	private S session;
	protected UserEventType type;
	
	public GenericUserEvent(P profile, S session) {
		this.profile = profile;
		this.session = session;
	}

	public P getProfile() {
		return profile;
	}

	public S getSession() {
		return session;
	}

	public UserEventType getType() {
		return type;
	}
	
}
