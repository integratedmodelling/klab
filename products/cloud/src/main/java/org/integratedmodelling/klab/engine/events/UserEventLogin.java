package org.integratedmodelling.klab.engine.events;

import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.HubUserProfile;

public class UserEventLogin extends GenericUserEvent<HubUserProfile, Session>{
    
    private boolean failed;
    private String message;
    
    public static enum MESSAGES {
        BAD_USER,
        BAD_TOKEN,
        NO_RESPONSE,
        EXISTING_SESSION,
        NEW_SESSION,
    }
    // we need time if failed, because no session is created
    private long time;

    public UserEventLogin(HubUserProfile profile, Session session, String message) {
        super(profile, session);
        this.failed = false;
        this.time = session != null ? session.getSessionReference().getTimeEstablished() : -1;
        this.message = null;
        this.type = UserEventType.LOGIN;
    }
    
    public UserEventLogin(String username, String message) {
		super(new HubUserProfile(), null);
		this.getProfile().setName(username);
		this.failed = true;
		this.message = message;
		this.time = System.currentTimeMillis();
		this.type = UserEventType.LOGIN;
	}

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
