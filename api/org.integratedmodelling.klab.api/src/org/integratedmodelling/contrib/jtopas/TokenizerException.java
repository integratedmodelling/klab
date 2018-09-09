/*
 * TokenizerException.java: Generic exception used by the Tokenizer interface
 *
 * Copyright (C) 2001 Heiko Blau
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

//------------------------------------------------------------------------------
// TokenizerException - definition
//

/**<p>
 * A {@link RuntimeException} subclass.
 *</p>
 *
 * @author 	Heiko Blau
 */
public class TokenizerException extends RuntimeException {

  /** Recommended by the {@link java.io.Serializable} interface */
  private static final long serialVersionUID = -228727645523892162L; 
  
  /**
   * Default constructor
   */
  public TokenizerException() {
    super();
  }
  
  /**
   * Constructor taking an exception message.
   * 
   * @param message   the exception message
   */
  public TokenizerException(String message) {
    super(message);
  }
  
  /**
   * Constructor for wrapper exceptions taking an the wrapped {@link Throwable}
   * as the cause. 
   * 
   * @param cause     the wrapped exception
   */
  public TokenizerException(Throwable cause) {
    super(cause);
  }
  
  /**
   * Combination of {@link #TokenizerException(String)} and {@link #TokenizerException(Throwable)}. 
   * 
   * @param message   the exception message
   * @param cause     the wrapped exception
   */
  public TokenizerException(String message, Throwable cause) {
    super(message, cause);
  }
}
