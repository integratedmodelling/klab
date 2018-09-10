/*
 * ReaderSource.java: java.io.Reader-backed data source for the Tokenizer.
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

package org.integratedmodelling.contrib.jtopas;

//-----------------------------------------------------------------------------
// Imports
//
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;


//-----------------------------------------------------------------------------
// Class ReaderSource
//

/**<p>
 * This implementation of the {@link TokenizerSource} interface uses the JDK 
 * {@link java.io.Reader} class to realize the requested functionality. Note that
 * the backing <code>Reader</code> can be changed during the parse operations of
 * the {@link Tokenizer} instance that accesses this <code>ReaderSource</code>.
 *</p>
 *
 * @see     de.susebox.jtopas.Tokenizer
 * @see     de.susebox.jtopas.AbstractTokenizer
 * @author  Heiko Blau
 * @see     java.io.Reader
 */
public class ReaderSource implements TokenizerSource {
  
  //---------------------------------------------------------------------------
  // Constructors
  //
  
  /**
   * The default constructor constructs a <code>ReaderSource</code> instance
   * that reads from nowhere. A call to the {@link #read} method will immediately
   * return the end-of-file condition.
   *
   * @see #read
   */
  public ReaderSource() {
    this((Reader)null);
  }
  
  /**
   * Constructing an <code>ReaderSource</code> object with the given {@link java.io.Reader} 
   * instance to get input data from. <code>null</code> is a valid value. The
   * {@link #read} method will return an end-of-file condition in that case.
   *<br>
   * The given {@link java.io.Reader} instance can be closed manually or by 
   * calling the {@link #close} method of this <code>ReaderSource</code> object.
   *
   * @param reader  the backing {@link java.io.Reader} or <code>null</code>
   */
  public ReaderSource(Reader reader) {
    setReader(reader);
  }
  
  /**
   * This Constructor takes an {@link java.io.InputStream}. It is comfortable
   * when You dont have a {@link java.io.Reader} in the first place. However, 
   * when using this constructor, the method {@link #close} should be called 
   * after tokenizing has been finished. It will also close the given 
   * <code>InputStream</code>.
   *<br>
   * The method accepts <code>null</code> leading to an end-of-file condition
   * in the first call to {@link #read}.
   *
   * @param is  the input stream or <code>null</code>
   */
  public ReaderSource(InputStream is) {
    if ((_inputStream = is) != null) {
      setReader(new InputStreamReader(is));
    }
  }
  
  /**
   * This Constructor takes an {@link java.io.File} to create a {@link java.io.Reader}
   * from. When using this constructor, the method {@link #close} should be 
   * called after tokenizing has been finished.
   *<br>
   * The method accepts <code>null</code> leading to an end-of-file condition
   * in the first call to {@link #read}.
   *
   * @param file  the {@link java.io.File} object that should be read or <code>null</code>
   */
  public ReaderSource(File file) throws IOException {
    if (file != null) {
      _inputStream = new FileInputStream(file);
      setReader(new InputStreamReader(_inputStream));
    }
  }
  
  /**
   * This Constructor takes an {@link java.lang.String} as a file path to create 
   * a {@link java.io.Reader} from. When using this constructor, the method 
   * {@link #close} should be called after tokenizing has been finished.
   *<br>
   * The method accepts <code>null</code> leading to an end-of-file condition
   * in the first call to {@link #read}.
   *
   * @param path  a file path or <code>null</code>
   */
  public ReaderSource(String path) throws IOException {
    if (path != null) {
      _inputStream = new FileInputStream(path);
      setReader(new InputStreamReader(_inputStream));
    }
  }
  
  
  //---------------------------------------------------------------------------
  // Methods of the TokenizerSource interface
  //
  
  /**
   * The method calls the {@link java.io.Reader#read(char[], int, int)} method of 
   * the currently backing {@link java.io.Reader}. If no <code>Reader is set so far,
   * -1 (end-of-file) is returned.
   *
   * @param cbuf      buffer to receive data
   * @param offset    position from where the data should be inserted in <CODE>cbuf</CODE>
   * @param maxChars  maximum number of characters to be read into <CODE>cbuf</CODE>
   * @return actually read characters or -1 on an end-of-file condition
   * @throws Exception anything that could happen during read, most likely {@link java.io.IOException}
   */
  public int read(char[] cbuf, int offset, int maxChars) throws Exception {
    if (_reader != null) {
      return _reader.read(cbuf, offset, maxChars);
    } else {
      return -1;
    }
  }
  
  //---------------------------------------------------------------------------
  // Implementation
  //
  
  /**
   * This method can be called to close streams (either {@link java.io.Reader}
   * or {@link java.io.InputStream} passed to the constructors or implicitely
   * created when this <code>ReaderSource</code> is setup.
   *<br>
   * It is a shortcut for otherwise nessecary operations that will usually consist
   * of calling {@link java.io.Reader#close} and {@link java.io.InputStream#close}
   * probably combined with an exception handling to catch {@link java.io.IOException}.
   */
  public void close() {
    if (_reader != null) {
      try { _reader.close(); } catch (IOException ex) { /* close must succeed */ }
    }
    if (_inputStream != null) {
      try { _inputStream.close(); } catch (IOException ex) { /* close must succeed */ }
    }
  }
  
  /**
   * Setting the backing {@link java.io.Reader} instance. <code>null</code> is a 
   * valid value. The {@link #read} method will return no data (end-of-file) in 
   * that case.
   *
   * @param reader  the backing {@link java.io.Reader}
   * @see   #read
   * @see   #getReader
   */
  public void setReader(Reader reader) {
    _reader = reader;
  }
  
  /**
   * Retrieving the current {@link java.io.Reader}. The method may return <code>null</code>
   * if a {@link #setReader} with null has occured before.
   *
   * @return  the current {@link java.io.Reader} or <code>null</code>
   * @see     #setReader
   */
  public Reader getReader() {
    return _reader;
  }
  
  /**
   * Release ressources
   */
  protected void finalize() {
    close();
  }
  
  //---------------------------------------------------------------------------
  // Members
  //
  private Reader      _reader       = null;
  private InputStream _inputStream  = null;
}
