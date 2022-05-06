package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.HubUserProfile;

public class UserEventLogout extends GenericUserEvent<HubUserProfile, Session>{

    private boolean forced;
    private long time;
    
	public UserEventLogout(HubUserProfile profile, Session session, boolean forced) {
		super(profile, session);
		this.time = System.currentTimeMillis();
		this.forced = forced;
		this.type = UserEventType.LOGOUT;
	}

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
