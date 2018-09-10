/*
 * TokenizerPropertyListener.java: tokenizer property change event handler.
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
// Interface TokenizerPropertyListener
//

/**<p>
 * The interface <code>TokenizerPropertyListener</code> is implemented by classes
 * that are interested in changes in {@link TokenizerProperties} objects. Such
 * changes are adding, removing and modifying of properties like keywords, special
 * sequences etc. or parse flags.
 *</p><p>
 * Usually, a {@link Tokenizer} implementation will also implement this interface.
 * An example is {@link StandardTokenizer}. With the listener design pattern,
 * a tokenizer can cache information for fast access that is originally held in 
 * its associated <code>TokenizerProperties</code> object, without the danger of
 * using out-of-date data.
 *</p><p>
 * The method {@link TokenizerProperties#addTokenizerPropertyListener} is used to
 * register a listener. De-registering is done via 
 * {@link TokenizerProperties#removeTokenizerPropertyListener}. The whole mechanism
 * is therefore the same as the event listener schemes used in the AWT or the 
 * Swing packages of the JDK.
 *</p>
 *
 * @see     TokenizerProperties
 * @see     Tokenizer
 * @see     TokenizerPropertyEvent
 * @author  Heiko Blau
 */
public interface TokenizerPropertyListener {
  
  /**
   * Event handler method. The given {@link TokenizerPropertyEvent} parameter
   * contains the nessecary information about the property change. We choose
   * one single method in favour of various more specialized methods since the
   * reactions on adding, removing and modifying tokenizer properties are often
   * the same (flushing cash, rereading information etc.) or not very different.
   *<br>
   * A {@link TokenizerProperties} object calling its listeners should do this in
   * the order the listeners were registered with the 
   * {@link TokenizerProperties#addTokenizerPropertyListener} method.
   *
   * @param event the {@link TokenizerPropertyEvent} that describes the change
   */
  public void propertyChanged(TokenizerPropertyEvent event);
}
