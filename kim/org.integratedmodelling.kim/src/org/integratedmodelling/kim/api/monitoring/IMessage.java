package org.integratedmodelling.kim.api.monitoring;

/**
 * Messages 
 * 
 * @author ferdinando.villa
 *
 */
public interface IMessage {

    /**
     * Message class. Ugly type name makes life easier. 
     * 
     * @author ferdinando.villa
     *
     */
    enum MessageClass {
        LOGGING,
        ENGINE,
        AUTH,
        TASK
    }
    
    /**
     * Message type within its class.
     * 
     * @author ferdinando.villa
     *
     */
    enum Type {
        /*
         * LOGGING-class types
         */
        DEBUG,
        INFO,
        WARNING,
        ERROR
        // TODO k.LAB specific types
    }
    
    /**
     * The message exposes the identity that created it through a token, which may 
     * or may not be parseable at the receiving end but will be consistently linked
     * to the message type. For example, task messages will have the identity of the
     * task that generated them so they can be correctly distributed among tasks.
     * 
     * @return the sender's identity. Never null.
     */
    String getIdentity();
    
    /**
     * 
     * @return the message class
     */
    MessageClass getMessageClass();
    
    /**
     * 
     * @return the message type
     */
    Type getType();
}
