/*
 * TokenizerProperty.java: Various characteristics of Tokenizer.
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


//-----------------------------------------------------------------------------
// Class TokenizerProperty
//

/**
 * This class is mainly used by {@link TokenizerProperties} implementations to 
 * return {@link java.util.Iterator} objects of their various properties (keywords, 
 * special sequences etc.). Moreover, it can be used whereever information about
 * a lexical element description is needed, for instance when firing a 
 * {@link TokenizerPropertyEvent}.
 *
 * @see     TokenizerProperties
 * @author  Heiko Blau
 */
public class TokenizerProperty {
  
  //---------------------------------------------------------------------------
  // special property types
  //
  
  /**
   * The property describes a parse flag mask. This type is nessecary for the
   * "parse flag changed" event fired by {@link TokenizerProperties} 
   * implementations.
   */
  public static final byte PARSE_FLAG_MASK = 127;
  
  
  //---------------------------------------------------------------------------
  // getter- and setter methods
  //
  
  /**
   * Setting the type of the <code>TokenizerProperty</code>. Usually, one of the 
   * constants defined in {@link Token} is passed to this method, for instance 
   * {@link Token#NORMAL}, {@link Token#KEYWORD} or {@link Token#WHITESPACE}.
   *<br>
   * However, implementations and subclasses of the interfaces and classes in the 
   * package {@link de.susebox.jtopas} may define, set and return their own type 
   * constants.
   *
   * @param type type of the tokenizer property (one of the {@link Token} constants)
   * @see   Token
   * @see   #getType
   */  
  public void setType(int type) {
    _type = type;
  }
    
  /**
   * Retrieving the type of this property. Usually, one of the constants defined 
   * in {@link Token} is returned, for instance {@link Token#NORMAL}, {@link Token#KEYWORD} 
   * or {@link Token#WHITESPACE}. However, implementations and subclasses of the
   * interfaces and classes in the package {@link de.susebox.jtopas} may define,
   * set and return their own type constants.
   *
   * @return type of the property
   * @see #setType
   */  
  public int getType() {
    return _type;
  }

  /**
   * Setting flags. These flags are not specified here. Usually, a combination
   * of {@link TokenizerProperties} <code>F_...</code> constants is used here.
   *<br>
   * The method is equivalent to <code>{@link #setFlags(int, int)}(flags, flags)</code>.
   *
   * @param flags   a bitmask
   * @see   #getFlags
   */  
  public void setFlags(int flags) {
    setFlags(flags, flags);
  }
    
  /**
   * Setting the values for the flags contained in the flag mask. The flags 
   * are not specified here. Usually, a combination of {@link TokenizerProperties} 
   * <code>F_...</code> constants is used here.
   *<br>
   * The flag mask contains the set flags that are valid for this 
   * <code>TokenizerProperty</code>. Flags that are not contained in the flag mask, 
   * have an "unknown" value in this instance.
   *<br>
   * A flag mask value of -1 means that all flags should are valid. <code>flag = 0</code>
   * and <code>flagMask = TokenizerProperties.F_CASE</code> means that the property
   * is case-insensitive, but there are no other characteristics explicitely set
   * for this property.
   *<br>
   *
   * @param flags     a bitmask of flags to be set or clear
   * @param flagMask  a bitmask containing the flags that are valid in <code>flags</code>
   * @see   #getFlags
   */  
  public void setFlags(int flags, int flagMask) {
    _flags    = (flags & flagMask);
    _flagMask = flagMask;
  }
    
  /**
   * Retrieving the flags of this property. 
   *
   * @return flags of the property
   * @see #setFlags(int)
   * @see #setFlags(int, int)
   */  
  public int getFlags() {
    return _flags;
  }

  /**
   * Retrieving the current flag mask containing the bits that are valid in the
   * return value of {@link #getFlags}. The return value -1 means that all bits
   * in <code>getFlags</code> are explicitely set for this property.
   *
   * @return a bitmask containing the flags that are explicitely set for this property
   * @see #getFlags()
   * @see #setFlags(int, int)
   */  
  public int getFlagMask() {
    return _flagMask;
  }
  
  /**
   * Checks wether a given flag is set or cleared for this property. The default
   * value is returned if the current flag mask (see {@link #getFlagMask}) does
   * not contain the given flag. This is the case if {@link #containsFlag} would
   * return <code>false</code> for the given flag.
   *<br>
   * If <code>containsFlag</code> returns <code>true</code> for the given flag,
   * <code>isFlagSet</code> returns <code>true</code> only if the all bits in the 
   * given flag are in the current flags.
   *
   * @param   flag      the flag to check
   * @param   defVal    the default value if the flag is not contained in the 
   *                    current flag mask
   * @return  <code>true</code> if the given flag is explicitely set or the default
   *          value is <code>true</code>, <code>false</code> if the given flag is
   *          explicitely cleared or the default value is <code>false</code>.
   */
  public boolean isFlagSet(int flag, boolean defVal) {
    if (containsFlag(flag)) {
      return (getFlags() & flag) == flag;
    } else {
      return defVal;
    }
  }

  /**
   * Checks if a given flag is in the current flag mask. That means that the
   * property contains its own explicit value for the flag.
   *<br>
   * The method can be used in combination with {@link #isFlagSet} to determine
   * wether the return value of <code>isFlagSet</code> is actually the default 
   * value given to that method, or the flag value of the property itself.
   *<br>
   * The method returns <code>true</code> only if the all bits in the given flag
   * are in the current flag mask.
   *
   * @param   flag      the flag to check
   * @return  <code>true</code> if the given flag is explicitely set or the default
   *          value is <code>true</code>, <code>false</code> if the given flag is
   *          explicitely cleared or the default value is <code>false</code>.
   */
  public boolean containsFlag(int flag) {
    return (getFlagMask() & flag) == flag;
  }

  /**
   * Images of lexical elements are quite different. Starting sequences of line 
   * comments, keywords  and special sequences are strings representing only 
   * themselfes. Whitespaces and separators are represented as string consisting 
   * of the single whitespace and separator characters and / or character ranges.
   *<br>
   * A block comment is represented an array of two strings. The first is the 
   * starting sequence, the second the finishing sequence. The same is true for
   * string elements. However, string elements usually have an escape sequence.
   *
   * @param   images  the characterising images of a lexical element
   */
  public void setImages(String[] images) throws IllegalArgumentException {
    _images = images;
  }
    
  /**
   * Retrieving the one or more images a lexical element description has.
   *
   * @return the array with images like string start and end sequences etc.
   * @see #setImages
   */
  public String[] getImages() {
    return _images;
  }
    
  /**
   * Some token may have associated informations for the user of the <code>Token</code>.
   * A popular thing would be the association of an integer constant to a special
   * sequence or keyword to be used in fast <code>switch</code> statetents.
   *
   * @param companion   the associated information for the lexical element
   */  
  public void setCompanion(Object companion) {
    _companion = companion;
  }
    
  /**
   * Obtaining the associated information of the token. Can be <code>null</code>. See
   * {@link #setCompanion} for details.
   *
   * @return the associated information of this token
   */  
  public Object getCompanion() {
    return _companion;
  }

  
  //---------------------------------------------------------------------------
  // construction
  //
  
  /**
   * Default constructor. Initializes a property with type {@link Token#UNKNOWN}
   * and no images or flags.
   */  
  public TokenizerProperty() {
    this(Token.UNKNOWN);
  }
  
  /**
   * Constructs a <code>TokenizerProperty</code> where only the type is known so
   * far. For the type, one of the constants defined in {@link Token} must be 
   * used.
   *
   * @param type the property type
   */  
  public TokenizerProperty(int type) {
    this(type, null);
  }
  
  /**
   * Constructs a <code>TokenizerProperty</code> with type and image(s). For the 
   * type, one of the constants defined in {@link Token} must be used.
   *
   * @param type    the property type
   * @param images  the characterising images of a lexical element
   */  
  public TokenizerProperty(int type, String[] images) {
    this(type, images, null);
  }
  
  /**
   * Constructs a <code>TokenProperty</code> object with a set of type, image(s)
   * and companion.
   *
   * @param type        the property type
   * @param images      the characterising images of a lexical element
   * @param companion   the associated information for the lexical element
   */  
  public TokenizerProperty(int type, String[] images, Object companion) {
    this(type, images, companion, 0);
  }
  
  /**
   * Constructs a <code>TokenProperty</code> object with a set of type, image(s), 
   * companion object and flags.
   *
   * @param type        the property type
   * @param images      the characterising images of a lexical element
   * @param companion   the associated information for the lexical element
   * @param flags       the specific parse flags for this lexical element
   */  
  public TokenizerProperty(int type, String[] images, Object companion, int flags) {
    this(type, images, companion, flags, flags);
  }
  
  /**
   * Constructs a <code>TokenProperty</code> object with a set of type, image(s), 
   * companion object and flags with an associated flag mask ({@link #setFlags(int, int)} 
   * for details). 
   *
   * @param type        the property type
   * @param images      the characterising images of a lexical element
   * @param companion   the associated information for the lexical element
   * @param flags       the specific parse flags for this lexical element
   * @param flagMask    which bits of the parse flags are actually valid
   */  
  public TokenizerProperty(int type, String[] images, Object companion, int flags, int flagMask) {
    setType(type);
    setImages(images);
    setCompanion(companion);
    setFlags(flags, flagMask);
  }
  
  
  //---------------------------------------------------------------------------
  // overloaded methods
  //
  
  /**
   * Redefinition of the well-known {@link java.lang.Object#equals} method.
   *
   * @param   that  compare this instance with that object
   * @return  <code>true</code> if the two object describe the same property,
   *          <code>false</code> otherwise
   */
  public boolean equals(Object that) {
    // primitive tests
    if (that == null) {
      return false;
    } else if (that == this) {
      return true;
    } else if ( ! (that.getClass() == getClass())) {
      return false;
    }
    
    // compare contents
    TokenizerProperty thatProp = (TokenizerProperty)that;

    if (   getType()      == thatProp.getType()
        && getCompanion() == thatProp.getCompanion()
        && getFlags()     == thatProp.getFlags()) {
      // compare images
      String[] thisImg = getImages();
      String[] thatImg = thatProp.getImages();
      
      if (thisImg != thatImg) {
        if (thisImg == null || thatImg == null || thisImg.length != thatImg.length) {
          return false;
        }
        for (int index = 0; index < thisImg.length; ++index) {
          if ( ! thisImg[index].equals(thatImg[index])) {
            return false;
          }
        }
      }
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Redefinition of the well-known {@link java.lang.Object#toString} method.
   *
   * @return a string representation of this <code>TokenizerProperty</code>
   */
  public String toString() {
    StringBuffer  buffer = new StringBuffer();
    
    buffer.append(getClass().getName());
    buffer.append(':');

    switch (getType()) {
      case Token.NORMAL:
        buffer.append(" NORMAL, ");
        break;
      case Token.BLOCK_COMMENT:
        buffer.append(" BLOCK_COMMENT, ");
        break;
      case Token.LINE_COMMENT:
        buffer.append(" LINE_COMMENT, ");
        break;
      case Token.STRING:
        buffer.append(" STRING, ");
        break;
      case Token.PATTERN:
        buffer.append(" PATTERN, ");
        break;
      case Token.KEYWORD:
        buffer.append(" KEYWORD, ");
        break;
      case Token.WHITESPACE:
        buffer.append(" WHITESPACE, ");
        break;
      case Token.SEPARATOR:
        buffer.append(" SEPARATOR, ");
        break;
      case Token.SPECIAL_SEQUENCE:
        buffer.append(" SPECIAL_SEQUENCE, ");
        break;
      case Token.EOF:
        buffer.append(" EOF, ");
        break;
      case TokenizerProperty.PARSE_FLAG_MASK:
        buffer.append(" PARSE FLAG MASK, ");
        break;
      default:
        buffer.append(" UNKNOWN, ");
    }
    
    // add the flags
    buffer.append("flags/mask 0x");
    buffer.append(Integer.toHexString(_flags));
    buffer.append("/0x");
    buffer.append(Integer.toHexString(_flagMask));
    
    // add images
    if (_images != null) {
      buffer.append(':');
      for (int index = 0; index < _images.length; ++index) {
        if (_images[index] != null) {
          buffer.append(' ');
          buffer.append(_images[index]);
        } else {
          break;
        }
      }
    }
    
    // ready
    return buffer.toString();
  }
  
  //---------------------------------------------------------------------------
  // implementation
  //
  
  //---------------------------------------------------------------------------
  // members
  //
  protected int       _type;
  protected int       _flags;
  protected int       _flagMask;
  protected String[]  _images;
  protected Object    _companion;
}
