/*
 * StandardTokenizer.java: core class for lexical parser.
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

import org.integratedmodelling.contrib.jtopas.spi.DataProvider;


//-----------------------------------------------------------------------------
// Class StandardTokenizer
//

/**<p>
 * This is the mainstream {@link Tokenizer}. It implements the {@link Tokenizer}
 * interface in a straightforward approach without too specialized parse
 * optimizations.
 * </p><p>
 * Beside the {@link Tokenizer} interface, the class <code>StandardTokenizer</code>
 * provides some basic features for cascading (nested) tokenizers. Consider the usual
 * HTML pages found today in the WWW. Most of them are a mixture of regular HTML,
 * cascading style sheets (CSS) and embedded JavaScript. These different languages
 * use different syntaxes, so one needs varous tokenizers on the same input stream.
 *</p><p>
 * This {@link Tokenizer} implementation is not synchronized. Take care when using
 * with multible threads.
 *</p>
 *
 * @see Tokenizer
 * @see TokenizerProperties
 * @author Heiko Blau
 */
public class StandardTokenizer 
  extends AbstractTokenizer 
  implements Tokenizer, TokenizerPropertyListener 
{
  //---------------------------------------------------------------------------
  // Constructors
  //
  
  /**
   * Default constructor that sets the tokenizer control flags as it would be
   * approbriate for C/C++ and Java. Found token images are copied. No line nor
   * column informations are provided. Nested comments are not allowed.
   *<br>
   * The tokenizer will use the {@link TokenizerProperties#DEFAULT_WHITESPACES} 
   * and {@link TokenizerProperties#DEFAULT_SEPARATORS} for whitespace and 
   * separator handling.
   */  
  public StandardTokenizer() {
    super();
  }
  
  /**
   * Contructing a <code>StandardTokenizer</code> with a backing {@link TokenizerProperties}
   * instance.
   *
   * @param properties  an {@link TokenizerProperties} object containing the 
   *                    settings for the tokenizing process
   */
  public StandardTokenizer(TokenizerProperties properties) {
    super.setTokenizerProperties(properties);
  }

  
  //---------------------------------------------------------------------------
  // Methods of the Tokenizer interface
  //
  
  /**
   * This method returns the absolute offset in characters to the start of the
   * parsed stream. See the method description in {@link Tokenizer}.
   *
   * @return the absolute offset of the current text window in characters from 
   *         the start of the data source of the Tokenizer
   * @see #getReadPosition
   */
  public int getRangeStart() {
    return _rangeStart;
  }
  
  /**
   * Additionally to the common behaviour implemented in 
   * {@link de.susebox.jtopas.AbstractTokenizer#setSource}, this method ajusts
   * the state speicific to the <code>StandardTokenizer</code> class.
   *
   * @param source  a {@link TokenizerSource} to read data from
   */
  public void setSource(TokenizerSource source) {
    super.setSource(source);
    _hasBeenRead = false;
    _rangeStart  = 0;
    try {
      _charSequenceTokenizerSource = (CharSequenceTokenizerSource)getSource();
      _dataProvider                = new StringDataProvider(_charSequenceTokenizerSource, 0, 0);
    } catch (ClassCastException ex) {
      _charSequenceTokenizerSource = null;
      _dataProvider = new CharArrayDataProvider(_inputBuffer, 0, 0);
    }
  }

  /**
   * Closing this tokenizer frees resources.
   */
  public void close() {
    _inputBuffer                  = null;
    _rangeStart                   = 0;
    _hasBeenRead                  = false;
    _charSequenceTokenizerSource  = null;
    _dataProvider                 = null;
    super.close();
  }
  
  
  //---------------------------------------------------------------------------
  // Implementation
  //

  /**
   * Implements the abstract method of the base class. 
   *
   * @param startPos    position in the input data
   * @param length      number of characters
   */
  protected DataProvider getDataProvider(int startPos, int length) {
    _dataProvider.setDataRange(startPos - getRangeStart(), length);
    return _dataProvider;
  }

  /**
   * This method organizes the input buffer. It moves the current text window if
   * nessecary or allocates more space, if data should be kept completely (see the
   * {@link Flags#F_KEEP_DATA} flag).
   * Its main purpose is to call the {@link TokenizerSource#read} method.
   *
   * @return  number of read bytes or -1 if an end-of-file condition occured
   * @throws  TokenizerException wrapped exceptions from the {@link TokenizerSource#read} 
   *          method
   */
  protected int readMoreData() throws TokenizerException  {
    if (_charSequenceTokenizerSource != null) {
      // new CharSequenceTokenizerSource
      if (_hasBeenRead || _charSequenceTokenizerSource.length() <= 0) {
        return -1;
      } else {
        _hasBeenRead = true;
        return _charSequenceTokenizerSource.length();
      }
    
    } else {
      // no input buffer so far
      if (_inputBuffer == null) {
        if (isFlagSet(Flags.F_KEEP_DATA)) {
          _inputBuffer = new char[LARGE_BUFFER_INITSIZE];   // 64k
        } else {
          _inputBuffer = new char[SMALL_BUFFER_INITSIZE];    // 8k
        }
        ((CharArrayDataProvider)_dataProvider).setData(_inputBuffer);
      }

      // this is a good moment to move already read data if the write position is
      // near the end of the buffer and there is a certain space before the current
      // read position
      int readPos  = getReadPosition() - getRangeStart();
      int writePos = currentlyAvailable();

      if ( ! isFlagSet(Flags.F_KEEP_DATA)) {
        if ((readPos > _inputBuffer.length / 4) && (writePos > (3 * _inputBuffer.length) / 4)) {
          reorganizeInputBuffer(_inputBuffer);
          writePos = currentlyAvailable();
        }
      }

      // if there is no space any more and data couldn't be moved (see above)
      // we need a new input buffer
      if (writePos >= _inputBuffer.length) {
        _inputBuffer = reorganizeInputBuffer(new char[_inputBuffer.length * 2]);
        writePos     = currentlyAvailable();
        ((CharArrayDataProvider)_dataProvider).setData(_inputBuffer);
      }

      // read data
      int chars = 0;

      while (chars == 0) {
        try {
          chars = getSource().read(_inputBuffer, writePos, _inputBuffer.length - writePos);
        } catch (Exception ex) {
          throw new TokenizerException(ex);
        }
      }
      return chars;
    }
  }
  
  /**
   * Move data in the input buffer and adjust various position values.
   */
  private char[] reorganizeInputBuffer(char[] newBuffer) {
    int readPos  = getReadPosition() - getRangeStart();
    int writePos = currentlyAvailable();

    if ( ! isFlagSet(Flags.F_KEEP_DATA)) {
      System.arraycopy(_inputBuffer, readPos, newBuffer, 0, writePos - readPos);
      _rangeStart += readPos;
    } else {
      System.arraycopy(_inputBuffer, 0, newBuffer, 0, writePos);
    }
    return newBuffer;
  }

  
  //---------------------------------------------------------------------------
  // Inner classes
  //

  /**
   * Base class for the various implementations of the 
   * {@link de.susebox.jtopas.spi.DataProvider} interface for the {@link StandardTokenizer}.
   */
  private abstract class AbstractDataProvider implements DataProvider {

    /**
     * The constructor takes the nessecary parameters for the methods defined
     * below
     *
     * @param startPosition   valid data start here
     * @param length          count of characters starting at startPosition
     */
    public AbstractDataProvider(int startPosition, int length) {
      setDataRange(startPosition, length);
    }

    /**
     * Retrieving the position where the data to analyze start in the buffer provided
     * by {@link #getData}. The calling {@link de.susebox.jtopas.spi.DataMapper} 
     * must not access data prior to this index in the character array.
     *
     * @return  index in the character array returned by {@link #getData}, where data starts
     */
    public int getStartPosition() {
      return _startPosition;
    }

    /**
     * Retrieving the maximum number of characters in the array provided by {@link getData}
     * that can be analyzed by the calling {@link de.susebox.jtopas.spi.DataMapper}.
     *
     * @param testChar  check this character
     * @return <code>true</code> if the given character is a separator,
     *         <code>false</code> otherwise
     */
    public int getLength() {
      return _length;
    }

    /**
     * Setting the start position and the length in the data buffer of this 
     * instance.
     *
     * @param startPosition   valid data start here
     * @param length          count of characters starting at startPosition
     */
    protected void setDataRange(int startPosition, int length) {
      _startPosition  = startPosition;
      _length         = length;
    }

    // Members
    protected int _startPosition;
    protected int _length;
  }

  /**
   * Implementation of the {@link de.susebox.jtopas.spi.DataProvider} interface 
   * for the {@link StandardTokenizer}.
   */
  private final class CharArrayDataProvider extends AbstractDataProvider implements DataProvider {

    /**
     * The constructor takes the nessecary parameters for the methods defined
     * below
     */
    public CharArrayDataProvider(char[] data, int startPosition, int length) {
      super(startPosition, length);
      setData(data);
      _dataAsString = null;
    }

    /**
     * See {@link de.susebox.jtopas.spi.DataProvider#getCharAt} for details.
     *
     * @param   index   an index between 0 and {@link #getLength} 
     * @return  the character at the given position
     */
    public char getCharAt(int index) {
      return _data[_startPosition + index];
    }

    /**
     * See {@link de.susebox.jtopas.spi.DataProvider#getData} for details.
     *
     * @return the character buffer to read data from
     */
    public char[] getData() {
      return _data;
    }

    /**
     * See {@link de.susebox.jtopas.spi.DataProvider#getDataCopy} for details.
     *
     * @return  a copy of the valid data of this {@link DataProvider}
     * @see #getData
     * @see #toString
     */
    public char[] getDataCopy() {
      char[] copy = new char[getLength()];

      System.arraycopy(_data, getStartPosition(), copy, 0, copy.length);
      return copy;
    }

    /**
     * Returning the valid data range of this <code>DataProvider</code> as a string.
     * This method is an alternative to {@link #getDataCopy}.
     *
     * @return the string representation of the valid data range
     */
    public String toString() {
      if (_dataAsString == null) {
        if (_data != null) {
          _dataAsString = new String(_data, _startPosition, _length);
        } else {
          _dataAsString = "";
        }
      }
      return _dataAsString;
    }

    /**
     * Setting the data buffer of this instance.
     */
    protected void setData(char[] data) {
      _data = data;
    }

    /**
     * Setting the start position and the length in the data buffer of this 
     * instance.
     *
     * @param startPosition   valid data start here
     * @param length          count of characters starting at startPosition
     */
    protected void setDataRange(int startPosition, int length) {
      super.setDataRange(startPosition, length);
      _dataAsString = null;
    }
    
    // Members
    private char[] _data;
    private String _dataAsString;
  }


  /**
   * Implementation of the {@link de.susebox.jtopas.spi.DataProvider} 
   * interface for {@link CharSequenceTokenizerSource} sources.
   */
  private final class StringDataProvider extends AbstractDataProvider implements DataProvider {

    /**
     * The constructor takes the nessecary parameters for the methods defined
     * below
     */
    public StringDataProvider(CharSequenceTokenizerSource source, int startPosition, int length) {
      super(startPosition, length);
      setData(source);
    }


    //---------------------------------------------------------------------------
    // methods of the DataProvider interface
    //

    /**
     * See {@link de.susebox.jtopas.spi.DataProvider#getCharAt} for details.
     *
     * @param   index   the index of the character starting from {@link #getStartPosition}
     * @return the character at the given position
     */
    public char getCharAt(int index) {
      return _source.charAt(_startPosition + index);
    }

    /**
     * See {@link de.susebox.jtopas.spi.DataProvider#getData} for details.
     *
     * @return the character buffer to read data from
     */
    public char[] getData() {
      char[] cbuf = new char[_source.length()];
      try {
        _source.read(cbuf, 0, cbuf.length);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
      return cbuf;
    }

    /**
     * See {@link de.susebox.jtopas.spi.DataProvider#getDataCopy} for details.
     *
     * @return  a copy of the valid data of this {@link DataProvider}
     * @see #getData
     * @see #toString
     */
    public char[] getDataCopy() {
      return toString().toCharArray();
    }

    /**
     * Returning the valid data range of this <code>DataProvider</code> as a string.
     * This method is an alternative to {@link #getDataCopy}.
     *
     * @return the string representation of the valid data range
     */
    public String toString() {
      return _source.subSequence(_startPosition, _startPosition + _length).toString();
    }

    /**
     * Setting the data source of this instance.
     */
    protected void setData(CharSequenceTokenizerSource source) {
      _source = source;
    }

    // Members
    private CharSequenceTokenizerSource _source;
  }

  
  //---------------------------------------------------------------------------
  // Class members
  //
  
  /**
   * Buffer sizes
   */
  private static final int SMALL_BUFFER_INITSIZE = 0x2000;    // 8K
  private static final int LARGE_BUFFER_INITSIZE = 0x10000;   // 64K
  
  
  //---------------------------------------------------------------------------
  // Members
  //
  
  /**
   * This buffer holds the currently read data. Dont use a buffered reader, since
   * we do buffering here.
   */
  protected char[] _inputBuffer = null;

  /**
   * Mapping of index 0 of {@link #_inputBuffer} to the absolute start of the 
   * input stream.
   */
  protected int _rangeStart = 0;

  /**
   * Flag used in conjunction with the {@link #_charSequenceTokenizerSource}.
   */
  protected boolean _hasBeenRead = false;
  
  /**
   * If a {@link CharSequenceTokenizerSource} is used, this member is set to
   * it.
   */
  protected CharSequenceTokenizerSource _charSequenceTokenizerSource = null;
  
  /**
   * The {@link de.susebox.jtopas.spi.DataProvider} instance for this object.
   * This instance is kept due to a significant performance boost compared with
   * construction of a <code>DataProvider</code> every time {@link #getDataProvider}
   * is called.
   */
  protected AbstractDataProvider _dataProvider = null;
}
