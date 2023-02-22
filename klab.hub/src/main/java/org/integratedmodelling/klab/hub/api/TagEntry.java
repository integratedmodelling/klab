package org.integratedmodelling.klab.hub.api;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Case of event by MongoTag.
 * Contains the information of the MongoTag itself, its creation date and, if needed, the sent time.
 */
public class TagEntry {

    @DBRef
    private MongoTag tag;
    private DateTime creationTime;
    private DateTime sentTime;

    public TagEntry(MongoTag tag) {
        this.tag = tag;
        this.creationTime = DateTime.now();
    }

    public MongoTag getTag() {
        return tag;
    }

    public void setSentTime(DateTime sentTime) {
        this.sentTime = sentTime;
    }

    /**
     * Checks if the tag has been sent.
     * @return true if the tag has been sent.
     */
    public boolean isSent() {
        return this.sentTime != null;
    }

}
