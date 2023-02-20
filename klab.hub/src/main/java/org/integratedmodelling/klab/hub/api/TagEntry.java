package org.integratedmodelling.klab.hub.api;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.DBRef;

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

    public boolean isSent() {
        return this.sentTime != null;
    }

}
