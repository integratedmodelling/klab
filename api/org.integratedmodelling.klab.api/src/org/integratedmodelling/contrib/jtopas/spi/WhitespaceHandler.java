/*
 * WhitespaceHandler.java: whitespace handling in tokenizers.
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

package org.integratedmodelling.contrib.jtopas.spi;


import org.integratedmodelling.contrib.jtopas.TokenizerException;


//-----------------------------------------------------------------------------
// Interface WhitespaceHandler
//

/**<p>
 * This interface declares the methods a {@link de.susebox.jtopas.Tokenizer} needs
 * to deal with whitespaces.
 *</p><p>
 * Whitespaces are single characters that separate other tokens. In programming
 * languages, the item whitespace also refers to comments. This does not apply
 * to a <code>WhitespaceHandler</code> that only recognizes single characters.
 * Comments are handled by the {@link SequenceHandler} interface.
 *</p>
 *
 * @see     de.susebox.jtopas.Tokenizer
 * @see     de.susebox.jtopas.TokenizerProperties
 * @see     de.susebox.jtopas.spi.DataMapper
 * @see     SequenceHandler
 * @author  Heiko Blau
 */
public interface WhitespaceHandler {
  
  /**
   * This method checks if the given character is a whitespace.
   *
   * @param testChar  check this character
   * @return <code>true</code> if the given character is a whitespace,
   *         <code>false</code> otherwise
   * @see de.susebox.jtopas.TokenizerProperties#setWhitespaces
   */
  public boolean isWhitespace(char testChar);
     
  /**
   * This method detects the number of whitespace characters the data range given
   * through the {@link DataProvider} parameter starts with.  An implementation 
   * should use a {@link  de.susebox.jtopas.TokenizerException} to report problems.
   *
   * @param   dataProvider  the source to get the data range from
   * @return  number of whitespace characters starting from the given offset
   * @throws  TokenizerException    generic exception
   * @throws  NullPointerException  if no {@link DataProvider} is given
   * @see     DataProvider
   */
  public int countLeadingWhitespaces(DataProvider dataProvider) 
    throws TokenizerException, NullPointerException;
  
  /**
   * If a {@link de.susebox.jtopas.Tokenizer} performs line counting, it is often 
   * nessecary to know if newline characters (Carriage return and line feed) are 
   * (currently) considered to be whitespaces, which is most often the case. 
   * This method informs interested callers about the current condition.
   *<br>
   * Note that this method should return <code>true</code> only if both newline
   * characters are part of the current whitespace set.
   *
   * @return  <code>true</code> if newline characters are in the current whitespace set,
   *          <code>false</code> otherwise
   */
  public boolean newlineIsWhitespace();
}
