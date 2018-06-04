package org.integratedmodelling.klab.client.messaging;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.monitoring.SubscriberRegistry;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

/**
 * A websockets-driven message bus. The messages to be exchanged should be defined in the k.IM package.
 * 
 * @author ferdinando.villa
 *
 */
public class StompMessageBus extends StompSessionHandlerAdapter implements IMessageBus {

    static String URL = "ws://localhost:8283/modeler/message";

    StompSession session;
    String sessionId;
    SubscriberRegistry registry = new SubscriberRegistry();
    Map<String, Receiver> responders = Collections.synchronizedMap(new HashMap<>());
    
    public StompMessageBus(String url, String sessionId) {
        this.sessionId = sessionId;
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport( new StandardWebSocketClient()) );
        WebSocketClient transport = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            this.session = stompClient.connect(url, this).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.session = session;
        session.subscribe("/message/" + sessionId, this);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
            Throwable exception) {
        // TODO
        System.err.println("Got an exception: " + exception.getMessage());
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message message = (Message) payload;
        if (message.getInResponseTo() != null) {
            Receiver responder = responders.remove(message.getInResponseTo());
            if (responder != null) {
                responder.receive(message);
                return;
            }
        }
        for (Receiver receiver : registry.getSubscribers(message)) {
            IMessage response = receiver.receive(message);
            if (response != null) {
                ((Message) response).setInResponseTo(message.getId());
                post(response);
            }
        }
    }

    @Override
    public void post(IMessage message) {
        session.send("/klab/message", message);
    }

    @Override
    public void subscribe(Receiver receiver, Object... filters) {
        registry.subscribe(receiver, filters);
    }

    @Override
    public void unsubscribe(Receiver receiver, Object... filters) {
        registry.unsubscribe(receiver, filters);
    }
    
    public static void main(String[] args) {
        StompMessageBus bus = new StompMessageBus(URL, null);
        bus.post(Message.create(bus.sessionId, "Zio carbonaro", IMessage.MessageClass.Notification, IMessage.Type.Info));
        while (true) {
        	try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// poh
			}
        }
    }

    @Override
    public void post(IMessage message, Receiver responder) {
        responders.put(((Message)message).getId(), responder);
        post(message);
    }

}
