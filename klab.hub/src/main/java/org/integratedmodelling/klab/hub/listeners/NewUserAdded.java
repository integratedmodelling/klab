package org.integratedmodelling.klab.hub.listeners;

import org.integratedmodelling.klab.hub.api.User;
import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author steve
 * Application Event we emit after a new user is added.
 * 
 */
public class NewUserAdded extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1727715673662948964L;
	private User user;

	public NewUserAdded(Object source, User user) {
		super(source);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
