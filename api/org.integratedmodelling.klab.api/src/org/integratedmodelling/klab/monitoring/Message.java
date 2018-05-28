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
package org.integratedmodelling.klab.monitoring;

import java.io.Serializable;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.utils.NameGenerator;

/**
 * Typed message with potential payload to be transferred through a message bus.
 * Used for fast, duplex engine/client communication.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class Message implements IMessage, Serializable {

	private static final long serialVersionUID = 4889814573447834819L;

	private Type type;
	private MessageClass messageClass;
	private String identity;
	private String payloadClass;
	private Object payload;
	private String id = NameGenerator.shortUUID();
	private String inResponseTo;
	private long timestamp = System.currentTimeMillis();

	/**
	 * Build a message by arranging all the arguments appropriately. Only one
	 * payload object can be passed.
	 * 
	 * @param identity
	 * @param o
	 * @return a new message
	 * @throws IllegalArgumentException
	 *             if there are not enough arguments or more than one payload was
	 *             passed
	 */
	public static Message create(String identity, Object... o) {
		Message ret = new Message();
		ret.identity = identity;
		for (Object ob : o) {
			if (ob instanceof Type) {
				ret.type = (Type) ob;
			} else if (ob instanceof MessageClass) {
				ret.messageClass = (MessageClass) ob;
			} else {
				if (ret.payload == null) {
					ret.payload = ob;
					ret.payloadClass = ob.getClass().getCanonicalName();
				} else {
					throw new IllegalArgumentException("payload already set: too many arguments");
				}
			}
		}
		return ret;
	}

	/**
	 * Build a message from a standard {@link INotification} and an identity.
	 * 
	 * @param notification
	 * @param identity
	 * @return a new message
	 */
	public static IMessage create(INotification notification, String identity) {
		
		Message ret = new Message();
		ret.identity = identity;
		ret.messageClass = MessageClass.Notification;
		ret.payload = notification.getMessage();
		ret.payloadClass = "String";
		
		if (notification.getLevel() == Level.FINE) {
			ret.type = Type.Debug;
		} else if (notification.getLevel() == Level.INFO) {
			ret.type = Type.Info;
		} else if (notification.getLevel() == Level.WARNING) {
			ret.type = Type.Warning;
		} else if (notification.getLevel() == Level.SEVERE) {
			ret.type = Type.Error;
		}
		
		return ret;
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
	 * @param type
	 *            the new type
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
	 * @param payload
	 *            the new payload
	 */
	public void setPayload(Object payload) {
		this.payload = payload;
	}

	@Override
	public String getIdentity() {
		return identity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInResponseTo() {
		return inResponseTo;
	}

	public void setInResponseTo(String inResponseTo) {
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
}
