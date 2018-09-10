/*
 * Flags.java: commonly used  constants.
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
// Interface Flags
//

/**
 * The interface defines flags that are used by various classes during tokenizing.
 * A flag can be set in three ways:
 *<ul><li>
 *    Globally for a {@link TokenizerProperties} object: The setting affects all
 *    {@link Tokenizer} instances that share this <code>TokenizerProperties</code>
 *    object as well as the {@link TokenizerProperty} objects registered in this
 *    <code>TokenizerProperties</code> that do haven't set the flag locally.
 *</li><li>
 *    Separately for a {@link Tokenizer} (see {@link Tokenizer#changeParseFlags}: 
 *    A single <code>Tokenizer</code> will behave differently to the setting in
 *    the used {@link TokenizerProperties} object, but still follow the setting
 *    for a single {@link TokenizerProperty} object. Only a limited number of
 *    flags can be set for a <code>Tokenizer</code>, especially the flags that
 *    are "dynamic", applicable more for the tokenizing process than describing
 *    an attribute of a {@link TokenizerProperty}, e. g. {@link #F_COUNT_LINES} 
 *    and {@link #F_KEEP_DATA}.
 *</li><li>
 *    Specifically for a single {@link TokenizerProperty}: This setting affects
 *    only the handling of the property and overrules both settings for the
 *    {@link TokenizerProperties} that contains the property, and settings for a
 *    {@link Tokenizer} using the <code>TokenizerProperties</code> object. Only
 *    a limited number of flags can be set for a singel property including the
 *    descriptive flags like {@link #F_NO_CASE}, {@link #F_ALLOW_NESTED_COMMENTS}
 *    and {@link #F_SINGLE_LINE_STRING}.
 *</li></ul>
 *
 * @see     TokenizerProperties
 * @author  Heiko Blau
 */
public interface Flags {
  
  /**
   * When this flag is set globally for a {@link TokenizerProperties} instance
   * (see {@link TokenizerProperties#setParseFlags}, input data is generally treated 
   * case-insensitive. Specific properties may still be treated case-sensitive. 
   * (Set this flag in the flag mask and clear it in the corresponding flags).
   *<br>
   * Implementation note: The flag should be applicable for both {@link TokenizerProperties}
   * and {@link TokenizerProperty} instances. It should not to be used
   * dynamically ({@link Tokenizer#changeParseFlags}).
   */
  public static final short F_NO_CASE = 0x0001;

  /**
   * General compare operations are case-sensitive, that means 'A' equals 'A' 
   * but not 'a'. It is not nessecary to set this flag, since case-sensitive 
   * comparison is the default.
   *<br>
   * The flag was mainly used in conjunction with {@link #F_NO_CASE}. If 
   * <code>F_NO_CASE</code> is set via {@link TokenizerProperties#setParseFlags}, 
   * <code>F_CASE</code> can be used for single properties where case-sensitivity 
   * is nessecary inspite of the global case-insensitivity.
   *<br>
   * If neither <code>F_CASE</code> nor <code>F_NO_CASE</code> is set, <code>F_CASE</code>
   * is assumed. If both flags are set, <code>F_CASE</code> takes preceedence.
   *<br>
   * Implementation note: The flag should be applicable for both {@link TokenizerProperties}
   * and {@link TokenizerProperty} instances. It should not to be used
   * dynamically ({@link Tokenizer#changeParseFlags}).
   *
   * @deprecated  for properties with a case handling different to the global
   *              settings of a {@link TokenizerProperties} instance use
   *              the constructor {@link TokenizerProperty#TokenizerProperty(int, String[], Object, int, int)}
   */
  public static final short F_CASE = 0x0002;

  /**
   * For performance and memory reasons, this flag is used to avoid copy operations
   * for every token. The token image itself is not returned in a {@link Token}
   * instance, only its position and length in the input stream.
   *<br>
   * Implementation note: The flag should be applicable for {@link TokenizerProperties},
   * and {@link TokenizerProperty} instances. It should also be a dynamic flag 
   * that can be switched on and off during runtime using {@link Tokenizer#changeParseFlags}.
   */
  public static final short F_TOKEN_POS_ONLY = 0x0010;

  /**
   * Set this flag to let a {@link Tokenizer} buffer all data. Usually, a tokenizer
   * will apply a strategie to allocate only a reasonable amount of memory.
   *<br>
   * Implementation note: The flag should be applicable for {@link TokenizerProperties}
   * and {@link Tokenizer} objects, but not for single {@link TokenizerProperty} 
   * instances. It could also be a dynamic flag that can be switched on and off 
   * during runtime of a tokenizer ({@link Tokenizer#changeParseFlags}), although 
   * it is generally set before parsing starts.
   */
  public static final short F_KEEP_DATA = 0x0020;

  /**
   * Tells a {@link Tokenizer} to count lines and columns. The tokenizer may use
   * {@link System#getProperty}<code>("line.separator")</code> to 
   * obtain the end-of-line sequence or accept different line separator sequences
   * for a better portability: single carriage return (Mac OS), single line feed
   * (Unix), combination of carriage return and line feed (Windows OS).
   *<br>
   * Usually, the end-of-line characters '\r' and '\n' are whitespaces. If they
   * are also part of one or more special sequences or pattern, it is 
   * <strong>NOT</strong> guaranteed that the line counting mechanism of a
   * {@link Tokenizer} implementation finds these occurences. This is in order to
   * maintain a good performance, since otherwise there would be a potential huge 
   * amount of unsuccessfull newline scans in these tokens. Consider defining 
   * special sequences for '\r', '\n' and '\r\n' alone and remove them from the
   * whitespace set, if You cannot live with the described limitation.
   *<br>
   * Implementation note: The flag should be applicable for {@link TokenizerProperties}
   * and {@link Tokenizer} objects, but not for single {@link TokenizerProperty} 
   * instances. It could also be a dynamic flag that can be switched on and off 
   * during runtime of a tokenizer, although it is generally set before parsing 
   * starts.
   */
  public static final short F_COUNT_LINES = 0x0040;

  /**
   * Nested block comments are normally not allowed. This flag changes the 
   * default behaviour.
   *<br>
   * Implementation note: The flag should be applicable for both {@link TokenizerProperties}
   * and {@link TokenizerProperty} instances. It should not to be used
   * dynamically (as in versions of JTopas prior to 0.8).
   */
  public static final short F_ALLOW_NESTED_COMMENTS = 0x0080;
  
  /**
   * Treat pattern the same way as whitespaces, separators or special sequences.
   * Pattern of this type are recognized anywhere outside comments and strings.
   * They terminate normal token. In fact, strings and comments could be 
   * described as free pattern.
   *<br>
   * Without this flag, pattern are treated in the same way as normal token. 
   * They are preceeded and followed by whitespaces, separators or special sequences. 
   *<br>
   * Implementation note: The flag should be applicable for both {@link TokenizerProperties}
   * and {@link TokenizerProperty} instances. It should not to be used
   * dynamically.
   */
  public static final short F_FREE_PATTERN = 0x0100;
  
  /**
   * Return simple whitespaces. These whitespaces are the ones set by
   * {@link TokenizerProperties#setWhitespaces}. The flag is part of the 
   * composite mask {@link #F_RETURN_WHITESPACES}.
   *<br>
   * Implementation note: The flag should be applicable for {@link TokenizerProperties}
   * and {@link Tokenizer}, but not for single {@link TokenizerProperty} 
   * instances. It is also a dynamic flag that can be switched on and off 
   * during runtime of a tokenizer (<strong>Note:</strong>: Flags for a single 
   * {@link TokenizerProperty} take precedence over other settings).
   */
  public static final short F_RETURN_SIMPLE_WHITESPACES = 0x0200;
  
  /**
   * Return block comments. The flag is part of the composite mask
   * {@link #F_RETURN_WHITESPACES}.
   *<br>
   * Implementation note: The flag should be applicable for <code>TokenizerProperties</code>,
   * {@link Tokenizer} and for single {@link TokenizerProperty} instances. It is 
   * also a dynamic flag that can be switched on and off during runtime of a 
   * tokenizer (<strong>Note:</strong>: Flags for a single {@link TokenizerProperty}
   * take precedence over other settings).
   */
  public static final short F_RETURN_BLOCK_COMMENTS = 0x0400;
  
  /**
   * Return line comments. The flag is part of the composite mask
   * {@link #F_RETURN_WHITESPACES}.
   *<br>
   * Implementation note: The flag should be applicable for <code>TokenizerProperties</code>,
   * {@link Tokenizer} and for single {@link TokenizerProperty} instances. It is 
   * also a dynamic flag that can be switched on and off during runtime of a 
   * tokenizer (<strong>Note:</strong>: Flags for a single {@link TokenizerProperty}
   * take precedence over other settings).
   */
  public static final short F_RETURN_LINE_COMMENTS = 0x0800;
  
  /**
   * In many cases, parsers are not interested in whitespaces. If You are, use
   * this value to force the tokenizer to return whitespace sequences and comments 
   * as a token. Per default, the flag is not set.
   *<br>
   * You can control the whitespace policy with finer granularity by using the
   * flags {@link #F_RETURN_SIMPLE_WHITESPACES}, {@link #F_RETURN_BLOCK_COMMENTS}
   * and {@link #F_RETURN_LINE_COMMENTS} either by setting it generally for
   * a {@link TokenizerProperties} or a single {@link Tokenizer} object or even
   * more specific for a single {@link TokenizerProperties}.
   */
  public static final short F_RETURN_WHITESPACES  = F_RETURN_SIMPLE_WHITESPACES 
                                                  + F_RETURN_BLOCK_COMMENTS
                                                  + F_RETURN_LINE_COMMENTS;
  
  /**
   * Per default, strings are all characters between and including a pair of
   * string start and end sequences, regardless if there are line separators in
   * between. This flag changes that behaviour for the <code>TokenizerProperties</code> 
   * instance in general or for a single string property.
   *<br>
   * Implementation note: The flag should be applicable for both <code>TokenizerProperties</code>
   * and {@link TokenizerProperty} instances. It should not to be used
   * dynamically.
   */
  public static final short F_SINGLE_LINE_STRING = 0x1000;
  
  /**
   * By setting this flag for a {@link TokenizerProperties} instance, a 
   * {@link Tokenizer} or for a single property, a tokenizer returns not only
   * the token images but also image parts (see {@link Token#getImageParts}).
   *<br>
   * Implementation note: The flag should be applicable for {@link TokenizerProperties},
   * {@link Tokenizer} and for single {@link TokenizerProperty} instances.
   */
  public static final short F_RETURN_IMAGE_PARTS = 0x4000;
}
