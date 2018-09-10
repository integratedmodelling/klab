/*
 * SequenceStore.java: string, comment and special sequence handling in tokenizers
 *
 * Copyright (C) 2003, 2004 Heiko Blau
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

//-----------------------------------------------------------------------------
// Imports
//
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.integratedmodelling.contrib.jtopas.Token;
import org.integratedmodelling.contrib.jtopas.Tokenizer;
import org.integratedmodelling.contrib.jtopas.TokenizerException;
import org.integratedmodelling.contrib.jtopas.TokenizerProperty;
import org.integratedmodelling.contrib.jtopas.spi.DataProvider;
import org.integratedmodelling.contrib.jtopas.spi.KeywordHandler;
import org.integratedmodelling.contrib.jtopas.spi.SequenceHandler;


//-----------------------------------------------------------------------------
// Class SequenceStore
//

/**
 * This class is used by {@link de.susebox.jtopas.StandardTokenizerProperties}
 * to store and search special sequences, comments and strings as well as 
 * keywords. The class is not suitable for standalone use since it does not check 
 * parameters for <code>null</code> values, assumes a thread-safe context etc. 
 *
 * @see     de.susebox.jtopas.StandardTokenizerProperties
 * @see     de.susebox.jtopas.spi.SequenceHandler
 * @author  Heiko Blau
 */
public class SequenceStore implements SequenceHandler, KeywordHandler {
  
  //---------------------------------------------------------------------------
  // Constants
  //
  
  /**
   * This number is the size of the array that is directly indexed by letters
   */
  public static char DIRECT_INDEX_COUNT = 256;
  
  
  //---------------------------------------------------------------------------
  // Constructors
  //
  
  /**
   * The constructor initializes a <code>SequenceStore</code> with the given
   * comparision policy (prefix comparison).
   *
   * @param useExactLength  if <code>true</code> search only for a property that
   *                        has the length of {@link de.susebox.jtopas.spi.DataProvider#getLength}
   */
  public SequenceStore(boolean useExactLength) {
    _useExactLength = useExactLength;
    _maxLength      = 0;
    _asciiArray     = new PropertyList[DIRECT_INDEX_COUNT];
    _nonASCIIMap    = new TreeMap();
  }

  
  //---------------------------------------------------------------------------
  // Methods of the SequenceHandler interface
  //
  
  /**
   * This method returns <code>true</code> if there are any special sequences,
   * strings or comments registered in this instance. See the 
   * {@link de.susebox.jtopas.spi.SequenceHandler} interface for details.
   *
   * @return  <code>true</code> if there are any special sequences, strings or
   *          comments available, <code>false</code> otherwise.
   */
  public boolean hasSequenceCommentOrString() {
    return _maxLength > 0;
  }
  
  /**
   * This method checks if a given range of data starts with a special sequence,
   * a comment or a string. See {@link de.susebox.jtopas.spi.SequenceHandler} for
   * details.
   *
   * @param   dataProvider  the source to get the data range from
   * @return  a {@link de.susebox.jtopas.TokenizerProperty} if a special sequence, 
   *          comment or string could be detected, <code>null</code> otherwise
   * @throws  TokenizerException    generic exception
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public TokenizerProperty startsWithSequenceCommentOrString(DataProvider dataProvider) 
    throws TokenizerException, NullPointerException
  {
    // only if characters are available
    if (dataProvider.getLength() > 0) {
      int           len       = dataProvider.getLength();
      char          startChar = getStartChar(dataProvider.getCharAt(0));
      PropertyList  list      = getList(startChar);

      while (list != null) {
        TokenizerProperty prop      = list._property;
        String            image     = prop.getImages()[0];
        int               imageLen  = image.length();

        // compare only if the enough data is available
        if (_useExactLength && imageLen < len) {
          break;            // dont check shorter properties
        } else if (imageLen <= len && comparePrefix(image, dataProvider, 1) == 0) {
          return prop;      // single point of success
        }
        list = list._next;
      }
    }
    
    // not found
    return null;
  }

  /**
   * This method returns the length of the longest special sequence, comment or
   * string prefix that is known to this <code>SequenceStore</code>. See 
   * {@link de.susebox.jtopas.spi.SequenceHandler} for details.
   *
   * @return  the number of characters needed in the worst case to identify a 
   *          special sequence
   */
  public int getSequenceMaxLength() {
    return _maxLength;
  }

  //---------------------------------------------------------------------------
  // Methods of the KeywordHandler interface
  //
  
  /**
   * This method returns <code>true</code> if there are any keywords registered 
   * in this instance. See the {@link de.susebox.jtopas.spi.KeywordHandler} 
   * interface for details.
   *
   * @return  <code>true</code> if there are any keywords available, 
   *          <code>false</code> otherwise.
   */
  public boolean hasKeywords() {
    // this classis is either used to store special sequences or keywords. 
    return hasSequenceCommentOrString();
  }  
  
  /**
   * This method checks if the given data form a keyword. 
   * See {@link de.susebox.jtopas.spi.KeywordHandler} for details.
   *
   * @param   dataProvider  the source to get the data range from
   * @return  a {@link de.susebox.jtopas.TokenizerProperty} if keyword has been found, 
   *          <code>null</code> otherwise
   * @throws  TokenizerException    generic exception
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public TokenizerProperty isKeyword(DataProvider dataProvider) throws TokenizerException, NullPointerException {
    return startsWithSequenceCommentOrString(dataProvider);
  }  
  
  
  //---------------------------------------------------------------------------
  // Implementation
  //
  
  /**
   * The method returns the normalized start character. This default implementation
   * returns the given character itself, for case-insensitive handling see the
   * derived class {@link NoCaseSequenceStore}.
   *
   * @param   startChar   a not normalized start character
   * @return  the normalized start character
   */
  protected char getStartChar(char startChar) {
    return startChar;
  }
  
  /**
   * Addingt or replacing a special sequence, comment or string.
   *
   * @param   property  the description of the new sequence
   * @return  the previously version of the given property or <code>null</code>
   */
  public TokenizerProperty addSpecialSequence(TokenizerProperty property) {
    String  image     = property.getImages()[0];
    int     length    = image.length();
    char    startChar = getStartChar(image.charAt(0));
    
    if (_maxLength < length) {
      _maxLength = length;
    }
    if (startChar >= 0 && startChar < DIRECT_INDEX_COUNT) {
      return insertDirect(startChar, property);
    } else {
      return insertMapped(startChar, property);
    }
  }

  /**
   * Removing a special sequence from the store. If the special sequence denoted
   * by the given string does not exist the method returns <code>null</code>.
   *
   * @param  image  sequence to remove
   * @return the removed property or <code>null</code> if the sequence was not found
   */  
  public TokenizerProperty removeSpecialSequence(String image) {
    return searchString(image, true);
  }
  
  /**
   * Get the full description of a special sequence property.
   *
   * @param   image   sequence to find
   * @return  the full sequence description or <code>null</code>
   */
  public TokenizerProperty getSpecialSequence(String image) {
    return searchString(image, false);
  }

  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   *
   * @param   type  type of special sequence like {@link de.susebox.jtopas.Token#STRING}
   * @return  enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getSpecialSequences(int type) {
    return new SpecialSequencesIterator(type);
  }
  
  /**
   * Addingt or replacing a keyword.
   *
   * @param   property  the description of the new keyword
   * @return  the previously version of the given property or <code>null</code>
   */
  public TokenizerProperty addKeyword(TokenizerProperty property) {
    return addSpecialSequence(property);
  }
  
  /**
   * Removing a special sequence from the store. If the special sequence denoted
   * by the given string does not exist the method returns <code>null</code>.
   *
   * @param  image  sequence to remove
   * @return the removed property or <code>null</code> if the sequence was not found
   */  
  public TokenizerProperty removeKeyword(String image) {
    return removeSpecialSequence(image);
  }
  
  /**
   * Get the full description of a keyword property.
   *
   * @param   image     keyword candidate to look for
   * @return  the full keyword description or <code>null</code>
   */
  public TokenizerProperty getKeyword(String image) {
    return getSpecialSequence(image);
  }

  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects describing keywords.
   *
   * @return  enumeration of {@link TokenizerProperty} objects representing 
   *          keywords
   */  
  public Iterator getKeywords() {
    return getSpecialSequences(Token.KEYWORD);
  }

  /**
   * Get the property list for a given character.
   *
   * @param   startChar   start character
   * @return  list of properties starting with the given character
   */
  private PropertyList getList(char startChar) {
    // get the list: here we try a very fast access for the ASCII characters
    // via unchecked access and caught exceptions
    PropertyList list;

    try {
      // direct indexed sequence
      list = _asciiArray[startChar];
    } catch (IndexOutOfBoundsException ex) {
      // mapped sequence
      list = (PropertyList)_nonASCIIMap.get(new Character(startChar));
    }
    return list;
  }


  /**
   * Search a string in the given list. Optionally, remove it. Removal may also
   * reorganize the indexed array or non-ASCII map.
   *
   * @param   image     sequence to search
   * @param   removeIt  if <code>true</code> remove a found sequence from the list
   * @return  the property or <code>null</code> if the sequence was not found
   */
  protected TokenizerProperty searchString(String image, boolean removeIt) {
    char          startChar = getStartChar(image.charAt(0));
    PropertyList  list      = getList(startChar);
    PropertyList  prev      = null;
    
    while (list != null) {
      TokenizerProperty prop  = list._property;
      String            img   = prop.getImages()[0];
      int               res   = compare(img, image, 1);

      if (res == 0) {
        if (removeIt) {
          if (prev != null) {
            prev._next = list._next;
          } else {
            list = list._next;
            if (startChar >= 0 && startChar < DIRECT_INDEX_COUNT) {
              _asciiArray[startChar] = list;
            } else if (list != null) {
              _nonASCIIMap.put(new Character(startChar), list);
            } else {
              _nonASCIIMap.remove(new Character(startChar));
            }
          }
        }
        return prop;
      } else if (res < 0) {
        break;
      }
      prev = list;
      list = list._next;
    }
    return null;
  }
  
  
  /**
   * Insert a new property into the direct-index array.
   *
   * @param   property  the description of the new sequence
   * @return  the previously version of the given property or <code>null</code>
   */
  private TokenizerProperty insertDirect(char startChar, TokenizerProperty property) {
    // the first element with the given start letter ...
    if (_asciiArray[startChar] == null) {
      _asciiArray[startChar] = new PropertyList(property);
      return null;

    // ... or inserting/replacing in an existing list
    } else {
      return putIntoList(_asciiArray[startChar], property);
    }
  }
    

  /**
   * Insert a new property into the hash table for real unicode letters.
   *
   * @param   property  the description of the new sequence
   * @return  the previously version of the given property or <code>null</code>
   */
  private TokenizerProperty insertMapped(char startChar, TokenizerProperty property) {
    Character    key  = new Character(getStartChar(startChar));
    PropertyList list = (PropertyList)_nonASCIIMap.get(key);
    
    if (list == null) {
      _nonASCIIMap.put(key, new PropertyList(property));
      return null;
    } else {
      return putIntoList(list, property);
    }
  }

  
  /**
   * Insert/replace a property in a property list. The list is ordered by string
   * comparison. This is important for the search in {@link #startsWithSequenceCommentOrString}.
   *
   * @param   list      insert or replace in this list
   * @param   property  the description of the new sequence
   * @return  the previously version of the given property or <code>null</code>
   */
  private TokenizerProperty putIntoList(PropertyList list, TokenizerProperty property) {
    String        newImage = property.getImages()[0];
    PropertyList  prev;

    do {
      TokenizerProperty prop  = list._property;
      String            image = prop.getImages()[0];
      int               res   = compare(image, newImage, 1);

      if (res == 0) {
        list._property = property;
        return prop;
      } else if (res < 0) {
        list._next     = new PropertyList(prop, list._next);
        list._property = property;
        return null;
      }
      prev = list;
    } while ((list = prev._next) != null);
    
    // Append element
    prev._next = new PropertyList(property);
    return null;
  }

  
  /**
   * Compare method for sequences. Longer Strings are greater, shorter are lesser. 
   * Strings with equal lengths are compared in the usual way.
   *
   * @param thisImage   first string to compare
   * @param thatImage   second string to compare
   * @param fromIndex   start comparison from this index
   * @return 0 if equal, < 0 if thisImage < thatImage, > 0 otherwise
   */
  private int compare(String thisImage, String thatImage, int fromIndex) {
    int thisLength = thisImage.length();
    int thatLength = thatImage.length();
    
    if (thisLength != thatLength) {
      return thisLength - thatLength;
    }
    
    while (fromIndex < thisLength) {
      int res = compare(thisImage.charAt(fromIndex), thatImage.charAt(fromIndex));

      if (res != 0) {
        return res;
      }
      fromIndex++;
    }
    return 0;
  }
    
  /**
   * Compare method for a string and a character array. The method assumes that
   * the character array holds at least as many characters as the given string.
   *<br>
   * See {@link #compare} for details how the comparison is performed.
   *
   * @param prefix        string to compare
   * @param dataProvider  source to get the other characters to compare
   * @param offset        start comparison from this index
   * @return 0 if equal, < 0 if thisImage < thatImage, > 0 otherwise
   */
  private int comparePrefix(String prefix, DataProvider dataProvider, int offset) {
    while (offset < prefix.length()) {
      int res = compare(prefix.charAt(offset), dataProvider.getCharAt(offset));

      if (res != 0) {
        return res;
      }
      offset++;
    }
    return 0;
  }
  
  /**
   * Compare tho characters. Returns the difference of the to characters, 0 if
   * they are equal. The default implementation compares case-sensitive, for the
   * lexicographical solution see {@link NoCaseSequenceStore}..............
   *
   * @param char1       first character to compare
   * @param char2       first character to compare
   * @return 0 if equal, < 0 if char1 < char2, > 0 otherwise
   */
  protected int compare(char char1, char char2) {
    return char1 - char2;
  }
  
  
  
  //---------------------------------------------------------------------------
  // Inner class
  //
  
  /**
   * List element for equaly starting special sequences.
   */
  final class PropertyList {

    /**
     * Constructor taking the {@link TokenizerProperty} as its single argument.
     *
     * @param property  a {@link TokenizerProperty} instance
     */
    PropertyList(TokenizerProperty property) {
      this(property, null);
    }

    /**
     * Constructor taking a {@link TokenizerProperty} and the next list element. 
     * For the next element, <code>null</code> is a valid value.
     *
     * @param property  a {@link TokenizerProperty} instance
     * @param next      the following {@link PropertyList} element 
     */
    PropertyList(TokenizerProperty property, PropertyList next) {
      _property = property;
      _next     = next;
    }

    // members
    public PropertyList       _next;
    public TokenizerProperty  _property;
  }
  

  /**
   * Iterator for comments, strings and ordinary special sequences.
   * Instances of this inner class are returned when a call to {@link #getSpecialSequences}
   * is done. Each element of the enumeration contains a {@link TokenizerProperty}
   * element, that in turn has the comment, special sequence etc. together with
   * its companion
   */
  protected final class SpecialSequencesIterator implements Iterator {

    /**
     * constructor taking the calling <code>Tokenizer</code> and the type of the
     * {@link TokenizerProperty}. If the type is 0 then special sequences, line and 
     * block comments are returned in one iterator
     *
     * @param parent  the calling tokenizer
     * @param type    type of the <code>TokenizerProperty</code> 
     */
    public SpecialSequencesIterator(int type) {
      _type = type;
    }

    /**
     * Checking for the next element in a special sequence list, that has the
     * required type. This method is the one that ultimately decides if there are
     * more elements or not.
     *
     * @return <code>true</code> if there is a matching {@link TokenizerProperty}
     *         element, <code>false</code> otherwise
     */
    private boolean listHasNext() {
      while (_currentList != null) {
        if (_type == 0 || _currentList._property.getType() == _type) {
          return true;
        }
        _currentList = _currentList._next;
      }
      return false;
    }

    /**
     * The well known method from the {@link java.util.Iterator} interface.
     *
     * @return <code>true</code> if there are more {@link TokenizerProperty}
     *         elements, <code>false</code> otherwise
     */
    public boolean hasNext() {
      // simple: check the current list for a successor
      if (listHasNext()) {
        return true;
      }

      // already reached the tree map iterator ?
      if (_mapIterator != null) {
        while (_mapIterator.hasNext()) {
          _currentList = (PropertyList)_mapIterator.next();
          if (listHasNext()) {
            return true;
          }
        }
        
      // ... or still the direct index array ?
      } else {
        while (++_currentIndex < SequenceStore.this._asciiArray.length) {
          if ((_currentList = SequenceStore.this._asciiArray[_currentIndex]) != null) {
            if (listHasNext()) {
              return true;
            }
          }
        }
        _mapIterator = SequenceStore.this._nonASCIIMap.values().iterator();
        _currentList = null;
        return hasNext();
      }

      // no (more) sequences
      return false;
    }

    /**
     * Retrieve the next {@link TokenizerProperty} in this enumeration.
     *
     * @return a {@link TokenizerProperty} of the desired type or <code>null</code>
     * @throws NoSuchElementException if there is no more element in this iterator
     */
    public Object next() throws NoSuchElementException {
      if (! hasNext()) {
        throw new NoSuchElementException();
      }
      
      _currentElem = _currentList;
      _currentList = _currentList._next;
      return _currentElem._property;
    }

    /**
     * Remove the current special sequence entry from the collection. This is an
     * alternative to {@link Tokenizer#removeSpecialSequence}.
     *
     * @throws  IllegalStateExcpetion if {@link #next} has not been called before or
     *          <code>remove</code> has been called already after the last <code>next</code>.
     */
    public void remove() throws IllegalStateException {
      // if current element is not set
      if (_currentElem == null) {
        throw new IllegalStateException();
      }

      // remove current element
      TokenizerProperty prop  = _currentElem._property;

      _currentElem = null;
      SequenceStore.this.searchString(prop.getImages()[0], true);
    }


    // members
    private final int     _type;
    private Iterator      _mapIterator  = null;
    private int           _currentIndex = -1;
    private PropertyList  _currentList  = null;
    private PropertyList  _currentElem  = null;
  }


  //---------------------------------------------------------------------------
  // Members
  //
  protected final PropertyList[]  _asciiArray;
  protected final TreeMap         _nonASCIIMap;
  private   int                   _maxLength;
  private   boolean               _useExactLength;
}
