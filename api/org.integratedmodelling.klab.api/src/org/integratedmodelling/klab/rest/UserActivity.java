package org.integratedmodelling.klab.rest;

import java.util.List;

public class UserActivity {
    
    private String user;
    private List<String> roles;
    private List<String> groups;
    private long time;
    private String sessionId;
    private boolean failed;
    private boolean forced;
    private String message;
    private TYPES type;
    
    public enum TYPES {
        LOGIN,
        LOGOUT
    }
    
    public UserActivity() {
        
    }
    
    public UserActivity(String user, List<String> roles, List<String> groups, long time, String sessionId, boolean failed, String message) {
        super();
        this.user = user;
        this.roles = roles;
        this.groups = groups;
        this.time = time;
        this.sessionId = sessionId;
        this.failed = failed;
        this.message = message;
        this.type = TYPES.LOGIN;
    }
    
    public UserActivity(String user, long time, String sessionId, boolean failed, boolean forced, String message) {
        super();
        this.user = user;
        this.roles = null;
        this.groups = null;
        this.time = time;
        this.sessionId = sessionId;
        this.failed = failed;
        this.setForced(forced);
        this.message = message;
        this.type = TYPES.LOGOUT;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public TYPES getType() {
        return type;
    }

    public void setType(TYPES type) {
        this.type = type;
    }

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    

}
