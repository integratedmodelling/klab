/*
 * AbstractTokenizer.java: base class for Tokenizer implementations.
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
// Imports
//
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;

import org.integratedmodelling.contrib.jtopas.spi.DataProvider;
import org.integratedmodelling.contrib.jtopas.spi.KeywordHandler;
import org.integratedmodelling.contrib.jtopas.spi.PatternHandler;
import org.integratedmodelling.contrib.jtopas.spi.SeparatorHandler;
import org.integratedmodelling.contrib.jtopas.spi.SequenceHandler;
import org.integratedmodelling.contrib.jtopas.spi.StandardKeywordHandler;
import org.integratedmodelling.contrib.jtopas.spi.StandardSeparatorHandler;
import org.integratedmodelling.contrib.jtopas.spi.StandardSequenceHandler;
import org.integratedmodelling.contrib.jtopas.spi.StandardWhitespaceHandler;
import org.integratedmodelling.contrib.jtopas.spi.WhitespaceHandler;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

//-----------------------------------------------------------------------------
// Class AbstractTokenizer
//

/**
 * <p> Base class for {@link Tokenizer} implementations. <code>AbstractTokenizer</code>
 * separates the data analysis from the actual data provision. Although the class
 * maintains read and write positions the physical representation of the logical character
 * buffer behind these positions concerns only the subclasses. </p>
 *
 * @see Tokenizer
 * @see TokenizerProperties
 * @author Heiko Blau
 */
public abstract class AbstractTokenizer implements Tokenizer, TokenizerPropertyListener {

    // ---------------------------------------------------------------------------
    // Abstract methods
    //

    /**
     * Subclasses have to provide {@link de.susebox.jtopas.spi.DataProvider} instances for
     * various token type handlers. The given start position is the absolute number of
     * characters from the beginning of the data source.
     *
     * @param startPos position in the input data
     * @param length number of characters
     * @return the <code>DataProvider</code> for the given data range
     */
    protected abstract DataProvider getDataProvider(int startPos, int length);

    /**
     * This method is called when the tokenizer runs out of data. Its main purpose is to
     * call the {@link TokenizerSource#read} method. It is also responsible to handle the
     * flag {@link Flags#F_KEEP_DATA} flag).
     *
     * @return number of read bytes or -1 if an end-of-file condition occured
     * @throws TokenizerException wrapped exceptions from the {@link TokenizerSource#read}
     * method
     */
    protected abstract int readMoreData() throws TokenizerException;

    // ---------------------------------------------------------------------------
    // Constructors
    //

    /**
     * Default constructor that sets the tokenizer control flags as it would be
     * approbriate for C/C++ and Java. Found token images are copied. No line nor column
     * informations are provided. Nested comments are not allowed. <br> The tokenizer will
     * use the {@link TokenizerProperties#DEFAULT_WHITESPACES} and
     * {@link TokenizerProperties#DEFAULT_SEPARATORS} for whitespace and separator
     * handling.
     */
    public AbstractTokenizer() {
        _baseTokenizer = this;
        if (_defaultProperties == null) {
            _defaultProperties = new StandardTokenizerProperties();
        }
        setTokenizerProperties(_defaultProperties);
    }

    /**
     * Contructing a <code>AbstractTokenizer</code> with a backing
     * {@link TokenizerProperties} instance.
     *
     * @param properties an {@link TokenizerProperties} object containing the settings for
     * the tokenizing process
     */
    public AbstractTokenizer(TokenizerProperties properties) {
        _baseTokenizer = this;
        setTokenizerProperties(properties);
    }

    // ---------------------------------------------------------------------------
    // data source
    //

    /**
     * Setting the source of data. This method is usually called during setup of the
     * <code>Tokenizer</code> but may also be invoked while the tokenizing is in progress.
     * It will reset the tokenizers input buffer, line and column counters etc. <br>
     * Subclasses should override this method to do their own actions on a data source
     * change. Generally, this base method should be called first in the subclass
     * implementation of <code>setSource</code> (equivalent to super calls in constructors
     * of derived classes).
     *
     * @param source a {@link TokenizerSource} to read data from
     * @see #getSource
     */
    @Override
    public void setSource(TokenizerSource source) {
        _source = source;
        _eofReached = false;
        _currentReadPos = 0;
        _currentWritePos = 0;
        if (isFlagSet(Flags.F_COUNT_LINES)) {
            _lineNumber = 0;
            _columnNumber = 0;
        } else {
            _lineNumber = -1;
            _columnNumber = -1;
        }
        Arrays.fill(_scannedToken, null);
    }

    /**
     * Convenience method to avoid the construction of a {@link TokenizerSource} from the
     * most important data source {@link java.io.Reader}.
     *
     * @param reader the {@link java.io.Reader} to get data from
     */
    public void setSource(Reader reader) {
        setSource(new ReaderSource(reader));
    }

    @Override
    public String expect(int type, String... values) throws KlabValidationException {
        
        if (!hasMoreTokens()) {
            throw new KlabValidationException("parse error: EOF encountered");
        }

        Token t = nextToken();
        return expect(t, type, values);
    }

    /**
     * Expect a particular token type and optionally match values. Throw appropriate
     * exceptions if the result is not what is expected.
     * 
     * @param tokenizer
     * @param type
     * @param values
     * @return
     * @throws KlabValidationException
     */
    @Override
    public String expect(Token t, int type, String... values) throws KlabValidationException {

        if (t.getType() != type) {
            throw new KlabValidationException(t.getStartPosition() + ": syntax error at " + t.getImage());
        }

        boolean found = true;
        if (values != null && values.length > 0) {
            found = false;
            for (String s : values) {
                if (t.getImage().equals(s)) {
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            throw new KlabValidationException(t.getStartPosition() + ": syntax error at " + t.getImage()
                    + ": " + Arrays.toString(values) + " expected");
        }

        return t.getImage();
    }

    /**
     * Retrieving the {@link TokenizerSource} of this <code>Tokenizer</code>. The method
     * may return <code>null</code> if there is no <code>TokenizerSource</code> associated
     * with it.
     *
     * @return the {@link TokenizerSource} associated with this <code>Tokenizer</code>
     * @see #setSource
     */
    @Override
    public TokenizerSource getSource() {
        return _source;
    }

    // ---------------------------------------------------------------------------
    // Methods of the Tokenizer interface
    //

    /**
     * Setting the tokenizer characteristics. See the method description in
     * {@link Tokenizer}.
     *
     * @param props the {@link TokenizerProperties} for this tokenizer
     * @throws NullPointerException if the <code>null</code> is passed to the call
     * @see #getTokenizerProperties
     */
    @Override
    public void setTokenizerProperties(TokenizerProperties props) throws NullPointerException {
        if (props == null) {
            throw new NullPointerException();
        }

        // set properties
        if (_properties != null) {
            _properties.removeTokenizerPropertyListener(this);
        }
        _properties = props;
        _properties.addTokenizerPropertyListener(this);

        // who is going to handle the various token types ?
        if (_properties instanceof WhitespaceHandler) {
            setWhitespaceHandler((WhitespaceHandler) _properties);
        } else {
            setWhitespaceHandler(new StandardWhitespaceHandler(_properties));
        }
        if (_properties instanceof SeparatorHandler) {
            setSeparatorHandler((SeparatorHandler) _properties);
        } else {
            setSeparatorHandler(new StandardSeparatorHandler(_properties));
        }
        if (_properties instanceof SequenceHandler) {
            setSequenceHandler((SequenceHandler) _properties);
        } else {
            setSequenceHandler(new StandardSequenceHandler(_properties));
        }
        if (props instanceof KeywordHandler) {
            setKeywordHandler((KeywordHandler) props);
        } else {
            setKeywordHandler(new StandardKeywordHandler(_properties));
        }
        if (_properties instanceof PatternHandler) {
            setPatternHandler((PatternHandler) _properties);
        } else {
            setPatternHandler(null);
        }

        // flag handling
        int newFlags = _properties.getParseFlags();

        if (newFlags != _flags) {
            propertyChanged(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_MODIFIED, new TokenizerProperty(TokenizerProperty.PARSE_FLAG_MASK, new String[] {
                    Integer.toBinaryString(newFlags) }), new TokenizerProperty(TokenizerProperty.PARSE_FLAG_MASK, new String[] {
                            Integer.toBinaryString(_flags) })));
        }
    }

    /**
     * Retrieving the current tokenizer characteristics. See the method description in
     * {@link Tokenizer}.
     *
     * @return the {@link TokenizerProperties} of this <code>Tokenizer</code>
     * @see #setTokenizerProperties
     */
    @Override
    public TokenizerProperties getTokenizerProperties() {
        return _properties;
    }

    /**
     * Setting the control flags of the <code>Tokenizer</code>. See the method description
     * in {@link Tokenizer}.
     *
     * @param flags the parser control flags
     * @param mask the mask for the flags to set or unset
     * @throws TokenizerException if one or more of the flags given cannot be honored
     * @see #getParseFlags
     */
    @Override
    public void changeParseFlags(int flags, int mask) throws TokenizerException {
        // test the given flags
        if ((mask | VALID_FLAGS_MASK) != VALID_FLAGS_MASK) {
            throw new TokenizerException("One or more flags cannot be set separately for a "
                    + AbstractTokenizer.class.getName() +
                    ". Violating flags in " + Integer.toHexString(flags) + ": " +
                    Integer.toHexString(mask & ~VALID_FLAGS_MASK) + ".");
        }

        // set the new flags for this tokenizer
        _flagMask = mask;
        _flags = (flags & mask) | (getTokenizerProperties().getParseFlags() & ~mask);

        // when counting lines initialize the current line and column position
        if (!isFlagSet(Flags.F_COUNT_LINES)) {
            _lineNumber = 0;
            _columnNumber = 0;
        }
    }

    /**
     * Retrieving the parser control flags. See the method description in
     * {@link Tokenizer}.
     *
     * @return the current parser control flags
     * @see #changeParseFlags
     */
    @Override
    public int getParseFlags() {
        return (getTokenizerProperties().getParseFlags() & ~_flagMask) + (_flags & _flagMask);
    }

    /**
     * Setting a new {@link de.susebox.jtopas.spi.KeywordHandler} or removing any
     * previously installed one. See the method description in {@link Tokenizer}.
     *
     * @param handler the (new) {@link KeywordHandler} to use or <code>null</code> to
     * remove it
     */
    @Override
    public void setKeywordHandler(KeywordHandler handler) {
        synchronized (this) {
            if (handler == _properties) {
                if (_properties != null && _properties.getKeywords().hasNext()) {
                    _keywordHandler = handler;
                } else {
                    _keywordHandler = null;
                }
                _internalFlags &= ~IFLAG_EXTERNAL_KEYWORD_HANDLER;
            } else {
                _keywordHandler = handler;
                _internalFlags |= IFLAG_EXTERNAL_KEYWORD_HANDLER;
            }
        }
    }

    /**
     * Retrieving the current {@link de.susebox.jtopas.spi.KeywordHandler}. See the method
     * description in {@link Tokenizer}.
     *
     * @return the currently active whitespace keyword or <code>null</code>, if keyword
     * support is switched off
     */
    @Override
    public KeywordHandler getKeywordHandler() {
        synchronized (this) {
            if ((_internalFlags & IFLAG_EXTERNAL_KEYWORD_HANDLER) == 0) {
                return (KeywordHandler) getTokenizerProperties();
            } else {
                return _keywordHandler;
            }
        }
    }

    /**
     * Setting a new {@link de.susebox.jtopas.spi.WhitespaceHandler} or removing any
     * previously installed one. See the method description in {@link Tokenizer}.
     *
     * @param handler the (new) whitespace handler to use or <code>null</code> to switch
     * off whitespace handling
     * @see #getWhitespaceHandler
     */
    @Override
    public void setWhitespaceHandler(WhitespaceHandler handler) {
        _whitespaceHandler = handler;
    }

    /**
     * Retrieving the current {@link de.susebox.jtopas.spi.WhitespaceHandler}. See the
     * method description in {@link Tokenizer}.
     *
     * @return the currently active whitespace handler or null, if the base implementation
     * is working
     */
    @Override
    public WhitespaceHandler getWhitespaceHandler() {
        return _whitespaceHandler;
    }

    /**
     * Setting a new {@link de.susebox.jtopas.spi.SeparatorHandler} or removing any
     * previously installed <code>SeparatorHandler</code>. See the method description in
     * {@link Tokenizer}.
     *
     * @param handler the (new) separator handler to use or <code>null</code> to remove it
     * @see #getSeparatorHandler
     */
    @Override
    public void setSeparatorHandler(SeparatorHandler handler) {
        _separatorHandler = handler;
    }

    /**
     * Retrieving the current {@link de.susebox.jtopas.spi.SeparatorHandler}. See the
     * method description in {@link Tokenizer}.
     *
     * @return the currently active {@link SeparatorHandler} or <code>null</code>, if
     * separators aren't recognized by the tokenizer
     * @see #setSequenceHandler
     */
    @Override
    public SeparatorHandler getSeparatorHandler() {
        return _separatorHandler;
    }

    /**
     * Setting a new {@link de.susebox.jtopas.spi.SequenceHandler} or removing any
     * previously installed one. See the method description in {@link Tokenizer}.
     *
     * @param handler the (new) {@link SequenceHandler} to use or null to remove it
     */
    @Override
    public void setSequenceHandler(SequenceHandler handler) {
        synchronized (this) {
            if (handler == _properties) {
                if (_properties != null && (_properties.getSpecialSequences().hasNext()
                        || _properties.getStrings().hasNext()
                        || _properties.getBlockComments().hasNext()
                        || _properties.getLineComments().hasNext())) {
                    _sequenceHandler = handler;
                } else {
                    _sequenceHandler = null;
                }
                _internalFlags &= ~IFLAG_EXTERNAL_SEQUENCE_HANDLER;
            } else {
                _sequenceHandler = handler;
                _internalFlags |= IFLAG_EXTERNAL_SEQUENCE_HANDLER;
            }
        }
    }

    /**
     * Retrieving the current {@link SequenceHandler}. See the method description in
     * {@link Tokenizer}.
     *
     * @return the currently active {@link SequenceHandler} or null, if the base
     * implementation is working
     */
    @Override
    public SequenceHandler getSequenceHandler() {
        synchronized (this) {
            if ((_internalFlags & IFLAG_EXTERNAL_SEQUENCE_HANDLER) == 0) {
                return (SequenceHandler) getTokenizerProperties();
            } else {
                return _sequenceHandler;
            }
        }
    }

    /**
     * Setting a new {@link de.susebox.jtopas.spi.PatternHandler} or removing any
     * previously installed one. See the method description in {@link Tokenizer}.
     *
     * @param handler the (new) {@link de.susebox.jtopas.spi.PatternHandler} to use or
     * <code>null</code> to remove it
     * @see #getPatternHandler
     */
    @Override
    public void setPatternHandler(PatternHandler handler) {
        synchronized (this) {
            if (handler == _properties) {
                if (_properties != null && _properties.getPatterns().hasNext()) {
                    _patternHandler = handler;
                } else {
                    _patternHandler = null;
                }
                _internalFlags &= ~IFLAG_EXTERNAL_PATTERN_HANDLER;
            } else {
                _patternHandler = handler;
                _internalFlags |= IFLAG_EXTERNAL_PATTERN_HANDLER;
            }
        }
    }

    /**
     * Retrieving the current {@link PatternHandler}. See the method description in
     * {@link Tokenizer}.
     *
     * @return the currently active {@link de.susebox.jtopas.spi.PatternHandler} or
     * <code>null</code>, if patterns are not recognized by the tokenizer
     * @see #setPatternHandler
     */
    @Override
    public PatternHandler getPatternHandler() {
        synchronized (this) {
            if ((_internalFlags & IFLAG_EXTERNAL_PATTERN_HANDLER) == 0) {
                return (PatternHandler) getTokenizerProperties();
            } else {
                return _patternHandler;
            }
        }
    }

    /**
     * Query the current row. The method can only be used if the flag
     * {@link Flags#F_COUNT_LINES} has been set. Without this flag being set, the return
     * value is undefined. <br> Note that row counting starts with 0, while editors often
     * use 1 for the first row.
     *
     * @return current row (starting with 0) or -1 if the flag {@link Flags#F_COUNT_LINES}
     * is set
     */
    public int getCurrentLine() {
        return _lineNumber;
    }

    /**
     * Retrieve the current column. The method can only be used if the flag
     * <code>F_COUNT_LINES</code> has been set. Without this flag being set, the return
     * value is undefined. Note that column counting starts with 0, while editors often
     * use 1 for the first column in one row.
     *
     * @return current column number (starting with 0)
     */
    public int getCurrentColumn() {
        return _columnNumber;
    }

    /**
     * Checking if there are more tokens available. See the method description in
     * {@link Tokenizer}.
     *
     * @return <code>true</code> if a ca_ll to {@link #nextToken} or {@link #nextImage}
     * will succed, <code>false</code> otherwise
     */
    @Override
    public boolean hasMoreTokens() {
        return _scannedToken[0] == null || _scannedToken[0].getType() != Token.EOF;
    }

    /**
     * Retrieving the next {@link Token}. See the method description in {@link Tokenizer}.
     *
     * @return found {@link Token} including the EOF token
     * @throws TokenizerException generic exception (list) for all problems that may occur
     * while parsing (IOExceptions for instance)
     */
    @Override
    public Token nextToken() throws TokenizerException {
        boolean returnIt = false;

        // Get the next token
        do {
            // analyze look-ahead token
            if (_scannedToken[1] == null) {
                if (!isEOF(0)) {
                    if (!isWhitespace(0)) {
                        if (!isPattern(0, false)) {
                            if (!isSpecialSequence(0)) {
                                if (!isSeparator(0)) {
                                    _scannedToken[1] = new Token(Token.NORMAL);
                                }
                            }
                        }
                    }
                }
            }
            _scannedToken[0] = _scannedToken[1];
            _scannedToken[1] = _scannedToken[2];
            _scannedToken[2] = null;

            // get new token or complete the previously found look-ahead token
            Token token = _scannedToken[0];
            TokenizerProperty prop = (TokenizerProperty) token.getCompanion();

            token.setCompanion((prop != null) ? prop.getCompanion() : null);
            token.setStartPosition(getReadPosition());
            token.setStartLine(_lineNumber);
            token.setStartColumn(_columnNumber);

            returnIt = true;

            switch (token.getType()) {
            case Token.EOF:
                token.setLength(0);
                break;
            case Token.WHITESPACE:
                token.setLength(completeWhitespace());
                returnIt = isFlagSet(Flags.F_RETURN_SIMPLE_WHITESPACES);
                break;
            case Token.SEPARATOR:     // Separators are always single characters.
                token.setLength(1);
                break;
            case Token.STRING:
                token.setLength(completeString(prop));
                break;
            case Token.LINE_COMMENT:
                token.setLength(completeLineComment(prop));
                returnIt = isFlagSet(prop, Flags.F_RETURN_LINE_COMMENTS);
                break;
            case Token.BLOCK_COMMENT:
                token.setLength(completeBlockComment(prop));
                returnIt = isFlagSet(prop, Flags.F_RETURN_BLOCK_COMMENTS);
                break;
            case Token.SPECIAL_SEQUENCE:
                token.setLength(prop.getImages()[0].length());
                break;
            case Token.PATTERN:
                // already contained in the first look-ahead token, see token shifting
                break;
            default:
                prop = completeBoundedToken(token);
            }

            // compute new line and column positions (if flag is set) and complete
            // the token
            adjustLineAndColumn(token.getType(), token.getLength());
            token.setEndLine(_lineNumber);
            token.setEndColumn(_columnNumber);

            // need to extract the image ?
            if (returnIt) {
                boolean tokenPosOnly = (prop != null) ? isFlagSet(prop, Flags.F_TOKEN_POS_ONLY)
                        : isFlagSet(Flags.F_TOKEN_POS_ONLY);
                boolean returnImageParts = (prop != null) ? isFlagSet(prop, Flags.F_RETURN_IMAGE_PARTS)
                        : isFlagSet(Flags.F_RETURN_IMAGE_PARTS);
                if (!tokenPosOnly || returnImageParts) {
                    token.setImage(getText(_currentReadPos, token.getLength()));
                }
                if (returnImageParts) {
                    switch (token.getType()) {
                    case Token.WHITESPACE:
                        token.setImageParts(splitIntoLines(token.getImage()));
                        break;
                    case Token.STRING:
                        token.setImageParts(splitString(prop, token.getImage()));
                        break;
                    case Token.LINE_COMMENT:
                        token.setImageParts(splitIntoLines(token.getImage()
                                .substring(prop.getImages()[0].length())));
                        break;
                    case Token.BLOCK_COMMENT:
                        token.setImageParts(splitBlockComment(prop, token.getImage()));
                        break;
                    case Token.PATTERN:
                        break;
                    case Token.EOF:
                        token.setImageParts(new String[] {});
                        break;
                    default:
                        token.setImageParts(new String[] { token.getImage() });
                    }
                }
            }

            // this is the one and only point where the current read position is
            // adjusted (except for the data shifting in readMoreData).
            _currentReadPos += token.getLength();

        } while (!returnIt);

        // the current token is the first in the list
        return _scannedToken[0];
    }

    /**
     * This method is a convenience method. It returns only the next token image without
     * any informations about its type or associated information. See the method
     * description in {@link Tokenizer}.
     *
     * @return the token image of the next token
     * @throws TokenizerException generic exception (list) for all problems that may occur
     * while parsing (IOExceptions for instance)
     * @see #currentImage
     */
    @Override
    public String nextImage() throws TokenizerException {
        nextToken();
        return currentImage();
    }

    /**
     * Retrieve the {@link Token} that was found by the last call to {@link #nextToken}.
     * See the method description in {@link Tokenizer}.
     *
     * @return the {@link Token} retrieved by the lahasest call to {@link #nextToken}.
     * @throws TokenizerException if the tokenizer has no current token
     */
    @Override
    public Token currentToken() throws TokenizerException {
        if (_scannedToken[0] == null) {
            throw new TokenizerException("No current token available (nextToken was not called / read position changed)");
        }
        return _scannedToken[0];
    }

    /**
     * Convenience method to retrieve only the token image of the {@link Token} that would
     * be returned by {@link #currentToken}. See the method description in
     * {@link Tokenizer}.
     *
     * @return the token image of the current token
     * @see #currentToken
     */
    @Override
    public String currentImage() throws TokenizerException {
        Token token = currentToken();

        if (token.getType() == Token.EOF) {
            return null;
        } else if (!isFlagSet(Flags.F_TOKEN_POS_ONLY) || token.getImage() != null) {
            return token.getImage();
        } else {
            return getText(token.getStartPosition(), token.getLength());
        }
    }

    /**
     * If the flag {@link Flags#F_COUNT_LINES} is set, this method will return the line
     * number starting with 0 in the input stream. See the method description in
     * {@link Tokenizer}.
     *
     * @return the current line number starting with 0 or -1 if no line numbers are
     * supplied.
     * @see #getColumnNumber
     */
    @Override
    public int getLineNumber() {
        return _lineNumber;
    }

    /**
     * If the flag {@link Flags#F_COUNT_LINES} is set, this method will return the current
     * column positionstarting with 0 in the input stream. See the method description in
     * {@link Tokenizer}.
     *
     * @return the current column position
     * @see #getLineNumber
     */
    @Override
    public int getColumnNumber() {
        return _columnNumber;
    }

    /**
     * Getting the current read offset. See the method description in {@link Tokenizer}.
     *
     * @return the absolute offset in characters from the start of the data source of the
     * Tokenizer where reading will be continued
     * @see #setReadPositionAbsolute
     * @see #setReadPositionRelative
     */
    @Override
    public int getReadPosition() {
        return _currentReadPos;
    }

    /**
     * Retrieving the number of the currently available characters. See the method
     * description in {@link Tokenizer}.
     *
     * @return number of currently available characters
     */
    @Override
    public int currentlyAvailable() {
        return _currentWritePos - getRangeStart();
    }

    /**
     * Try to read more data into the text buffer of the tokenizer. See the method
     * description in {@link Tokenizer}.
     *
     * @return the number of character now available
     * @throws TokenizerException generic exception (list) for all problems that may occur
     * while reading (IOExceptions for instance)
     */
    @Override
    public int readMore() throws TokenizerException {
        readMoreDataFromBase();
        return currentlyAvailable();
    }

    /**
     * Returns the character at the given position. The method does not attempt to read
     * more data.
     *
     * @param pos get character on this position in the data stream
     * @return the character at the given position
     * @throws IndexOutOfBoundsException if the parameter <code>pos</code> is not in the
     * available text range (text window)
     */
    @Override
    public char getChar(int pos) throws IndexOutOfBoundsException {
        return getBaseDataProvider(pos, 1).getCharAt(0);
    }

    /**
     * Retrieve text from the currently available range. See the method description in
     * {@link Tokenizer}.
     *
     * @param start position where the text begins
     * @param len length of the text
     * @return the text beginning at the given position ith the given length
     * @throws IndexOutOfBoundsException if the starting position or the length is out of
     * the current text window
     */
    @Override
    public String getText(int start, int len) throws IndexOutOfBoundsException {
        return getBaseDataProvider(start, len).toString();
    }

    /**
     * This method sets the tokenizers current read position to the given absolute read
     * position. See the method description in {@link Tokenizer}. <br> When using this
     * method with embedded tokenizers, the user is responsible to set the read position
     * in the currently used tokenizer. It will be propagated by the next call to
     * {@link #switchTo}. Until that point, a call to this method has no effect on the
     * other tokenizers sharing the same data source.
     *
     * @param position absolute position for the next parse operation
     * @throws IndexOutOfBoundsException if the parameter <code>position</code> is not in
     * the available text range (text window)
     * @see #setReadPositionRelative
     */
    @Override
    public void setReadPositionAbsolute(int position) throws IndexOutOfBoundsException {
        if (position < getRangeStart()) {
            throw new IndexOutOfBoundsException("Invalid read position " + position
                    + " below the current text window start " + getRangeStart() + ".");
        } else if (position > _currentWritePos) {
            throw new IndexOutOfBoundsException("Invalid read position " + position
                    + " at or above the current text window end " + (currentlyAvailable() + getRangeStart())
                    + ".");
        }
        _currentReadPos = position;
        Arrays.fill(_scannedToken, null);

        // adjust line and column counting
        if (isFlagSet(Flags.F_COUNT_LINES)) {
            SortedMap map = _position2LineMap.headMap(new Integer(position + 1));

            if (map != null && !map.isEmpty()) {
                Integer lastLineStart = (Integer) map.lastKey();

                _lineNumber = ((Integer) map.get(lastLineStart)).intValue();
                _columnNumber = position - lastLineStart.intValue();
            } else {
                _lineNumber = 0;
                _columnNumber = position;
            }
        }
    }

    /**
     * This method sets the tokenizers new read position the given number of characters
     * forward (positive value) or backward (negative value) starting from the current
     * read position. See the method description in {@link Tokenizer}. <br> When using
     * this method with embedded tokenizers, the user is responsible to set the read
     * position in the currently used tokenizer. It will be propagated by the next call to
     * {@link #switchTo}. Until that point, a call to this method has no effect on the
     * other tokenizers sharing the same data source.
     *
     * @param offset number of characters to move forward (positive offset) or backward
     * (negative offset)
     * @throws IndexOutOfBoundsException if the parameter <code>offset</code> would move
     * the read position out of the available text range (text window)
     * @see #setReadPositionAbsolute
     */
    @Override
    public void setReadPositionRelative(int offset) throws IndexOutOfBoundsException {
        setReadPositionAbsolute(getReadPosition() + offset);
    }

    /**
     * Closing this tokenizer frees resources and deregisters from the associated
     * {@link TokenizerProperties} object.
     */
    @Override
    public void close() {
        // deregister from the properties
        if (_properties != null) {
            _properties.removeTokenizerPropertyListener(this);
            _properties = null;
        }

        // freeing memory
        if (_position2LineMap != null) {
            _position2LineMap.clear();
            _position2LineMap = null;
        }

        // adjust members
        _eofReached = true;
        _flags = 0;
        _flagMask = 0;
        _internalFlags = 0;
        _currentReadPos = 0;
        _currentWritePos = 0;
        _lineNumber = -1;
        _columnNumber = -1;
        _nextTokenizer = null;
        _prevTokenizer = null;
        _whitespaceHandler = null;
        _separatorHandler = null;
        _keywordHandler = null;
        _sequenceHandler = null;
        _patternHandler = null;
        _source = null;
        Arrays.fill(_scannedToken, null);
    }

    // ---------------------------------------------------------------------------
    // embedded tokenizer support
    //

    /**
     * Adding an embedded tokenizer. Embedded tokenizer work on the same input buffer as
     * their base tokenizer. A situation where embedded tokenizer could be applied, is a
     * HTML stream with cascading style sheet (CSS) and JavaScript parts. <br> There are
     * no internal means of switching from one tokenizer to another. This should be done
     * by the caller using the method {@link #switchTo}. <br> The
     * {@link Flags#F_KEEP_DATA} and {@link Flags#F_COUNT_LINES} flags of the base
     * tokenizer take effect also in the embedded tokenizers. <br> Since is might be
     * possible that the given <code>tokenizer</code> is a derivation of the
     * <code>AbstractTokenizer</code> class, this method is synchronized on
     * <code>tokenizer</code>.
     *
     * @param tokenizer an embedded tokenizer
     * @throws TokenizerException if something goes wrong (not likely :-)
     */
    public void addTokenizer(AbstractTokenizer tokenizer) throws TokenizerException {
        AbstractTokenizer curr = this;

        while (curr._nextTokenizer != null) {
            curr = curr._nextTokenizer;
        }

        if (tokenizer != null) {
            synchronized (tokenizer) {
                curr._nextTokenizer = tokenizer;
                tokenizer._prevTokenizer = curr;

                // share the input buffer of the base tokenizer
                AbstractTokenizer baseTokenizer = getBaseTokenizer();

                tokenizer._baseTokenizer = baseTokenizer;

                // inherited flags
                tokenizer.changeParseFlags(baseTokenizer.getParseFlags(), Flags.F_COUNT_LINES);
            }
        }
    }

    /**
     * Changing fron one tokenizer to another. If the given tokenizer has not been added
     * with {@link #addTokenizer}, an exception is thrown.<br> The <code>switchTo</code>
     * method does the nessecary synchronisation between <code>this</code> and the given
     * tokenizer. The user is therefore responsible to use <code>switchTo</code> whenever
     * a tokenizer change is nessecary. It must be done this way: <blockquote><pre>
     * Tokenizer base = new MyTokenizer(...) Tokenizer embedded = new MyTokenizer(...)
     *
     * // setting properties (comments, keywords etc.) ...
     *
     * // embedding a tokenizer base.addTokenizer(embedded);
     * 
     * // tokenizing with base ... if (<i>switch_condition</i>) { base.switchTo(embedded);
     * }
     *
     * // tokenizing with embedded ... if (<i>switch_condition</i>) {
     * embedded.switchTo(base); } </pre></blockquote> That way we avoid a more complex
     * synchronisation between tokenizers whenever one of them parses the next data in the
     * input stream. However, the danger of not synchronized tokenizers remains, so take
     * care. <br> Since is might be possible that the given <code>tokenizer</code> is a
     * derivation of the <code>AbstractTokenizer</code> class, this method is synchronized
     * on <code>tokenizer</code>.
     *
     * @param tokenizer the tokenizer that should be used from now on
     */
    public void switchTo(AbstractTokenizer tokenizer) throws TokenizerException {
        if (tokenizer != null) {
            synchronized (tokenizer) {
                if (tokenizer._baseTokenizer != _baseTokenizer) {
                    throw new TokenizerException("Trying to switch to an alien tokenizer (not added with addTokenizer).", null);
                }
                tokenizer._eofReached = this._eofReached;
                tokenizer._currentReadPos = this._currentReadPos;
                tokenizer._currentWritePos = this._currentWritePos;
                tokenizer._columnNumber = this._columnNumber;
                tokenizer._lineNumber = this._lineNumber;
                tokenizer._position2LineMap = this._position2LineMap;
            }
        } else {
            throw new TokenizerException(new NullPointerException());
        }
    }

    // ---------------------------------------------------------------------------
    // Methods that may be overwritten in derived classes
    //

    /**
     * This method checks if the character is a whitespace. Implement Your own code for
     * situations where this default implementation is not fast enough or otherwise not
     * really good.
     *
     * @param testChar check this character
     * @return <code>true</code> if the given character is a whitespace,
     * <code>false</code> otherwise
     */
    protected boolean isWhitespace(char testChar) {
        if (_whitespaceHandler != null) {
            return _whitespaceHandler.isWhitespace(testChar);
        } else {
            return false;
        }
    }

    /**
     * This method detects the number of whitespace characters starting at the given
     * position. It should return the number of characters identified as whitespaces
     * starting from and including the given start position. <br> Then overriding this
     * method, use {@link #getBaseDataProvider} to access characters. <br> Do not attempt
     * to actually read more data or do anything that leads to the change of the data
     * source or to tokenizer switching. This is done by the tokenizer framework.
     *
     * @param startingAtPos start checking for whitespace from this position
     * @param maxChars if there is no non-whitespace character, read up to this number of
     * characters
     * @return number of whitespace characters starting from the given offset
     * @throws TokenizerException failure while reading data from the input stream
     */
    protected int readWhitespaces(int startingAtPos, int maxChars) throws TokenizerException {
        if (_whitespaceHandler != null) {
            DataProvider dataProvider = getBaseDataProvider(startingAtPos, maxChars);
            return _whitespaceHandler.countLeadingWhitespaces(dataProvider);
        } else {
            return 0;
        }
    }

    /**
     * This method checks if the character sequence starting at a given position with a
     * given lenghth is a keyword. If so, it returns the keyword description as
     * {@link TokenizerProperty} object.
     *
     * @param startingAtPos check at this position
     * @param length the candidate has this number of characters
     * @throws TokenizerException routed exception from the active
     * {@link de.susebox.jtopas.spi.KeywordHandler}
     * @return {@link TokenizerProperty} describing the keyword or <code>null</code>
     */
    protected TokenizerProperty isKeyword(int startingAtPos, int length) throws TokenizerException {
        if (_keywordHandler != null) {
            DataProvider dataProvider = getBaseDataProvider(startingAtPos, length);
            return _keywordHandler.isKeyword(dataProvider);
        } else {
            return null;
        }
    }

    // ---------------------------------------------------------------------------
    // TokenizerPropertyListener methods
    //

    /**
     * Splits a given String into lines. The method ist used to retrieve the image parts
     * of several token types.
     *
     * @param image split this string into lines
     * @return an array containing the lines of the image without line separator
     * characters
     */
    protected String[] splitIntoLines(String image) {
        LinkedList lines = new LinkedList();
        int index = 0;
        int start = 0;

        while (index < image.length()) {
            switch (image.charAt(index)) {
            case '\r':
                lines.add(image.substring(start, index));
                if (index + 1 < image.length() && image.charAt(index + 1) == '\n') {
                    index += 2;
                } else {
                    index++;
                }
                start = index;
                break;
            case '\n':
                lines.add(image.substring(start, index));
                start = ++index;
                break;
            default:
                index++;
            }
        }

        if (start < index || start > 0) {
            lines.add(image.substring(start, index));
        }

        return (String[]) lines.toArray(new String[lines.size()]);
    }

    /**
     * Splits a given string into lines and removing string escapes. The method is used to
     * retrieve the image parts for string token types.
     *
     * @param prop the {@link TokenizerProperty} describing a string
     * @param image split this string into lines
     * @return an array containing the lines of the image without line separator
     * characters
     */
    protected String[] splitString(TokenizerProperty prop, String image) {
        // complete string
        String[] images = prop.getImages();
        String begin = images[0];
        String end = images[1];
        String esc = images[2];
        boolean noCase = isFlagSet(prop, Flags.F_NO_CASE);
        StringBuffer buffer = null;
        int index = begin.length();
        int start = index;
        int endIndex;

        if (image.length() - start >= end.length()
                && ((!noCase && end.equals(image.substring(image.length() - end.length())))
                        || (noCase
                                && end.equalsIgnoreCase(image.substring(image.length() - end.length()))))) {
            endIndex = image.length() - end.length();
        } else {
            endIndex = image.length();
        }

        while (index < endIndex) {
            if ((!noCase && image.startsWith(esc, index))
                    || (noCase && image.substring(index, index + esc.length()).equalsIgnoreCase(esc))) {
                if (buffer == null) {
                    buffer = new StringBuffer(image.length());
                }
                buffer.append(image.substring(start, index));
                index += esc.length();
                if (index < image.length()) {
                    if ((!noCase && image.startsWith(esc, index))
                            || (noCase
                                    && image.substring(index, index + esc.length()).equalsIgnoreCase(esc))) {
                        buffer.append(esc);
                        index += esc.length();
                    } else if ((!noCase && image.startsWith(begin, index))
                            || (noCase && image.substring(index, index + begin.length())
                                    .equalsIgnoreCase(begin))) {
                        buffer.append(begin);
                        index += begin.length();
                    } else if ((!noCase && image.startsWith(end, index))
                            || (noCase
                                    && image.substring(index, index + end.length()).equalsIgnoreCase(end))) {
                        buffer.append(end);
                        index += end.length();
                    }
                }
                start = index;
            }
            index++;
        }

        if (buffer != null && start < index) {
            buffer.append(image.substring(start, endIndex));
        }

        return splitIntoLines((buffer != null) ? buffer.toString() : image.substring(start, endIndex));
    }

    /**
     * Splits a given block comment into lines. The method is used to retrieve the image
     * parts for block comment token types.
     *
     * @param prop the {@link TokenizerProperty} describing a block comment
     * @param image split this string into lines
     * @return an array containing the lines of the image without line separator
     * characters
     */
    protected String[] splitBlockComment(TokenizerProperty prop, String image) {
        // complete string
        String[] images = prop.getImages();
        String start = images[0];
        String end = images[1];
        boolean noCase = isFlagSet(prop, Flags.F_NO_CASE);

        if (image.length() - start.length() >= end.length()
                && ((!noCase && end.equals(image.substring(image.length() - end.length())))
                        || (noCase
                                && end.equalsIgnoreCase(image.substring(image.length() - end.length()))))) {
            return splitIntoLines(image.substring(start.length(), image.length() - end.length()));
        } else {
            return splitIntoLines(image.substring(start.length()));
        }
    }

    /**
     * Event handler method. The given {@link TokenizerPropertyEvent} parameter contains
     * the nessecary information about the property change. We choose one single method in
     * favour of various more specialized methods since the reactions on adding, removing
     * and modifying tokenizer properties are often the same (flushing cash, rereading
     * information etc.) are probably not very different. <br> Note that a modification of
     * the parse flags in the backing {@link TokenizerProperties} object removes all flags
     * previously modified through {@link #changeParseFlags}.
     *
     * @param event the {@link TokenizerPropertyEvent} that describes the change
     */
    @Override
    public void propertyChanged(TokenizerPropertyEvent event) {
        TokenizerProperty prop = event.getProperty();

        synchronized (this) {
            switch (event.getType()) {
            case TokenizerPropertyEvent.PROPERTY_ADDED:
            case TokenizerPropertyEvent.PROPERTY_REMOVED:
                switch (prop.getType()) {
                case Token.LINE_COMMENT:
                case Token.BLOCK_COMMENT:
                case Token.STRING:
                case Token.SPECIAL_SEQUENCE:
                    if ((_internalFlags & IFLAG_EXTERNAL_SEQUENCE_HANDLER) == 0
                            && _properties instanceof SequenceHandler) {
                        setSequenceHandler((SequenceHandler) _properties);
                    }
                    break;
                case Token.KEYWORD:
                    if ((_internalFlags & IFLAG_EXTERNAL_KEYWORD_HANDLER) == 0
                            && _properties instanceof KeywordHandler) {
                        setKeywordHandler((KeywordHandler) _properties);
                    }
                    break;
                case Token.PATTERN:
                    if ((_internalFlags & IFLAG_EXTERNAL_PATTERN_HANDLER) == 0
                            && _properties instanceof PatternHandler) {
                        setPatternHandler((PatternHandler) _properties);
                    }
                    break;
                }
                break;

            case TokenizerPropertyEvent.PROPERTY_MODIFIED:
                switch (prop.getType()) {
                case TokenizerProperty.PARSE_FLAG_MASK:
                    _flags = getTokenizerProperties().getParseFlags();
                    _flagMask = 0;
                    if (isFlagSet(Flags.F_COUNT_LINES)) {
                        if (_lineNumber < 0) {
                            if (_position2LineMap != null) {
                                _position2LineMap.clear();
                            }
                            _lineNumber = 0;
                            putPosition(_currentReadPos, _lineNumber);
                        }
                        if (_columnNumber < 0) {
                            _columnNumber = 0;
                        }
                    } else {
                        _lineNumber = -1;
                        _columnNumber = -1;
                    }
                    break;
                }
                break;
            }
        }
    }

    // ---------------------------------------------------------------------------
    // Implementation
    //

    /**
     * Embedded tokenizers have their base tokenizer they share the input stream with.
     *
     * @return the base tokenizer (the one owning the input stream and text buffer)
     */
    protected AbstractTokenizer getBaseTokenizer() {
        return _baseTokenizer;
    }

    /**
     * Returns the {@link de.susebox.jtopas.spi.DataProvider} of the base tokenizer. This
     * is this tokenizer if it is not an embedded one.
     *
     * @param startPos position in the input data
     * @param length number of characters
     * @return the <code>DataProvider</code> for the given data range
     */
    protected DataProvider getBaseDataProvider(int startPos, int length) {
        return getBaseTokenizer().getDataProvider(startPos, length);
    }

    /**
     * This method organizes the input buffer. It moves the current text window if
     * nessecary or allocates more space, if data should be kept completely (see the
     * {@link Flags#F_KEEP_DATA} flag). Its main purpose is to call the
     * {@link TokenizerSource#read} method.
     *
     * @return number of read bytes or -1 if an end-of-file condition occured
     * @throws TokenizerException wrapped exceptions from the {@link TokenizerSource#read}
     * method
     */
    protected int readMoreDataFromBase() throws TokenizerException {
        // its always the base tokenizer doing the reading
        int readChars = -1;

        if (!_eofReached) {
            AbstractTokenizer baseTokenizer = getBaseTokenizer();

            if (baseTokenizer != this) {
                readChars = baseTokenizer.readMoreData();
            } else {
                readChars = readMoreData();
            }
            if (readChars > 0) {
                _currentWritePos += readChars;
            } else if (readChars < 0) {
                readChars = -1;
                _eofReached = true;
            }

            // Inform all embedded tokenizers about input buffer changes
            synchronizeAll();
        }
        return readChars;
    }

    /**
     * When the method {@link #readMoreData} changes the contents of the input buffer or
     * the input buffer itself, all embedded tokenizers must be synchronized. That means
     * their member variables are adjusted to the base tokenizer.
     *
     * @throws TokenizerException if something goes wrong
     */
    protected void synchronizeAll() throws TokenizerException {
        AbstractTokenizer embedded = getBaseTokenizer();

        while ((embedded = embedded._nextTokenizer) != null) {
            switchTo(embedded);   // adjust the member variables
        }
    }

    /**
     * Checks the EOF condition at the given offset.
     *
     * @param offset check at this position relative to the current read position
     * @return <code>true</code> if EOF has been reached, <code>false</code> otherwise
     * @throws TokenizerException failure while reading data from the input stream
     */
    protected boolean isEOF(int offset) throws TokenizerException {
        if (_currentReadPos + offset < _currentWritePos || readMoreDataFromBase() > 0) {
            return false;
        } else {
            _scannedToken[1] = new Token(Token.EOF);
            return true;
        }
    }

    /**
     * The number of characters until the next comment, whitespace, string, special
     * sequence or separator are determined. The character sequnce is then checked for
     * keyword or pattern matching.
     *
     * @param token buffer to receive information about the keyword or normal token
     * @return <code>null</code> or a {@link TokenizerProperty} if a keyword or pattern is
     * found
     * @throws TokenizerException failure while reading data from the input stream
     */
    protected TokenizerProperty completeBoundedToken(Token token) throws TokenizerException {
        // find out the return value (length of normal token)
        int len = 1;  // the first character is a normal one, see call of this method

        while (!(isEOF(len)
                || isWhitespace(len)
                || isPattern(len, true)
                || isSpecialSequence(len)
                || isSeparator(len))) {
            len++;
        }
        token.setLength(len);

        // test on keyword or non-free pattern
        TokenizerProperty prop = null;

        if ((prop = isKeyword(_currentReadPos, len)) != null) {
            token.setType(Token.KEYWORD);
            token.setCompanion(prop.getCompanion());
        } else {
            token.setType(Token.NORMAL);
        }
        return prop;
    }

    /**
     * After having identified a whitespace, this method continues to read data until it
     * detects a non-whitespace.
     *
     * @return number of consecutive whitespaces
     * @throws TokenizerException failure while reading data from the input stream
     */
    protected int completeWhitespace() throws TokenizerException {
        int start = _currentReadPos + 1;  // the first whitespace we have already
        int available = _currentWritePos - start;
        int len = readWhitespaces(start, available);

        while (len == available) {
            if (readMoreDataFromBase() <= 0) {
                break;
            }
            start += len;
            available = _currentWritePos - start;
            len += readWhitespaces(start, available);
        }
        return len + 1;   // the first whitespace we had already
    }

    /**
     * This method checks at the given offset if it is a whitespace.
     *
     * @param offset check at this position relative to the current read position
     * @throws TokenizerException failure while reading data from the input stream
     * @return <code>true</code> if a whitespace sequence was found at the given offset,
     * <code>false</code> otherwise
     */
    protected boolean isWhitespace(int offset) throws TokenizerException {
        if (_whitespaceHandler != null) {
            if (_currentReadPos + offset >= _currentWritePos && readMoreDataFromBase() < 0) {
                return false;
            }

            if (isWhitespace(getChar(_currentReadPos + offset))) {
                _scannedToken[1] = new Token(Token.WHITESPACE);
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks at the given offset if it contains a separator.
     *
     * @param offset check at this position relative to the current read position
     * @throws TokenizerException failure while reading data from the input stream
     * @return <code>true</code> if a separator was found atthe given offset,
     * <code>false</code> otherwise
     */
    protected boolean isSeparator(int offset) throws TokenizerException {
        if (_separatorHandler != null
                && (_currentReadPos + offset < _currentWritePos || readMoreDataFromBase() > 0)
                && _separatorHandler.isSeparator(getChar(_currentReadPos + offset))) {
            _scannedToken[1] = new Token(Token.SEPARATOR);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Testing for pattern matching.
     *
     * @param offset check at this position relative to the current read position
     * @param freePatternOnly if <code>true</code> consider only pattern that can occur
     * anywhere in the data
     * @throws TokenizerException failure while reading data from the input stream
     * @return <code>true</code> if a pattern match was found at the given offset,
     * <code>false</code> otherwise
     */
    protected boolean isPattern(int offset, boolean freePatternOnly) throws TokenizerException {
        if (_patternHandler != null) {
            // for pattern, we might need a lot of data
            int startingAtPos = _currentReadPos + offset;

            while (_currentWritePos - startingAtPos < PATTERN_MAX_SIZE) {
                if (readMoreDataFromBase() <= 0) {
                    break;
                }
            }

            // try pattern matching
            DataProvider dataProvider = getBaseDataProvider(startingAtPos, _currentWritePos - startingAtPos);
            PatternHandler.Result result = _patternHandler.matches(dataProvider);
            boolean isFree = (result != null) ? isFlagSet(result.getProperty(), Flags.F_FREE_PATTERN) : false;

            if (result != null && (isFree || !freePatternOnly)) {
                if (!isFree) {
                    int nextOffset = offset + result.getLengthOfMatch();

                    if (isEOF(nextOffset)
                            || isWhitespace(nextOffset)
                            || isPattern(nextOffset, true)
                            || isSpecialSequence(nextOffset)
                            || isSeparator(nextOffset)) {
                        _scannedToken[2] = _scannedToken[1];
                    } else {
                        return false;
                    }
                }
                _scannedToken[1] = new Token(Token.PATTERN, null, result.getProperty());
                _scannedToken[1].setLength(result.getLengthOfMatch());
                if (isFlagSet(result.getProperty(), Flags.F_RETURN_IMAGE_PARTS)) {
                    _scannedToken[1].setImageParts(result.getGroups());
                }
                return true;
            }
        }

        // no pattern matching available or no match found
        return false;
    }

    /**
     * This method checks at the given offset if it contains a a special sequence.
     *
     * @param offset check at this position relative to the current read position
     * @throws TokenizerException failure while reading data from the input stream
     * @return <code>true</code> if a special sequence was found at the given offset,
     * <code>false</code> otherwise
     */
    protected boolean isSpecialSequence(int offset) throws TokenizerException {
        if (_sequenceHandler != null) {
            // do we need more data to ensure enough characters for even the longest
            // possible sequence match
            int startingAtPos = _currentReadPos + offset;

            while (_sequenceHandler.getSequenceMaxLength() > _currentWritePos - startingAtPos) {
                if (readMoreDataFromBase() <= 0) {
                    break;
                }
            }

            // invoke the sequence handler
            DataProvider dataProvider = getBaseDataProvider(startingAtPos, _currentWritePos - startingAtPos);
            TokenizerProperty prop = _sequenceHandler.startsWithSequenceCommentOrString(dataProvider);

            if (prop != null) {
                _scannedToken[1] = new Token(prop.getType(), null, prop);
                return true;
            }
        }

        // no sequence handler given or no special sequence at given offset
        return false;
    }

    /**
     * Completing a line comment. After a line comment sequence has been found, all
     * characters up to and including the end-of-line combination belong to the line
     * comment. Note that on reaching end-of-file a line comment does not nessecarily ends
     * with an end-of-line sequence (linefeed for example).
     *
     * @param prop the property describing the line comment to complete
     * @return length of the line comment
     * @throws TokenizerException failure while reading data from the input stream
     */
    protected int completeLineComment(TokenizerProperty prop) throws TokenizerException {
        String[] images = prop.getImages();
        int len = images[0].length();

        while (_currentReadPos + len < _currentWritePos || readMoreDataFromBase() > 0) {
            switch (getChar(_currentReadPos + len)) {
            case '\r':
                len++;
                if (_currentReadPos + len < _currentWritePos || readMoreDataFromBase() > 0) {
                    if (getChar(_currentReadPos + len) == '\n') {
                        len++;
                    }
                }
                return len;
            case '\n':
                len++;
                return len;
            default:
                len++;
            }
        }
        return len;
    }

    /**
     * Completing a block comment. After a block comment sequence has been found, all
     * characters up to and including the end sequence of the block comment belong to the
     * block comment. Note that on reaching end-of-file a block comment does not
     * nessecarily ends with an end-of-block-comment sequence.
     *
     * @param prop the property describing the block comment to complete
     * @return length of the block comment
     * @throws TokenizerException failure while reading data from the input stream
     */
    protected int completeBlockComment(TokenizerProperty prop) throws TokenizerException {
        String[] images = prop.getImages();
        String start = images[0];
        String end = images[1];
        boolean noCase = isFlagSet(prop, Flags.F_NO_CASE);
        boolean nested = isFlagSet(prop, Flags.F_ALLOW_NESTED_COMMENTS);
        int len = start.length();
        int level = 0;

        __LOOP__: do {
            // test on nested comments: we take only care for nesting the same
            // block comment
            if (nested) {
                switch (comparePrefix(len, start, noCase)) {
                case 0:     // comment start identified
                    level++;
                    len += start.length();
                    continue __LOOP__;
                case -1:    // EOF reached
                    return _currentWritePos - _currentReadPos;
                }
            }

            // is it the end ?
            switch (comparePrefix(len, end, noCase)) {
            case 0:       // comment end identified
                level--;
                len += end.length();
                break;
            case -1:      // EOF reached
                return _currentWritePos - _currentReadPos;
            default:
                len++;
            }
        } while (level >= 0);

        // block comment regularly terminated
        return len;
    }

    /**
     * Completing a string. After a string start sequence has been found, all characters
     * up to and including the end-of-string sequence belong to the string. Note that on
     * reaching end-of-file a string does not nessecarily ends with an end-of-string
     * sequence.
     *
     * @param prop the property describing the string to complete
     * @return length of the string
     * @throws TokenizerException failure while reading data from the input stream
     */
    protected int completeString(TokenizerProperty prop) throws TokenizerException {
        // complete string
        String[] images = prop.getImages();
        String start = images[0];
        String end = images[1];
        String esc = images[2];
        int len = start.length();
        boolean noCase = isFlagSet(prop, Flags.F_NO_CASE);
        boolean escEqualsEnd = (!noCase && esc != null && esc.compareTo(end) == 0)
                || (noCase && esc != null && esc.compareToIgnoreCase(end) == 0);

        while (true) {
            // test on escape
            if (esc != null) {
                switch (comparePrefix(len, esc, noCase)) {
                case 0:       // escape found
                    len += esc.length();
                    if (escEqualsEnd) {
                        switch (comparePrefix(len, end, noCase)) {
                        case 0:
                            len += end.length();
                            break;
                        case -1:      // EOF reached
                            return _currentWritePos - _currentReadPos;
                        default:      // regular return point if esc is the string end
                            return len;
                        }
                    } else {
                        len++;        // esc != string end: skip the next character
                    }
                    continue;
                case -1:          // EOF reached
                    return _currentWritePos - _currentReadPos;
                }
            }

            // test on end sequence
            switch (comparePrefix(len, end, noCase)) {
            case 0:             // this is the regular return point if esc != string end
                len += end.length();
                return len;
            case -1:            // EOF reached
                return _currentWritePos - _currentReadPos;
            default:
                len++;
            }
        }
    }

    /**
     * This method compares the characters at the given offset (from the current read
     * position) with the given prefix.
     *
     * @param offset start comparing at this offset from the current read position
     * @param prefix compare read data with this prefix
     * @param noCase case- or not case-sensitive comparison
     * @throws TokenizerException failure while reading data from the input stream
     * @return 0 if the the given prefix matches the input stream, -1 on EOF and 1 if not
     * matching
     */
    protected int comparePrefix(int offset, String prefix, boolean noCase)
            throws TokenizerException {
        // compare
        int len = prefix.length();

        for (int pos = offset; pos < offset + len; ++pos) {
            // do we have enough data
            if (_currentReadPos + pos >= _currentWritePos && readMoreDataFromBase() < 0) {
                return -1;
            }

            // compare single character
            char c1 = prefix.charAt(pos - offset);
            char c2 = getChar(_currentReadPos + pos);

            if (c1 != c2
                    && (!noCase || Character.toUpperCase(c1) != Character.toUpperCase(c2))) {
                return 1;
            }
        }

        // found
        return 0;
    }

    /**
     * The method recomputes the line and column position of the tokenizer, if the flag
     * {@link Flags#F_COUNT_LINES} is set. It gets the token type of the {@link Token}
     * that has been retrieved by the calling {@link #nextToken}. Using the tokenizer
     * control flags and certain other information it tries to to find end-of-line
     * sequences as fast as possible. For example, a line comment should always contain a
     * end-of-line sequence, so we can simply increase the line count and set the column
     * count to 0.
     *
     * @param type the type of the current token
     * @param length the length of the current token
     */
    protected void adjustLineAndColumn(int type, int length) {
        // line and column counting not required
        if (!isFlagSet(Flags.F_COUNT_LINES)) {
            return;
        }

        // there might be a simple way to determine the current line and column position
        switch (type) {
        case Token.EOF:
            return;

        case Token.LINE_COMMENT:        // a line comment always ends with a newline
            _lineNumber++;
            _columnNumber = 0;
            putPosition(_currentReadPos + length, _lineNumber);
            return;

        case Token.SPECIAL_SEQUENCE:
        case Token.SEPARATOR:
        case Token.NORMAL:
        case Token.KEYWORD:
            if (_whitespaceHandler != null && _whitespaceHandler.newlineIsWhitespace()) { // newline
                                                                                          // is
                                                                                          // a
                                                                                          // whitespace
                                                                                          // character
                _columnNumber += length;                      // it should therefore not
                                                              // occure in other
                return;                                       // tokens
            }
            break;

        case Token.WHITESPACE:
            if (!(_whitespaceHandler.isWhitespace('\n') || _whitespaceHandler.isWhitespace('\r'))) {
                _columnNumber += length;                      // newline is not a
                                                              // whitespace; we do not
                                                              // have
                return;                                       // to test for it in the
                                                              // current token
            }
            break;
        }

        // count it
        for (int pos = _currentReadPos; pos < _currentReadPos + length; ++pos) {
            switch (getChar(pos)) {
            case '\r':
                if (pos + 1 >= _currentReadPos + length || getChar(pos + 1) != '\n') {
                    _lineNumber++;
                    _columnNumber = 0;
                    putPosition(pos + 1, _lineNumber);
                    break;
                }
                pos++;
                /* no break; */
            case '\n':
                _lineNumber++;
                _columnNumber = 0;
                putPosition(pos + 1, _lineNumber);
                break;

            default:
                _columnNumber++;
            }
        }
    }

    /**
     * Putting a new position into the position-to-line-number map.
     *
     * @param position the position to map to the current line number
     */
    private void putPosition(int position, int lineNumber) {
        if (_position2LineMap == null) {
            _position2LineMap = new TreeMap();
        }
        _position2LineMap.put(new Integer(position), new Integer(lineNumber));
    }

    /**
     * Checking a given flag. The method considers both the globally set flags in the
     * associated {@link TokenizerProperties} instance and the locally set by
     * {@link #changeParseFlags}.
     *
     * @param flag one of the <code>F_...</code> flags defined in
     * {@link TokenizerProperties}
     */
    protected boolean isFlagSet(int flag) {
        return (getParseFlags() & flag) != 0;
    }

    /**
     * Checking if a given flag is set for the given {@link TokenizerProperty}, for this
     * <code>Tokenizer</code> or for the used {@link TokenizerProperties}. The method
     * considers both the globally set flags in the associated {@link TokenizerProperties}
     * instance and the locally set by {@link #changeParseFlags}.
     *
     * @param prop check the flag for this property
     * @param flag one of the {@link Flags} constants
     */
    protected boolean isFlagSet(TokenizerProperty prop, int flag) {
        return prop
                .isFlagSet(flag, (getTokenizerProperties().getParseFlags() & flag) != 0 || isFlagSet(flag));
    }

    // ---------------------------------------------------------------------------
    // Class members
    //

    /**
     * mask of flags that can be set separately for a <code>AbstractTokenizer</code>.
     */
    protected static final int VALID_FLAGS_MASK = Flags.F_RETURN_WHITESPACES
            | Flags.F_TOKEN_POS_ONLY
            | Flags.F_KEEP_DATA
            | Flags.F_COUNT_LINES;

    /**
     * {@link TokenizerProperties} tha tare used if no others have been specified by
     * calling {@link #setTokenizerProperties}.
     */
    protected StandardTokenizerProperties _defaultProperties = null;

    /**
     * Buffer sizes
     */
    private static final int PATTERN_MAX_SIZE = 0x40000;   // 256K

    /**
     * Bits for the internal flag bitmask
     */
    private static final byte IFLAG_EXTERNAL_PATTERN_HANDLER  = 0x01;
    private static final byte IFLAG_EXTERNAL_KEYWORD_HANDLER  = 0x02;
    private static final byte IFLAG_EXTERNAL_SEQUENCE_HANDLER = 0x04;

    // ---------------------------------------------------------------------------
    // Members
    //

    /**
     * overall tokenizer flags.
     */
    protected int _flags = 0;

    /**
     * a combination of <code>F_...</code> constants defined in
     * {@link TokenizerProperties} indicating which bits in the {@link #_flags} member are
     * valid. All other flags are taken from the associated {@link TokenizerProperties}
     * object.
     *
     * @see #changeParseFlags
     */
    private int _flagMask = 0;

    /**
     * Flag if EOF has been reached. The flag should speed up calls to
     * {@link readMoreDataFromBase}
     */
    private boolean _eofReached = true;

    /**
     * Data index there {@link #nextToken} will start parsing.
     */
    protected int _currentReadPos = 0;

    /**
     * Data index there {@link #readMoreDataFromBase} will fill in new data.
     */
    protected int _currentWritePos = 0;

    /**
     * if line counting is enabled, this contains the current line number starting with 0.
     */
    protected int _lineNumber = -1;

    /**
     * if line counting is enabled, this contains the current column number starting with
     * 0.
     */
    protected int _columnNumber = -1;

    /**
     * List of currently known token. The first element is the current token returned by
     * the last call to {@link #nextToken}. The following elements are look-ahead token
     * that have already been identified when extracting the current token.
     */
    protected Token[] _scannedToken = new Token[] { null, null, null };

    /**
     * For embedded tokenizers: this is the list of the succeding tokenizers
     */
    protected AbstractTokenizer _nextTokenizer = null;

    /**
     * For embedded tokenizers: this is the base tokenizer that reads the data
     */
    protected AbstractTokenizer _baseTokenizer = null;

    /**
     * For embedded tokenizers: this is the list of the previous tokenizers
     */
    protected AbstractTokenizer _prevTokenizer = null;

    /**
     * Whitespace handler
     */
    private WhitespaceHandler _whitespaceHandler = null;

    /**
     * Separator handler
     */
    private SeparatorHandler _separatorHandler = null;

    /**
     * Keyword handler
     */
    private KeywordHandler _keywordHandler = null;

    /**
     * Sequence handler
     */
    private SequenceHandler _sequenceHandler = null;

    /**
     * Sequence handler
     */
    private PatternHandler _patternHandler = null;

    /**
     * The source of input data
     */
    private TokenizerSource _source = null;

    /**
     * The characteristics of this tokenizer.
     */
    private TokenizerProperties _properties = null;

    /**
     * Line number to position mapping
     */
    private TreeMap _position2LineMap = null;

    /**
     * Control flags for the internal work
     */
    private long _internalFlags = 0;
}
