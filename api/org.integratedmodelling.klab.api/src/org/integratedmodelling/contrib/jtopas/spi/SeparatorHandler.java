/*
 * SeparatorHandler.java: separator handling in tokenizers
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


//-----------------------------------------------------------------------------
// Interface SeparatorHandler
//

/**<p>
 * This interface declares the methods a {@link de.susebox.jtopas.Tokenizer} needs
 * to deal with separators.
 *</p><p>
 * Separators are single characters that are either not very important in the 
 * tokenizing process or are sufficiently characterized by their token image. 
 * Examples are opening and closing parentheses, commas, semicolons etc. Their 
 * main feature is that they separate other tokens in the same way as whiterspaces
 * and comments.
 * 
 *</p>
 *
 * @see     de.susebox.jtopas.Tokenizer
 * @see     de.susebox.jtopas.TokenizerProperties
 * @see     de.susebox.jtopas.spi.DataMapper
 * @author  Heiko Blau
 */
public interface SeparatorHandler {
  
  /**
   * This method checks if the character is a separator.
   *
   * @param testChar  check this character
   * @return <code>true</code> if the given character is a separator,
   *         <code>false</code> otherwise
   * @see de.susebox.jtopas.TokenizerProperties#setSeparators
   */
  public boolean isSeparator(char testChar);
}
