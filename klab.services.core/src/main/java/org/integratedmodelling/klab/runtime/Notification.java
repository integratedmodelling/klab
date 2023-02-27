package org.integratedmodelling.klab.runtime;

import java.io.Serializable;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.services.runtime.KNotification;

/**
 * Trivial bean for notifications, so these can be sent outside of the validator and processed in
 * it. The constructors are messy and nasty but cleaning this is low priority.
 * 
 * @author ferdinando.villa
 *
 */
public class Notification implements KNotification, Serializable {

    private static final long serialVersionUID = -5812547783872203517L;

    String message;
    Level level;
    Type type = Type.None;
    long timestamp = System.currentTimeMillis();
    // this will be null when parsed, identities are in the runtime
    String identity;

    public Notification() {
    }

    public Notification(String message, Level level) {
        this.message = message;
        this.level = level;
    }

    public Notification(Pair<String, Type> message, Level level) {
        this.message = message.getFirst();
        this.type = message.getSecond();
        this.level = level;
    }

    public Notification(String message2, Level level2, long timestamp2) {
        this(message2, level2);
        this.timestamp = timestamp2;
    }

    public Notification(Pair<String, Type> message2, Level level2, long timestamp2) {
        this(message2.getFirst(), level2);
        this.timestamp = timestamp2;
        this.type = message2.getSecond();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level.getName();
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getIdentity() {
        return identity;
    }

}
