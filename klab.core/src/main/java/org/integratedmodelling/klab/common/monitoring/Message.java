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
package org.integratedmodelling.klab.common.monitoring;

import org.integratedmodelling.kim.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.auth.IIdentity;

/**
 * Typed message with potential payload to be transferred across a multicast connection. Used for
 * fast, duplex engine/client communication.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class Message implements IMessage {

    private String text;
    private Type type;
    private MessageClass messageClass;
    private String identity;
    private Object payload;

    /**
     * 
     * @param cls
     * @param type
     * @param payload
     * @return a new message
     */
    public static Message create(MessageClass cls, Type type, Object payload) {
        return null;
    }

    /**
     * 
     * @param cls
     * @param type
     * @param identity
     * @param payload
     * @return a new message
     */
    public static Message create(MessageClass cls, Type type, IIdentity identity, Object payload) {
        return null;
    }

    /**
     * Build a message by arranging all the arguments appropriately (recognize identities and 
     * types, and ensure there is one payload by assembling >1 into a collection).
     * 
     * @param identity 
     * @param o
     * @return a new message
     * @throws IllegalArgumentException if there are not enough arguments
     */
    public static IMessage buildMessage(IIdentity identity, Object[] o) {
        // TODO Auto-generated method stub
        return null;
    }

    
    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text the new text
     */
    public void setText(String text) {
        this.text = text;
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


}
