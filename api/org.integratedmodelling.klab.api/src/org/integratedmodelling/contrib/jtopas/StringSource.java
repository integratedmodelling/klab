/*
 * StringSource.java: TokenizerSource implementation for strings.
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

package org.integratedmodelling.contrib.jtopas;


//-----------------------------------------------------------------------------
// Class StringSource
//

/**
 * Implementation of the {@link TokenizerSource} and its extension 
 * {@link CharSequenceTokenizerSource} for strings. It is a shortcut for:
 *<block><pre>
 *   String           myData = "...";
 *   TokenizerSource  source = new ReaderSource(new StringReader(myData));
 *</pre></block>
 * The class also provides a faster access to the data in a string through the 
 * methods of the {@link java.lang.CharSequence} interface. Since this interface 
 * was only introduced with Java 1.4, this class can only be used with 1.4 or 
 * higher Java versions.
 *
 * @see     TokenizerSource
 * @see     CharSequenceTokenizerSource
 * @see     CharArraySource
 * @author  Heiko Blau
 */
public class StringSource implements CharSequenceTokenizerSource {
  
  //---------------------------------------------------------------------------
  // Constructors
  //
  
  /**
   * Constructing a <code>StringSource</code> on the given {@link java.lang.String}.
   * A <code>null</code> value is handled like an empty string.
   *
   * @param data  the string
   */
  public StringSource(String data) {
    _data = data;
  }
  

  //---------------------------------------------------------------------------
  // Methods of the TokenizerSource interface
  //
  
  /**
   * This method copies the available data into the given buffer according to 
   * the given offset and maximum character count. See {@link TokenizerSource#read}
   * for details.
   *
   * @param cbuf      buffer to receive data
   * @param offset    position from where the data should be inserted in <code>cbuf</code>
   * @param maxChars  maximum number of characters to be read into <code>cbuf</code>
   * @return actually read characters or -1 on an end-of-file condition
   */
  public int read(char[] cbuf, final int offset, final int maxChars) throws Exception {
    int length  = (_data != null) ? _data.length() : 0;
    int left    = Math.min(length - _readOffset, maxChars);
      
    if (left > 0) {
      _data.getChars(_readOffset, _readOffset + left, cbuf, offset);
      _readOffset += left;
      return left;
    } else {
      return -1;
    }
  }

  //---------------------------------------------------------------------------
  // Methods of the CharSequence interface
  //
  
  /**
   * Implements {@link java.lang.CharSequence#charAt} for this class.
   *
   * @param index   which character to retrieve
   * @return the character at the given index
   */
  public char charAt(int index) {
    return _data.charAt(index);
  }  

  /**
   * Implements {@link java.lang.CharSequence#length} for this class.
   *
   * @return the number of available characters
   */
  public int length() {
    return (_data != null) ? _data.length() : 0;
  }
  
  /**
   * Implements {@link java.lang.CharSequence#subSequence} for this class.
   *
   * @param start   the new <code>CharSequence</code> contains the characters 
   *                from and including this position
   * @param end     the new <code>CharSequence</code> contains the characters 
   *                up to and excluding this position
   * @return a part of this <code>CharSequence</code>
   */
  public CharSequence subSequence(int start, int end) {
    return _data.subSequence(start, end);
  }

  
  //---------------------------------------------------------------------------
  // Members
  //
  private String  _data       = null;
  private int     _readOffset = 0;
}
