/*
 * NoCaseSequenceStore.java: case-insensitive derivate of SequenceStore
 *
 * Copyright (C) 2004 Heiko Blau
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

package org.integratedmodelling.contrib.jtopas.impl;

//-----------------------------------------------------------------------------
// Imports
//

//-----------------------------------------------------------------------------
// Class NoCaseSequenceStore
//

/**
 * Case-insensitive extension of {@link SequenceStore}.
 *
 * @see     de.susebox.jtopas.StandardTokenizerProperties
 * @see     de.susebox.jtopas.spi.SequenceHandler
 * @author  Heiko Blau
 */
public class NoCaseSequenceStore extends SequenceStore {
  
  //---------------------------------------------------------------------------
  // Constructors
  //
  
  /**
   * The constructor initializes a <code>NoCaseSequenceStore</code>
   *
   * @param useExactLength  if <code>true</code> search only for a property that
   *                        has the length of {@link de.susebox.jtopas.spi.DataProvider#getLength}
   */
  public NoCaseSequenceStore(boolean useExactLength) {
    super(useExactLength);
  }

  
  //---------------------------------------------------------------------------
  // Implementation
  //
  
  /**
   * Derived method for case-insensitive handling.
   *
   * @param   startChar   a not normalized start character
   * @return  the normalized start character
   */
  protected char getStartChar(char startChar) {
    return Character.toUpperCase(startChar);
  }
  
  /**
   * Compare tho characters. Returns the difference of the to characters, 0 if
   * they are equal.
   *
   * @param char1       first character to compare
   * @param char2       first character to compare
   * @return 0 if equal, < 0 if char1 < char2, > 0 otherwise
   */
  protected int compare(char char1, char char2) {
    int res = char1 - char2;

    if (res != 0) {
      res = Character.toUpperCase(char1) - Character.toUpperCase(char2);
    }
    return res;
  }
}
