/*
 * CharArraySource.java: TokenizerSource implementation for character buffers.
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

package org.integratedmodelling.contrib.jtopas;


//-----------------------------------------------------------------------------
// Class CharArraySource
//

/**
 * Implementation of the {@link TokenizerSource} and its extension 
 * {@link CharSequenceTokenizerSource} for character arrays. It is a shortcut for:
 *<block><pre>
 *   char[]           myData = { ... };
 *   TokenizerSource  source = new ReaderSource(new CharArrayReader(myData));
 *</pre></block>
 * The class also provides a faster access to the data in a character array
 * through the methods of the {@link java.lang.CharSequence} interface.
 * Since this interface was only introduced with Java 1.4, this class can only
 * be used with 1.4 or higher Java versions.
 *
 * @see     TokenizerSource
 * @see     CharSequenceTokenizerSource
 * @see     StringSource
 * @author  Heiko Blau
 */
public class CharArraySource implements CharSequenceTokenizerSource {
  
  //---------------------------------------------------------------------------
  // Constructors
  //
  
  /**
   * Constructing a <code>CharArraySource</code> instance using the
   * given character buffer starting with offset 0 and the full length of the
   * array.
   *
   * @param data  the character data to tokenize
   */
  public CharArraySource(char[] data) {
    this(data, 0, (data != null) ? data.length : -1);
  }
  
  /**
   * Constructing an <code>CharArraySource</code> instance using the
   * given character buffer starting with the given offset 0 and the given length.
   *
   * @param data    the character data to tokenize
   * @param offset  there to start in the given data
   * @param length  count of characters to tokenize
   */
  public CharArraySource(char[] data, int offset, int length) {
    _offset = offset;
    if (data == null || length < 0) {
      _data   = null;
      _length = -1;
    } else {
      _data   = data;
      _length = length;
    }
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
    int left = Math.min(_length - _readOffset, maxChars);
      
    if (left > 0) {
      System.arraycopy(_data, _offset + _readOffset, cbuf, offset, left);
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
   * Implements {@link java.lang.CharSequence#charAt} for this class. Note that
   * the given index is relative to the offset that was passed when this instance
   * was constructed ({@link #CharArraySource(char[], int, int)})
   *
   * @param index   which character to retrieve
   * @return the character at the given index
   */
  public char charAt(int index) {
    return _data[_offset + index];
  }  

  /**
   * Implements {@link java.lang.CharSequence#length} for this class.
   *
   * @return the number of available characters
   */
  public int length() {
    return _length;
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
    return new CharArraySource(_data, _offset + start, end - start);
  }
  
  /**
   * Returns the string representation of this <code>CharArraySource</code> according
   * to the {@link java.lang.CharSequence} interface contract.
   *
   * @return  a string containing all characters of this <code>CharArraySource</code>
   */
  public String toString() {
    return new String(_data, _offset, _length);
  }

  
  //---------------------------------------------------------------------------
  // Members
  //
  private char[]  _data       = null;
  private int     _offset     = 0;
  private int     _length     = -1;
  private int     _readOffset = 0;
}
