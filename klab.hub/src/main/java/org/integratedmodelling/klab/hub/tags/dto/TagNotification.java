package org.integratedmodelling.klab.hub.tags.dto;

import org.integratedmodelling.klab.rest.HubNotificationMessage.Type;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * Represents a HubNotificationMessage associated to a MongoTag.
 */
@Document(collection = "NotificationTags")
@TypeAlias("NotificationTag")
public class TagNotification {

    @DBRef
    private MongoTag tag;
    
    @Enumerated(EnumType.STRING) 
    private Type type;
    
    private String message;

    public void setTag(MongoTag tag) {
        this.tag = tag;
    }

    public MongoTag getTag() {
        return tag;
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

}
