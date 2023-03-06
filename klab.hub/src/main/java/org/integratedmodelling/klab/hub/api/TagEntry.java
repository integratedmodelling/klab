package org.integratedmodelling.klab.hub.api;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Case of event by MongoTag.
 * Contains the information of the MongoTag itself, its creation date and, if needed, the sent time.
 */
public class TagEntry {

    @DBRef
    private MongoTag tag;
    private LocalDateTime creationTime;
    private LocalDateTime sentTime;

    public TagEntry(MongoTag tag) {
        this.tag = tag;
        this.creationTime = LocalDateTime.now();
    }

    public MongoTag getTag() {
        return tag;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    /**
     * Checks if the tag has been sent.
     * @return true if the tag has been sent.
     */
    public boolean isSent() {
        return this.sentTime != null;
    }

    /**
     * Checks if the tag has expired.
     * @return true if the tag has not expired or there is no expiration time.
     */
    public boolean isExpired() {
        if(tag.getTimeToExpiration() == null) {
            return false;
        }
        return LocalDateTime.now().isBefore(getExpirationDate());
    }

    /**
     * Gets the expiration time of the tag.
     * @return expiration time
     */
    public LocalDateTime getExpirationDate() {
        return creationTime.plus(tag.getTimeToExpiration());
    }
}
