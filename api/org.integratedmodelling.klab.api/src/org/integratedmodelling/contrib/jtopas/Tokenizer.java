/*
 * Tokenizer.java: lexical parser interface.
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

import java.io.Closeable;

import org.integratedmodelling.contrib.jtopas.spi.KeywordHandler;
import org.integratedmodelling.contrib.jtopas.spi.PatternHandler;
import org.integratedmodelling.contrib.jtopas.spi.SeparatorHandler;
import org.integratedmodelling.contrib.jtopas.spi.SequenceHandler;
import org.integratedmodelling.contrib.jtopas.spi.WhitespaceHandler;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

//-----------------------------------------------------------------------------
// Imports
//

//-----------------------------------------------------------------------------
// Interface Tokenizer
//

/**
 * <p> The interface <code>Tokenizer</code> contains setup methods, parse operations and
 * other getter and setter methods for a tokenizer. A tokenizer splits a stream of input
 * data into various units like whitespaces, comments, keywords etc. These units are the
 * tokens that are reflected in the {@link Token} class of the
 * <code>de.susebox.jtopas</code> package. </p><p> A <code>Tokenizer</code> is configured
 * using a {@link TokenizerProperties} object that contains declarations for whitespaces,
 * separators, comments, keywords, special sequences and patterns. It is designed to
 * enable a common approach for parsing texts like program code, annotated documents like
 * HTML and so on. </p><p> To detect links in an HTML document, a tokenizer would be
 * invoked like that (see {@link StandardTokenizerProperties} and
 * {@link StandardTokenizer} for the classes mentioned here): <blockquote><pre>
 *
 * Vector links = new Vector(); FileReader reader = new FileReader("index.html");
 * TokenizerProperties props = new StandardTokenizerProperties(); Tokenizer tokenizer =
 * new StandardTokenizer(); Token token;
 *
 * props.setParseFlags(Tokenizer.F_NO_CASE); props.setSeparators("=");
 * props.addString("\"", "\"", "\\"); props.addBlockComment("&gt;", "&lt;");
 * props.addKeyword("HREF");
 *
 * tokenizer.setTokenizerProperties(props); tokenizer.setSource(new ReaderSource(reader));
 *
 * try { while (tokenizer.hasMoreToken()) { token = tokenizer.nextToken(); if
 * (token.getType() == Token.KEYWORD) { tokenizer.nextToken(); // should be the '='
 * character links.addElement(tokenizer.next()); } } } finally { tokenizer.close();
 * reader.close(); }
 *
 * </pre></blockquote> This somewhat rough way to find links should work fine on
 * syntactically correct HTML code. It finds common links as well as mail, ftp links etc.
 * Note the block comment. It starts with the "&gt;" character, that is the closing
 * character for HTML tags and ends with the "&lt;" being the starting character of HTML
 * tags. The effect is that all the real text is treated as a comment. </p><p> To extract
 * the contents of a HTML file, one would write: <blockquote><pre>
 *
 * StringBuffer contents = new StringBuffer(4096); FileReader reader = new
 * FileReader("index.html"); TokenizerProperties props = new
 * StandardTokenizerProperties(); Tokenizer tokenizer = new StandardTokenizer(); Token
 * token;
 *
 * props.setParseFlags(Tokenizer.F_NO_CASE); props.addBlockComment("&gt;", "&lt;");
 * props.addBlockComment("&gt;HEAD&lt;", "&gt;/HEAD&lt;");
 * props.addBlockComment("&gt;!--;", "--&lt;");
 * 
 * tokenizer.setTokenizerProperties(props); tokenizer.setSource(new ReaderSource(reader));
 *
 * try { while (tokenizer.hasMoreToken()) { token = tokenizer.nextToken(); if
 * (token.getType() != Token.BLOCK_COMMENT) { contents.append(token.getToken()); } } }
 * finally { tokenizer.close(); reader.close(); }
 *
 * </pre></blockquote> Here the block comment is the exact opposite of the first example.
 * Now all the HTML tags are skipped. Moreover, we declared the HTML-Header as a block
 * comment as well - the informations from the header are thus skipped alltogether.
 * </p><p> Parsing (tokenizing) is done on a well defined priority scheme. See
 * {@link #nextToken} for details. </p><p> NOTE: if a character sequence is registered for
 * two categories of tokenizer properties (e.g. as a line comments starting sequence as
 * well as a special sequence), the category with the highest priority wins (e.g. if the
 * metioned sequence is found, it is interpreted as a line comment). </p><p> The tokenizer
 * interface is clearly designed for "readable" data, say ASCII- or UNICODE data. Parsing
 * binary data has other characteristics that do not necessarily fit in a scheme of
 * comments, keywords, strings, identifiers and operators. </p><p> Note that the interface
 * has no methods that handle stream data sources. This is left to the implementations
 * that may have quite different data sources, e. g. {@link java.io.InputStreamReader},
 * database queries, string arrays etc. The interface {@link TokenizerSource} serves as an
 * abstraction of such widely varying data sources. </p>
 *
 * @see Token
 * @see TokenizerProperties
 * @author Heiko Blau
 */
public interface Tokenizer extends Closeable {

    // ---------------------------------------------------------------------------
    // data source
    //

    /**
     * Setting the source of data. This method is usually called during setup of the
     * <code>Tokenizer</code> but may also be invoked while the tokenizing is in progress.
     * It will reset the tokenizers input buffer, line and column counters etc. <br> It is
     * allowed to pass <code>null</code>. Calls to {@link #hasMoreToken} will return
     * <code>false</code>, while calling {@link #nextToken} will return an EOF token.
     *
     * @param source a {@link TokenizerSource} to read data from
     * @see #getSource
     */
    public void setSource(TokenizerSource source);

    /**
     * Retrieving the {@link TokenizerSource} of this <code>Tokenizer</code>. The method
     * may return <code>null</code> if there is no <code>TokenizerSource</code> associated
     * with this <code>Tokenizer</code>.
     *
     * @return the {@link TokenizerSource} associated with this <code>Tokenizer</code>
     * @see #setSource
     */
    public TokenizerSource getSource();

    // ---------------------------------------------------------------------------
    // configuration
    //

    /**
     * Setting the tokenizer characteristics. This operation is usually done before the
     * parse process. A common place is a constructor of a <code>Tokenizer</code>
     * implementation. If the tokenizer characteristics change during the parse process
     * they take effect with the next call of {@link #nextToken} or {@link #nextImage}.
     * Usually, a <code>Tokenizer</code> implementation will also implement the
     * {@link TokenizerPropertyListener} interface to be notified about property changes.
     * <br> Generally, the <code>Tokenizer</code> implementation should also implement the
     * {@link de.susebox.jtopas.spi.DataProvider} interface or provide an inner class that
     * implements the <code>DataProvider</code> interface, while the
     * {@link TokenizerProperties} implementation should in turn implement the interfaces
     * <ul><li> {@link de.susebox.jtopas.spi.WhitespaceHandler}, </li><li>
     * {@link de.susebox.jtopas.spi.SeparatorHandler}, </li><li>
     * {@link de.susebox.jtopas.spi.SequenceHandler}, </li><li>
     * {@link de.susebox.jtopas.spi.KeywordHandler} and </li><li>
     * {@link de.susebox.jtopas.spi.PatternHandler} </li></ul> These handler interfaces
     * are collected in the {@link de.susebox.jtopas.spi.DataMapper} interface. <br>
     * Although the implementation of the mentioned interfaces is recommended, it is not a
     * mandatory way. Except for {@link de.susebox.jtopas.spi.PatternHandler} that must be
     * implemented by the {@link TokenizerProperties} implementation, since it is not
     * possible for a <code>Tokenizer</code> to interpret a regular expression pattern
     * only with the information provided through the <code>TokenizerProperties</code>
     * interface. <br> If a <code>Tokenizer</code> implementation chooses to use a
     * exclusively tailored {@link TokenizerProperties} implementation, it should throw an
     * {@link java.lang.IllegalArgumentException} if it is not provided with an instance
     * of that {@link TokenizerProperties} implementation. <br> If <code>null</code> is
     * passed to the method it throws {@link java.lang.NullPointerException}.
     *
     * @param props the {@link TokenizerProperties} for this tokenizer
     * @throws NullPointerException if the <code>null</code> is passed to the call
     * @throws IllegalArgumentException if the {@link TokenizerProperties} implementation
     * of the parameter cannot be used with the implementation of this
     * <code>Tokenizer</code>
     * @see #getTokenizerProperties
     */
    public void setTokenizerProperties(TokenizerProperties props)
            throws NullPointerException, IllegalArgumentException;

    /**
     * Retrieving the current tokenizer characteristics. The method may return
     * <code>null</code> if {@link #setTokenizerProperties} has not been called so far.
     *
     * @return the {@link TokenizerProperties} of this <code>Tokenizer</code>
     * @see #setTokenizerProperties
     */
    public TokenizerProperties getTokenizerProperties();

    /**
     * Expect a particular token type and optionally match values. Throw appropriate
     * exceptions if the result is not what is expected.
     * 
     * @param tokenizer
     * @param type
     * @param values
     * @return the validated token image (string value).
     * 
     * @throws KlabValidationException
     */
    public String expect(int type, String... values) throws KlabValidationException;
    
    /**
     * Like {@link #expect(int, String...)} but uses the passed token instead of getting
     * the next.
     * 
     * @param token
     * @param type
     * @param values
     * @return
     * @throws KlabValidationException
     */
    public String expect(Token token, int type, String... values) throws KlabValidationException;


    /**
     * Setting the control flags of the <code>TokenizerProperties</code>. Use a
     * combination of the <code>F_...</code> flags declared in {@link TokenizerProperties}
     * for the parameter. The <code>mask</code> parameter contains a bit mask of the
     * <code>F_...</code> flags to change. <br> The parse flags for a tokenizer can be set
     * through the associated {@link TokenizerProperties} instance. These global settings
     * take effect in all <code>Tokenizer</code> instances that use the same
     * <code>TokenizerProperties</code> object. Flags related to the parsing process can
     * also be set separately for each tokenizer during runtime. These are the dynamic
     * flags: <ul><li> {@link Flags#F_RETURN_WHITESPACES} and its sub-flags </li><li>
     * {@link Flags#F_TOKEN_POS_ONLY} </li></ul> Other flags can also be set for each
     * tokenizer separately, but should be set before the tokenizing starts to make sense.
     * <ul><li> {@link Flags#F_KEEP_DATA} </li><li> {@link Flags#F_COUNT_LINES} </li></ul>
     * The other flags should only be used on the <code>TokenizerProperties</code>
     * instance or on single {@link TokenizerProperty} objects and influence all
     * <code>Tokenizer</code> instances sharing the same <code>TokenizerProperties</code>
     * object. For instance, using the flag {@link Flags#F_NO_CASE} is an invalid
     * operation on a <code>Tokenizer</code>. It affects the interpretation of keywords
     * and sequences by the associated <code>TokenizerProperties</code> instance and,
     * moreover, possibly the storage of these properties. <br> This method throws a
     * {@link TokenizerException} if a flag is passed that cannot be handled by the
     * <code>Tokenizer</code> object itself. <br> This method takes precedence over the
     * {@link TokenizerProperties#setParseFlags} method of the associated
     * <code>TokenizerProperties</code> object. Even if the global settings of one of the
     * dynamic flags (see above) change after a call to this method, the flags set
     * separately for this tokenizer, stay active.
     *
     * @param flags the parser control flags
     * @param mask the mask for the flags to set or unset
     * @throws TokenizerException if one or more of the flags given cannot be honored
     * @see #getParseFlags
     */
    public void changeParseFlags(int flags, int mask) throws TokenizerException;

    /**
     * Retrieving the parser control flags. A bitmask containing the <code>F_...</code>
     * constants is returned. This method returns both the flags that are set separately
     * for this <code>Tokenizer</code> and the flags set for the associated
     * {@link TokenizerProperties} object.
     *
     * @return the current parser control flags
     * @see #changeParseFlags
     */
    public int getParseFlags();

    /**
     * Setting a new {@link de.susebox.jtopas.spi.KeywordHandler} or removing any
     * previously installed one. If <code>null</code> is passed (installed handler
     * removed), no keyword support is available. <br> Usually, the
     * {@link TokenizerProperties} used by a <code>Tokenizer</code> implement the
     * {@link de.susebox.jtopas.spi.KeywordHandler} interface. If so, the
     * <code>Tokenizer</code> object sets the <code>TokenizerProperties</code> instance as
     * its <code>KeywordHandler</code>. A different or a handler specific to a certain
     * <code>Tokenizer</code> instance, can be set using this method.
     *
     * @param handler the (new) {@link de.susebox.jtopas.spi.KeywordHandler} to use or
     * <code>null</code> to remove it
     * @see #getKeywordHandler
     * @see TokenizerProperties#addKeyword
     */
    public void setKeywordHandler(KeywordHandler handler);

    /**
     * Retrieving the current {@link de.susebox.jtopas.spi.KeywordHandler}. The method may
     * return <code>null</code> if there isn't any handler installed.
     *
     * @return the currently active {@link de.susebox.jtopas.spi.KeywordHandler} or
     * <code>null</code>, if keyword support is switched off
     * @see #setKeywordHandler
     */
    public KeywordHandler getKeywordHandler();

    /**
     * Setting a new {@link de.susebox.jtopas.spi.WhitespaceHandler} or removing any
     * previously installed one. If <code>null</code> is passed, the tokenizer will not
     * recognize whitespaces. <br> Usually, the {@link TokenizerProperties} used by a
     * <code>Tokenizer</code> implement the
     * {@link de.susebox.jtopas.spi.WhitespaceHandler} interface. If so, the
     * <code>Tokenizer</code> object sets the <code>TokenizerProperties</code> instance as
     * its <code>WhitespaceHandler</code>. A different handler or a handler specific to a
     * certain <code>Tokenizer</code> instance, can be set using this method.
     *
     * @param handler the (new) whitespace handler to use or <code>null</code> to switch
     * off whitespace handling
     * @see #getWhitespaceHandler
     * @see TokenizerProperties#setWhitespaces
     */
    public void setWhitespaceHandler(WhitespaceHandler handler);

    /**
     * Retrieving the current {@link de.susebox.jtopas.spi.WhitespaceHandler}. The method
     * may return <code>null</code> if there whitespaces are not recognized.
     *
     * @return the currently active whitespace handler or null, if the base implementation
     * is working
     * @see #setWhitespaceHandler
     */
    public WhitespaceHandler getWhitespaceHandler();

    /**
     * Setting a new {@link de.susebox.jtopas.spi.SeparatorHandler} or removing any
     * previously installed <code>SeparatorHandler</code>. If <code>null</code> is passed,
     * the tokenizer doesn't recognize separators. <br> Usually, the
     * {@link TokenizerProperties} used by a <code>Tokenizer</code> implement the
     * {@link de.susebox.jtopas.spi.SeparatorHandler} interface. If so, the
     * <code>Tokenizer</code> object sets the <code>TokenizerProperties</code> instance as
     * its <code>SeparatorHandler</code>. A different handler or a handler specific to a
     * certain <code>Tokenizer</code> instance, can be set using this method.
     *
     * @param handler the (new) separator handler to use or <code>null</code> to remove it
     * @see #getSeparatorHandler
     * @see TokenizerProperties#setSeparators
     */
    public void setSeparatorHandler(SeparatorHandler handler);

    /**
     * Retrieving the current {@link de.susebox.jtopas.spi.SeparatorHandler}. The method
     * may return <code>null</code> if there isn't any handler installed.
     *
     * @return the currently active {@link de.susebox.jtopas.spi.SeparatorHandler} or
     * <code>null</code>, if separators aren't recognized by the tokenizer
     * @see #setSeparatorHandler
     */
    public SeparatorHandler getSeparatorHandler();

    /**
     * Setting a new {@link de.susebox.jtopas.spi.SequenceHandler} or removing any
     * previously installed one. If <code>null</code> is passed, the tokenizer will not
     * recognize line and block comments, strings and special sequences. <br> Usually, the
     * {@link TokenizerProperties} used by a <code>Tokenizer</code> implement the
     * {@link de.susebox.jtopas.spi.SequenceHandler} interface. If so, the
     * <code>Tokenizer</code> object sets the <code>TokenizerProperties</code> instance as
     * its <code>SeparatorHandler</code>. A different handler or a handler specific to a
     * certain <code>Tokenizer</code> instance, can be set using this method.
     *
     * @param handler the (new) {@link de.susebox.jtopas.spi.SequenceHandler} to use or
     * <code>null</code> to remove it
     * @see #getSequenceHandler
     * @see TokenizerProperties#addSpecialSequence
     * @see TokenizerProperties#addLineComment
     * @see TokenizerProperties#addBlockComment
     * @see TokenizerProperties#addString
     */
    public void setSequenceHandler(SequenceHandler handler);

    /**
     * Retrieving the current {@link de.susebox.jtopas.spi.SequenceHandler}. The method
     * may return <code>null</code> if there isn't any handler installed. <br> A
     * <code>SequenceHandler</code> deals with line and block comments, strings and
     * special sequences.
     *
     * @return the currently active {@link de.susebox.jtopas.spi.SequenceHandler} or
     * <code>null</code>, if no
     * @see #setSequenceHandler
     */
    public SequenceHandler getSequenceHandler();

    /**
     * Setting a new {@link de.susebox.jtopas.spi.PatternHandler} or removing any
     * previously installed one. If <code>null</code> is passed, pattern are not supported
     * by the tokenizer (any longer). <br> Usually, the {@link TokenizerProperties} used
     * by a <code>Tokenizer</code> implement the
     * {@link de.susebox.jtopas.spi.PatternHandler} interface. If so, the
     * <code>Tokenizer</code> object sets the <code>TokenizerProperties</code> instance as
     * its <code>PatternHandler</code>. A different handler or a handler specific to a
     * certain <code>Tokenizer</code> instance, can be set using this method.
     *
     * @param handler the (new) {@link de.susebox.jtopas.spi.PatternHandler} to use or
     * <code>null</code> to remove it
     * @see #getPatternHandler
     * @see TokenizerProperties#addPattern
     */
    public void setPatternHandler(PatternHandler handler);

    /**
     * Retrieving the current {@link de.susebox.jtopas.spi.PatternHandler}. The method may
     * return <code>null</code> if there isn't any handler installed.
     *
     * @return the currently active {@link de.susebox.jtopas.spi.PatternHandler} or
     * <code>null</code>, if patterns are not recognized by the tokenizer
     * @see #setPatternHandler
     */
    public PatternHandler getPatternHandler();

    // ---------------------------------------------------------------------------
    // tokenizer operations
    //

    /**
     * Check if there are more tokens available. This method will return <code>true</code>
     * until and enf-of-file condition is encountered during a call to {@link #nextToken}
     * or {@link #nextImage}. <br> That means, that the EOF is returned one time,
     * afterwards <code>hasMoreToken</code> will return <code>false</code>. Furthermore,
     * that implies, that the method will return <code>true</code> at least once, even if
     * the input data stream is empty. <br> The method can be conveniently used in a while
     * loop.
     *
     * @return <code>true</code> if a call to {@link #nextToken} or {@link #nextImage}
     * will succed, <code>false</code> otherwise
     */
    public boolean hasMoreTokens();

    /**
     * Retrieving the next {@link Token}. The method works in this order: <ol><li> Check
     * for an end-of-file condition. If there is such a condition then return it.
     * </li><li> Try to collect a sequence of whitespaces. If such a sequence can be found
     * return if the flag <code>F_RETURN_WHITESPACES</code> is set, or skip these
     * whitespaces. </li><li> Check the next characters against all known pattern. A
     * pattern is usually a regular expression that is used by
     * {@link java.util.regex.Pattern}. But implementations of
     * {@link de.susebox.jtopas.spi.PatternHandler} may use other pattern syntaxes. Note
     * that pattern are not recognized within "normal" text (see below for a more precise
     * description). </li><li> Check the next characters against all known line and block
     * comments. If a line or block comment starting sequence matches, return if the flag
     * <code>F_RETURN_WHITESPACES</code> is set, or skip the comment. If comments are
     * returned they include their starting and ending sequences (newline in case of a
     * line comment). </li><li> Check the next characters against all known string
     * starting sequences. If a string begin could be identified return the string until
     * and including the closing sequence. </li><li> Check the next characters against all
     * known special sequences. Especially, find the longest possible match. If a special
     * sequence could be identified then return it. </li><li> Check for ordinary
     * separators. If one could be found return it. </li><li> Check the next characters
     * against all known keywords. If a keyword could be identified then return it.
     * </li><li> Return the text portion until the next whitespace, comment, special
     * sequence or separator. Note that pattern are not recognized within "normal" text. A
     * pattern match has therefore always a whitespace, comment, special sequence,
     * separator or another pattern match in front of it or starts at position 0 of the
     * data. </li></ol> The method will return the EOF token as long as
     * {@link #hasMoreToken} returns <code>false</code>. It will not return
     * <code>null</code> in such conditions.
     *
     * @return found {@link Token} including the EOF token
     * @throws TokenizerException generic exception (list) for all problems that may occur
     * while parsing (IOExceptions for instance)
     * @see #nextImage
     */
    public Token nextToken() throws TokenizerException;

    /**
     * This method is a convenience method. It returns only the next token image without
     * any informations about its type or associated information. This is an especially
     * useful method, if the parse flags for this <code>Tokenizer</code> have the flag
     * {@link Flags#F_TOKEN_POS_ONLY} set, since this method returns a valid string even
     * in that case.
     *
     * @return the token image of the next token
     * @throws TokenizerException generic exception (list) for all problems that may occur
     * while parsing (IOExceptions for instance)
     * @see #nextToken
     * @see #currentImage
     */
    public String nextImage() throws TokenizerException;

    /**
     * Retrieve the {@link Token} that was found by the last call to {@link #nextToken}.
     * or {@link #nextImage}. <br> Since version 0.6.1 of JTopas, this method throws a
     * {@link TokenizerException} rather than returning <code>null</code> if neither
     * {@link #nextToken} nor {@link #nextImage} have been called before or
     * {@link #setReadPositionRelative} or {@link #setReadPositionAbsolute} have been
     * called after the last call to <code>nextToken</code> or <code>nextImage</code>.
     *
     * @return the {@link Token} retrieved by the last call to {@link #nextToken}.
     * @throws TokenizerException if the tokenizer has no current token
     * @see #nextToken
     * @see #currentImage
     */
    public Token currentToken() throws TokenizerException;

    /**
     * Convenience method to retrieve only the token image of the {@link Token} that would
     * be returned by {@link #currentToken}. This is an especially usefull method, if the
     * parse flags for this <code>Tokenizer</code> have the flag
     * {@link Flags#F_TOKEN_POS_ONLY} set, since this method returns a valid string even
     * in that case. <br> Since version 0.6.1 of JTopas, this method throws a
     * {@link TokenizerException} rather than returning <code>null</code> if neither
     * {@link #nextToken} nor {@link #nextImage} have been called before or
     * {@link #setReadPositionRelative} or {@link #setReadPositionAbsolute} habe been
     * called after the last call to <code>nextToken</code> or <code>nextImage</code>.
     *
     * @return the token image of the current token
     * @throws TokenizerException if the tokenizer has no current token
     * @see #currentToken
     * @see #nextImage
     */
    public String currentImage() throws TokenizerException;

    // ---------------------------------------------------------------------------
    // line and column positions
    //

    /**
     * If the flag {@link Flags#F_COUNT_LINES} is set, this method will return the line
     * number starting with 0 in the input stream. The implementation of the
     * <code>Tokenizer</code> interface can decide which end-of-line sequences should be
     * recognized. The most flexible approach is to process the following end-of-line
     * sequences: <br><ul><li> Carriage Return (ASCII 13, '\r'). This EOL is used on Apple
     * Macintosh </li><li> Linefeed (ASCII 10, '\n'). This is the UNIX EOL character.
     * </li><li> Carriage Return + Linefeed ("\r\n"). This is used on MS Windows systems.
     * </li></ul> Another legitime and in many cases satisfying way is to use the system
     * property "line.separator". <br> Displaying information about lines usually means
     * adding 1 to the zero-based line number.
     *
     * @return the current line number starting with 0 or -1 if no line numbers are
     * supplied ({@link Flags#F_COUNT_LINES} is not set).
     * @see #getColumnNumber
     */
    public int getLineNumber();

    /**
     * If the flag {@link Flags#F_COUNT_LINES} is set, this method will return the current
     * column position starting with 0 in the input stream. Displaying information about
     * columns usually means adding 1 to the zero-based column number.
     *
     * @return the current column position or -1 if the flag if no column numbers are
     * supplied {@link Flags#F_COUNT_LINES} is not set). is not set
     * @see #getLineNumber
     */
    public int getColumnNumber();

    // ---------------------------------------------------------------------------
    // text range operations
    //

    /**
     * This method returns the absolute offset in characters to the start of the parsed
     * stream. Together with {@link #currentlyAvailable} it describes the currently
     * available text "window". <br> The position returned by this method and also by
     * {@link #getReadPosition} are absolute rather than relative in a text buffer to give
     * the tokenizer the full control of how and when to refill its text buffer.
     *
     * @return the absolute offset of the current text window in characters from the start
     * of the data source of the Tokenizer
     */
    public int getRangeStart();

    /**
     * Getting the current read offset. This is the absolute position where the next call
     * to <code>nextToken</code> or <code>next</code> will start. It is therefore
     * <b><k>not</k></b> the same as the position returned by
     * {@link Token#getStartPosition} of the current token ({@link #currentToken}). <br>
     * It is the starting position of the token returned by the next call to
     * {@link #nextToken}, if that token is no whitespace or if whitespaces are returned (
     * {@link Flags#F_RETURN_WHITESPACES}). <br> The position returned by this method and
     * also by {@link #getRangeStart} are absolute rather than relative in a text buffer
     * to give the tokenizer the full control of how and when to refill its text buffer.
     *
     * @return the absolute offset in characters from the start of the data source of the
     * Tokenizer where reading will be continued
     */
    public int getReadPosition();

    /**
     * Retrieving the number of the currently available characters. This includes both
     * characters already parsed by the <code>Tokenizer</code> and characters still to be
     * analyzed.<br>
     *
     * @return number of currently available characters
     */
    public int currentlyAvailable();

    /**
     * Retrieve text from the currently available range. The start and length parameters
     * must be inside {@link #getRangeStart} and {@link #getRangeStart} +
     * {@link #currentlyAvailable}. <br> Example: <block><pre> int startPos =
     * tokenizer.getReadPosition(); String source;
     *
     * while (tokenizer.hasMoreToken()) { Token token = tokenizer.nextToken();
     * 
     * switch (token.getType()) { case Token.LINE_COMMENT: case Token.BLOCK_COMMENT:
     * source = tokenizer.getText(startPos, token.getStartPos() - startPos); startPos =
     * token.getStartPos(); } } </pre></block>
     *
     * @param start position where the text begins
     * @param length length of the text
     * @return the text beginning at the given position ith the given length
     * @throws IndexOutOfBoundsException if the starting position or the length is out of
     * the current text window
     */
    public String getText(int start, int length) throws IndexOutOfBoundsException;

    /**
     * Get a single character from the current text range.
     *
     * @param pos position of the required character
     * @return the character at the specified position
     * @throws IndexOutOfBoundsException if the parameter <code>pos</code> is not in the
     * available text range (text window)
     */
    public char getChar(int pos) throws IndexOutOfBoundsException;

    /**
     * Try to read more data into the text buffer of the tokenizer. This can be useful
     * when a method needs to look ahead of the available data or a skip operation should
     * be performed. <br> The method returns the same value than an immediately following
     * call to {@link #currentlyAvailable} would return.
     *
     * @return the number of character now available
     * @throws TokenizerException generic exception (list) for all problems that may occur
     * while reading (IOExceptions for instance)
     */
    public int readMore() throws TokenizerException;

    /**
     * This method sets the tokenizers current read position to the given absolute read
     * position. It realizes one type of rewind / forward operations. The given position
     * must be inside the intervall {@link #getRangeStart} and {@link #getRangeStart} +
     * {@link #currentlyAvailable} - 1. <br> The current read position is the end position
     * of the current token. That means that the following assertion can be made: <pre>
     * Token token1 = tokenizer.nextToken();
     * tokenizer.setReadPositionAbsolute(tokenizer.getReadPosition() -
     * token1.getLength()); Token token2 = tokenizer.nextToken();
     * assert(token1.equals(token2)); </pre> <br> Since JTopas version 0.6.1, the
     * operation clears the current token. Therefore, {@link #currentImage} and
     * {@link #currentToken} will throw a {@link TokenizerException} if called after a
     * <code>setReadPositionAbsolute</code> without a subsequent call to
     * {@link #nextToken} of {@link #nextImage}.
     *
     * @param position absolute position for the next parse operation
     * @throws IndexOutOfBoundsException if the parameter <code>position</code> is not in
     * the available text range (text window)
     * @see #setReadPositionRelative
     */
    public void setReadPositionAbsolute(int position) throws IndexOutOfBoundsException;

    /**
     * This method sets the tokenizers new read position the given number of characters
     * forward (positive value) or backward (negative value) starting from the current
     * read position. It realizes one type of rewind / forward operations. The given
     * offset must be greater or equal than {@link #getRangeStart} -
     * {@link #getReadPosition} and lower than {@link #currentlyAvailable} -
     * {@link #getReadPosition}. <br> Since JTopas version 0.6.1, the operation clears the
     * current token. Therefore, {@link #currentImage} and {@link #currentToken} will
     * throw a {@link TokenizerException} if called after a
     * <code>setReadPositionAbsolute</code> without a subsequent call to
     * {@link #nextToken} of {@link #nextImage}.
     *
     * @param offset number of characters to move forward (positive offset) or backward
     * (negative offset)
     * @throws IndexOutOfBoundsException if the parameter <code>offset</code> would move
     * the read position out of the available text range (text window)
     * @see #setReadPositionAbsolute
     */
    public void setReadPositionRelative(int offset) throws IndexOutOfBoundsException;

    // ---------------------------------------------------------------------------
    // Cleanup
    //

    /**
     * This method is nessecary to release memory and remove object references if a
     * <code>Tokenizer</code> instances are frequently created for small tasks. Generally,
     * the method shouldn't throw any exceptions. It is also ok to call it more than once.
     * <br> It is an error, to call any other method of the implementing class after
     * <code>close</code> has been called.
     */
    @Override
    public void close();
}
