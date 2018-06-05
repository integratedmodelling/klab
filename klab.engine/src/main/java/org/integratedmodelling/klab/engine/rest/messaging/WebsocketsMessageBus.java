package org.integratedmodelling.klab.engine.rest.messaging;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.engine.rest.messaging.WebsocketsMessageBus.ReceiverDescription.MethodDescriptor;
import org.integratedmodelling.klab.monitoring.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class WebsocketsMessageBus implements IMessageBus {

	static public String URL = "ws://localhost:8283/modeler/message";
	
	class ReceiverDescription {

		public ReceiverDescription(Class<? extends IIdentity> cls) {
			for (Method method : cls.getDeclaredMethods()) {
				for (Annotation annotation : method.getDeclaredAnnotations()) {
					if (annotation instanceof MessageHandler) {
						MethodDescriptor mdesc = new MethodDescriptor(method, (MessageHandler) annotation);
						this.handlers.put(mdesc.payloadType, mdesc);
					}
				}
			}
			receiverTypes.put(cls, this);
		}

		class MethodDescriptor {

			Method method;
			Class<?> payloadType;

			public MethodDescriptor(Method method, MessageHandler handler) {
				
				this.method = method;
				this.method.setAccessible(true);
				for (Class<?> cls : method.getParameterTypes()) {
					if (cls.getPackage().getName().startsWith(Klab.REST_RESOURCES_PACKAGE_ID)) {
						this.payloadType = cls;
						break;
					}
				}
				if (this.payloadType == null) {
					throw new IllegalStateException(
							"wrong usage of @MessageHandler: the annotated method must take a bean from package "
									+ Klab.REST_RESOURCES_PACKAGE_ID + " as parameter");
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
					this.method.invoke(identity, params.toArray());
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					Logging.INSTANCE.error("error while dispatching message to handler: " + e.getMessage());
				}
			}
		}

		Map<Class<?>, MethodDescriptor> handlers = new HashMap<>();
	}

	private Map<Class<?>, ReceiverDescription> receiverTypes = Collections.synchronizedMap(new HashMap<>());
//	private SubscriberRegistry registry = new SubscriberRegistry();
	private Map<String, Consumer<IMessage>> responders = Collections.synchronizedMap(new HashMap<>());

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
	 * This gets messages sent to /klab/message from the javascript side of the
	 * dataviewer.
	 * 
	 * @param message
	 */
	@MessageMapping(API.MESSAGE)
	public void handleTask(Message message) {

//		System.out.println(JsonUtils.printAsJson(message));

		if (message.getInResponseTo() != null) {
			Consumer<IMessage> responder = responders.remove(message.getInResponseTo());
			if (responder != null) {
				responder.accept(message);
				return;
			}
		}

		/*
		 * If the identity is known at our end, check if it has a handler for our
		 * specific payload type. If so, turn the payload into that and dispatch it.
		 */

		IIdentity identity = Auth.INSTANCE.getIdentity(message.getIdentity(), IIdentity.class);
		if (identity != null) {
			dispatchMessage(message, identity);
		}

//		for (Receiver receiver : registry.getSubscribers(message)) {
//			IMessage response = receiver.receive(message);
//			if (response != null) {
//				((Message) response).setInResponseTo(message.getId());
//				post(response);
//			}
//		}
	}

	private void dispatchMessage(Message message, IIdentity identity) {

		try {
			/*
			 * 1. Determine payload type
			 */
			Class<?> cls = Class.forName(Klab.REST_RESOURCES_PACKAGE_ID + "." + message.getPayloadClass());

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
			MethodDescriptor mdesc = rdesc.handlers.get(cls);
			if (mdesc != null) {
				Object payload = objectMapper.convertValue(message.getPayload(), cls);
				mdesc.handle(identity, payload, message);
			}

		} catch (Throwable e) {
			Logging.INSTANCE.error("internal error: converting payload of message " + message.getId()
					+ "  for payload type " + message.getPayloadClass());
		}

	}

	@Override
	public void post(IMessage message) {
		webSocket.convertAndSend(API.MESSAGE + "/" + message.getIdentity(), message);
	}

	@Override
	public void post(IMessage message, Consumer<IMessage> responder) {
		responders.put(((Message) message).getId(), responder);
		post(message);
	}

//	@Override
//	public void subscribe(Receiver receiver, Object... filters) {
//		registry.subscribe(receiver, filters);
//	}
//
//	@Override
//	public void unsubscribe(Receiver receiver, Object... filters) {
//		registry.unsubscribe(receiver, filters);
//	}
}
