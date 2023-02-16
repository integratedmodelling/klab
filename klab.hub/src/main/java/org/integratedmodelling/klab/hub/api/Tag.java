package org.integratedmodelling.klab.hub.api;

import java.io.Serializable;

import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.joda.time.DateTime;

public class Tag implements Serializable {

    private static final long serialVersionUID = 7819502042440147000L;

    public String description;
    private HubNotificationMessage.Type type = HubNotificationMessage.Type.INFO;
    private DateTime creationTime = null;
    private DateTime sentTime = null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tag(String description) {
        this.description = description;
        this.creationTime = DateTime.now();
    }

    public Tag() {
        this.creationTime = DateTime.now();
    }

    public Tag(String description, HubNotificationMessage.Type type) {
        this.description = (description);
        this.type = type;
        this.creationTime = DateTime.now();
        this.sentTime = null;
    }

    public void updateSentTime() {
        this.sentTime = DateTime.now();
    }

    public boolean isSent() {
        return sentTime != null;
    }

    public HubNotificationMessage.Type getType() {
        return type;
    }

}
