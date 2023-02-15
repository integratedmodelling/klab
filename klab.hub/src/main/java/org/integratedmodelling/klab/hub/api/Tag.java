package org.integratedmodelling.klab.hub.api;

import java.io.Serializable;

import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.joda.time.DateTime;
import org.springframework.data.annotation.PersistenceConstructor;

public class Tag implements Serializable {

    private static final long serialVersionUID = 7819502042440147000L;
    
    private String description;
    private HubNotificationMessage.Type type;
    private DateTime creationTime;
    private DateTime sentTime;

    public String getDescription() {
        return this.description;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    @PersistenceConstructor
    public Tag(String description) {
        this.description = description;
        this.creationTime = DateTime.now();
    }

    public Tag(String description, HubNotificationMessage.Type type) {
        this.description = description;
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
        return this.type;
    }
}
