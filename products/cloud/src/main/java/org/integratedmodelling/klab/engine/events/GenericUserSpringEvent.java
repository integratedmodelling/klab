package org.integratedmodelling.klab.engine.events;

import org.springframework.context.ApplicationEvent;

public class GenericUserSpringEvent<P, S> extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5738683345561595420L;
	
	
	private final P profile;
	private final S session;
	
	public GenericUserSpringEvent(final Object source, final P profile, final S session) {
		super(source);
		this.session = session;
		this.profile = profile;
	}
	
	public P getProfile() {
		return profile;
	}
	
	public S getSessio() {
		return session;
	}
}
