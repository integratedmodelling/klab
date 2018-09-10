/*
 * StandardSeparatorHandler.java: Implementation of the SeparatorHandler
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


import org.integratedmodelling.contrib.jtopas.Flags;
import org.integratedmodelling.contrib.jtopas.TokenizerProperties;


//-----------------------------------------------------------------------------
// class StandardSeparatorHandler
//

/**<p>
 * Simple implementation of the {@link SeparatorHandler} interface. This class
 * works only with the {@link de.susebox.jtopas.TokenizerProperties} interface 
 * methods and is aware of changes in these properties. It does not cache any 
 * information and is therefore a more or less slow way to handle separators. 
 *</p><p>
 * This class is a bridge between arbitrary {@link de.susebox.jtopas.Tokenizer} 
 * implementations using the SPI interface {@link SeparatorHandler} and any 
 * {@link de.susebox.jtopas.TokenizerProperties} implementation that does not 
 * implement the <code>SeparatorHandler</code> interface itself.
 *</p>
 *
 * @see     SeparatorHandler
 * @see     de.susebox.jtopas.Tokenizer
 * @see     de.susebox.jtopas.TokenizerProperties
 * @author  Heiko Blau
 */
public class StandardSeparatorHandler implements SeparatorHandler {
  
  /**
   * The constructor takes the {@link de.susebox.jtopas.TokenizerProperties}
   * that provide the separators.
   *
   * @param props   the {@link de.susebox.jtopas.TokenizerProperties} to take the 
   *                separators from 
   */
  public StandardSeparatorHandler(TokenizerProperties props) {
    _properties = props;
  }
  
  /**
   * This method checks if the character is a separator.
   *
   * @param testChar  check this character
   * @return <code>true</code> if the given character is a separator,
   *         <code>false</code> otherwise
   */
  public boolean isSeparator(char testChar) {
    String separators;
    
    if (_properties != null && (separators = _properties.getWhitespaces()) != null) {
      if (_properties.isFlagSet(Flags.F_NO_CASE)) {
        return separators.toLowerCase().indexOf(Character.toLowerCase(testChar)) >= 0;
      } else {
        return separators.indexOf(testChar) >= 0;
      }
    } else {
      return false;
    }
  }

  
  //---------------------------------------------------------------------------
  // Members
  //
  
  /**
   * The {@link TokenizerProperties} that provide the separators and the 
   * control flags.
   */
  private TokenizerProperties _properties = null;
}
