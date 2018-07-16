package org.integratedmodelling.klab.api.monitoring;

import java.util.function.Consumer;

/**
 * A message bus is created by the runtime and exposed on request. Objects may
 * register with it as receivers. Messages are sent to the bus using the
 * IMonitor API in the engine, which formats any passed object into a proper
 * message.
 * <p>
 * Several implementations may exist, from a local, non-RPC channel to one based
 * on RPC such as multicast or websockets.
 * <p>
 * The ID of the engine or node returned by the capabilities is the main channel
 * prefix for the communicating server. Subchannels will be prefixed by the same
 * ID and separated by slashes.
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
	 * Post a message with a specified response handler. If this one is used, the
	 * subscriber is expected to send a response, which will be handled by the
	 * passed responder when it is sent.
	 * 
	 * @param message
	 * @param responder
	 */
	void post(IMessage message, Consumer<IMessage> responder);

	/**
	 * Return an object that correspond to the identity ID set in the message. If
	 * that object is not null, the implementation will scan the object's class for
	 * methods that can handle the class of the payload in the message and call them
	 * accordingly. Such methods can be private or public and must be annotated with
	 * {@link MessageHandler}. Parameters will be matched according to their
	 * declaration.
	 * 
	 * @param identity
	 * @return a receiver object, or null.
	 */
	Object getReceiver(String identity);

}
