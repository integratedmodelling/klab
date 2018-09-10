/*
 * PatternMatcher.java: Interface for pattern-aware tokenizers.
 *
 * Copyright (C) 2003 Heiko Blau
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

import java.util.regex.Matcher;
//-----------------------------------------------------------------------------
// Imports
//
import java.util.regex.Pattern;

import org.integratedmodelling.contrib.jtopas.Flags;
import org.integratedmodelling.contrib.jtopas.TokenizerException;
import org.integratedmodelling.contrib.jtopas.TokenizerProperty;
import org.integratedmodelling.contrib.jtopas.spi.DataProvider;
import org.integratedmodelling.contrib.jtopas.spi.PatternHandler;


//-----------------------------------------------------------------------------
// Class PatternMatcher
//

/**<p>
 * Implementation of the {@link PatternHandler} interface using the JDK 1.4 
 * package <code>java.util.regex</code>.
 *</p>
 *
 * @author  Heiko Blau
 */
public class PatternMatcher implements PatternHandler {
  
  //---------------------------------------------------------------------------
  // Constructors
  //
  
  /**
   * The constructor takes a pattern and the {@link TokenizerProperty} object
   * associated with this instance of <code>PatternMatcher</code>. The global
   * flags are passed to control the behaviour for attributes that are not
   * specified in the property itself (e.g. case-sensitivity).
   *
   * @param   prop          the {@link TokenizerProperty} associated with this object
   * @param   globalFlags   flags that are to be used if not set explicitely in the property
   * @throws  NullPointerException if the given parameter is <code>null</code>
   */ 
  public PatternMatcher(TokenizerProperty prop, int globalFlags) throws NullPointerException {
    _globalFlags = globalFlags;
    setProperty(prop);
  }
  

  //---------------------------------------------------------------------------
  // Methods of the PatternHandler interface
  //
  
  /**
   * The method is a dummy implementation for the interface {@link PatternHandler}
   * and always returns <code>true</code>.
   *
   * @return  always <code>true</code>
   */
  public boolean hasPattern() {
    return true;
  }
  
  /**
   * This method checks if the start of a character range given through the 
   * {@link DataProvider} matches a pattern. See {@link PatternHandler#matches}
   * for details.
   *
   * @param   dataProvider    the source to get the data from
   * @return  a {@link de.susebox.jtopas.spi.PatternHandler.Result} object 
   *          or <code>null</code> if no match was found
   * @throws  TokenizerException    generic exception
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public PatternHandler.Result matches(DataProvider dataProvider) 
    throws TokenizerException, NullPointerException
  {
    // invoke JDK 1.4 or jakarta regexp API
    try {
      String[]  groups;
      
      _matcher.reset(new DataProviderCharSequence(dataProvider));
      if (_matcher.lookingAt()) {
        if (_property.isFlagSet(Flags.F_RETURN_IMAGE_PARTS, (_globalFlags & Flags.F_RETURN_IMAGE_PARTS) != 0)) {
          // get the capturing groups
          groups = new String[_matcher.groupCount() + 1];
          for (int index = 0; index < groups.length; ++index) {
            groups[index] = _matcher.group(index);
          }
        } else {
          groups = new String[] {};
        }
        return new LocalResult(_property, _matcher.end(), groups);
      } else {
        return null;
      }
    } catch (Exception ex) {
      throw new TokenizerException(ex);
    }
  }

  
  //---------------------------------------------------------------------------
  // Methods
  //

  /**
   * Setting the {@link TokenizerProperty} for this <code>PatternMatcher</code>.
   * This method will recompile the regular expression pattern. 
   *
   * @param   prop    the {@link TokenizerProperty} associated with this object
   * @throws  NullPointerException if the given parameter is <code>null</code>
   */
  public void setProperty(TokenizerProperty prop) throws NullPointerException {
    // no pattern given
    if (prop == null) {
      throw new NullPointerException("No property given.");
    } else if (prop.getImages() == null || prop.getImages().length < 1 || prop.getImages()[0] == null) {
      throw new NullPointerException("Property contains no pattern image.");
    }
    
    // compile the pattern
    int flags = Pattern.MULTILINE | Pattern.DOTALL;

    if (prop.isFlagSet(Flags.F_NO_CASE, (_globalFlags & Flags.F_NO_CASE) != 0)) {
      flags |= Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
    }
    _matcher = Pattern.compile(prop.getImages()[0], flags).matcher("");

    // set property
    _property = prop;
  }
  
  /**
   * Retrieving the {@link TokenizerProperty} of this <code>PatternMatcher</code>.
   *
   * @return  the {@link TokenizerProperty} associated with this object
   */
  public TokenizerProperty getProperty() {
    return _property;
  }
  

  //---------------------------------------------------------------------------
  // Inner Classes
  //
  
  /**
   * The result of a match operation.
   */
  private final class LocalResult implements PatternHandler.Result {
    
    /**
     * The constructor gets all the nessecary parameters.
     *
     * @param prop          the pattern property
     * @param lengthOfMatch the detected number of characters that match the pattern
     * @param groups        array with the capturing groups
     */
    protected LocalResult(TokenizerProperty prop, int lengthOfMatch, String[] groups) {
      _localProperty  = prop;
      _lengthOfMatch  = lengthOfMatch;
      _groups         = groups;
    }
    
    /**
     * Returns the capturing groups of a match. 
     *
     * @return  the capturing groups of the last pattern match in {@link #matches}.
     */
    public String[] getGroups() throws TokenizerException {
      return _groups;
    }
    
    /**
     * Returns the number of characters that are part of a match.
     *
     * @return length of match
     */
    public int getLengthOfMatch() {
      return _lengthOfMatch;
    }
    
    /**
     * Returns the {@link TokenizerProperty} that describes the pattern that 
     * matches data passed to {@link PatternHandler#matches}.
     *
     * @return the pattern property of a successful match
     */
    public TokenizerProperty getProperty() {
      return _localProperty;
    }
    
    // member
    private final TokenizerProperty _localProperty;
    private final int               _lengthOfMatch;
    private final String[]          _groups;
  }

  /**
   * An implementation of the JDK 1.4 {@link java.lang.CharSequence} interface
   * backed by a {@link DataProvider}.
   */
  private final class DataProviderCharSequence implements CharSequence {
    
    /**
     * The constructor takes the reference to the {@link DataProvider}.
     *
     * @param dataProvider  the backing <code>DataProvider</code>
     */
    public DataProviderCharSequence(DataProvider dataProvider) {
      this(dataProvider, dataProvider.getStartPosition(), dataProvider.getLength());
    }
    
    /**
     * The constructor takes the reference to the {@link DataProvider}, the
     * start position and length. It is nessecary for the {@link #subSequence}
     * method
     *
     * @param dataProvider  the backing <code>DataProvider</code>
     */
    private DataProviderCharSequence(DataProvider dataProvider, int start, int length) {
      _dataProvider = dataProvider;
      _start        = start;
      _length       = length;
    }
    
    /** 
     * Returns the character at the specified index.  An index ranges from zero
     * to <code>length() - 1</code>.  The first character of the sequence is at
     * index zero, the next at index one, and so on, as for array
     * indexing. </p>
     *
     * @param   index   the index of the character to be returned
     * @return  the specified character
     * @throws  ArrayIndexOutOfBoundsException
     *          if the <code>index</code> argument is negative or not less than
     *          <code>length()</code>
     */
    public char charAt(int index) throws ArrayIndexOutOfBoundsException {
      return _dataProvider.getCharAt(_start + index - _dataProvider.getStartPosition());
    }
    
    /** Returns the length of this character sequence.  The length is the number
     * of 16-bit Unicode characters in the sequence. </p>
     *
     * @return  the number of characters in this sequence
     *
     */
    public int length() {
      return _length;
    }
    
    /** 
     * Returns a new character sequence that is a subsequence of this sequence.
     * See {@link java.lang.CharSequence#subSequence} for details.
     *
     * @param   start   the start index, inclusive
     * @param   end     the end index, exclusive
     * @return  the specified subsequence
     * @throws  IndexOutOfBoundsException
     *          if <code>start</code> or <code>end</code> are negative,
     *          if <code>end</code> is greater than <code>length()</code>,
     *          or if <code>start</code> is greater than <code>end</code>
     */
    public CharSequence subSequence(int start, int end) {
      if (start < 0 || end < 0 || end > length() || start > end) {
        throw new IndexOutOfBoundsException();
      }
      return new DataProviderCharSequence(_dataProvider, _start + start, end - start);
    }
    
    /**
     * Returns the string representation for the <code>DataProvider</code>.
     *
     * @return the string consisting of all available data in the DataProvider.
     */
    public String toString() {
      return new String(_dataProvider.getData(), _start, _length);
    }
    
    // members
    private final DataProvider  _dataProvider;
    private final int           _start;
    private final int           _length;
  }

  
  //---------------------------------------------------------------------------
  // Members
  //
  private TokenizerProperty _property    = null;
  private Matcher           _matcher     = null;
  private int               _globalFlags = 0;
}
  
