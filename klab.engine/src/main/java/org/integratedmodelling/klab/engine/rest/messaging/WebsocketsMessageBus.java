package org.integratedmodelling.klab.engine.rest.messaging;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.logging.Level;

import javax.annotation.PostConstruct;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.klab.api.monitoring.IMessage.Repeatability;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.engine.rest.messaging.WebsocketsMessageBus.ReceiverDescription.MethodDescriptor;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ferdinando.villa
 *
 */
@RestController
public class WebsocketsMessageBus implements IMessageBus {

    static public String URL = "ws://localhost:8283/modeler/message";

    private Map<String, Set<Object>> receivers = Collections.synchronizedMap(new HashMap<>());
    private static final String UNKNOWN_IDENTITY = "UNKNOWN_IDENTITY";

    class ReceiverDescription {

        private Map<Class<?>, Set<MethodDescriptor>> handlers = new HashMap<>();

        public ReceiverDescription(Class<?> cls) {
            for (Method method : cls.getDeclaredMethods()) {
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    if (annotation instanceof MessageHandler) {
                        MethodDescriptor mdesc = new MethodDescriptor(method, (MessageHandler) annotation);
                        if (!this.handlers.containsKey(mdesc.payloadType)) {
                            this.handlers.put(mdesc.payloadType, new HashSet<>());
                        }
                        this.handlers.get(mdesc.payloadType).add(mdesc);
                    }
                }
            }
            receiverTypes.put(cls, this);
        }

        class MethodDescriptor {

            Method method;
            Class<?> payloadType;
            IMessage.MessageClass mclass = null;
            IMessage.Type mtype = null;

            public MethodDescriptor(Method method, MessageHandler handler) {

                this.method = method;
                this.method.setAccessible(true);
                for (Class<?> cls : method.getParameterTypes()) {
                    if (!IMessage.Type.class.isAssignableFrom(cls) && !IMessage.MessageClass.class.isAssignableFrom(cls)
                            && !IMessage.class.isAssignableFrom(cls)) {
                        this.payloadType = cls;
                        break;
                    }
                }
                if (this.payloadType == null) {
                    throw new IllegalStateException(
                            "wrong usage of @MessageHandler: the annotated method must have a parameter for the payload"
                                    + IConfigurationService.REST_RESOURCES_PACKAGE_ID + " as parameter");
                }
                if (handler.type() != IMessage.Type.Void) {
                    this.mtype = handler.type();
                }
                if (handler.messageClass() != IMessage.MessageClass.Void) {
                    this.mclass = handler.messageClass();
                }
            }

            void handle(Object identity, Object payload, IMessage message) {

                List<Object> params = new ArrayList<>();
                for (Class<?> cls : method.getParameterTypes()) {
                    if (cls.isAssignableFrom(this.payloadType)) {
                        params.add(payload);
                    } else if (cls.isAssignableFrom(IMessage.class)) {
                        params.add(message);
                    } else if (cls.isAssignableFrom(Date.class)) {
                        params.add(new Date(message.getTimestamp()));
                    } else if (cls.isAssignableFrom(MessageClass.class)) {
                        params.add(message.getMessageClass());
                    } else if (cls.isAssignableFrom(IMessage.Type.class)) {
                        params.add(message.getType());
                    } else if (cls.isAssignableFrom(String.class)) {
                        params.add(payload.toString());
                    } else {
                        params.add(null);
                    }
                }

                try {
                    if (identity instanceof Session) {
                        ((Session)identity).touch();
                    }
                    this.method.invoke(identity, params.toArray());
                } catch (Throwable e) {
                    if (e instanceof InvocationTargetException) {
                        e = ((InvocationTargetException) e).getTargetException();
                    }
                    if (identity instanceof IRuntimeIdentity) {
                        ((IRuntimeIdentity) identity).getMonitor().error(e);
                    } else {
                        Logging.INSTANCE.error("error while dispatching message to handler: " + e.getMessage());
                    }
                }
            }

            public boolean appliesTo(Message message) {
                return (mclass == null || mclass == message.getMessageClass())
                        && (mtype == null || mtype == message.getType());
            }
        }

    }

    private Map<Class<?>, ReceiverDescription> receiverTypes = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Consumer<IMessage>> responders = Collections.synchronizedMap(new HashMap<>());
    private Map<String, IMessage> responses = Collections.synchronizedMap(new HashMap<>());
    private Set<String> requests = Collections.synchronizedSet(new HashSet<>());

    public WebsocketsMessageBus() {
    }
    
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private SimpMessagingTemplate webSocket;

    @PostConstruct
    public void publishMessageBus() {
        Logging.INSTANCE.info("Setting up message bus on " + URL);
        Klab.INSTANCE.setMessageBus(this);
    }

    /**
     * This gets messages sent to /klab/message from the remote side.
     * 
     * @param message
     */
    @MessageMapping(API.MESSAGE)
    public void handleTask(Message message) {

        // TODO for now: print out all messages except network status, which clutters
        // the output. This is really important for development but obviously should be removed.
        if (Configuration.INSTANCE.isEchoEnabled() && message.getType() != IMessage.Type.NetworkStatus) {
            System.out.println(JsonUtils.printAsJson(message));
        }

        if (message.getInResponseTo() != null) {

            if (requests.contains(message.getInResponseTo())) {

                requests.remove(message.getInResponseTo());
                responses.put(message.getInResponseTo(), message);
                return;

            } else {

                Consumer<IMessage> responder = message.getRepeatability() == Repeatability.Once
                        ? responders.remove(message.getInResponseTo())
                        : responders.get(message.getInResponseTo());
                        
                if (responder != null) {
                    responder.accept(message);
                    return;
                }
            }
        }

        /*
         * If the identity is known at our end, check if it has a handler for our
         * specific payload type. If so, turn the payload into that and dispatch it.
         */
        IIdentity auth = Authentication.INSTANCE.getIdentity(message.getIdentity(), IIdentity.class);
        if (auth != null) {
            dispatchMessage(message, auth);
        } else {
            post(Message.create(new Notification(UNKNOWN_IDENTITY, Level.SEVERE.getName()), message.getIdentity()));
        }
        /*
         * Any other subscribed object
         */
        for (Object identity : getReceivers(message.getIdentity())) {
            dispatchMessage(message, identity);
        }
    }

    private void dispatchMessage(Message message, Object identity) {

//	    System.out.println("DISPATCHING " + message);
        
        try {
            /*
             * 1. Determine payload type
             */
            Class<?> cls = message.getPayloadClass().equals("String") ? String.class
                    : Class.forName(IConfigurationService.REST_RESOURCES_PACKAGE_ID + "." + message.getPayloadClass());

            /*
             * 2. Determine if the object has a method to react to it, caching the result
             * and the parameter sequence.
             */
            ReceiverDescription rdesc = receiverTypes.get(identity.getClass());
            if (rdesc == null) {
                rdesc = new ReceiverDescription(identity.getClass());
            }

            /*
             * 3. If there is a method, invoke it.
             */
            if (rdesc.handlers.containsKey(cls)) {
                for (MethodDescriptor mdesc : rdesc.handlers.get(cls)) {
                    if (mdesc.appliesTo(message)) {
                        Object payload = cls == String.class ? message.getPayload().toString()
                                : objectMapper.convertValue(message.getPayload(), cls);
                        mdesc.handle(identity, payload, message);
                        if (identity instanceof IMessageBus.Relay) {
                            for (String relayId : ((IMessageBus.Relay) identity).getRelayIdentities()) {
                                post(message.copyWithIdentity(relayId));
                            }
                        }
                    }
                }
            }

        } catch (Throwable e) {
            Logging.INSTANCE.error("internal error: converting payload of message " + message.getId()
                    + "  for payload type " + message.getPayloadClass());
        }

    }

    @Override
    public synchronized void post(IMessage message) {

//	    System.out.println("POSTING " + message);

        try {
            webSocket.convertAndSend(API.MESSAGE + "/" + message.getIdentity(), message);
        } catch (Throwable e) {
            Logging.INSTANCE.error("internal error: posting message " + message.getId()
                    + "  for payload type " + ((Message)message).getPayloadClass());
        }
    }

    @Override
    public synchronized Future<IMessage> ask(IMessage message) {

        requests.add(message.getId());
        webSocket.convertAndSend(API.MESSAGE + "/" + message.getIdentity(), message);
        return new Future<IMessage>() {

            long origin = System.currentTimeMillis();
            IMessage m;
            boolean cancelled;

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                requests.remove(message.getId());
                cancelled = true;
                return true;
            }

            @Override
            public boolean isCancelled() {
                return cancelled;
            }

            @Override
            public boolean isDone() {
                if (responses.containsKey(message.getId())) {
                    m = responses.get(message.getId());
                    requests.remove(message.getId());
                    responses.remove(message.getId());
                }
                return m != null;
            }

            @Override
            public IMessage get() throws InterruptedException, ExecutionException {
                while (true) {
                    if (m != null) {
                        break;
                    } else if (responses.containsKey(message.getId())) {
                        m = responses.get(message.getId());
                        requests.remove(message.getId());
                        responses.remove(message.getId());
                        break;
                    }
                    Thread.sleep(250);
                }
                return m;
            }

            @Override
            public IMessage get(long timeout, TimeUnit unit)
                    throws InterruptedException, ExecutionException, TimeoutException {
                while (true) {
                    if (System.currentTimeMillis() - origin >= unit.toMillis(timeout)) {
                        return null;
                    } else if (m != null) {
                        break;
                    } else if (responses.containsKey(message.getId())) {
                        m = responses.get(message.getId());
                        requests.remove(message.getId());
                        responses.remove(message.getId());
                        break;
                    }
                    Thread.sleep(250);
                }
                return m;
            }

        };
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
            ret = new HashSet<>();
            receivers.put(identity, ret);
        }
        return ret;
    }

    @Override
    public void subscribe(String identity, Object receiver) {
        Set<Object> ret = receivers.get(identity);
        if (ret == null) {
            ret = new HashSet<>();
            receivers.put(identity, ret);
        }
        ret.add(receiver);
    }

    @Override
    public void unsubscribe(String identity) {
        receivers.remove(identity);
    }

    @Override
    public void unsubscribe(String identity, Object receiver) {
        Set<Object> ret = receivers.get(identity);
        if (ret == null) {
            ret = new HashSet<>();
            receivers.put(identity, ret);
        }
        ret.remove(receiver);
    }

}
