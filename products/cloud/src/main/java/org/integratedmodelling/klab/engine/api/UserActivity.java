package org.integratedmodelling.klab.engine.api;

import org.integratedmodelling.klab.engine.services.HubUserProfile;

public class UserActivity {

    public enum TYPES {
        LOGIN,
        LOGOUT
    }
    
    private TYPES type;
    private HubUserProfile profile;
    private long time;
    
    public UserActivity(TYPES type, HubUserProfile profile, long time) {
        super();
        this.type = type;
        this.profile = profile;
        this.time = time;
    }
    
    public TYPES getType() {
        return type;
    }
    public void setType(TYPES type) {
        this.type = type;
    }
    public HubUserProfile getProfile() {
        return profile;
    }
    public void setProfile(HubUserProfile profile) {
        this.profile = profile;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
   
}
