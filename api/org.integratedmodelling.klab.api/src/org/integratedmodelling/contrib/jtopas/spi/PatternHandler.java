/*
 * PatternHandler.java: Interface for pattern-aware tokenizers.
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
import org.integratedmodelling.contrib.jtopas.TokenizerProperty;


//-----------------------------------------------------------------------------
// Interface PatternHandler
//

/**<p>
 * This interface must be implemented by classes that should be used as a 
 * pattern handler for a {@link de.susebox.jtopas.Tokenizer}. Pattern are usually
 * regular expressions that are applied on token images to check if that image 
 * matches the pattern.
 *</p>
 *
 * @see     de.susebox.jtopas.Tokenizer
 * @see     de.susebox.jtopas.TokenizerProperties
 * @see     de.susebox.jtopas.spi.DataMapper
 * @author  Heiko Blau
 */
public interface PatternHandler {
  
  /**
   * This method can be used by a {@link de.susebox.jtopas.Tokenizer} implementation 
   * for a fast detection if pattern matching must be performed at all. If the method
   * returns <code>false</code> time-consuming preparations can be skipped.
   *
   * @return  <code>true</code> if there actually are pattern that can be tested
   *          for a match, <code>false</code> otherwise.
   */
  public boolean hasPattern();
  
  /**
   * This method checks if the start of a character range given through the 
   * {@link DataProvider} matches a pattern. An implementation should use
   * a {@link de.susebox.jtopas.TokenizerException} to report problems.
   *<br>
   * The method returns <code>null</code> if the beginning of the character range
   * doesn't match a pattern known to the <code>PatternHandler</code>. Otherwise
   * it returns an object with the implemented interface {@link PatternHandler.Result}.
   *<br>
   * The pattern check is repeated if the method returns a match that is exactly
   * as long as the given data range and more data is available. Since it is 
   * probably a rare case, that where are not enough data to find a complete or 
   * no match, the overhead of a repeated check on part of the data is neglected.
   *<br>
   * If a pattern handler has more than one pattern that could be applied to the
   * given data, it should return the longest possible match.
   *
   * @param   dataProvider    the source to get the data from
   * @return  a {@link PatternHandler.Result} object or <code>null</code> if no
   *          match was found
   * @throws  TokenizerException    generic exception
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public PatternHandler.Result matches(DataProvider dataProvider)
    throws TokenizerException, NullPointerException;
  
  
  //---------------------------------------------------------------------------
  // Inner Interfaces
  //

  /**
   * An inner interface for the pattern match result.
   */
  public static interface Result {
    
    /**
     * Returns the {@link TokenizerProperty} that describes the pattern that 
     * matches data passed to {@link PatternHandler#matches}. The returned value
     * is <strong>not</strong> <code>null</code>.
     *
     * @return the pattern property of a successful match
     */
    public TokenizerProperty getProperty();
    
    /**
     * Returns the number of characters that are part of a match.
     *
     * @return length of match
     */
    public int getLengthOfMatch();
    
    /**
     * Returns the capturing groups of a match. It is used if the calling tokenizer 
     * needs these groups (e. g. if the flag {@link de.susebox.jtopas.Flags#F_RETURN_IMAGE_PARTS}   
     * is set).
     *<br>
     * The return value must not be null or empty. The first element (array index 0) 
     * must contain the whole pattern match (as described in the Java 1.4
     * documentation for {@link java.util.regex.Matcher} or the newer Java 1.5 
     * {@link java.util.regex.MatchResult}).
     *
     * @return  the capturing groups of the last pattern match in {@link #matches}.
     */
    public String[] getGroups() throws TokenizerException;
  }
}

