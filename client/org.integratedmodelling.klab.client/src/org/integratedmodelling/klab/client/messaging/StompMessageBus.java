package org.integratedmodelling.klab.client.messaging;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.client.utils.JsonUtils;
import org.integratedmodelling.klab.monitoring.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.ConnectionLostException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSession.Subscription;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A websockets-driven message bus. The messages to be exchanged should be
 * defined in the k.IM package.
 * 
 * FIXME this should be revised so that multiple identities can be subscribed.
 * At the moment it allows subscribers with different identities but only
 * handles STOMP messages from a single one.
 * 
 * @author ferdinando.villa
 *
 */
public class StompMessageBus extends StompSessionHandlerAdapter implements IMessageBus {

	// private static final String URL = "ws://localhost:8283/modeler/message";
	private static final Set<Object> emptySet = new HashSet<Object>();
	private static final Logger logger = LoggerFactory.getLogger(StompMessageBus.class);

	private ObjectMapper objectMapper = new ObjectMapper()
			// I'll never understand why this shit isn't enabled by default
			.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	private StompSession session;
	private Map<String, Consumer<IMessage>> responders = Collections.synchronizedMap(new HashMap<>());
	private Map<String, Set<Object>> receivers = Collections.synchronizedMap(new HashMap<>());
	private Map<String, Subscription> subscriptions = Collections.synchronizedMap(new HashMap<>());
	private Reactor reactor = new Reactor(this);

	public StompMessageBus(String url) {

		/*
		 * Took two days to figure the next three lines out.
		 */
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		container.setDefaultMaxBinaryMessageBufferSize(1024 * 1024);
		container.setDefaultMaxTextMessageBufferSize(1024 * 1024);
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient(container)));
		SockJsClient transport = new SockJsClient(transports);

		/*
		 * and three more for the next one
		 */
		transport.setMessageCodec(new Jackson2SockJsMessageCodec(objectMapper));
		WebSocketStompClient stompClient = new WebSocketStompClient(transport);
		stompClient.setInboundMessageSizeLimit(1024 * 1024);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		try {
			this.session = stompClient.connect(url, this).get();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		throw new RuntimeException("STOMP exception: " + exception.getMessage());
	}

	@Override
	public void handleTransportError(StompSession session, Throwable throwable) {
		if (throwable instanceof ConnectionLostException) {
			// if connection lost, call this
			// error("Connection lost.");
			logger.warn("Connection lost: " + throwable);
		} else {
			// error("Unknown message transport error. Please report the error.");
			logger.warn("Unknown message transport error: "+ throwable.getMessage());
		}
		super.handleTransportError(session, throwable);
	}

	/**
	 * Override for error handling.
	 */
	protected void error(String string) {
		logger.error("ERROR: " + string);
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return Message.class;
	}

	@Override
	public synchronized void handleFrame(StompHeaders headers, Object payload) {
		// won't happen
	    logger.warn("stomp message bus: what won't happen happened");
	}

	@Override
	public synchronized void post(IMessage message) {
		session.send("/klab/message", message);
	}

	@Override
	public synchronized void post(IMessage message, Consumer<IMessage> responder) {
		responders.put(((Message) message).getId(), responder);
		post(message);
	}

	@Override
	public Collection<Object> getReceivers(String identity) {
		Set<Object> ret = receivers.get(identity);
		if (ret == null) {
			return emptySet;
		}
		return ret;
	}

	@Override
	public void subscribe(String identity, Object receiver) {

		Set<Object> ret = receivers.get(identity);

		if (ret == null) {
			ret = new HashSet<>();
			Subscription subscription = session.subscribe("/message/" + identity, new StompFrameHandler() {

				@Override
				public synchronized void handleFrame(StompHeaders arg0, Object payload) {

					try {

						final Message message = (Message) payload;

//						System.err.println("received payload of type " + message.getPayloadClass() + ", size="
//								+ (payload == null ? 0 : payload.toString().length()) + " with mclass = " + message.getMessageClass());

						/*
						 * No automatic translation at the receiving end
						 */
						if (message.getPayload() instanceof Map && message.getPayloadClass() != null) {
							message.setPayload(JsonUtils.convert(message.getPayload(),
									Class.forName(IConfigurationService.REST_RESOURCES_PACKAGE_ID + "."
											+ message.getPayloadClass())));
						}

						new Thread() {

							@Override
							public void run() {

								if (message.getInResponseTo() != null) {
									Consumer<IMessage> responder = responders.remove(message.getInResponseTo());
									if (responder != null) {
										responder.accept(message);
										return;
									}
								}

								for (Object identity : getReceivers(message.getIdentity())) {
									reactor.dispatchMessage(message, identity);
								}
							}
						}.start();

					} catch (Throwable e) {
						error("Internal: websockets communication error: " + e);
						// throw new RuntimeException(e);
					}
				}

				@Override
				public Type getPayloadType(StompHeaders arg0) {
					return Message.class;
				}
			});

			subscriptions.put(identity, subscription);
			receivers.put(identity, ret);
		}
		ret.add(receiver);
	}

	@Override
	public synchronized void unsubscribe(String identity) {

		if (subscriptions.containsKey(identity)) {
			subscriptions.get(identity).unsubscribe();
			subscriptions.remove(identity);
		}
		receivers.remove(identity);
	}

	@Override
	public void unsubscribe(String identity, Object receiver) {
		Set<Object> ret = receivers.get(identity);
		if (ret != null) {
			ret.remove(receiver);
			if (ret.isEmpty()) {
				unsubscribe(identity);
			}
		}
	}

	public void stop() {
		if (session.isConnected()) {
			for (Subscription subscription : subscriptions.values()) {
				subscription.unsubscribe();
			}
		}
		subscriptions.clear();
	}

	@Override
	public Future<IMessage> ask(IMessage message) {
		final CompletableFuture<IMessage> ret = new CompletableFuture<>();
		post(message, (m)-> ret.complete(m));
		return ret;
	}

}
