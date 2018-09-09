/*
 * TokenizerPropertyEvent.java: tokenizer property change event.
 *
 * Copyright (C) 2002 Heiko Blau
 *
 * This file belongs to the JTopas Library.
 * JTopas is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation; either version 2.1 of the License, or (at your 
 * option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with JTopas. If not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330, 
 *   Boston, MA 02111-1307 
 *   USA
 *
 * or check the Internet: http://www.fsf.org
 *
 * Contact:
 *   email: heiko@susebox.de 
 */

package org.integratedmodelling.contrib.jtopas;

//-----------------------------------------------------------------------------
// Imports
//


//-----------------------------------------------------------------------------
// class TokenizerPropertyEvent
//

/**<p>
 * The class <code>TokenizerPropertyEvent</code> describes changes in
 * {@link TokenizerProperties} objects. Instances of this class are produced by
 * <code>TokenizerProperties</code> and passed to {@link TokenizerPropertyListener}
 * objects that are registered with the issuing <code>TokenizerProperties</code>
 * instance.
 *</p>
 *
 * @see     TokenizerProperties
 * @see     TokenizerPropertyListener
 * @author  Heiko Blau
 */
public class TokenizerPropertyEvent {
  
  //---------------------------------------------------------------------------
  // constants
  //
  
  /**
   * A property has been added.
   */
  public static final byte PROPERTY_ADDED = 1;
  
  /**
   * A property has been removed.
   */
  public static final byte PROPERTY_REMOVED = 2;
  
  /**
   * A property has been modified.
   */
  public static final byte PROPERTY_MODIFIED = 3;
  
  
  //---------------------------------------------------------------------------
  // constructors
  //
  
  /**
   * The standard constructor initializes an emtpy event.
   */
  public TokenizerPropertyEvent() {
    this(0, null);
  }
  
  /**
   * This constructor takes the type of the event and the {@link TokenizerProperty}
   * that changed.
   *
   * @param type        type of the event, one of the <code>PROPERTY_...</code> constants
   * @param property    the property that was added, removed or the new value of a modified one
   */
  public TokenizerPropertyEvent(int type, TokenizerProperty property) {
    setType(type);
    setProperty(property);
    setOldProperty(null);
  }
  
  /**
   * This constructor takes the type of the event and the {@link TokenizerProperty}
   * that changed together with its old value.
   *
   * @param type        type of the event, one of the <code>PROPERTY_...</code> constants
   * @param property    the property that was added, removed or the new value of a modified one
   * @param oldProperty the old value of the property that modified
   */
  public TokenizerPropertyEvent(int type, TokenizerProperty property, TokenizerProperty oldProperty) {
    setType(type);
    setProperty(property);
    setOldProperty(null);
  }
  
  //---------------------------------------------------------------------------
  // getter and setter methods
  //
  
  /**
   * Setting the event type. Use one of the <code>PROPERTY_...</code> constants
   * for the parameter.
   *
   * @param type  the event type
   * @see   #getType
   * @see   #PROPERTY_ADDED
   * @see   #PROPERTY_REMOVED
   * @see   #PROPERTY_MODIFIED
   */
  public void setType(int type) {
    _type = type;
  }
  
  /**
   * Retrieving the event type. This will usually be one of the <code>PROPERTY_...</code>
   * constants.
   *
   * @return  the type of the event
   */
  public int getType() {
    return _type;
  }
  
  /**
   * Setting the {@link TokenizerProperty} the event is about.
   *
   * @param property  the added, removed or modified {@link TokenizerProperty}
   */
  public void setProperty(TokenizerProperty property) {
    _property = property;
  }
  
  /**
   * Retrieving the property this event is reporting.
   *
   * @return the {@link TokenizerProperty} that is added, removed or modified
   */
  public TokenizerProperty getProperty() {
    return _property;
  }

  /**
   * Setting the {@link TokenizerProperty} that was changed. This is a valid
   * method for the event {@link #PROPERTY_MODIFIED}.
   *
   * @param property  the previously set {@link TokenizerProperty}
   */
  public void setOldProperty(TokenizerProperty property) {
    _oldProperty = property;
  }
  
  /**
   * Retrieving the property that was set before the modification. The method
   * returns <code>null</code> when the event is <strong>NOT</strong> {@link #PROPERTY_MODIFIED}.
   *
   * @return the {@link TokenizerProperty} set before the modification
   */
  public TokenizerProperty getOldProperty() {
    return _oldProperty;
  }

  //---------------------------------------------------------------------------
  // overloaded methods
  //
  
  /**
   * Redefinition of the well-known {@link java.lang.Object#equals} method.
   *
   * @param   that  compare this instance with that object
   * @return  <code>true</code> if the two object describe the same property,
   *          <code>false</code> otherwise
   */
  public boolean equals(Object that) {
    // primitive tests
    if (that == null) {
      return false;
    } else if (that == this) {
      return true;
    } else if ( ! (that instanceof TokenizerPropertyEvent)) {
      return false;
    }
    
    // compare contents
    TokenizerPropertyEvent thatEvent   = (TokenizerPropertyEvent)that;
    TokenizerProperty      thatProp    = thatEvent.getProperty();
    TokenizerProperty      thatOldProp = thatEvent.getOldProperty();
    
    if (   getType() == thatEvent.getType()
        && (_property == thatProp || (_property != null && _property.equals(thatProp)))
        && (_oldProperty == thatOldProp || (_oldProperty != null && _oldProperty.equals(thatOldProp)))) {
      return true;
    } else {
      return false;
    }
  }       
    
  /**
   * Redefinition of the well-known {@link java.lang.Object#toString} method.
   *
   * @return a string representation of this <code>TokenizerProperty</code>
   */
  public String toString() {
    StringBuffer  buffer = new StringBuffer();
    
    buffer.append(getClass().getName());
    buffer.append(": ");

    switch (_type) {
    case PROPERTY_ADDED:
      buffer.append("added ");
      break;
    case PROPERTY_REMOVED:
      buffer.append("removed ");
      break;
    case PROPERTY_MODIFIED:
      buffer.append("modified ");
      break;
    default:
      buffer.append("<unknown type> ");
    }
    
    if (getProperty() != null) {
      buffer.append(getProperty().toString());
    } else {
      buffer.append("<no property>");
    }
    return buffer.toString();
  }
  
  //---------------------------------------------------------------------------
  // members
  //
  private int               _type         = 0;
  private TokenizerProperty _property     = null;
  private TokenizerProperty _oldProperty  = null;
}
