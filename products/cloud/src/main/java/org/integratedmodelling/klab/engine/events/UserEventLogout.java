package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.HubUserProfile;

public class UserEventLogout extends GenericUserEvent<HubUserProfile, Session>{

    private boolean forced;
    private boolean failed;
    private String message;
    private long time;
    
    public UserEventLogout(String username, String message) {
        super(new HubUserProfile(), null);
        this.getProfile().setName(username);
        this.forced = false;
        this.failed = true;
        this.message = message;
        this.type = UserEventType.LOGOUT;
    }
    
	public UserEventLogout(HubUserProfile profile, Session session, boolean forced) {
		super(profile, session);
		this.time = System.currentTimeMillis();
		this.forced = forced;
		this.failed = false;
		this.message = null;
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

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
