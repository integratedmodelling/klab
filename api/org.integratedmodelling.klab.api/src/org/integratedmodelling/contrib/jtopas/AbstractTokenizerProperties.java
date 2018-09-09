/*
 * AbstractTokenizerProperties.java: Core implementation of TokenizerProperties
 *
 * Copyright (C) 2003 Heiko Blau
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
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

//-----------------------------------------------------------------------------
// Class AbstractTokenizerProperties
//

/**
 * <p> The class <code>AbstractTokenizerProperties</code> provides the skeleton for
 * implementations of the {@link TokenizerProperties} interface. It leaves only the more
 * general methods to these implementations. For instance, the simple method
 * {@link #addKeyword(String)} can and should call the more complex method
 * {@link #addKeyword(String, Object)} with the second parameter <code>null</code>. That
 * method in turn should call {@link #addKeyword(String, Object, int)} etc. </p>
 *
 * @see TokenizerProperties
 * @see Tokenizer
 * @author Heiko Blau
 */
public abstract class AbstractTokenizerProperties implements TokenizerProperties {

    // ---------------------------------------------------------------------------
    // Abstract methods
    //

    /**
     * This method must be implemented by derived classes to register a
     * {@link TokenizerProperty}. When called, the given <code>property</code> has already
     * been checked for not being <code>null</code> or incomplete (no leading image). <br>
     * If the property to register is already known, perhaps with a different type,
     * different flags or a different companion, it is replaced and returned. <br> The
     * notification of the registered {@link TokenizerPropertyListener} is done by this
     * abstract class, the implementations must not do it themselves. <br> The method is
     * called in a thread-safe way. That means only one thread can enter the method at a
     * given time.
     *
     * @param property a non-null, complete token description
     * @return the old, replaced property or <code>null</code>
     */
    protected abstract TokenizerProperty doAddProperty(TokenizerProperty property);

    /**
     * This method must be implemented by derived classes to deregister a
     * {@link TokenizerProperty}. When called, the given <code>property</code> has already
     * been checked for not being <code>null</code> or incomplete (no leading image). <br>
     * According to the {@link TokenizerProperties} interface specification of the
     * {@link TokenizerProperties#removeProperty} method, this method does nothing if the
     * given property is unknown. In this case <code>null</code> is returned. <br>
     * Otherwise the removed property is returned. <br> The notification of the registered
     * {@link TokenizerPropertyListener} is done by this abstract class, the
     * implementations must not do it themselves. <br> The method is called in a
     * thread-safe way. That means only one thread can enter the method at a given time.
     *
     * @param property a non-null, complete token description
     * @return the removed property or <code>null</code>
     */
    protected abstract TokenizerProperty doRemoveProperty(TokenizerProperty property);

    /**
     * This method must be implemented by derived classes to retrieve the
     * {@link TokenizerProperty} for the given image. When called, the given
     * <code>image</code> has already been checked for not being <code>null</code>. <br>
     * According to the {@link TokenizerProperties} interface specification of the various
     * get methods, this method must return <code>null</code> if there is no property that
     * matches the given image. <br> The method is called in a thread-safe way. That means
     * only one thread can enter the method at a given time.
     *
     * @param type the type the returned property should have
     * @param startImage the (starting) image
     * @return the token description for the image or <code>null</code>
     */
    protected abstract TokenizerProperty doGetProperty(int type, String startImage);

    /**
     * This method must be implemented by derived classes to register the given simple
     * whitespaces. When called, the calling method has already ensured that the parameter
     * is not <code>null</code> (but could be empty) and uppercase if the
     * {@link Flags#F_NO_CASE} flag is set. <br> The method should return the old
     * whitespace set. When first called, this is the default whitespace set. It is ok to
     * return <code>null</code> if the old set is empty. <br> The notification of the
     * registered {@link TokenizerPropertyListener} is done by this abstract class, the
     * implementations must not do it themselves. <br> The method is called in a
     * thread-safe way. That means only one thread can enter the method at a given time.
     *
     * @param whitespaces the new whitespace set
     * @return the old whitespace set
     */
    protected abstract String doSetWhitespaces(String whitespaces);

    /**
     * This method must be implemented by derived classes to set the given simple
     * separators. When called, the calling method {@link #setSeparators} has already
     * ensured that the parameter is not <code>null</code> (but empty) and uppercase if
     * the {@link Flags#F_NO_CASE} flag is set. <br> The method should return the old
     * separator set. When first called, this is the default separator set. It is ok to
     * return <code>null</code> if the old set is empty. <br> The notification of the
     * registered {@link TokenizerPropertyListener} is done by this abstract class, the
     * implementations must not do it themselves. <br> The method is called in a
     * thread-safe way. That means only one thread can enter the method at a given time.
     *
     * @param separators the new separator set
     * @return the old separator set
     */
    protected abstract String doSetSeparators(String separators);

    // ---------------------------------------------------------------------------
    // Methods of the TokenizerProperties interface
    //

    /**
     * See the method description in {@link TokenizerProperties}.
     *
     * @param flags the parser control flags
     * @see #getParseFlags
     */
    @Override
    public void setParseFlags(int flags) {
        // normalize flags
        flags = normalizeFlags(flags, flags);

        // set flags
        synchronized (this) {
            int oldFlags = _flags;

            _flags = flags;
            if (oldFlags != _flags) {
                notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_MODIFIED, new TokenizerProperty(TokenizerProperty.PARSE_FLAG_MASK, new String[] {
                        Integer.toBinaryString(_flags) }), new TokenizerProperty(TokenizerProperty.PARSE_FLAG_MASK, new String[] {
                                Integer.toBinaryString(oldFlags) })));
            }
        }
    }

    /**
     * See the method description in {@link TokenizerProperties}.
     *
     * @return the current parser control flags
     * @see #setParseFlags
     */
    @Override
    public int getParseFlags() {
        return _flags;
    }

    /**
     * Returns <code>true</code> if a given flag is set in the current parse flags. If the
     * parameter contains more than one bit the method returns only <code>true</code> if
     * all bits are set.
     *
     * @param flag the flag to test
     * @return <code>true</code> if all bits in flag are set.
     * @see #setParseFlags
     */
    @Override
    public boolean isFlagSet(int flag) {
        return (_flags & flag) == flag;
    }

    /**
     * Checks if a given flag (see the constants in {@link Flags}) is set for the given
     * {@link TokenizerProperty} in the context of this <code>TokenizerProperties</code>
     * instance.
     *
     * @param prop the {@link TokenizerProperty} concerned
     * @param flag the flag to check (may contain more than one bit)
     * @return <code>true</code> if the flag is set either explicit in the property or
     * globally for this <code>TokenizerProperties</code> object, <code>false</code>
     * otherwise
     * @throws NullPointerException if no property is given
     */
    @Override
    public boolean isFlagSet(TokenizerProperty prop, int flag) throws NullPointerException {
        return prop.isFlagSet(flag, isFlagSet(flag));
    }

    // ---------------------------------------------------------------------------
    // simple whitespaces and separators
    //

    /**
     * Setting the whitespace character set of the tokenizer. See the method description
     * in {@link TokenizerProperties}. <br> This method calls the abstract method
     * {@link #doSetWhitespaces}. It guaranties that the parameter passed to
     * <code>doSetWhitespaces</code> is not null (instead empty) and uppercase if the Flag
     * {@link Flags#F_NO_CASE} is set.
     *
     * @param whitespaces the whitespace set
     */
    @Override
    public void setWhitespaces(String whitespaces) {
        // normalize whitespaces
        String newValue = (whitespaces != null) ? whitespaces : "";
        String oldValue;

        if ((_flags & Flags.F_NO_CASE) != 0) {
            newValue = newValue.toUpperCase();
        }

        // set new whitespaces
        synchronized (this) {
            oldValue = doSetWhitespaces(newValue);

            // notify listeners
            handleEvent(Token.WHITESPACE, newValue, oldValue);
        }
    }

    /**
     * Adding new whitespaces to the existing set.
     *
     * @param whitespaces additional whitespaces for the whitespace set
     * @throws IllegalArgumentException when <code>null</code> is passed or incomplete
     * ranges are specified (e.g. <code>"a-"</code>)
     */
    @Override
    public void addWhitespaces(String whitespaces) throws IllegalArgumentException {
        try {
            if (whitespaces.length() > 0) {
                setWhitespaces(mergeSet(getWhitespaces(), whitespaces, false));
            }
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Removing whitespaces from the existing set.
     *
     * @param whitespaces whitespaces to remove from the whitespace set
     * @throws IllegalArgumentException when <code>null</code> is passed or incomplete
     * ranges are specified (e.g. <code>"a-"</code>)
     */
    @Override
    public void removeWhitespaces(String whitespaces) throws IllegalArgumentException {
        try {
            if (whitespaces.length() > 0) {
                setWhitespaces(mergeSet(getWhitespaces(), whitespaces, true));
            }
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Setting the separator character set of the tokenizer. See the method description in
     * {@link TokenizerProperties}. <br> This method calls the abstract method
     * {@link #doSetSeparators}. It guaranties that the parameter passed to
     * <code>doSetSeparators</code> is not null (instead empty) and uppercase if the Flag
     * {@link Flags#F_NO_CASE} is set.
     *
     * @param separators the separator set
     */
    @Override
    public void setSeparators(String separators) {
        // normalize separators
        String newValue = (separators != null) ? separators : "";
        String oldValue;

        if ((_flags & Flags.F_NO_CASE) != 0) {
            newValue = newValue.toUpperCase();
        }

        // set new separator set
        synchronized (this) {
            oldValue = doSetSeparators(newValue);

            // notify listeners
            handleEvent(Token.SEPARATOR, newValue, oldValue);
        }
    }

    /**
     * Adding new separators to the existing set.
     *
     * @param separators additional separators for the separator set
     * @throws IllegalArgumentException when <code>null</code> is passed or incomplete
     * ranges are specified (e.g. <code>"a-"</code>)
     */
    @Override
    public void addSeparators(String separators) throws IllegalArgumentException {
        try {
            if (separators.length() > 0) {
                setSeparators(mergeSet(getSeparators(), separators, false));
            }
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Removing separators from the existing set.
     *
     * @param separators separating characters to remove from the separator set
     * @throws IllegalArgumentException when <code>null</code> is passed or incomplete
     * ranges are specified (e.g. <code>"a-"</code>)
     */
    @Override
    public void removeSeparators(String separators) throws IllegalArgumentException {
        try {
            if (separators.length() > 0) {
                setSeparators(mergeSet(getSeparators(), separators, true));
            }
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException();
        }
    }

    // ---------------------------------------------------------------------------
    // string properties
    //

    /**
     * Registering a string description. See the method description in the interface
     * {@link TokenizerProperties}.
     *
     * @param start the starting sequence of a string
     * @param end the finishing sequence of a string
     * @param escape the escape sequence inside the string
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start or end
     * @see #removeString
     * @see #addString(String, String, String, Object)
     */
    @Override
    public void addString(String start, String end, String escape)
            throws IllegalArgumentException {
        addString(start, end, escape, null);
    }

    /**
     * Registering a the sequences that are used for string-like text parts. See the
     * method description in {@link TokenizerProperties}.
     *
     * @param start the starting sequence of a string
     * @param end the finishing sequence of a string
     * @param escape the escape sequence inside the string
     * @param companion the associated information
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start or end
     *
     */
    @Override
    public void addString(String start, String end, String escape, Object companion)
            throws IllegalArgumentException {
        addString(start, end, escape, companion, getParseFlags());
    }

    /**
     * Registering a the sequences that are used for string-like text parts. See the
     * method description in {@link TokenizerProperties}.
     *
     * @param start the starting sequence of a string
     * @param end the finishing sequence of a string
     * @param escape the escape sequence inside the string
     * @param companion the associated information
     * @param flags modification flags
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start or end
     */
    @Override
    public void addString(String start, String end, String escape, Object companion, int flags)
            throws IllegalArgumentException {
        addString(start, end, escape, companion, flags, flags);
    }

    /**
     * Registering a string with a set of flags and an associated flag mask.
     *
     * @param start the starting sequence of a string
     * @param end the finishing sequence of a string
     * @param escape the escape sequence inside the string
     * @param companion the associated information
     * @param flags modification flags
     * @param flagMask flags that have valid values in the parameter <code>flags</code>
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for keyword
     */
    @Override
    public void addString(String start, String end, String escape, Object companion, int flags, int flagMask)
            throws IllegalArgumentException {
        addProperty(new TokenizerProperty(Token.STRING, new String[] {
                start,
                end,
                escape }, companion, flags, flagMask));
    }

    /**
     * Removing a string description. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param start the starting sequence of a string
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start
     */
    @Override
    public void removeString(String start) throws IllegalArgumentException {
        TokenizerProperty prop = getString(start);

        if (prop != null) {
            removeProperty(prop);
        }
    }

    /**
     * Retrieving the information associated with a certain string. See the method
     * description in {@link TokenizerProperties}.
     *
     * @param start the starting sequence of a string
     * @return the associated information or <code>null</code>
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start
     */
    @Override
    public Object getStringCompanion(String start) throws IllegalArgumentException {
        TokenizerProperty prop = getString(start);

        if (prop != null) {
            return prop.getCompanion();
        } else {
            return null;
        }
    }

    /**
     * Checks if the given starting sequence of the string is known to the parser. See the
     * method description in {@link TokenizerProperties}.
     *
     * @param start the starting sequence of a string
     * @return <code>true</code> if the string is registered, <code>false</code> otherwise
     */
    @Override
    public boolean stringExists(String start) {
        try {
            return getString(start) != null;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Get the full description of a string property. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param start the starting sequence of a string
     * @return the full string description or <code>null</code>
     * @throws IllegalArgumentException if the given keyword is empty or null
     */
    @Override
    public TokenizerProperty getString(String start) throws IllegalArgumentException {
        // check parameter
        checkArgument(start, "String");

        // get the real thing
        synchronized (this) {
            return doGetProperty(Token.STRING, start);
        }
    }

    // ---------------------------------------------------------------------------
    // line comment properties
    //

    /**
     * Registering a the starting sequence of a line comment. See the method description
     * in {@link TokenizerProperties}.
     *
     * @param lineComment the starting sequence of the line comment
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start sequence of the line comment
     */
    @Override
    public void addLineComment(String lineComment) throws IllegalArgumentException {
        addLineComment(lineComment, null);
    }

    /**
     * Registering a the starting sequence of a line comment.
     *
     * See the method description in {@link TokenizerProperties}.
     * @param lineComment the starting sequence of a line comment
     * @param companion the associated information
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start sequence of the line comment
     */
    @Override
    public void addLineComment(String lineComment, Object companion) throws IllegalArgumentException {
        addLineComment(lineComment, companion, getParseFlags());
    }

    /**
     * Registering a the starting sequence of a line comment. See the method description
     * in {@link TokenizerProperties}.
     *
     * @param lineComment the starting sequence of a line comment
     * @param companion the associated information
     * @param flags modification flags
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start sequence of the line comment
     */
    @Override
    public void addLineComment(String lineComment, Object companion, int flags)
            throws IllegalArgumentException {
        addLineComment(lineComment, companion, flags, flags);
    }

    /**
     * Registering a line comment with a set of flags and an associated flag mask.
     *
     * @param lineComment the starting sequence of a line comment
     * @param companion the associated information
     * @param flags modification flags
     * @param flagMask flags that have valid values in the parameter <code>flags</code>
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for keyword
     */
    @Override
    public void addLineComment(String lineComment, Object companion, int flags, int flagMask)
            throws IllegalArgumentException {
        addProperty(new TokenizerProperty(Token.LINE_COMMENT, new String[] {
                lineComment }, companion, flags, flagMask));
    }

    /**
     * Removing a certain line comment. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param lineComment the starting sequence of the line comment
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start sequence of the line comment
     */
    @Override
    public void removeLineComment(String lineComment) throws IllegalArgumentException {
        TokenizerProperty prop = getLineComment(lineComment);

        if (prop != null) {
            removeProperty(prop);
        }
    }

    /**
     * Retrieving the associated object of a certain line comment. See the method
     * description in {@link TokenizerProperties}.
     *
     * @param lineComment the starting sequence of the line comment
     * @return the object associated with the line comment
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start sequence of the line comment
     */
    @Override
    public Object getLineCommentCompanion(String lineComment) throws IllegalArgumentException {
        TokenizerProperty prop = getLineComment(lineComment);

        if (prop != null) {
            return prop.getCompanion();
        } else {
            return null;
        }
    }

    /**
     * Checks if the give line comment is known. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param lineComment the starting sequence of the line comment
     * @return <code>true</code> if the line comment is known, <code>false</code>
     * otherwise
     */
    @Override
    public boolean lineCommentExists(String lineComment) {
        try {
            return getLineComment(lineComment) != null;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Get the full description of a line comment property. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param lineComment the starting sequence of the line comment
     * @return the full line comment description or <code>null</code>
     * @throws IllegalArgumentException if the given image is empty or null
     */
    @Override
    public TokenizerProperty getLineComment(String lineComment) throws IllegalArgumentException {
        // check parameter
        checkArgument(lineComment, "Line comment");

        // get the real thing
        synchronized (this) {
            return doGetProperty(Token.LINE_COMMENT, lineComment);
        }
    }

    // ---------------------------------------------------------------------------
    // block comment properties
    //

    /**
     * Registering a block comment. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param start the starting sequence of the block comment
     * @param end the finishing sequence of the block comment
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start / end sequence of the block comment
     */
    @Override
    public void addBlockComment(String start, String end) throws IllegalArgumentException {
        addBlockComment(start, end, null);
    }

    /**
     * Registering a block comment. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param start the starting sequence of the block comment
     * @param end the finishing sequence of the block comment
     * @param companion information object associated with this block comment
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start / end sequence of the block comment
     */
    @Override
    public void addBlockComment(String start, String end, Object companion) throws IllegalArgumentException {
        addBlockComment(start, end, companion, getParseFlags());
    }

    /**
     * Registering a block comment. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param start the starting sequence of the block comment
     * @param end the finishing sequence of the block comment
     * @param companion information object associated with this block comment
     * @param flags modification flags
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start / end sequence of the block comment
     */
    @Override
    public void addBlockComment(String start, String end, Object companion, int flags)
            throws IllegalArgumentException {
        addBlockComment(start, end, companion, flags, flags);
    }

    /**
     * Registering a block comment with a set of flags and an associated flag mask.
     *
     * @param start the starting sequence of the block comment
     * @param end the finishing sequence of the block comment
     * @param companion information object associated with this block comment
     * @param flags modification flags
     * @param flagMask flags that have valid values in the parameter <code>flags</code>
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for keyword
     */
    @Override
    public void addBlockComment(String start, String end, Object companion, int flags, int flagMask)
            throws IllegalArgumentException {
        addProperty(new TokenizerProperty(Token.BLOCK_COMMENT, new String[] {
                start,
                end }, companion, flags, flagMask));
    }

    /**
     * Removing a certain block comment. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param start the starting sequence of the block comment
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start sequence of the block comment
     */
    @Override
    public void removeBlockComment(String start) throws IllegalArgumentException {
        TokenizerProperty prop = getBlockComment(start);

        if (prop != null) {
            removeProperty(prop);
        }
    }

    /**
     * Retrieving a certain block comment. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param start the starting sequence of the block comment
     * @return the associated object of the block comment
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for start sequence of the block comment
     */
    @Override
    public Object getBlockCommentCompanion(String start) throws IllegalArgumentException {
        TokenizerProperty prop = getBlockComment(start);

        if (prop != null) {
            return prop.getCompanion();
        } else {
            return null;
        }
    }

    /**
     * Checks if the given block comment is known. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param start the starting sequence of the block comment
     * @return <code>true</code> if the block comment is known, <code>false</code>
     * otherwise
     */
    @Override
    public boolean blockCommentExists(String start) {
        try {
            return getBlockComment(start) != null;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Get the full description of a block comment property. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param start the starting sequence of the block comment
     * @return the full block comment description or <code>null</code>
     * @throws IllegalArgumentException if the given image is empty or null
     */
    @Override
    public TokenizerProperty getBlockComment(String start) throws IllegalArgumentException {
        // check parameter
        checkArgument(start, "Block comment");

        // get the real thing
        synchronized (this) {
            return doGetProperty(Token.BLOCK_COMMENT, start);
        }
    }

    // ---------------------------------------------------------------------------
    // special sequence properties
    //

    /**
     * Registering a special sequence of characters. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param specSeq special sequence to register
     * @throws IllegalArgumentException if the given sequence is empty or null
     * @see #addKeyword
     * @see #setSeparators
     */
    @Override
    public void addSpecialSequence(String specSeq) throws IllegalArgumentException {
        addSpecialSequence(specSeq, null);
    }

    /**
     * Registering a special sequence of characters. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param specSeq special sequence to register
     * @param companion information object associated with this special sequence
     * @throws IllegalArgumentException if the given sequence is empty or null
     * @see #addKeyword
     * @see #setSeparators
     */
    @Override
    public void addSpecialSequence(String specSeq, Object companion) throws IllegalArgumentException {
        addSpecialSequence(specSeq, companion, getParseFlags());
    }

    /**
     * Registering a special sequence of characters. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param specSeq special sequence to register
     * @param companion information object associated with this special sequence
     * @param flags modification flags
     * @throws IllegalArgumentException if the given sequence is empty or null
     * @see #addKeyword
     * @see #setSeparators
     */
    @Override
    public void addSpecialSequence(String specSeq, Object companion, int flags)
            throws IllegalArgumentException {
        addSpecialSequence(specSeq, companion, flags, flags);
    }

    /**
     * Registering a special sequence with a set of flags and an associated flag mask.
     *
     * @param specSeq special sequence to register
     * @param companion information object associated with this special sequence
     * @param flags modification flags
     * @param flagMask flags that have valid values in the parameter <code>flags</code>
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for keyword
     */
    @Override
    public void addSpecialSequence(String specSeq, Object companion, int flags, int flagMask)
            throws IllegalArgumentException {
        addProperty(new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] {
                specSeq }, companion, flags, flagMask));
    }

    /**
     * Deregistering a special sequence from the parser. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param specSeq sequence to remove
     * @throws IllegalArgumentException if the given sequence is empty or null
     */
    @Override
    public void removeSpecialSequence(String specSeq) throws IllegalArgumentException {
        TokenizerProperty prop = getSpecialSequence(specSeq);

        if (prop != null) {
            removeProperty(prop);
        }
    }

    /**
     * Retrieving the companion of the given special sequence. See the method description
     * in {@link TokenizerProperties}.
     *
     * @param specSeq sequence to remove
     * @return the object associated with the special sequence
     * @throws IllegalArgumentException if the given sequence is empty or null
     */
    @Override
    public Object getSpecialSequenceCompanion(String specSeq) throws IllegalArgumentException {
        TokenizerProperty prop = getSpecialSequence(specSeq);

        if (prop != null) {
            return prop.getCompanion();
        } else {
            return null;
        }
    }

    /**
     * Checks if the given special sequence is known to the <code>Tokenizer</code>. See
     * the method description in {@link TokenizerProperties}.
     *
     * @param specSeq sequence to check
     * @return <code>true</code> if the block comment is known, <code>false</code>
     * otherwise
     */
    @Override
    public boolean specialSequenceExists(String specSeq) {
        try {
            return getSpecialSequence(specSeq) != null;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Get the full description of a special sequence property. See the method description
     * in {@link TokenizerProperties}.
     *
     * @param specSeq sequence to search
     * @return the full sequence description or <code>null</code>
     * @throws IllegalArgumentException if the given keyword is empty or null
     */
    @Override
    public TokenizerProperty getSpecialSequence(String specSeq) throws IllegalArgumentException {
        // check parameter
        checkArgument(specSeq, "Special sequence");

        // get the keyword
        synchronized (this) {
            return doGetProperty(Token.SPECIAL_SEQUENCE, specSeq);
        }
    }

    // ---------------------------------------------------------------------------
    // keyword properties
    //

    /**
     * Registering a keyword. See the method description in {@link TokenizerProperties}.
     *
     * @param keyword keyword to register
     * @throws IllegalArgumentException if the given keyword is empty or null
     */
    @Override
    public void addKeyword(String keyword) throws IllegalArgumentException {
        addKeyword(keyword, null);
    }

    /**
     * Registering a keyword. See the method description in {@link TokenizerProperties}.
     *
     * @param keyword keyword to register
     * @param companion information object associated with this keyword
     * @throws IllegalArgumentException if the given keyword is empty or null
     */
    @Override
    public void addKeyword(String keyword, Object companion) throws IllegalArgumentException {
        addKeyword(keyword, companion, getParseFlags());
    }

    /**
     * Registering a keyword. See the method description in {@link TokenizerProperties}.
     *
     * @param keyword keyword to register
     * @param companion information object associated with this keyword
     * @throws IllegalArgumentException if the given keyword is empty or null
     */
    @Override
    public void addKeyword(String keyword, Object companion, int flags) throws IllegalArgumentException {
        addKeyword(keyword, companion, flags, flags);
    }

    /**
     * Registering a keyword with a set of flags and an associated flag mask..
     *
     * @param keyword keyword to register
     * @param companion information object associated with this keyword
     * @param flags modification flags
     * @param flagMask flags that have valid values in the parameter <code>flags</code>
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed for keyword
     */
    @Override
    public void addKeyword(String keyword, Object companion, int flags, int flagMask)
            throws IllegalArgumentException {
        addProperty(new TokenizerProperty(Token.KEYWORD, new String[] {
                keyword }, companion, flags, flagMask));
    }

    /**
     * Deregistering a keyword. See the method description in {@link TokenizerProperties}.
     *
     * @param keyword keyword to remove
     * @throws IllegalArgumentException if the given keyword is empty or null
     */
    @Override
    public void removeKeyword(String keyword) throws IllegalArgumentException {
        TokenizerProperty prop = getKeyword(keyword);

        if (prop != null) {
            removeProperty(prop);
        }
    }

    /**
     * Retrieving the companion of the given keyword. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param keyword keyword thats companion is sought
     * @return the object associated with the keyword
     * @throws IllegalArgumentException if the given keyword is empty or null
     */
    @Override
    public Object getKeywordCompanion(String keyword) throws IllegalArgumentException {
        TokenizerProperty prop = getKeyword(keyword);

        if (prop != null) {
            return prop.getCompanion();
        } else {
            return null;
        }
    }

    /**
     * Checks if the given keyword is known to the <code>Tokenizer</code>. See the method
     * description in {@link TokenizerProperties}.
     *
     * @param keyword keyword to search
     * @return <code>true</code> if the keyword is known, <code>false</code> otherwise
     */
    @Override
    public boolean keywordExists(String keyword) {
        try {
            return getKeyword(keyword) != null;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Get the full description of a keyword property. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param keyword keyword to search
     * @return the full keyword description or <code>null</code>
     * @throws IllegalArgumentException if the given keyword is empty or null
     */
    @Override
    public TokenizerProperty getKeyword(String keyword) throws IllegalArgumentException {
        // check parameter
        checkArgument(keyword, "Keyword");

        // get the keyword
        synchronized (this) {
            return doGetProperty(Token.KEYWORD, keyword);
        }
    }

    // ---------------------------------------------------------------------------
    // pattern properties
    //

    /**
     * Registering a pattern. See the method description in {@link TokenizerProperties}.
     *
     * @param pattern the regular expression to be added
     * @throws IllegalArgumentException when <code>null</code> or an empty pattern is
     * passed
     * @see #removePattern
     * @see #addPattern(String, Object)
     * @see #addPattern(String, Object, int)
     */
    @Override
    public void addPattern(String pattern) throws IllegalArgumentException {
        addPattern(pattern, null);
    }

    /**
     * Registering a pattern with an associated object. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param pattern the regular expression to be added
     * @param companion information object associated with this pattern
     * @throws IllegalArgumentException when <code>null</code> or an empty pattern is
     * passed
     * @see #removePattern
     * @see #addPattern(String)
     * @see #addPattern(String, Object, int)
     */
    @Override
    public void addPattern(String pattern, Object companion) throws IllegalArgumentException {
        addPattern(pattern, companion, getParseFlags());
    }

    /**
     * Registering a pattern with an associated object. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param pattern the regular expression to be added
     * @param companion information object associated with this keyword
     * @param flags modification flags
     * @throws IllegalArgumentException when <code>null</code> or an empty pattern is
     * passed
     * @see #removePattern
     * @see #addPattern(String)
     * @see #addPattern(String, Object)
     */
    @Override
    public void addPattern(String pattern, Object companion, int flags) throws IllegalArgumentException {
        addPattern(pattern, companion, flags, flags);
    }

    /**
     * Registering a pattern with an associated object and explicitely given flags. See
     * the description of the {@link #addPattern(String)} for details on pattern.
     *
     * @param pattern the regular expression to be added
     * @param companion information object associated with this keyword
     * @param flags values for modification flags
     * @param flagMask flags that have valid values in the parameter <code>flags</code>
     * @throws IllegalArgumentException when <code>null</code> or an empty pattern is
     * passed
     */
    @Override
    public void addPattern(String pattern, Object companion, int flags, int flagMask)
            throws IllegalArgumentException {
        addProperty(new TokenizerProperty(Token.PATTERN, new String[] {
                pattern }, companion, flags, flagMask));
    }

    /**
     * Removing a pattern. See the method description in {@link TokenizerProperties}.
     *
     * @param pattern the regular expression to be removed
     * @throws IllegalArgumentException when <code>null</code> or an empty string is
     * passed
     */
    @Override
    public void removePattern(String pattern) throws IllegalArgumentException {
        TokenizerProperty prop = getPattern(pattern);

        if (prop != null) {
            removeProperty(prop);
        }
    }

    /**
     * Retrieving the information associated with a given pattern. See the method
     * description in {@link TokenizerProperties}.
     *
     * @param pattern the regular expression to be removed
     * @return the associated information or <code>null</code>
     * @throws IllegalArgumentException when <code>null</code> or an emtpy pattern is
     * passed
     */
    @Override
    public Object getPatternCompanion(String pattern) throws IllegalArgumentException {
        TokenizerProperty prop = getPattern(pattern);

        if (prop != null) {
            return prop.getCompanion();
        } else {
            return null;
        }
    }

    /**
     * Checks if the given pattern is known to the parser. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param pattern the regular expression to be looked for
     * @return <code>true</code> if the pattern is registered, <code>false</code>
     * otherwise
     */
    @Override
    public boolean patternExists(String pattern) {
        try {
            return getPattern(pattern) != null;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Get the full description of a string property starting with the given prefix. The
     * method returns <code>null</code> if the passed <code>start</code> parameter cannot
     * be mapped to a known string description ({@link #stringExists} would return
     * <code>false</code>).
     *
     * @param pattern the regular expression to be looked for
     * @return the full pattern description or <code>null</code>
     * @throws IllegalArgumentException when <code>null</code> or an emtpy pattern is
     * passed
     */
    @Override
    public TokenizerProperty getPattern(String pattern) throws IllegalArgumentException {
        // check parameter
        checkArgument(pattern, "Pattern");

        // get the pattern
        synchronized (this) {
            return doGetProperty(Token.PATTERN, pattern);
        }
    }

    // ---------------------------------------------------------------------------
    // Common properties
    //

    /**
     * Registering a {@link TokenizerProperty}. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param property property to register
     * @throws IllegalArgumentException when <code>null</code>, an incomplete or otherwise
     * unusable property is passed
     */
    @Override
    public void addProperty(TokenizerProperty property) throws IllegalArgumentException {
        // check the parameter
        checkPropertyArgument(property);

        // check special cases
        String[] images = property.getImages();

        switch (property.getType()) {
        case Token.STRING:
        case Token.BLOCK_COMMENT:
            checkArgument((images.length < 2) ? null : images[1], "End sequence");
            break;
        }

        // add property according to type
        synchronized (this) {
            TokenizerProperty oldProp = doAddProperty(property);

            if (oldProp == null) {
                notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_ADDED, property));
            } else if (!oldProp.equals(property)) {
                notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_MODIFIED, property, oldProp));
            }
        }
    }

    /**
     * Deregistering a {@link TokenizerProperty} from the store. See the method
     * description in {@link TokenizerProperties}.
     *
     * @param property property to register
     * @throws IllegalArgumentException when <code>null</code>, an incomplete or otherwise
     * unusable property is passed
     */
    @Override
    public void removeProperty(TokenizerProperty property) throws IllegalArgumentException {
        // check the parameter
        checkPropertyArgument(property);

        // removing property according to type
        synchronized (this) {
            TokenizerProperty removed = doRemoveProperty(property);

            if (removed != null) {
                notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, removed));
            }
        }
    }

    /**
     * Checks if the given {@link TokenizerProperty} is known. See the method description
     * in {@link TokenizerProperties}.
     *
     * @param property the property to search
     * @return <code>true</code> if the property is known, <code>false</code> otherwise
     */
    @Override
    public boolean propertyExists(TokenizerProperty property) {
        try {
            checkPropertyArgument(property);
            synchronized (this) {
                return doGetProperty(property.getType(), property.getImages()[0]) != null;
            }
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    // ---------------------------------------------------------------------------
    // Methods of the DataMapper interface
    //

    /**
     * Registering a new {@link TokenizerPropertyListener}. See the method description in
     * {@link TokenizerProperties}.
     *
     * @param listener the new {@link TokenizerPropertyListener}
     * @see #removeTokenizerPropertyListener
     */
    @Override
    public void addTokenizerPropertyListener(TokenizerPropertyListener listener) {
        if (listener != null) {
            synchronized (_listeners) {
                WeakReference ref = new WeakReference(listener);
                _listeners.add(ref);
            }
        }
    }

    /**
     * Removing a listener from the list of registered {@link TokenizerPropertyListener}
     * instances. See the method description in {@link TokenizerProperties}.
     *
     * @param listener the {@link TokenizerPropertyListener} to deregister
     * @see #addTokenizerPropertyListener
     */
    @Override
    public void removeTokenizerPropertyListener(TokenizerPropertyListener listener) {
        if (listener != null) {
            synchronized (_listeners) {
                Iterator iter = _listeners.iterator();

                while (iter.hasNext()) {
                    WeakReference ref = (WeakReference) iter.next();
                    Object elem = ref.get();

                    if (elem == null) {
                        // implicit cleanup
                        iter.remove();
                    } else if (listener.equals(elem)) {
                        // found the real one
                        iter.remove();
                        break;
                    }
                }
            }
        }
    }

    // ---------------------------------------------------------------------------
    // Implementation
    //

    /**
     * Puts or removes all characters in the given set into or from a given
     * {@link java.util.Map}.
     *
     * @param map put the characters of the set into this map
     * @param set the character set to map
     * @param removeIt if <code>true</code> remove the characters of the set, otherwise
     * add them
     * @throws IllegalArgumentException if the set contains incomplete ranges
     */
    private void mapCharacterSet(Map map, String set, boolean removeIt) throws IllegalArgumentException {
        for (int index = 0; index < set.length(); ++index) {
            char cc = set.charAt(index);

            switch (cc) {
            case '-':
                try {
                    char start = set.charAt(index - 1);
                    char end = set.charAt(index + 1);
                    if (end == '\\') {
                        end = set.charAt(index + 2);
                        index += 2;
                    } else {
                        index++;
                    }
                    for (char rangeCC = start; rangeCC <= end; ++rangeCC) {
                        if (removeIt) {
                            map.remove(new Character(rangeCC));
                        } else {
                            map.put(new Character(rangeCC), null);
                        }
                    }
                } catch (Exception ex) {
                    throw new IllegalArgumentException(set);
                }
                break;

            case '\\':
                index++;
                cc = set.charAt(index);
                /* no break; */
            default:
                if (index + 1 >= set.length() || set.charAt(index + 1) != '-') {
                    if (removeIt) {
                        map.remove(new Character(cc));
                    } else {
                        map.put(new Character(cc), null);
                    }
                }
            }
        }
    }

    /**
     * Build the escape sequence for a character in a set if nessecary.
     *
     * @param cc the character to test
     * @return <code>true</code> if the given character must be escaped,
     * <code>false</code> otherwise
     */
    private boolean escapeChar(char cc) {
        switch (cc) {
        case '\\':
        case '-':
            return true;
        default:
            return false;
        }
    }

    /**
     * Add a character range to a string. The method checks if the given start and end
     * characters actually form a range.
     *
     * @param buffer add range to this buffer
     * @param rangeStart first character in range
     * @param rangeEnd last character in range
     */
    private void addRange(StringBuffer buffer, char rangeStart, char rangeEnd) {
        if (escapeChar(rangeStart)) {
            buffer.append('\\');
        }
        buffer.append(rangeStart);
        if (rangeStart < rangeEnd - 1) {
            buffer.append('-');
        }
        if (rangeStart != rangeEnd) {
            if (escapeChar(rangeEnd)) {
                buffer.append('\\');
            }
            buffer.append(rangeEnd);
        }
    }

    /**
     * Merges tho character set strings that may contain characters ranges like "a-z". The
     * result is united character set of both parameters.
     *
     * @param set1 first character set
     * @param set2 second character set
     * @param removeSet2 should the second set ber removed rather than added?
     * @return the characters of the first set + the characters of the second set if not
     * already present in the first set.
     * @throws IllegalArgumentException if the set contains incomplete ranges
     */
    private String mergeSet(String set1, String set2, boolean removeSet2) throws IllegalArgumentException {
        // merge the sets into a map
        TreeMap map = new TreeMap();

        mapCharacterSet(map, set1, false);
        mapCharacterSet(map, set2, removeSet2);

        // iterate through the map in a predefined order
        StringBuffer buffer = new StringBuffer(set1.length() + set2.length());

        if (map.size() > 0) {
            Iterator<?> iter = map.keySet().iterator();
            char rangeStart = ((Character) map.firstKey()).charValue();
            char rangeEnd = rangeStart;

            while (iter.hasNext()) {
                char cc = ((Character) iter.next()).charValue();

                if (cc > rangeEnd + 1) {
                    addRange(buffer, rangeStart, rangeEnd);
                    rangeStart = rangeEnd = cc;
                } else {
                    rangeEnd = cc;
                }
            }
            addRange(buffer, rangeStart, rangeEnd);
        }

        // ready
        return buffer.toString();
    }

    /**
     * Normalize flags. This is necessary for the case-sensitivity flags
     * {@link Flags#F_CASE} and {@link Flags#F_NO_CASE}. If neither <code>F_CASE</code>
     * nor <code>F_NO_CASE</code> is set, <code>F_CASE</code> is assumed. If both flags
     * are set, <code>F_CASE</code> takes preceedence.
     *
     * @param flags not yet normalized flags
     * @param flagMask which flags should be handled
     * @return the normalized flags
     */
    private int normalizeFlags(int flags, int flagMask) {
        if ((flagMask & (Flags.F_CASE | Flags.F_NO_CASE)) == (Flags.F_CASE | Flags.F_NO_CASE)) {
            if ((flags & (Flags.F_CASE | Flags.F_NO_CASE)) == 0) {
                // none set: F_CASE is the default
                flags |= Flags.F_CASE;
            } else if ((flags & Flags.F_CASE) != 0) {
                // perhaps both set: F_CASE weights more
                flags &= ~Flags.F_NO_CASE;
            }
        }
        return flags;
    }

    /**
     * Checking a string parameter on null or emptiness. The method encapsulates commonly
     * used code (see {@link #addKeyword} or {@link #addSpecialSequence} for example).
     *
     * @param arg the parameter to check
     * @param name a name for the <code>arg</code> parameter
     * @throws IllegalArgumentException if the given <code>arg</code> is null or empty
     */
    protected void checkArgument(String arg, String name) throws IllegalArgumentException {
        if (arg == null) {
            throw new IllegalArgumentException(name + " is null.");
        } else if (arg.length() <= 0) {
            throw new IllegalArgumentException(name + " is empty.");
        }
    }

    /**
     * Checking a {@link TokenizerProperty} parameter on null or missing nessecary values.
     * The method encapsulates commonly used code (see {@link #addProperty} and
     * {@link #removeProperty}).
     *
     * @param property the parameter to check
     * @throws IllegalArgumentException if the given <code>arg</code> is null or empty
     */
    protected void checkPropertyArgument(TokenizerProperty property) throws IllegalArgumentException {
        // check the parameter
        if (property == null) {
            throw new IllegalArgumentException("Property is null.");
        } else if (property.getImages() == null) {
            throw new IllegalArgumentException("No image(s) given in property.");
        } else if (property.getImages()[0] == null) {
            throw new IllegalArgumentException("No (leading) image given in property.");
        }
    }

    /**
     * The method fires the nessecary events when whitespace or separator sets change.
     *
     * @param type token type
     * @param newValue the newly set value
     * @param oldValue the old value with case-sensitive handling
     */
    protected void handleEvent(int type, String newValue, String oldValue) {
        if (newValue != null && newValue.length() > 0) {
            if (oldValue == null) {
                notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_ADDED, new TokenizerProperty(type, new String[] {
                        newValue })));
            } else if (!oldValue.equals(newValue)) {
                notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_MODIFIED, new TokenizerProperty(type, new String[] {
                        newValue }), new TokenizerProperty(type, new String[] { oldValue })));
            }
        } else if (oldValue != null && oldValue.length() > 0) {
            notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, new TokenizerProperty(type, new String[] {
                    oldValue })));
        }
    }

    /**
     * Notifying the registered listeners about a change in the properties. Listeners are
     * called in the order of their registration (see
     * {@link #addTokenizerPropertyListener}).
     *
     * @param event the {@link TokenizerPropertyEvent} to communicate to the listeners
     */
    protected void notifyListeners(TokenizerPropertyEvent event) {
        Iterator iter = _listeners.iterator();

        while (iter.hasNext()) {
            WeakReference ref = (WeakReference) iter.next();
            TokenizerPropertyListener listener = (TokenizerPropertyListener) ref.get();

            if (listener == null) {
                // implicit cleanup of unused listeners
                iter.remove();
            } else {
                // call listener
                listener.propertyChanged(event);
            }
        }
    }

    // ---------------------------------------------------------------------------
    // Members
    //

    /**
     * overall tokenizer flags.
     */
    protected int _flags = 0;

    /**
     * List of {@link TokenizerPropertyListener} instances.
     */
    private LinkedList _listeners = new LinkedList();
}
