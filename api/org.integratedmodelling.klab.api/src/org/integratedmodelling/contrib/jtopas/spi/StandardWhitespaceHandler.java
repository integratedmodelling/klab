/*
 * StandardWhitespaceHandler.java: default implementation of WhitespaceHandler
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
// class StandardWhitespaceHandler
//

/**<p>
 * Simple implementation of the {@link WhitespaceHandler} interface. This class
 * works only with the {@link de.susebox.jtopas.TokenizerProperties} interface 
 * methods and is aware of changes in these properties. It does not cache any 
 * information and is therefore a more or less slow way to handle whitespaces. 
 *</p><p>
 * This class is a bridge between arbitrary {@link de.susebox.jtopas.Tokenizer} 
 * implementations using the SPI interface {@link WhitespaceHandler} and any 
 * {@link de.susebox.jtopas.TokenizerProperties} implementation that does not 
 * implement the <code>WhitespaceHandler</code> interface itself.
 *</p>
 *
 * @see     WhitespaceHandler
 * @see     de.susebox.jtopas.Tokenizer
 * @see     de.susebox.jtopas.TokenizerProperties
 * @author  Heiko Blau
 */
public class StandardWhitespaceHandler implements WhitespaceHandler {
  
  /**
   * The constructor takes the {@link de.susebox.jtopas.TokenizerProperties}
   * that provide the whitespaces.
   *
   * @param props   the {@link de.susebox.jtopas.TokenizerProperties} to take the 
   *                whitespaces from 
   */
  public StandardWhitespaceHandler(TokenizerProperties props) {
    _properties = props;
  }
  
  /**
   * This method checks if the given character is a whitespace.
   *
   * @param testChar  check this character
   * @return <code>true</code> if the given character is a whitespace,
   *         <code>false</code> otherwise
   */
  public boolean isWhitespace(char testChar) {
    String whitespaces;
    
    if (_properties != null && (whitespaces = _properties.getWhitespaces()) != null) {
      if (_properties.isFlagSet(Flags.F_NO_CASE)) {
        return whitespaces.toLowerCase().indexOf(Character.toLowerCase(testChar)) >= 0;
      } else {
        return whitespaces.indexOf(testChar) >= 0;
      }
    } else {
      return false;
    }
  }
     
  /**
   * This method detects the number of whitespace characters the data range given
   * through the {@link DataProvider} parameter starts with.
   *
   * @param   dataProvider  the source to get the data range from
   * @return  number of whitespace characters starting from the given offset
   * @throws  NullPointerException  if no {@link DataProvider} is given
   * @see     DataProvider
   */
  public int countLeadingWhitespaces(DataProvider dataProvider) throws NullPointerException {
    int len      = 0;
    int maxChars = dataProvider.getLength();
    
    while (len < maxChars && isWhitespace(dataProvider.getCharAt(len))) {
      len++;
    }
    return len;
  }

  /** 
   * If a {@link de.susebox.jtopas.Tokenizer} performs line counting, it is often 
   * nessecary to know if newline characters is considered to be a whitespace. 
   * See {@link WhitespaceHandler} for details.
   *
   * @return  <code>true</code> if newline characters are in the current whitespace set,
   *          <code>false</code> otherwise
   *
   */
  public boolean newlineIsWhitespace() {
    String  whitespaces;
    
    if (_properties != null && (whitespaces = _properties.getWhitespaces()) != null) {
      return newlineIsWhitespace(whitespaces);
    } else {
      return false;
    }
  }  
  
  //---------------------------------------------------------------------------
  // Implementation
  //
  
  /**
   * Check a set that may contain ranges
   *
   * @param set the whitespace set
   */
  private boolean newlineIsWhitespace(String set) {
    int     len = (set != null) ? set.length() : 0;
    char    start;
    char    end;
    boolean crFound = false;
    boolean lfFound = false;
    
    for (int ii = 0; ii < len; ++ii)  {
      switch (set.charAt(ii)) {
      case '-':
        start = (ii > 0) ? set.charAt(ii - 1) : 0;
        end   = (ii < len - 1) ? set.charAt(ii + 1) : 0xFFFF;
        if ('\n' >= start && '\n' <= end) {
          lfFound = true;
        }
        if ('\r' >= start && '\r' <= end) {
          crFound = true;
        }
        ii += 2; 
        break;
        
      case '\r':
        crFound = true;
        break;
        
      case '\n':
        lfFound = true;
        break;
        
      case '\\':
        ii++;
        break;
      }
      
      // both characters found ?
      if (crFound && lfFound) {
        return true;
      }
    }
    
    // not found
    return false;
  }
  
  //---------------------------------------------------------------------------
  // Members
  //
  
  /**
   * The {@link TokenizerProperties} that provide the whitespaces and the 
   * control flags.
   */
  private TokenizerProperties _properties = null;
}
