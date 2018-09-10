/*
 * DataMapper.java: service provider interface for data-to-token mapping.
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

import org.integratedmodelling.contrib.jtopas.TokenizerProperties;


//-----------------------------------------------------------------------------
// Interface DataMapper
//

/**<p>
 * This interface declares the methods that a {@link de.susebox.jtopas.Tokenizer}
 * will call to detect the image and type of a token. Usually, an implementation
 * of the {@link de.susebox.jtopas.TokenizerProperties} interface should also
 * implement the <code>DataMapper</code> interface, but this is not mandatory.
 *</p><p>
 * If a {@link de.susebox.jtopas.TokenizerProperties} instance given to a 
 * {@link de.susebox.jtopas.Tokenizer} does not implement the <code>DataMapper</code>
 * interface, then the <code>Tokenizer</code> will contruct a default <code>DataMapper</code>
 * for its properties.
 *</p><p>
 * This interface extends the SPI interfaces specialized in handling various token
 * types.
 *</p>
 *
 * @see de.susebox.jtopas.Tokenizer
 * @see de.susebox.jtopas.TokenizerProperties
 * @author Heiko Blau
 */
public interface DataMapper 
  extends WhitespaceHandler, SeparatorHandler, KeywordHandler, SequenceHandler, PatternHandler
{
  
  /**
   * Setting the backing {@link de.susebox.jtopas.TokenizerProperties} instance
   * this <code>DataMapper</code> is working with. Usually, the <code>DataMapper</code>
   * interface is implemented by <code>TokenizerProperties</code> implementations,
   * too. Otherwise the {@link de.susebox.jtopas.Tokenizer} using the 
   * <code>TokenizerProperties</code>, will construct a default <code>DataMapper</code>
   * an propagate the <code>TokenizerProperties</code> instance by calling this 
   * method.
   *<br>
   * The method should throw an {@link java.lang.UnsupportedOperationException}
   * if this <code>DataMapper</code> is an extension to an <code>TokenizerProperties</code>
   * implementation.
   *
   * @param   props   the {@link de.susebox.jtopas.TokenizerProperties}
   * @throws  UnsupportedOperationException is this is a <code>DataMapper</code>
   *          implemented by a {@link de.susebox.jtopas.TokenizerProperties}
   *          implementation
   * @throws  NullPointerException  if no {@link TokenizerProperties} are given
   */
  public void setTokenizerProperties(TokenizerProperties props) 
    throws UnsupportedOperationException, NullPointerException;

  /**
   * The method retrieves the backing {@link de.susebox.jtopas.TokenizerProperties}
   * instance, this <code>DataMapper</code> is working on. For implementations
   * of the <code>TokenizerProperties</code> interface that also implement the
   * <code>DataMapper</code> interface, this method returns the instance itself.
   *<br>
   * Otherwise the method returns the <code>TokenizerProperties</code> instance 
   * passed through the last call to {@link #setTokenizerProperties} or <code>null</code>
   * if no such call has taken place so far.
   *
   * @return the backing {@link de.susebox.jtopas.TokenizerProperties} or <code>null</code>
   */
  public TokenizerProperties getTokenizerProperties();
}
