/*
 * CharSequenceTokenizerSource.java: extension of the TokenizerSource interface.
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
// Interface CharSequenceTokenizerSource
//

/**<p>
 * Extension of the {@link TokenizerSource} interface and the {@link java.lang.CharSequence}
 * interface that was introduced in J2SE 1.4. The <code>CharSequence</code>
 * interface provides a safe way to access character data for reading without 
 * exposing internal buffers or copying. The drawback is that it is only available
 * with Java versions from 1.4, while there are still lots of Java 1.3 and even
 * older environments in use.
 *</p><p>
 * While implementations of this interface can be used by all {@link Tokenizer},
 * specialized tokenizers can take advantage of the generally faster methods 
 * provided by the <code>CharSequence</code> interface.
 *</p>
 *
 * @see     TokenizerSource
 * @author  Heiko Blau
 */
public interface CharSequenceTokenizerSource extends TokenizerSource, CharSequence {
  // bundles the two base interfaces into one
}
