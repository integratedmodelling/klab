/*
 * TokenizerSource.java: Data source for the Tokenizer.
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


//-----------------------------------------------------------------------------
// Interface TokenizerSource
//

/**<p>
 * This interface describes the data source for a {@link Tokenizer}. It is a
 * simplification of the {@link java.io.Reader} class (<code>java.io.Reader</code>).
 *</p>
 *
 * @see     Tokenizer
 * @author  Heiko Blau
 */
public interface TokenizerSource {
  
  /**
   * A basic method to supply character data for a {@link Tokenizer}. Note that 
   * the more complicated operations of buffering, skipping etc. are done by
   * the <code>Tokenizer</code> implementation using this data source. An implementation
   * of this interface should therefore avoid caching data itself. Otherwise,
   * storing data is done twice wasting memory.
   *<br>
   * In correspondence to the methods in {@link java.io.Reader} this method 
   * returns -1 on end-of-file (EOF).
   *
   * @param cbuf      buffer to receive data
   * @param offset    position from where the data should be inserted in <code>cbuf</code>
   * @param maxChars  maximum number of characters to be read into <code>cbuf</code>
   * @return actually read characters or -1 on an end-of-file condition
   * @throws Exception anything that could happen during read, most likely {@link java.io.IOException}
   */
  int read(char[] cbuf, int offset, int maxChars) throws Exception;
}
