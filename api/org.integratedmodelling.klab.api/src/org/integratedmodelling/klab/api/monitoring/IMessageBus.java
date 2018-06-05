package org.integratedmodelling.klab.api.monitoring;

import java.util.function.Consumer;

import javax.sound.midi.Receiver;

import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;

/**
 * A message bus is created by the runtime and exposed on request. Objects may
 * register with it as receivers. Messages are sent to the bus using the IMonitor
 * API in the engine, which formats any passed object into a proper message.
 * <p>
 * Several implementations may exist, from a local, non-RPC channel to one based on RPC such as 
 * multicast or websockets.
 * <p>
 * The ID of the engine or node returned by the capabilities is the main channel prefix for the
 * communicating server. Subchannels will be prefixed by the same ID and separated by slashes.
 * 
 * @author ferdinando.villa
 *
 */
public interface IMessageBus {

    /**
     * Post a message.
     * 
     * @param message
     */
    void post(IMessage message);

    /**
     * Post a message with a specified response handler. If this one is 
     * used, the subscriber is expected to send a response, which will
     * be handled by the passed responder when it is sent.
     * 
     * @param message
     * @param responder 
     */
    void post(IMessage message, Consumer<IMessage> responder);
    
//    /**
//     * Subscribe a receiver. Additional parameters may be used to filter the 
//     * messages received.
//     * 
//     * @param receiver
//     * @param filters can be one or more strings for a channel, one or more {@link Type types} and/or 
//     *        one or more {@link MessageClass classes}.
//     */
//    void subscribe(Receiver receiver, Object... filters);
//
//    /**
//     * Unsubscribe the receiver. Additional parameters may be used to only unsubscribe from 
//     * specific types of message.
//     * 
//     * @param receiver
//     * @param filters 
//     */
//    void unsubscribe(Receiver receiver, Object... filters);

}
