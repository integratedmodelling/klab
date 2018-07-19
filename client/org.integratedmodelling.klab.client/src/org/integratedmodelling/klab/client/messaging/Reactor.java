package org.integratedmodelling.klab.client.messaging;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.client.messaging.Reactor.ReceiverDescription.MethodDescriptor;
import org.integratedmodelling.klab.monitoring.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Reactor {

	private Map<Class<?>, ReceiverDescription> receiverTypes = Collections.synchronizedMap(new HashMap<>());
	ObjectMapper objectMapper = new ObjectMapper();
	IMessageBus bus;

	public Reactor(IMessageBus bus) {
		this.bus = bus;
	}

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
					} else if (IMessageBus.class.isAssignableFrom(cls)) {
						params.add(bus);
					} else {
						params.add(null);
					}
				}

				try {
					Object ret = this.method.invoke(identity, params.toArray());
					if (ret instanceof Boolean && !((Boolean) ret)) {
						bus.unsubscribe(message.getIdentity());
					}
				} catch (Throwable e) {
					// TODO log, don't throw
					throw new RuntimeException("error while dispatching message to handler: " + e.getMessage());
				}
			}

			public boolean appliesTo(Message message) {
				return (mclass == null || mclass == message.getMessageClass())
						&& (mtype == null || mtype == message.getType());
			}
		}
	}

	public synchronized void dispatchMessage(Message message, Object identity) {

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
					}
				}
			}

		} catch (Throwable e) {
			throw new RuntimeException("internal error: converting payload of message " + message.getId()
			+ "  for payload type " + message.getPayloadClass());
		}

	}

}
