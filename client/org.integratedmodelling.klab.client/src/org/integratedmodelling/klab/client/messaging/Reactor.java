package org.integratedmodelling.klab.client.messaging;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.client.messaging.Reactor.ReceiverDescription.MethodDescriptor;
import org.integratedmodelling.klab.monitoring.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Reactor {

	private Map<Class<?>, ReceiverDescription> receiverTypes = Collections.synchronizedMap(new HashMap<>());
	ObjectMapper objectMapper = new ObjectMapper();

	class ReceiverDescription {

		private Map<Class<?>, MethodDescriptor> handlers = new HashMap<>();

		public ReceiverDescription(Class<?> cls) {
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
					if (cls.getPackage().getName().startsWith(IConfigurationService.REST_RESOURCES_PACKAGE_ID)) {
						this.payloadType = cls;
						break;
					}
				}
				if (this.payloadType == null) {
					throw new IllegalStateException(
							"wrong usage of @MessageHandler: the annotated method must take a bean from package "
									+ IConfigurationService.REST_RESOURCES_PACKAGE_ID + " as parameter");
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
					// TODO log error
					System.err.println(e.getStackTrace());
				}
			}
		}

	}
	
	public void dispatchMessage(Message message, Object identity) {

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
			MethodDescriptor mdesc = rdesc.handlers.get(cls);
			if (mdesc != null) {
				Object payload = cls == String.class ? message.getPayload().toString()
						: objectMapper.convertValue(message.getPayload(), cls);
				mdesc.handle(identity, payload, message);
			}

		} catch (Throwable e) {
			// TODO log error
			System.err.println(e.getStackTrace());
		}

	}

}
