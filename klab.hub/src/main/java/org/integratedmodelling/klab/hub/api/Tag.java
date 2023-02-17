package org.integratedmodelling.klab.hub.api;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.joda.time.DateTime;

public class Tag implements Serializable {

    private static final long serialVersionUID = 7819502042440147000L;

    private String description;
    private HubNotificationMessage.Type type = HubNotificationMessage.Type.INFO;
    private DateTime creationTime = null;
    private DateTime sentTime = null;

    public String getDescription() {
        return description;
    }

    private boolean isValidDescription(String description) {
        if(description.isEmpty()) {
            return false;
        }
        return Pattern.compile("([a-zA-Z0-9])+(-([a-zA-Z0-9])+)*")
                .matcher(description).matches();
    }

    public void setDescription(String description) {
        if(!isValidDescription(description)) {
            throw new IllegalArgumentException("Tag has not a valid format");
        }
        this.description = description;
    }

    public Tag() {
        this.creationTime = DateTime.now();
    }

    public void updateSentTime() {
        this.sentTime = DateTime.now();
    }

    public boolean isSent() {
        return this.sentTime != null;
    }

    public HubNotificationMessage.Type getType() {
        return type;
    }

}
