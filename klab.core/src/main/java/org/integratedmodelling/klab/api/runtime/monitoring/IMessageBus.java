package org.integratedmodelling.klab.api.runtime.monitoring;

/**
 * A message bus is created by the runtime and exposed on request. Objects may
 * register with it as receivers. Messages are sent to the bus using {@link IMonitor#send(Object...)}, 
 * which formats any passed object into a proper message. Subscribers can 
 * @author ferdinando.villa
 *
 */
public interface IMessageBus {

    /**
     * Make any object a receiver by implementing {@code Receiver}
     * @author ferdinando.villa
     *
     */
    public interface Receiver {
        
        /**
         * Receives any message that matches the filters for this receiver.
         * 
         * @param message
         */
        void message(IMessage message);
    }
    
    public interface Filter {
        
    }
    
    /**
     * Subscribe a receiver. Additional parameters may be used to filter the 
     * messages received.
     * 
     * @param receiver
     * @param filters 
     */
    void subscribe(Receiver receiver, Object... filters);
    
    /**
     * Unsubscribe the receiver. Additional parameters may be used to only unsubscribe from 
     * specific types of message.
     * 
     * @param receiver
     * @param filters 
     */
    void unsubscribe(Receiver receiver, Object... filters);

}
