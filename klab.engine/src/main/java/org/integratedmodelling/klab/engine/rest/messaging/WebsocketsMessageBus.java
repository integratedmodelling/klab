package org.integratedmodelling.klab.engine.rest.messaging;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.integratedmodelling.kim.api.monitoring.IMessage;
import org.integratedmodelling.kim.api.monitoring.IMessageBus;
import org.integratedmodelling.kim.api.monitoring.IMessageBus.Receiver;
import org.integratedmodelling.kim.monitoring.Message;
import org.integratedmodelling.kim.monitoring.SubscriberRegistry;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.engine.rest.client.StompMessageBus;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsocketsMessageBus implements IMessageBus {

    private SubscriberRegistry registry = new SubscriberRegistry();
    private Map<String, Receiver> responders = Collections.synchronizedMap(new HashMap<>());
    
    @Autowired
    private SimpMessagingTemplate webSocket;

    @PostConstruct
    public void publishMessageBus() {
        Logging.INSTANCE.info("Setting up message bus on " + StompMessageBus.URL);
        Klab.INSTANCE.setMessageBus(this);
        
    }
    
    /**
     * This gets messages sent to /klab/message from the javascript
     * side of the dataviewer.
     * 
     * @param message
     */
    @MessageMapping(API.MESSAGE)
    public void handleTask(Message message) {
        System.out.println(JsonUtils.printAsJson(message));
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
        webSocket.convertAndSend("/message/" + message.getIdentity(), message);
    }

    @Override
    public void post(IMessage message, Receiver responder) {
        responders.put(((Message)message).getId(), responder);
        post(message);
    }

    @Override
    public void subscribe(Receiver receiver, Object... filters) {
        registry.subscribe(receiver, filters);
    }

    @Override
    public void unsubscribe(Receiver receiver, Object... filters) {
        registry.unsubscribe(receiver, filters);
    }
}
