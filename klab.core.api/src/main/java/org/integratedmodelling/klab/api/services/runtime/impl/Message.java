/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.services.runtime.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.services.runtime.KMessage;
import org.integratedmodelling.klab.api.services.runtime.KNotification;
import org.integratedmodelling.klab.api.utils.Utils;

/**
 * Typed message with potential payload to be transferred through a message bus.
 * Used for fast, duplex engine/client communication.
 * <p>
 * Payloads that are maps can be optionally translated to
 * implementation-dependent types by supplying a static translator function.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class Message implements KMessage, Serializable {

	private static final long serialVersionUID = 4889814573447834819L;

	private static AtomicLong nextId = new AtomicLong(0l);
	
	private Type type;
	private MessageClass messageClass;
	private String identity;
	private String payloadClass;
	private Object payload;
	private long id = nextId.incrementAndGet();
	private long inResponseTo;
	private KNotification.Type notificationType;
	private long timestamp = System.currentTimeMillis();
	private Repeatability repeatability = Repeatability.Once;

	private static BiFunction<Map<?, ?>, Class<?>, Object> translator;

	public static void setPayloadMapTranslator(BiFunction<Map<?, ?>, Class<?>, Object> function) {
		translator = function;
	}

	/**
	 * Build a message by arranging all the arguments appropriately. Only one
	 * payload object can be passed.
	 * 
	 * @param identity
	 * @param o
	 * @return a new message
	 * @throws IllegalArgumentException if there are not enough arguments or more
	 *                                  than one payload was passed
	 */
	public static Message create(String identity, Object... o) {
		Message ret = new Message();
		ret.identity = identity;
		KNotification.Type notype = null;
		for (Object ob : o) {
			if (ob instanceof Type) {
				ret.type = (Type) ob;
			} else if (ob instanceof MessageClass) {
				ret.messageClass = (MessageClass) ob;
			} else if (ob instanceof KNotification.Type) {
				notype = (KNotification.Type) ob;
			} else if (ob instanceof Repeatability) {
				ret.repeatability = (Repeatability)ob;
			}  else if (ob instanceof KNotification) {
               notype = ((KNotification)ob).getType();
               ret.payload = ((KNotification)ob).getMessage();
            } else if (ob != null) {
				if (ret.payload == null) {
					ret.payload = ob;
					ret.payloadClass = Utils.Paths.getLast(ob.getClass().getCanonicalName(), '.');
				} else {
					throw new IllegalArgumentException("payload already set: too many arguments");
				}
			}
		}

		// defaults so that we can just post a string
		if (ret.messageClass == null) {
			ret.messageClass = MessageClass.Notification;
			if (ret.type == null) {
				ret.type = Type.Info;
			}
		}
		ret.setNotificationType(notype);

		return ret;
	}

	/**
	 * Build a message from a standard {@link INotification} and an identity.
	 * 
	 * @param notification
	 * @param identity
	 * @return a new message
	 */
	public static Message create(KNotification notification, String identity) {

		Message ret = new Message();
		ret.identity = identity;
		ret.messageClass = MessageClass.Notification;
		ret.payload = notification.getMessage();
		ret.payloadClass = "String";

		if (notification.getLevel().equals(Level.FINE.getName())) {
			ret.type = Type.Debug;
		} else if (notification.getLevel().equals(Level.INFO.getName())) {
			ret.type = Type.Info;
		} else if (notification.getLevel().equals(Level.WARNING.getName())) {
			ret.type = Type.Warning;
		} else if (notification.getLevel().equals(Level.SEVERE.getName())) {
			ret.type = Type.Error;
		}

		return ret;
	}

	@Override
	public String toString() {
		return "{" + identity + ": " + messageClass + "/" + type + ": " + payload + "}";
	}

	public Message inResponseTo(KMessage message) {
		this.inResponseTo = ((Message) message).id;
		return this;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public MessageClass getMessageClass() {
		return messageClass;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Gets the payload.
	 *
	 * @return the payload
	 */
	public Object getPayload() {
		return payload;
	}

	/**
	 * Sets the payload.
	 *
	 * @param payload the new payload
	 */
	public void setPayload(Object payload) {
		this.payload = payload;
	}

	@Override
	public String getIdentity() {
		return identity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getInResponseTo() {
		return inResponseTo;
	}

	public Message inResponseTo(long inResponseTo) {
		this.inResponseTo = inResponseTo;
		return this;
	}

	public void setInResponseTo(long inResponseTo) {
		this.inResponseTo = inResponseTo;
	}

	public void setMessageClass(MessageClass messageClass) {
		this.messageClass = messageClass;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getPayloadClass() {
		return payloadClass;
	}

	public void setPayloadClass(String payloadClass) {
		this.payloadClass = payloadClass;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Make an exact copy of this message using a different identity. Used for
	 * relaying.
	 * 
	 * @param relayId
	 * @return a new message identified by relayId
	 */
	public Message copyWithIdentity(String relayId) {
		Message ret = new Message();
		ret.identity = relayId;
		ret.messageClass = this.messageClass;
		ret.payload = this.payload;
		ret.payloadClass = this.payloadClass;
		ret.type = this.type;
		ret.inResponseTo = this.inResponseTo;
		ret.timestamp = this.timestamp;
		return ret;
	}

	@Override
	public <T> T getPayload(Class<? extends T> cls) {

		if (payload == null) {
			return null;
		}

		Object p = payload;

		if (payload instanceof Map && translator != null) {
			p = translator.apply((Map<?, ?>) p, cls);
		}

		return Utils.Data.asType(p, cls);
	}

	public KNotification.Type getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(KNotification.Type notificationType) {
		this.notificationType = notificationType;
	}

	@Override
	public Repeatability getRepeatability() {
		return repeatability;
	}

	public void setRepeatability(Repeatability repeatability) {
		this.repeatability = repeatability;
	}
}
