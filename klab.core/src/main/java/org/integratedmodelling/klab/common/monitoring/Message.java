package org.integratedmodelling.klab.common.monitoring;

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
  
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public Type getType() {
    return type;
  }
  public void setType(Type type) {
    this.type = type;
  }
  public Object getPayload() {
    return payload;
  }
  public void setPayload(Object payload) {
    this.payload = payload;
  }

}
