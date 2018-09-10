/*
 * StandardTokenizerProperties.java: general-use TokenizerProperties implementation
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

import java.util.ArrayList;
//-----------------------------------------------------------------------------
// Imports
//
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.integratedmodelling.contrib.jtopas.impl.NoCaseSequenceStore;
import org.integratedmodelling.contrib.jtopas.impl.PatternMatcher;
import org.integratedmodelling.contrib.jtopas.impl.SequenceStore;
import org.integratedmodelling.contrib.jtopas.spi.DataMapper;
import org.integratedmodelling.contrib.jtopas.spi.DataProvider;
import org.integratedmodelling.contrib.jtopas.spi.PatternHandler;


//-----------------------------------------------------------------------------
// Class StandardTokenizerProperties
//

/**<p>
 * The class <code>StandardTokenizerProperties</code> provides a simple implementation
 * of the {@link TokenizerProperties} interface for use in most situations.
 *</p><p>
 * Note that this class takes advantage of JTopas features that use Java 1.4 or
 * higher. It can still be used in older environments but not compiled with JDK
 * versions below 1.4!
 *</p>
 *
 * @see TokenizerProperties
 * @see Tokenizer
 * @author Heiko Blau
 */
public class StandardTokenizerProperties
  extends     AbstractTokenizerProperties
  implements  TokenizerProperties, DataMapper
{
  
  //---------------------------------------------------------------------------
  // Properties
  //
  
  /**
   * Maximum length of a non-free pattern match. These are patterns that dont
   * have the {@link Flags#F_FREE_PATTERN} flag set. A common 
   * example are number patterns.
   */
  public static final short MAX_NONFREE_MATCHLEN = 1024;
  
  
  //---------------------------------------------------------------------------
  // Constructors
  //
  
  /**
   * Default constructor that intitializes an instance with the default whitespaces
   * and separator sets. {@link Tokenizer} instances using this <code>StandardTokenizerProperties</code>
   * object, split text between spaces, tabs and line ending sequences as well
   * as between punctuation characters.
   */  
  public StandardTokenizerProperties() {
    this(0);
  }

  /**
   * This constructor takes the control flags to be used. It is a shortcut to:
   * <pre>
   *   TokenizerProperties props = new StandardTokenizerProperties();
   *
   *   props.setParseFlags(flags);
   * </pre>
   * See the {@link TokenizerProperties} interface for the supported flags.
   *<br>
   * The {@link TokenizerProperties#DEFAULT_WHITESPACES} and 
   * {@link TokenizerProperties#DEFAULT_SEPARATORS} are used for whitespace and 
   * separator handling if no explicit calls to {@link #setWhitespaces} and 
   * {@link #setSeparators} will follow subsequently.
   *
   * @param flags     tokenizer control flags
   * @see   #setParseFlags
   */  
  public StandardTokenizerProperties(int flags) {
    this(flags, DEFAULT_WHITESPACES, DEFAULT_SEPARATORS);
  }
  
  
  /**
   * This constructor takes the whitespace and separator sets to be used. It is 
   * a shortcut to:
   * <pre>
   *   TokenizerProperties props = new StandardTokenizerProperties();
   *
   *   props.setWhitespaces(ws);
   *   props.setSeparators(sep);
   * </pre>
   *
   * @param flags       tokenizer control flags
   * @param whitespaces the whitespace set
   * @param separators  the set of separating characters
   * @see   #setParseFlags
   * @see   #setWhitespaces
   * @see   #setSeparators
   */  
  public StandardTokenizerProperties(int flags, String whitespaces, String separators) {
    Arrays.fill(_charFlags, 0);
    setParseFlags(flags);
    setWhitespaces(whitespaces);
    setSeparators(separators);
  }
  
  
  //---------------------------------------------------------------------------
  // Abstract methods of the base class
  //

  /**
   * Retrieving a property by a given type and image. See the method description
   * in {@link AbstractTokenizerProperties} for details.
   *
   * @param   type        the type the returned property should have
   * @param   startImage  the (starting) image
   * @return  the token description for the image or <code>null</code>
   */
  protected TokenizerProperty doGetProperty(int type, String startImage) {
    TokenizerProperty prop = null;
    
    switch (type) {
    case Token.KEYWORD:
      if (_keywords[0] != null) {
        prop = _keywords[0].getKeyword(startImage);
      }
      if (prop == null && _keywords[1] != null) {
        prop = _keywords[1].getKeyword(startImage);
      }
      break;
      
    case Token.STRING:
    case Token.LINE_COMMENT:
    case Token.BLOCK_COMMENT:
    case Token.SPECIAL_SEQUENCE:
      if (_sequences[0] != null) {
        prop = _sequences[0].getSpecialSequence(startImage);
      }
      if (prop == null && _sequences[1] != null) {
        prop = _sequences[1].getSpecialSequence(startImage);
      } 
      break;
      
    case Token.PATTERN:
      for (int index = 0; index < _patterns.size(); ++index) {
        PatternMatcher    data = (PatternMatcher)_patterns.get(index);

        prop = data.getProperty();
        if (prop.getImages()[0].equals(startImage)) {
          break;
        }
        prop = null;
      }
      break;

    case Token.WHITESPACE:
    case Token.SEPARATOR:
    default:
      throw new IllegalArgumentException("Unsupported property type " + type + ". (Leading) image \"" + startImage + "\".");
    }

    // either the required property or null
    return prop;
  }  
  
  
  /**
   * Setting a new separator set. See the method description in 
   * {@link AbstractTokenizerProperties} for details.
   *
   * @param   separators    the set of separators including ranges
   * @return  the replaced separator set or <code>null</code>
   */
  protected String doSetSeparators(String separators) {
    String oldValue;

    // which separators should be set?
    if ((_flags & Flags.F_NO_CASE) == 0) {
      oldValue          = (_separatorsCase.length() > 0) ? _separatorsCase : _separatorsNoCase;
      _separatorsCase   = separators;
      _separatorsNoCase = "";
    } else {
      oldValue          = (_separatorsNoCase.length() > 0) ? _separatorsNoCase : _separatorsCase;
      _separatorsCase   = "";
      _separatorsNoCase = separators;
    }

    // mark seaparators in character table
    putCharSet(oldValue,   Token.SEPARATOR, false);
    putCharSet(separators, Token.SEPARATOR, true);

    // normalize the old value
    if (oldValue == null || oldValue.length() == 0) {
      return null;
    } else {
      return oldValue;
    }
  }
  
  /**
   * Setting a new whitespace set. See the method description in 
   * {@link AbstractTokenizerProperties} for details.
   *
   * @param   whitespaces   the set of whitespaces including ranges
   * @return  the replaced whitespace set or <code>null</code>
   */
  protected String doSetWhitespaces(String whitespaces) {
    // set the right whitespaces
    String oldValue;

    if ((_flags & Flags.F_NO_CASE) == 0) {
      oldValue            = (_whitespacesCase.length() > 0) ? _whitespacesCase : _whitespacesNoCase;
      _whitespacesCase    = whitespaces;
      _whitespacesNoCase  = "";
    } else {
      oldValue            = (_whitespacesNoCase.length() > 0) ? _whitespacesNoCase : _whitespacesCase;
      _whitespacesCase    = "";
      _whitespacesNoCase  = whitespaces;
    }

    // mark whitespaces in character table
    putCharSet(oldValue,    Token.WHITESPACE, false);
    putCharSet(whitespaces, Token.WHITESPACE, true);

    // return changes
    if (oldValue == null || oldValue.length() == 0) {
      return null;
    } else {
      return oldValue;
    }
  }
  
  /**
   * Registering a {@link TokenizerProperty}.
   * See the method description in {@link AbstractTokenizerProperties}.
   *
   * @param   property   property to register
   * @return  the replaced property or <code>null</code>
   */
  protected TokenizerProperty doAddProperty(TokenizerProperty property) {
    switch (property.getType()) {
    case Token.STRING:
    case Token.LINE_COMMENT:
    case Token.BLOCK_COMMENT:
    case Token.SPECIAL_SEQUENCE:
      return addSpecialSequence(property);

    case Token.KEYWORD:
      return addKeyword(property);

    case Token.PATTERN:
      return addPattern(property);

    case Token.WHITESPACE:
    case Token.SEPARATOR:
    default:
      throw new IllegalArgumentException("Unsupported property type " + property.getType() + ". (Leading) image \"" + property.getImages()[0] + "\".");
    }
  }
  
  /**
   * Deregistering a {@link TokenizerProperty} from the store.
   * See the method description in {@link AbstractTokenizerProperties}.
   *
   * @param   property    property to remove
   * @return  the replaced property or <code>null</code>
   */  
  protected TokenizerProperty doRemoveProperty(TokenizerProperty property) {
    // removing property according to type
    TokenizerProperty prop  = null;
    String            image = property.getImages()[0];
    
    switch (property.getType()) {
    case Token.LINE_COMMENT:
    case Token.BLOCK_COMMENT:
    case Token.STRING:
    case Token.SPECIAL_SEQUENCE:
      if (_sequences[0] != null) {
        prop = _sequences[0].removeSpecialSequence(image);
      } 
      if (prop == null && _sequences[1] != null) {
        prop = _sequences[1].removeSpecialSequence(image);
      }
      break;

    case Token.KEYWORD:
      if (_keywords[0] != null) {
        prop = _keywords[0].removeKeyword(image);
      } 
      if (prop == null && _keywords[1] != null) {
        prop = _keywords[1].removeKeyword(image);
      }
      break;

    case Token.PATTERN:
      for (int index = 0; index < _patterns.size(); ++index) {
        PatternMatcher    data = (PatternMatcher)_patterns.get(index);

        prop = data.getProperty();
        if (prop.getImages()[0].equals(image)) {
          _patterns.remove(index);
          break;
        } else {
          prop = null;
        }
      }
      break;

    case Token.WHITESPACE:
    case Token.SEPARATOR:
    default:
      throw new IllegalArgumentException("Unsupported property type " + property.getType() + ". (Leading) image \"" + image + "\".");
    }
    
    // return removed property
    return prop;
  }
  

  //---------------------------------------------------------------------------
  // Methods of the TokenizerProperties interface
  //
  
  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects. See the method description in {@link TokenizerProperties}.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getStrings() {
    return new SpecialSequencesIterator(this, _sequences, Token.STRING);
  }
  
  /**
   * Obtaining the whitespace character set.
   * See the method description in {@link TokenizerProperties}.
   *
   * @see #setWhitespaces
   * @return the currently active whitespace set
   */
  public String getWhitespaces() {
    synchronized(this) {
      return _whitespacesCase + _whitespacesNoCase;
    }
  }
  
  /**
   * Obtaining the separator set of the <code>Tokenizer</code>.
   * See the method description in {@link TokenizerProperties}.
   *
   * @see #setSeparators
   * @return the currently used set of separating characters
   */
  public String getSeparators() {
    synchronized(this) {
      return _separatorsCase + _separatorsNoCase;
    }
  }
  
  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   * See the method description in {@link TokenizerProperties}.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getLineComments() {
    return new SpecialSequencesIterator(this, _sequences, Token.LINE_COMMENT);
  }
  
  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   * See the method description in {@link TokenizerProperties}.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getBlockComments() {
    return new SpecialSequencesIterator(this, _sequences, Token.BLOCK_COMMENT);
  }
  
  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   * See the method description in {@link TokenizerProperties}.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getSpecialSequences() {
    return new SpecialSequencesIterator(this, _sequences, Token.SPECIAL_SEQUENCE);
  }
  
  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   * See the method description in {@link TokenizerProperties}.
   *
   * @return iteration of {@link TokenizerProperty} objects
   */  
  public Iterator getKeywords() {
    return new SpecialSequencesIterator(this, _keywords, Token.KEYWORD);
  }
  
  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects. Each <code>TokenizerProperty</code> object contains a pattern and 
   * its companion if such an associated object exists.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getPatterns() {
    return new PatternIterator(this);
  }
  

  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   * See the method description in {@link TokenizerProperties}.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getProperties() {
    return new FullIterator(this);
  }
  
  
  //---------------------------------------------------------------------------
  // Methods of the DataMapper interface
  //
  
  /**
   * Setting the backing {@link TokenizerProperties} instance this <code>DataMapper</code> 
   * is working with. Usually, the <code>DataMapper</code>
   * interface is implemented by <code>TokenizerProperties</code> implementations,
   * too. Otherwise the {@link Tokenizer} using the <code>TokenizerProperties</code>, 
   * will construct a default <code>DataMapper</code> an propagate the 
   * <code>TokenizerProperties</code> instance by calling this method.
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
    throws UnsupportedOperationException, NullPointerException
  {
    throw new UnsupportedOperationException(
      "Class " + StandardTokenizerProperties.class.getName() + " already defines the " + DataMapper.class.getName() + " interface.");
  }

  /**
   * The method retrieves the backing {@link de.susebox.jtopas.TokenizerProperties}
   * instance, this <code>DataMapper</code> is working on. For implementations
   * of the <code>TokenizerProperties</code> interface that also implement the
   * <code>DataMapper</code> interface, this method returns the instance itself
   * it is called on.
   *<br>
   * Otherwise the method returns the <code>TokenizerProperties</code> instance 
   * passed through the last call to {@link #setTokenizerProperties} or <code>null</code>
   * if no such call has taken place so far.
   *
   * @return the backing {@link de.susebox.jtopas.TokenizerProperties} or <code>null</code>
   */
  public TokenizerProperties getTokenizerProperties() {
    return this;
  }

  /**
   * This method checks if the character is a whitespace. Implement Your own
   * code for situations where this default implementation is not fast enough
   * or otherwise not really good.
   *
   * @param testChar  check this character
   * @return <code>true</code> if the given character is a whitespace,
   *         <code>false</code> otherwise
   */
  public boolean isWhitespace(char testChar) {
    try {
      return (_charFlags[testChar] & CHARFLAG_WHITESPACE) != 0;
    } catch (ArrayIndexOutOfBoundsException ex) {
      Integer extFlags = (Integer)_extCharFlags.get(new Integer(testChar));
      return (extFlags != null && (extFlags.intValue() & CHARFLAG_WHITESPACE) != 0);
    }
  }
      
 
  /**
   * This method detects the number of whitespace characters the data range given
   * through the {@link DataProvider} parameter starts with.
   *
   * @param   dataProvider  the source to get the data range from
   * @return  number of whitespace characters starting from the given offset
   * @throws  TokenizerException failure while reading data from the input stream
   * @throws  NullPointerException  if no {@link DataProvider} is given
   * @see     de.susebox.jtopas.spi.DataProvider
   */
  public int countLeadingWhitespaces(DataProvider dataProvider) throws NullPointerException {
    int maxChars = dataProvider.getLength();
    int len      = 0;
    
    while (len < maxChars && isWhitespace(dataProvider.getCharAt(len))) {
      len++;
    }
    return len;
  }
  
 
  /** 
   * If a {@link Tokenizer} performs line counting, it is often nessecary to
   * know if newline characters are considered to be whitespaces. See 
   * {@link de.susebox.jtopas.spi.WhitespaceHandler} for details.
   *
   * @return  <code>true</code> if newline characters are in the current whitespace set,
   *          <code>false</code> otherwise
   *
   */
  public boolean newlineIsWhitespace() {
    return   (_charFlags['\n'] & CHARFLAG_WHITESPACE) != 0
          && (_charFlags['\r'] & CHARFLAG_WHITESPACE) != 0;
  }  
  

  /**
   * This method checks the given character if it is a separator.
   *
   * @param testChar  check this character
   * @return <code>true</code> if the given character is a separator,
   *         <code>false</code> otherwise
   */
  public boolean isSeparator(char testChar) {
    try {
      return (_charFlags[testChar] & CHARFLAG_SEPARATOR) != 0;
    } catch (ArrayIndexOutOfBoundsException ex) {
      Integer extFlags = (Integer)_extCharFlags.get(new Integer(testChar));
      return (extFlags != null && (extFlags.intValue() & CHARFLAG_SEPARATOR) != 0);
    }
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
    synchronized(_sequences) {
      return (_sequences[0] != null || _sequences[1] != null);
    }
  }
  
  /**
   * This method checks if a given range of data starts with a special sequence,
   * a comment or a string. These three types of token are testet together since
   * both comment and string prefixes are ordinary special sequences. Only the 
   * actions preformed <strong>after</strong> a string or comment has been detected,
   * are different.
   *<br>
   * The method returns <code>null</code> if no special sequence, comment or string 
   * could matches the the leading part of the data range given through the
   * {@link DataProvider}.
   *<br>
   * In cases of strings or comments, the return value contains the description
   * for the introducing character sequence, <strong>NOT</strong> the whole
   * string or comment. The reading of the rest of the string or comment is done
   * by the calling {@link de.susebox.jtopas.Tokenizer}.
   *
   * @param   dataProvider  the source to get the data range from
   * @return  a {@link de.susebox.jtopas.TokenizerProperty} if a special sequence, 
   *          comment or string could be detected, <code>null</code> otherwise
   * @throws  TokenizerException failure while reading more data
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public TokenizerProperty startsWithSequenceCommentOrString(DataProvider dataProvider) 
    throws TokenizerException, NullPointerException
  {
    // we need the longest possible match
    synchronized(_sequences) {
      TokenizerProperty caseProp   = (_sequences[0] != null) ? 
                                        _sequences[0].startsWithSequenceCommentOrString(dataProvider) : null;

      TokenizerProperty noCaseProp = (_sequences[1] != null) ? 
                                        _sequences[1].startsWithSequenceCommentOrString(dataProvider) : null;

      if (noCaseProp == null) {
        return caseProp;
      } else if (caseProp == null) {
        return noCaseProp;
      } else if (caseProp.getImages()[0].length() >= noCaseProp.getImages()[0].length()) {
        return caseProp;
      } else {
        return noCaseProp;
      }
    }
  }

  /**
   * This method returns the length of the longest special sequence, comment or
   * string prefix that is known to this <code>SequenceHandler</code>. When
   * calling {@link #startsWithSequenceCommentOrString}, the passed {@link DataProvider}
   * parameter will supply at least this number of characters (see {@link DataProvider#getLength}).
   * If less characters are provided, EOF is reached.
   *
   * @return  the number of characters needed in the worst case to identify a 
   *          special sequence
   */
  public int getSequenceMaxLength() {
    int maxLength = 0;

    synchronized(_sequences) {
      if (_sequences[0] != null) {
        maxLength = _sequences[0].getSequenceMaxLength();
      }
      if (_sequences[1] != null && _sequences[1].getSequenceMaxLength() > maxLength) {
        maxLength = _sequences[1].getSequenceMaxLength();
      }
    }
    return maxLength;
  }

  
  /**
   * This method can be used by a {@link de.susebox.jtopas.Tokenizer} implementation 
   * for a fast detection if keyword matching must be performed at all. If the method
   * returns <code>false</code> time-consuming preparations can be skipped.
   *
   * @return  <code>true</code> if there actually are pattern that can be tested
   *          for a match, <code>false</code> otherwise.
   */
  public boolean hasKeywords() {
    synchronized(_keywords) {
      return (_keywords[0] != null || _keywords[1] != null);
    }
  }
  
  /**
   * This method checks if the character range given through the 
   * {@link DataProvider} comprises a keyword.
   *
   * @param   dataProvider  the source to get the data from, that are checked
   * @return  a {@link de.susebox.jtopas.TokenizerProperty} if a keyword could be 
   *          found, <code>null</code> otherwise
   * @throws  TokenizerException failure while reading more data
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public TokenizerProperty isKeyword(DataProvider dataProvider)
    throws TokenizerException, NullPointerException
  {
    synchronized(_keywords) {
      TokenizerProperty prop;
    
      if (_keywords[0] != null) {
        prop = _keywords[0].isKeyword(dataProvider);
      } else {
        prop = null;
      }
      if (prop == null && _keywords[1] != null) {
        prop = _keywords[1].isKeyword(dataProvider);
      }
      return prop;
    }
  }
  
  
  /**
   * This method can be used by a {@link de.susebox.jtopas.Tokenizer} implementation 
   * for a fast detection if pattern matching must be performed at all. If the method
   * returns <code>false</code> time-consuming preparations can be skipped.
   *
   * @return  <code>true</code> if there actually are pattern that can be tested
   *          for a match, <code>false</code> otherwise.
   */
  public boolean hasPattern() {
    synchronized(_patterns) {
      return (_patterns.size() > 0);
    }
  }
    
  /**
   * This method checks if the start of a character range given through the 
   * {@link DataProvider} matches a pattern.
   *
   * @param   dataProvider    the source to get the data from
   * @return  a {@link de.susebox.jtopas.spi.PatternHandler.Result} object 
   *          or <code>null</code> if no match was found
   * @throws  TokenizerException    generic exception
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public PatternHandler.Result matches(DataProvider dataProvider)
    throws TokenizerException, NullPointerException
  {
    synchronized(_patterns) {
      PatternHandler.Result bestResult   = null;
      
      // only get the string if pattern are available
      for (int index = 0; index < _patterns.size(); ++index) {
        PatternMatcher        data = (PatternMatcher)_patterns.get(index);
        PatternHandler.Result result = data.matches(dataProvider);

        if (result != null) {
          if (bestResult == null || bestResult.getLengthOfMatch() < result.getLengthOfMatch()) {
            bestResult = result;
          }
        }
      }
      
      // return the best result
      return bestResult;
    } 
  }

  
  //---------------------------------------------------------------------------
  // Implementation
  //

  /**
   * Registering a pattern with an associated object. The method assumes that the 
   * given pattern property has been checked for not being null, having a non-empty 
   * pattern image and normalized flags ({@link AbstractTokenizerProperties#normalizeFlags}).
   * See the method description in {@link AbstractTokenizerProperties}.
   *
   * @param   patternProp     the regular expression to be added
   * @return  the replaced pattern property or <code>null</code>
   * @throws  IllegalArgumentException if pattern matching is not available
   */
  protected TokenizerProperty addPattern(TokenizerProperty patternProp) {
    // construct the pattern
    String          pattern = patternProp.getImages()[0];
    PatternMatcher  data    = new PatternMatcher(patternProp, getParseFlags());
    
    // Register pattern. First search for existing one
    for (int index = 0; index < _patterns.size(); ++index) {
      PatternMatcher    oldData = (PatternMatcher)_patterns.get(index);
      TokenizerProperty oldProp = oldData.getProperty();

      if (oldProp.getImages()[0].equals(pattern)) {
        _patterns.set(index, data);
        return oldProp;
      }
    }

    // not found -> register new pattern
    _patterns.add(data);
    return null;
  }
  
  /**
   * Registering a keyword property. The method assumes that the given keyword 
   * property has been checked for not being null, having a non-empty keyword 
   * image and normalized flags ({@link AbstractTokenizerProperties#normalizeFlags}).
   *
   * @param   keywordProp   keyword property to register
   * @return  the replaced keyword property or <code>null</code>
   */  
  protected TokenizerProperty addKeyword(TokenizerProperty keywordProp) {
    // case-sensitive keyword?
    boolean noCase   = isFlagSet(keywordProp, Flags.F_NO_CASE);
    int     arrayIdx = noCase ? 1 : 0;

    // first keyword?
    if (_keywords[arrayIdx] == null) {
      if (noCase) {
        _keywords[arrayIdx] = new NoCaseSequenceStore(true);
      } else {
        _keywords[arrayIdx] = new SequenceStore(true);
      }
    }

    // add / replace property
    return _keywords[arrayIdx].addKeyword(keywordProp);
  }
  
  
  /**
   * This method adds or replaces strings, comments and ordinary special sequences.
   * The method assumes that the given special sequence property has been checked 
   * for not being null, having a non-empty imagesand normalized flags 
   * ({@link AbstractTokenizerProperties#normalizeFlags}).
   *
   * @param   property  the description of the new sequence
   * @return  the replaced special sequence property or <code>null</code>
   */
  protected TokenizerProperty addSpecialSequence(TokenizerProperty property) {
    // case-sensitive sequence?
    boolean noCase   = isFlagSet(property, Flags.F_NO_CASE);
    int     arrayIdx = noCase ? 1 : 0;

    // first special sequence?
    if (_sequences[arrayIdx] == null) {
      if (noCase) {
        _sequences[arrayIdx] = new NoCaseSequenceStore(false);      
      } else {
        _sequences[arrayIdx] = new SequenceStore(false);      
      }
    }

    // add / replace property
    return _sequences[arrayIdx].addSpecialSequence(property);
  }
  
  /**
   * Set or removes the flags corresponding to type and case-sensitivity from the
   * character flags tables.
   *
   * @param set   the character set to handle (may contain ranges)
   * @param type  token type fro the characters ({@link Token#WHITESPACE} or {@link Token#SEPARATOR})
   * @param setIt if <code>true</code> the approbriate flags will be set, otherwise removed
   */
  private void putCharSet(String set, int type, boolean setIt) {
    // which flags ?
    int charFlags = 0;
    
    switch (type) {
    case Token.WHITESPACE:
      charFlags = CHARFLAG_WHITESPACE;
      break;
    case Token.SEPARATOR:
      charFlags = CHARFLAG_SEPARATOR;
      break;
    }
    
    // analyze the given set
    int   length = (set != null) ? set.length() : 0;
    char  start, end, setChar;
    
    for (int ii = 0; ii < length; ++ii)  {
      setChar = set.charAt(ii);

      switch (setChar) {
      case '-':
        start = (ii > 0) ? set.charAt(ii - 1) : 0;
        end   = (ii < length - 1) ? set.charAt(ii + 1) : 0xFFFF;
        ii += 2; 
        break;

      case '\\':
        setChar = (ii + 1 >= length) ? 0 : set.charAt(ii + 1);
        ii++;
        /* no break */

      default:
        start = end = setChar;
      }
      
      // put flags
      for (char index = start; index <= end; ++index) {
        char currChar = index;
        
        do {
          if (currChar < _charFlags.length) {
            // one-byte characters 
            if (setIt) {
              _charFlags[currChar] |= charFlags;
            } else {
              _charFlags[currChar] &= ~charFlags;
            }
            
          } else {
            // longer characters
            Integer key      = new Integer(currChar);
            Integer extFlags = (Integer)_extCharFlags.get(key);

            if (setIt) {
              extFlags = new Integer(extFlags.intValue() | charFlags);
            } else {
              extFlags = new Integer(extFlags.intValue() & ~charFlags);
            }
            _extCharFlags.put(key, extFlags);
          }
          
          // settings must be also done for the upper/lowercase variant 
          if (Character.isLowerCase(currChar)) {
            currChar = Character.toUpperCase(currChar);
          } else if (Character.isUpperCase(currChar)) {
            currChar = Character.toLowerCase(currChar);
          }
        } while ((_flags & Flags.F_NO_CASE) != 0 && currChar != index);
      }
    }
  }
  
  
  //---------------------------------------------------------------------------
  // Class members
  //
  
  /**
   * character flag for whitespaces
   */
  public static final int CHARFLAG_WHITESPACE = 1;
  
  /**
   * character flag for whitespaces
   */
  public static final int CHARFLAG_SEPARATOR = 2;

  
  //---------------------------------------------------------------------------
  // Members
  //
  
  /**
   * array containing the flags for whitespaces and separators
   */
  protected int _charFlags[] = new int[256];
  
  /**
   * Map with flags for characters beyond 256;
   */
  protected HashMap _extCharFlags = new HashMap();
   
  /**
   * current whitespace characters including character ranges.
   */
  protected String _whitespacesCase = DEFAULT_WHITESPACES;
  
  /**
   * current whitespace characters including character ranges. Case is ignored.
   */
  protected String _whitespacesNoCase = "";
  
  /**
   * current separator characters including character ranges.
   */
  protected String _separatorsCase = DEFAULT_SEPARATORS;
  
  /**
   * current separator characters including character ranges. Case is ignored.
   */
  protected String _separatorsNoCase = "";
  
  /**
   * The first element is the {@link de.susebox.jtopas.impl.SequenceStore} for 
   * the case-sensitive sequences, the second is for the case-insensitive ones.
   */
  protected SequenceStore[] _sequences = new SequenceStore[2];
  
  /**
   * Like the array {@link #_sequences} this two-element Array contains two
   * {@link de.susebox.jtopas.impl.SequenceStore}, the first for the case-sensitive 
   * keywords, the second for the case-insensitive ones.
   */
  protected SequenceStore[] _keywords = new SequenceStore[2];
  
  /**
   * This array contains the patterns
   */
  protected ArrayList _patterns = new ArrayList();
}



//---------------------------------------------------------------------------
// inner classes
//

/**
 * Instances of this inner class are returned when a call to 
 * {@link TokenizerProperties#getProperties}.
 * Each element of the enumeration contains a {@link TokenizerProperty} element.
 */
final class FullIterator implements Iterator {
  
  /**
   * constructor taking the calling {@link TokenizerProperties} object to retrieve
   * the members holding {@link TokenizerProperty} elements which are iterated by 
   * this <code>FullIterator</code> instance.
   *
   * @param caseSensitiveMap  map with properties where case matters
   * @param caseSensitiveMap  map with properties where case doesn't matter
   */
  public FullIterator(StandardTokenizerProperties parent) {
    // create list of iterators
    _iterators    = new Object[3];
    _iterators[0] = new SpecialSequencesIterator(parent, parent._keywords, Token.KEYWORD);
    _iterators[1] = new SpecialSequencesIterator(parent, parent._sequences, 0);
    _iterators[2] = new PatternIterator(parent);
    _currIndex    = 0;
  }

  /**
   * Test wether there is another element in the iterated set or not. See
   * {@link java.util.Iterator} for details.
   *
   * @return <code>true</code>if another call to {@link #next} will return an object,
   *        <code>false</code> otherwise
   */
  public boolean hasNext() {
    synchronized(this) {
      while (_currIndex < _iterators.length) {
        Iterator iter = (Iterator)_iterators[_currIndex];

        if (iter.hasNext()) {
          return true;
        }
        _currIndex++;
      }
      return false;
    }
  }
  
  /**
   * Retrieve the next element in the iterated set. See {@link java.util.Iterator} 
   * for details.
   *
   * @return the next element or <code>null</code> if there is none
   */
  public Object next() {
    if (hasNext()) {
      synchronized(this) {
        Iterator iter = (Iterator)_iterators[_currIndex];
        return iter.next();
      }
    } else {
      return null;
    }
  }
  
  /**
   * Retrieve the next element in the iterated set. See {@link java.util.Iterator} 
   * for details.
   *
   * @return the next element or <code>null</code> if there is none
   */
  public void remove() {
    if (_currIndex < _iterators.length) {
      Iterator iter = (Iterator)_iterators[_currIndex];
      iter.remove();
    }
  }
  
  
  // members
  private Object[]                    _iterators  = null;
  private int                         _currIndex  = -1;
}

/**
 * Instances of this inner class are returned when a call to {@link TokenizerProperties#getKeywords}
 * or {@link TokenizerProperties#getPatterns}.
 * Each element of the enumeration contains a {@link TokenizerProperty} element,
 * that in turn has the keyword or a pattern with its companion
 */
final class MapIterator implements Iterator {

  /**
   * constructor taking the a case-sensitive and a case-insensitive {@link java.util.Map}
   * which are iterated by this <code>MapIterator</code> instance.
   *
   * @param caseSensitiveMap  map with properties where case matters
   * @param caseSensitiveMap  map with properties where case doesn't matter
   */
  public MapIterator(StandardTokenizerProperties parent, Map caseSensitiveMap, Map caseInsensitiveMap) {
    synchronized(this) {
      _parent = parent;
      if (caseSensitiveMap != null) {
        _iterators[0] = caseSensitiveMap.values().iterator();
      }
      if (caseInsensitiveMap != null) {
        _iterators[1] = caseInsensitiveMap.values().iterator();
      }
    }
  }

  /**
   * the well known method from the {@link java.util.Iterator} interface.
   *
   * @return <code>true</code> if there are more {@link TokenizerProperty}
   *         elements, <code>false</code> otherwise
   */
  public boolean hasNext() {
    // check the current array
    synchronized(_iterators) {
      if (_iterators[0] != null) {
        if (_iterators[0].hasNext()) {
          return true;
        } else {
          _iterators[0] = null;
        }
      }
      if (_iterators[1] != null) {
        if (_iterators[1].hasNext()) {
          return true;
        } else {
          _iterators[1] = null;
        }
      }
      return false;
    }
  }

  /**
   * Retrieve the next {@link TokenizerProperty} in this enumeration. 
   *
   * @return the next keyword as a <code>TokenizerProperty</code>
   * @throws NoSuchElementException if there is no more element in this iterator
   */
  public Object next() {
    if ( ! hasNext()) {
      throw new NoSuchElementException();
    }
    
    synchronized(this) {
      if (_iterators[0] != null) {
        _currentData = (TokenizerProperty)_iterators[0].next();
      } else {
        _currentData = (TokenizerProperty)_iterators[1].next();
      }
      return _currentData;
    }
  }
  
  /**
   * This method is similar to {@link Tokenizer#removeKeyword}.
   *
   * @throws  IllegalStateExcpetion if {@link #next} has not been called before or
   *          <code>remove</code> has been called already after the last <code>next</code>.
   */
  public void remove() {
    synchronized(this) {
      // if current element is not set
      if (_currentData == null) {
        throw new IllegalStateException();
      }
    
      if (_iterators[0] != null) {
        _iterators[0].remove();
      } else {
        _iterators[1].remove();
      }
      _parent.notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, _currentData));
      _currentData = null;
    }
  }

  // members
  private StandardTokenizerProperties _parent     = null;
  private Iterator[]                  _iterators  = new Iterator[2];
  private TokenizerProperty           _currentData   = null;
}



/**
 * Iterator for comments, strings and special sequences.
 * Instances of this inner class are returned when a call to one of the methods
 *<ul><li>
 *    {@link #getBlockComments}
 *</li><li>
 *    {@link #getLineComments}
 *</li><li>
 *    {@link #getStrings}
 *</li><li>
 *    {@link #getSpecialSequences}
 *</li></ul>
 * is done. Each element of the enumeration contains a {@link TokenizerProperty}
 * element, that in turn has the comment, special sequence etc. together with
 * its companion
 */
final class SpecialSequencesIterator implements Iterator {

  /**
   * constructor taking the calling <code>Tokenizer</code> and the type of the
   * {@link TokenizerProperty}. If the type is 0 then special sequences, line and 
   * block comments are returned in one iterator
   *
   * @param parent  the calling tokenizer
   * @param stores  which array of {@link de.susebox.jtopas.impl.SequenceStore} to use
   * @param type    type of the <code>TokenizerProperty</code> 
   */
  public SpecialSequencesIterator(StandardTokenizerProperties parent, SequenceStore[] stores, int type) {
    _type   = type;
    _parent = parent;
    _stores = stores;
  }

  /**
   * the well known method from the {@link java.util.Iterator} interface.
   *
   * @return <code>true</code> if there are more {@link TokenizerProperty}
   *         elements, <code>false</code> otherwise
   */
  public boolean hasNext() {
    synchronized(this) {
      if (_currentIterator != null && _currentIterator.hasNext()) {
        return true;
      }

      while (_stores != null && ++_currentIndex < _stores.length) {
        if (_stores[_currentIndex] != null) {
          _currentIterator = _stores[_currentIndex].getSpecialSequences(_type);
          if (_currentIterator.hasNext()) {
            return true;
          }
        }
      }
      return false;
    }
  }

  /**
   * Retrieve the next {@link TokenizerProperty} in this enumeration.
   *
   * @return a {@link TokenizerProperty} of the desired type or <code>null</code>
   * @throws NoSuchElementException if there is no more element in this iterator
   */
  public Object next() throws NoSuchElementException {
    synchronized(this) {
      if (! hasNext()) {
        throw new NoSuchElementException();
      }
      _currentElement = (TokenizerProperty)_currentIterator.next();
      return _currentElement;
    }
  }
  
  /**
   * Remove the current special sequence entry from the collection. This is an
   * alternative to {@link Tokenizer#removeSpecialSequence}.
   *
   * @throws  IllegalStateExcpetion if {@link #next} has not been called before or
   *          <code>remove</code> has been called already after the last <code>next</code>.
   */
  public void remove() throws IllegalStateException {
    synchronized(this) {
      // if current element is not set
      if (_currentElement == null) {
        throw new IllegalStateException();
      }
    
      try {
        _currentIterator.remove();
        _parent.notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, _currentElement));
        _currentElement = null;
      } catch (Exception ex) {
        throw new RuntimeException("While trying to remove current element of a SpecialSequencesIterator.", ex);
      }
    }
  }


  // members
  private StandardTokenizerProperties _parent           = null;
  private SequenceStore[]             _stores           = null;
  private TokenizerProperty           _currentElement   = null;
  private Iterator                    _currentIterator  = null;
  private int                         _currentIndex     = -1;
  private int                         _type             = Token.UNKNOWN;
}


/**
 * An {@link java.util.Iterator} for pattern.
 */
final class PatternIterator implements Iterator {
  /**
   * constructor taking the calling {@link TokenizerProperties} object.
   *
   * @param parent  the caller
   */
  public PatternIterator(StandardTokenizerProperties parent) {
    _parent   = parent;
    synchronized(parent._patterns) {
      _iterator = parent._patterns.iterator();
    }
  }

  /**
   * the well known method from the {@link java.util.Iterator} interface.
   *
   * @return <code>true</code> if there are more {@link TokenizerProperty}
   *         elements, <code>false</code> otherwise
   */
  public boolean hasNext() {
    return _iterator.hasNext();
  }

  /**
   * Retrieve the next {@link TokenizerProperty} in this enumeration. 
   *
   * @return  the next keyword as a <code>TokenizerProperty</code>
   * @throws NoSuchElementException if there is no more element in this iterator
   */
  public Object next() throws NoSuchElementException {
    synchronized(this) {
      _currentData = (PatternMatcher)_iterator.next();
      return _currentData.getProperty();
    }
  }
  
  /**
   * This method is similar to {@link Tokenizer#removeKeyword}
   */
  public void remove() {
    synchronized(this) {
      _iterator.remove();
      _parent.notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, _currentData.getProperty()));
    }
  }

  // members
  private StandardTokenizerProperties _parent = null;
  private Iterator                    _iterator = null;
  private PatternMatcher              _currentData = null;
}
