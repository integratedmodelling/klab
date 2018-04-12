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

// TODO: Auto-generated Javadoc
/**
 * Typed message with potential payload to be transferred across a multicast connection. Used for
 * fast, duplex engine/client communication.
 * 
 * @author ferdinando.villa
 *
 */
public class Message {

  enum Type {

  }

  private String text;
  private Type   type;
  private Object payload;
  
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
  public Type getType() {
    return type;
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

}
