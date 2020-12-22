package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.HubUserProfile;

public class UserEventLogout extends GenericUserEvent<HubUserProfile, Session>{

	public UserEventLogout(HubUserProfile profile, Session session) {
		super(profile, session);
		this.type = UserEventType.LOGOUT;
	}

}
