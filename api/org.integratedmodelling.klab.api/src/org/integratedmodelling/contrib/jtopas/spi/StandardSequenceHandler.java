/*
 * StandardSequenceHandler.java: simple implementation of SequenceHandler
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
// Imports
//
import java.util.Iterator;

import org.integratedmodelling.contrib.jtopas.TokenizerProperties;
import org.integratedmodelling.contrib.jtopas.TokenizerProperty;


//-----------------------------------------------------------------------------
// Interface SequenceHandler
//

/**<p>
 * Simple implementation of the {@link SequenceHandler} interface. This class
 * works only with the {@link de.susebox.jtopas.TokenizerProperties} interface 
 * methods and is aware of changes in these properties. It does not cache any 
 * information and is therefore a more or less slow way to handle special sequences. 
 *</p><p>
 * This class is a bridge between arbitrary {@link de.susebox.jtopas.Tokenizer} 
 * implementations using the SPI interface {@link SequenceHandler} and any 
 * {@link de.susebox.jtopas.TokenizerProperties} implementation that does not 
 * implement the <code>SequenceHandler</code> interface itself.
 *</p>
 *
 * @see     SequenceHandler
 * @see     de.susebox.jtopas.Tokenizer
 * @see     de.susebox.jtopas.TokenizerProperties
 * @author  Heiko Blau
 */
public class StandardSequenceHandler implements SequenceHandler {
  
  /**
   * The constructor takes the {@link de.susebox.jtopas.TokenizerProperties}
   * that provide the special sequences.
   *
   * @param props   the {@link de.susebox.jtopas.TokenizerProperties} to take the 
   *                sequences from 
   */
  public StandardSequenceHandler(TokenizerProperties props) {
    _properties = props;
  }
  
  /**
   * This method can be used by a {@link de.susebox.jtopas.Tokenizer} implementation 
   * for a fast detection if special sequence checking must be performed at all. 
   * If the method returns <code>false</code> time-consuming preparations can be 
   * skipped.
   *
   * @return  <code>true</code> if there actually are pattern that can be tested
   *          for a match, <code>false</code> otherwise.
   */
  public boolean hasSequenceCommentOrString() {
    if (_properties != null) {
      return _properties.getSpecialSequences().hasNext();
    } else {
      return false;
    }
  }
  
  /**
   * This method checks if a given range of data starts with a special sequence,
   * a comment or a string. These three types of token are tested together since
   * both comment and string prefixes are ordinary special sequences. Only the 
   * actions preformed <strong>after</strong> a string or comment has been detected,
   * are different.
   *<br>
   * The method returns <code>null</code> if no special sequence, comment or string 
   * could matches the the leading part of the data range given through the
   * {@link DataProvider}.
   *
   * @param   dataProvider  the source to get the data range from
   * @return  a {@link de.susebox.jtopas.TokenizerProperty} if a special sequence, 
   *          comment or string could be detected, <code>null</code> otherwise
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public TokenizerProperty startsWithSequenceCommentOrString(DataProvider dataProvider) 
    throws NullPointerException
  {
    TokenizerProperty prop = null;
    
    if (_properties != null) {
      String data = dataProvider.toString();

      prop = getLongestMatch(data, _properties.getSpecialSequences(), prop);
      prop = getLongestMatch(data, _properties.getLineComments(),     prop);
      prop = getLongestMatch(data, _properties.getBlockComments(),    prop);
      prop = getLongestMatch(data, _properties.getStrings(),          prop);
    }
    return prop;
  }

  /**
   * This method returns the length of the longest special sequence, comment or
   * string prefix that is known to this <code>SequenceHandler</code>. When
   * calling {@link #startsWithSequenceCommentOrString}, the passed {@link DataProvider}
   * parameter will supply at least this number of characters (see {@link DataProvider#getLength}).
   * If less characters are provided, EOF is reached.
   *<br>
   * The method is an easy approach to the problem of how to provide more data
   * in case a test runs out of characters. The invoking {@link de.susebox.jtopas.Tokenizer}
   * (represented by the given {@link DataProvider}) can supply enough data for
   * the {@link #startsWithSequenceCommentOrString} method.
   *
   * @return  the number of characters needed in the worst case to identify a 
   *          special sequence
   */
  public int getSequenceMaxLength() {
    int maxLength = 0;
    
    if (_properties != null) {
      maxLength = getSequenceMaxLength(_properties.getSpecialSequences(), maxLength);
      maxLength = getSequenceMaxLength(_properties.getLineComments(),     maxLength);
      maxLength = getSequenceMaxLength(_properties.getBlockComments(),    maxLength);
      maxLength = getSequenceMaxLength(_properties.getStrings(),          maxLength);
    }
    return maxLength;
  }
    
  /**
   * Retrieving the maximum length of a {@link TokenizerProperty} from an
   * {@link java.util.Iterator}.
   *
   * @param   iter        a initialized {@link java.util.Iterator} to walk through
   * @param   currentMax  the currently known maximum length
   * @return  the maximum length of the {@link TokenizerProperty} images in the 
   *          iterator 
   */
  private int getSequenceMaxLength(Iterator iter, int currentMax) {
    while (iter.hasNext()) {
      TokenizerProperty prop = (TokenizerProperty)iter.next();
      int               len  = prop.getImages()[0].length();

      if (len > currentMax) {
        currentMax = len;
      }
    }
    return currentMax;
  }

  
  /**
   * Retrieving the longest {@link TokenizerProperty} that matches the start of 
   * the given string.
   *
   * @param   data          check the start of this string
   * @param   iter          a initialized {@link java.util.Iterator} to walk through
   * @param   currentMatch  the currently known longest match 
   * @return  the longest matching {@link TokenizerProperty} or <code>null</code>
   */
  private TokenizerProperty getLongestMatch(
    String            data,
    Iterator          iter, 
    TokenizerProperty currentMatch
  ) 
  {
    int               currentMax = (currentMatch != null) ? currentMatch.getImages()[0].length() : 0;
    TokenizerProperty retProp    = currentMatch;
    
    while (iter.hasNext()) {
      TokenizerProperty prop = (TokenizerProperty)iter.next();
      int               len  = prop.getImages()[0].length();

      if (len > currentMax) {
        currentMax = len;
        retProp    = prop;
      }
    }
    return retProp;
  }

  
  //---------------------------------------------------------------------------
  // Members
  //
  
  /**
   * The {@link TokenizerProperties} that provide the sequences and the 
   * control flags.
   */
  private TokenizerProperties _properties = null;
}
