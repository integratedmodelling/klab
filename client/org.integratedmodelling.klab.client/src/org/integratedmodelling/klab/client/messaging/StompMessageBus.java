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
import java.util.function.Consumer;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.client.utils.JsonUtils;
import org.integratedmodelling.klab.monitoring.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
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
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        // won't happen
    }

    @Override
    public void post(IMessage message) {
        session.send("/klab/message", message);
    }

    @Override
    public void post(IMessage message, Consumer<IMessage> responder) {
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
                        throw new RuntimeException(e);
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
    public void unsubscribe(String identity) {

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

}
