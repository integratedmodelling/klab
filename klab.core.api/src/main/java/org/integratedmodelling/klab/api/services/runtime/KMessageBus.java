package org.integratedmodelling.klab.api.services.runtime;

import java.util.Collection;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * A message bus is created by the runtime linked to a specific identity and
 * exposed on request. Additional objects can be register with it as receivers.
 * Messages are sent to the bus using the IMonitor API in the engine, which
 * formats any passed object into a proper message. In a back-end message bus,
 * the running application should have a specific object tied to the identity,
 * and automatically create a subscription for the identity of reference.
 * <p>
 * Several implementations may exist, from a local, non-RPC channel to one based
 * on RPC such as multicast or websockets. A client-side bus will simply "tune
 * in" to the back-end identity using its ID and may subscribe any reactors it
 * wishes to see messages from it.
 * <p>
 * A full-fledged engine client will probably have multiple buses and handle
 * them according to the posting/receiving identity.
 * 
 * @author ferdinando.villa
 *
 */
public interface KMessageBus {

    /**
     * Any receiver implementing the Relay interface will relay the messages it gets
     * to a set of identities. This can be used by a receiver to let an external
     * receiver serve as a double or snoop on communication.
     * 
     * @author ferdinando.villa
     *
     */
    public interface Relay {

        /**
         * Identities to resend our messages to.
         */
        Collection<String> getRelayIdentities();
    }

    /**
     * Post a message asynchronously. A response may or may not be sent. Any
     * subscribers will be notified. If get() is called on the result and a
     * response is not sent, the calling side may deadlock.
     * 
     * @param message
     */
    void post(KMessage message);

    /**
     * Post a message asynchronously and return a future to access the response message. 
     * If get() is called on the result and a response is not sent, the calling side may deadlock.
     * 
     * @param message
     */
    Future<KMessage> ask(KMessage message);

    /**
     * Post a message with a specified response handler. If this one is used, the
     * subscriber is expected to send a response, which will be handled by the
     * passed responder when it is sent.
     * 
     * @param message
     * @param responder
     */
    void post(KMessage message, Consumer<KMessage> responder);

    /**
     * Return any objects that subscribed with the identity ID set in the message.
     * If that object is not null, the implementation will scan the object's class
     * for methods that can handle the class of the payload in the message and call
     * them accordingly. Such methods can be private or public and must be annotated
     * with {@link MessageHandler}. Parameters will be matched according to their
     * declaration.
     * 
     * @param identity
     * @return a receiver object, or null.
     */
    Collection<Object> getReceivers(String identity);

    /**
     * Explicitly subscribe an object to the message bus. Will use its annotated
     * methods and parameters to dispatch messages.
     * 
     * @param identity
     * @param receiver
     * @see MessageHandler
     */
    void subscribe(String identity, Object receiver);

    void unsubscribe(String identity);

    void unsubscribe(String identity, Object receiver);

}
