package org.integratedmodelling.klab.hub.tags.dto;

import org.integratedmodelling.klab.rest.HubNotificationMessage.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a HubNotificationMessage associated to a MongoTag.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "NotificationTags")
@TypeAlias("NotificationTag")
public class TagNotification {

    @Id
    @Indexed(unique = true)
    private String id;

    @DBRef
    private MongoTag tag;

    /**
     * Type  of notification
     */
    @Enumerated(EnumType.STRING)
    private Type type;

    /**
     * Title of notification
     */
    private String title;
    
    /**
     * Message of notification
     */
    private String message;
    
    /**
     * Indicates whether navigation is required and where to navigate to execute an action.
     */
    private String navigateTo;
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    


}
