package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.HubUserProfile;

public class UserEventLogin extends GenericUserEvent<HubUserProfile, Session>{

	public UserEventLogin(HubUserProfile profile, Session session) {
		super(profile, session);
		this.type = UserEventType.LOGIN;
	}

}
