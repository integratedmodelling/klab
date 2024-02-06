package org.integratedmodelling.klab.hub.users.requests;

import java.util.Collection;

public class UserCustomPropertyRequest {
    private Collection<String> usernames;
    private String key;
    private String value;
    private boolean onlyAdmin;

    public UserCustomPropertyRequest() {}

    public UserCustomPropertyRequest(Collection<String> usernames, String key, String value, boolean onlyAdmin) {
        this.usernames = usernames;
        this.key = key;
        this.value = value;
        this.onlyAdmin = onlyAdmin;
    }

    public UserCustomPropertyRequest(Collection<String> usernames, String key) {
        this.usernames = usernames;
        this.key = key;
    }

    public Collection<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(Collection<String> usernames) {
        this.usernames = usernames;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isOnlyAdmin() {
        return onlyAdmin;
    }

    public void setOnlyAdmin(boolean onlyAdmin) {
        this.onlyAdmin = onlyAdmin;
    }
}
