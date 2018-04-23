package org.integratedmodelling.klab.messaging;

import java.lang.reflect.Type;

import org.integratedmodelling.kim.api.monitoring.IMessage;
import org.integratedmodelling.kim.api.monitoring.IMessageBus;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * A websockets-driven message bus. The messages to be exchanged should be defined in the k.IM package.
 * 
 * @author ferdinando.villa
 *
 */
// see https://github.com/eugenp/tutorials/blob/master/spring-boot/src/main/java/org/baeldung/websocket/client/StompClient.java
public class StompMessageBus extends StompSessionHandlerAdapter implements IMessageBus {

    static String URL = "ws://localhost:8080/klab/ide";
    
    StompSession session;

    public StompMessageBus(String url) {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.connect(url, this);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.session = session;
        //        logger.info("New session established : " + session.getSessionId());
        //        session.subscribe("/topic/messages", this);
        //        logger.info("Subscribed to /topic/messages");
        //        session.send("/app/chat", getSampleMessage());
        //        logger.info("Message sent to websocket server");    
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
            Throwable exception) {
        //        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return null; // Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        //        Message msg = (Message) payload;
        //        logger.info("Received : " + msg.getText() + " from : " + msg.getFrom());
    }

    @Override
    public void post(IMessage message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void subscribe(Receiver receiver, Object... filters) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unsubscribe(Receiver receiver, Object... filters) {
        // TODO Auto-generated method stub

    }

}
