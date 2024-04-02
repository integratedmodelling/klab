/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.utils;

/*
 * MiscUtilities.java - Various miscallaneous utility functions
 * :tabSize=8:indentSize=8:noTabs=false: :folding=explicit:collapseFolds=1:
 *
 * Copyright (C) 1999, 2005 Slava Pestov Portions copyright (C) 2000 Richard S. Hall Portions
 * copyright (C) 2001 Dirk Moebius Portions copyright (C) 2017 Ferdinando Villa
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import java.util.UUID;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

// TODO: Auto-generated Javadoc
/**
 * Path name manipulation, string manipulation, and more.
 * <p>
 *
 * The most frequently used members of this class are:
 * <p>
 *
 * For example, you might call:
 * <p>
 *
 * <code>Arrays.sort(myListOfStrings,
 *     new MiscUtilities.StringICaseCompare());</code>
 *
 * @author Slava Pestov
 * @author John Gellene (API documentation)
 * @version $Id: MiscUtilities.java,v 1.8 2006/11/23 02:47:31 fvilla Exp $
 */
public class MiscUtilities {

    /**
     * Count how many bits are set in a long. Used to quickly check the observables from
     * compositions.
     *
     * @param number the number
     * @return the int
     */
    public static int countSetBits(long number) {
        int count = 0;
        while(number > 0) {
            ++count;
            number &= number - 1;
        }
        return count;
    }

    /**
     * Flatten a parameter list, potentially containing collections, into a single array.
     *
     * @param objects the objects
     * @return a flat array with all collections expanded in it.
     */
    public static Object[] flattenParameterList(Object... objects) {

        List<Object> ret = new ArrayList<>();

        for (Object o : objects) {
            if (o instanceof Collection) {
                for (Object obj : (Collection<?>) o) {
                    ret.add(obj);
                }
            } else {
                ret.add(o);
            }
        }

        return ret.toArray();
    }

    /**
     * Return a suffix representing the current date (to the second) suitable to being used to
     * append to a filename to make it date-specific.
     *
     * @return a suffix representing the date without any filename-offending characters.
     */
    public static String getDateSuffix() {

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        return formatter.format(today);
    }

    /**
     * Compute name for sidecar file of another file.
     *
     * @param originalFile the original file
     * @param suffixAndExtension [suffix and] extension that we want for the original filename.
     *        Include dots.
     * @return the sidecar file
     */
    public static File getSidecarFile(File originalFile, String suffixAndExtension) {
        String baseName = MiscUtilities.getFilePath(originalFile.toString()) + File.separator
                + MiscUtilities.getFileBaseName(originalFile) + suffixAndExtension;
        return new File(baseName);
    }

    /**
     * Read a properties file into a properties object without making life painful. Silently returns
     * an empty property object if file isn't readable or errors happen.
     *
     * @param pfile the pfile
     * @return the properties read from the file.
     */
    public static Properties readProperties(File pfile) {
        Properties ret = new Properties();
        if (pfile == null || !pfile.exists() || !pfile.isFile() || !pfile.canRead()) {
            return ret;
        }
        try (InputStream finp = new FileInputStream(pfile)) {
            ret.load(finp);
        } catch (Exception e) {
            // just return the empty object
        }
        return ret;
    }

    /**
     * This encoding is not supported by Java, yet it is useful. A UTF-8 file that begins with
     * 0xEFBBBF.
     */
    public static final String UTF_8_Y = "UTF-8Y";

    /**
     * Extract the file extension from a file name.
     *
     * @param s the s
     * @return the file extension
     */
    public static String getFileExtension(String s) {

        String ret = "";

        int sl = s.lastIndexOf(".");
        if (sl > 0)
            ret = s.substring(sl + 1);

        return ret;
    }

    /**
     * Return file path without extension if any.
     *
     * @param s the s
     * @return s with no .xxx at end.
     */
    public static String getFileBasePath(String s) {

        String ret = s;

        int sl = s.lastIndexOf(".");
        if (sl > 0)
            ret = s.substring(0, sl);

        return ret;
    }

    /**
     * Return file path without extension if any.
     *
     * @param s the s
     * @return path for passed file name (directory it's in).
     */
    public static String getFilePath(String s) {

        String ret = s;

        int sl = s.lastIndexOf("/");
        if (sl < 0)
            sl = s.lastIndexOf(File.separator);
        if (sl > 0)
            ret = s.substring(0, sl);

        return ret;
    }

    /**
     * Return file name with no path or extension.
     *
     * @param s the s
     * @return the simple name of the file without extension or path.
     */
    public static String getFileBaseName(String s) {

        String ret = s;

        int sl = ret.lastIndexOf(File.separator);
        if (sl > 0)
            ret = ret.substring(sl + 1);
        sl = ret.lastIndexOf(".");
        if (sl > 0)
            ret = ret.substring(0, sl);

        return ret;
    }

    /**
     * Return URL base name with no path or extension. Just like getFileBaseName but uses / instead
     * of system separator.
     *
     * @param s the s
     * @return extracted name from URL
     */
    public static String getURLBaseName(String s) {

        /* just in case */
        String ret = s.replace('\\', '/');

        if (ret.contains("?")) {
            ret = ret.substring(0, ret.indexOf('?'));
        }

        if (ret.contains("#")) {
            ret = ret.substring(0, ret.indexOf('#'));
        }

        int sl = ret.lastIndexOf(".");
        if (sl > 0)
            ret = ret.substring(0, sl);
        sl = ret.lastIndexOf("/");
        if (sl >= 0)
            ret = ret.substring(sl + 1);

        return ret;
    }

    /**
     * Return file name with no path but with extension.
     *
     * @param s the s
     * @return name of file without path, preserving any extension.
     */
    public static String getFileName(String s) {

        String ret = s;

        int sl = ret.lastIndexOf(File.separator);
        if (sl < 0)
            sl = ret.lastIndexOf('/');
        if (sl > 0)
            ret = ret.substring(sl + 1);

        return ret;
    }

    /**
     * Writes InputStream to a given <code>fileName<code>. And, if directory for this file does not
     * exists, if createDir is true, creates it, otherwice throws OMDIOexception.
     *
     * @param fileName - filename save to.
     * @param iStream - InputStream with data to read from.
     * @param createDir (false by default)
     * @return number of bytes written
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static int writeToFile(String fileName, InputStream iStream, boolean createDir) throws IOException {
        String me = "FileUtils.WriteToFile";
        if (fileName == null) {
            throw new IOException(me + ": filename is null");
        }

        File theFile = new File(fileName);
        int bytes = 0;

        // Check if a file exists.
        if (theFile.exists()) {
            String msg = theFile.isDirectory() ? "directory" : (!theFile.canWrite() ? "not writable" : null);
            if (msg != null) {
                throw new IOException(me + ": file '" + fileName + "' is " + msg);
            }
        }

        // Create directory for the file, if requested.
        if (createDir && theFile.getParentFile() != null) {
            theFile.getParentFile().mkdirs();
        }

        // Save InputStream to the file.
        BufferedOutputStream fOut = null;
        try {
            fOut = new BufferedOutputStream(new FileOutputStream(theFile));
            byte[] buffer = new byte[32 * 1024];
            int bytesRead = 0;
            if (iStream != null) {
                while((bytesRead = iStream.read(buffer)) != -1) {
                    fOut.write(buffer, 0, bytesRead);
                    bytes += bytesRead;
                }
            }
        } catch (Exception e) {
            throw new IOException(me + " failed, got: " + e.toString());
        } finally {
            if (iStream != null)
                iStream.close();
            fOut.close();
        }

        return bytes;
    }

    /**
     * Read the last N lines of file into string and return it. Emulates Unix's tail command.
     * 
     * Unsophisticated about EOL encodings - I'm pretty sure it will create artificial empty lines
     * on Win. On the other hand, all the uses I have for it are a joke on anything but unix.
     *
     * @param fileName the file name
     * @param n the n
     * @return last requested lines from file.
     * @throws KlabIOException the klab IO exception
     */
    public static String tail(String fileName, int n) throws KlabIOException {
        java.io.RandomAccessFile fileHandler = null;
        try {
            java.io.File file = new java.io.File(fileName);
            fileHandler = new java.io.RandomAccessFile(file, "r");
            long fileLength = file.length() - 1;
            StringBuilder sb = new StringBuilder();

            // backward line counter
            int l = 0;

            for (long filePointer = fileLength; filePointer != -1; filePointer--) {

                boolean lineread = false;

                fileHandler.seek(filePointer);
                int readByte = fileHandler.readByte();

                if (readByte == 0xA) {
                    if (filePointer == fileLength) {
                        continue;
                    } else {
                        l++;
                        lineread = true;
                    }
                } else if (readByte == 0xD) {
                    if (filePointer == fileLength - 1) {
                        continue;
                    } else {
                        l++;
                        lineread = true;
                    }
                }

                if (lineread && l == n)
                    break;

                sb.append((char) readByte);
            }

            String lastLine = sb.reverse().toString();
            return lastLine;
        } catch (Exception e) {
            throw new KlabIOException(e);
        } finally {
            if (fileHandler != null) {
                try {
                    fileHandler.close();
                } catch (IOException e) {
                    throw new KlabIOException(e);
                }
            }
        }
    }

    // /**
    // * Closes InputStream and/or OutputStream.
    // * It makes sure that both streams tried to be closed,
    // * even first throws an exception.
    // *
    // * @throw IOException if stream (is not null and) cannot be closed.
    // *
    // */
    // protected static void close(InputStream iStream, OutputStream oStream)
    // throws IOException {
    // try {
    // if (iStream != null) {
    // iStream.close();
    // }
    // } finally {
    // if (oStream != null) {
    // oStream.close();
    // }
    // }
    // }

    // {{{ getProtocolOfURL() method
    /**
     * Returns the protocol specified by a URL.
     *
     * @param url The URL
     * @return the protocol of URL
     * @since jEdit 2.6pre5
     */
    public static String getProtocolOfURL(String url) {
        return url.substring(0, url.indexOf(':'));
    } // }}}

    // // {{{ closeQuietly() method
    // /**
    // * Method that will close an {@link InputStream} ignoring it if it is null
    // and ignoring exceptions.
    // *
    // * @param in the InputStream to close.
    // * @since jEdit 4.3pre3
    // */
    // public static void closeQuietly(InputStream in) {
    // if (in != null) {
    // try {
    // in.close();
    // } catch (IOException e) {
    // // ignore
    // }
    // }
    // } // }}}

    /**
     * Convert stream to string.
     *
     * @param is the is
     * @return string with stream contents.
     * @throws KlabException the klab exception
     */
    public static String convertStreamToString(InputStream is) throws KlabException {

        /*
         * To convert the InputStream to String we use the Reader.read(char[] buffer) method. We
         * iterate until the Reader return -1 which means there's no more data to read. We use the
         * StringWriter class to produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                try {
                    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    int n;
                    while((n = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, n);
                    }
                } finally {
                    is.close();
                }
            } catch (Exception e) {
                throw new KlabIOException(e);
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    // public static int saveStreamToFile(InputStream content, File fname)
    // throws ThinklabException {
    // try {
    // return writeToFile(fname.toString(), content, false);
    // } catch (IOException e) {
    // throw new ThinklabIOException(e);
    // }
    // }

    // {{{ copyStream() method
    /**
     * Method that will close an {@link java.io.OutputStream} ignoring it if it is null and ignoring
     * exceptions.
     *
     * @param out the OutputStream to close.
     * @since jEdit 4.3pre3
     */
    public static void closeQuietly(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                // ignore
            }
        }
    } // }}}

    // {{{ fileToClass() method
    /**
     * Converts a file name to a class name. All slash characters are replaced with periods and the
     * trailing '.class' is removed.
     *
     * @param name The file name
     * @return the string
     */
    public static String fileToClass(String name) {
        char[] clsName = name.toCharArray();
        for (int i = clsName.length - 6; i >= 0; i--)
            if (clsName[i] == '/')
                clsName[i] = '.';
        return new String(clsName, 0, clsName.length - 6);
    } // }}}

    /**
     * Gets the name from URL.
     *
     * @param uu the uu
     * @return the name from URL
     */
    public static String getNameFromURL(String uu) {
        int sl = uu.lastIndexOf('/');
        String name = sl == -1 ? uu : uu.substring(sl + 1);
        int dt = name.lastIndexOf('.');
        return dt == -1 ? name : name.substring(0, dt);
    }

    // {{{ classToFile() method
    /**
     * Converts a class name to a file name. All periods are replaced with slashes and the '.class'
     * extension is added.
     *
     * @param name The class name
     * @return the string
     */
    public static String classToFile(String name) {
        return name.replace('.', '/').concat(".class");
    } // }}}

    // //{{{ pathsEqual() method
    // /**
    // * @param p1 A path name
    // * @param p2 A path name
    // * @return True if both paths are equal, ignoring trailing slashes, as
    // * well as case insensitivity on Windows.
    // * @since jEdit 4.3pre2
    // */
    // public static boolean pathsEqual(String p1, String p2)
    // {
    // VFS v1 = VFSManager.getVFSForPath(p1);
    // VFS v2 = VFSManager.getVFSForPath(p2);
    //
    // if(v1 != v2)
    // return false;
    //
    // if(p1.endsWith("/") || p1.endsWith(File.separator))
    // p1 = p1.substring(0,p1.length() - 1);
    //
    // if(p2.endsWith("/") || p2.endsWith(File.separator))
    // p2 = p2.substring(0,p2.length() - 1);
    //
    // if((v1.getCapabilities() & VFS.CASE_INSENSITIVE_CAP) != 0)
    // return p1.equalsIgnoreCase(p2);
    // else
    // return p1.equals(p2);
    // } //}}}

    // }}}

    // {{{ Text methods

    // {{{ getLeadingWhiteSpace() method
    /**
     * Returns the number of leading white space characters in the specified string.
     *
     * @param str The string
     * @return the leading white space
     */
    public static int getLeadingWhiteSpace(String str) {
        int whitespace = 0;
        loop: for (; whitespace < str.length();) {
            switch(str.charAt(whitespace)) {
            case ' ':
            case '\t':
                whitespace++;
                break;
            default:
                break loop;
            }
        }
        return whitespace;
    }

    /**
     * Returns the number of trailing whitespace characters in the specified string.
     *
     * @param str The string
     * @return the trailing white space
     * @since jEdit 2.5pre5
     */
    public static int getTrailingWhiteSpace(String str) {
        int whitespace = 0;
        loop: for (int i = str.length() - 1; i >= 0; i--) {
            switch(str.charAt(i)) {
            case ' ':
            case '\t':
                whitespace++;
                break;
            default:
                break loop;
            }
        }
        return whitespace;
    }

    /**
     * Returns the width of the leading white space in the specified string.
     *
     * @param str The string
     * @param tabSize The tab size
     * @return the leading white space width
     */
    public static int getLeadingWhiteSpaceWidth(String str, int tabSize) {
        int whitespace = 0;
        loop: for (int i = 0; i < str.length(); i++) {
            switch(str.charAt(i)) {
            case ' ':
                whitespace++;
                break;
            case '\t':
                whitespace += (tabSize - whitespace % tabSize);
                break;
            default:
                break loop;
            }
        }
        return whitespace;
    }

    /**
     * Creates a string of white space with the specified length.
     * <p>
     *
     * To get a whitespace string tuned to the current buffer's settings, call this method as
     * follows:
     *
     * <pre>
     * myWhitespace = MiscUtilities.createWhiteSpace(myLength, (buffer.getBooleanProperty("noTabs") ? 0 : buffer.getTabSize()));
     * </pre>
     *
     * @param len The length
     * @param tabSize The tab size, or 0 if tabs are not to be used
     * @return the string
     */
    public static String createWhiteSpace(int len, int tabSize) {
        return createWhiteSpace(len, tabSize, 0);
    }

    /**
     * Creates a string of white space with the specified length.
     * <p>
     *
     * To get a whitespace string tuned to the current buffer's settings, call this method as
     * follows:
     *
     * <pre>
     * myWhitespace = MiscUtilities.createWhiteSpace(myLength, (buffer.getBooleanProperty("noTabs") ? 0 : buffer.getTabSize()));
     * </pre>
     *
     * @param len The length
     * @param tabSize The tab size, or 0 if tabs are not to be used
     * @param start The start offset, for tab alignment
     * @return the string
     * @since jEdit 4.2pre1
     */
    public static String createWhiteSpace(int len, int tabSize, int start) {
        StringBuffer buf = new StringBuffer();
        if (tabSize == 0) {
            while(len-- > 0)
                buf.append(' ');
        } else if (len == 1)
            buf.append(' ');
        else {
            int count = (len + start % tabSize) / tabSize;
            if (count != 0)
                len += start;
            while(count-- > 0)
                buf.append('\t');
            count = len % tabSize;
            while(count-- > 0)
                buf.append(' ');
        }
        return buf.toString();
    } // }}}

    // {{{ globToRE() method
    /**
     * Converts a Unix-style glob to a regular expression.
     * <p>
     *
     * ? becomes ., * becomes .*, {aa,bb} becomes (aa|bb).
     *
     * @param glob The glob pattern
     * @return the string
     */
    public static String globToRE(String glob) {
        final Object NEG = new Object();
        final Object GROUP = new Object();
        Stack<Object> state = new Stack<Object>();

        StringBuffer buf = new StringBuffer();
        boolean backslash = false;

        for (int i = 0; i < glob.length(); i++) {
            char c = glob.charAt(i);
            if (backslash) {
                buf.append('\\');
                buf.append(c);
                backslash = false;
                continue;
            }

            switch(c) {
            case '\\':
                backslash = true;
                break;
            case '?':
                buf.append('.');
                break;
            case '.':
            case '+':
            case '(':
            case ')':
                buf.append('\\');
                buf.append(c);
                break;
            case '*':
                buf.append(".*");
                break;
            case '|':
                if (backslash)
                    buf.append("\\|");
                else
                    buf.append('|');
                break;
            case '{':
                buf.append('(');
                if (i + 1 != glob.length() && glob.charAt(i + 1) == '!') {
                    buf.append('?');
                    state.push(NEG);
                } else
                    state.push(GROUP);
                break;
            case ',':
                if (!state.isEmpty() && state.peek() == GROUP)
                    buf.append('|');
                else
                    buf.append(',');
                break;
            case '}':
                if (!state.isEmpty()) {
                    buf.append(")");
                    if (state.pop() == NEG)
                        buf.append(".*");
                } else
                    buf.append('}');
                break;
            default:
                buf.append(c);
            }
        }

        return buf.toString();
    }

    /**
     * Converts "\n" and "\t" escapes in the specified string to newlines and tabs.
     *
     * @param str The string
     * @return the string
     * @since jEdit 2.3pre1
     */
    public static String escapesToChars(String str) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch(c) {
            case '\\':
                if (i == str.length() - 1) {
                    buf.append('\\');
                    break;
                }
                c = str.charAt(++i);
                switch(c) {
                case 'n':
                    buf.append('\n');
                    break;
                case 't':
                    buf.append('\t');
                    break;
                default:
                    buf.append(c);
                    break;
                }
                break;
            default:
                buf.append(c);
            }
        }
        return buf.toString();
    }

    /**
     * Escapes newlines, tabs, backslashes, and quotes in the specified string.
     *
     * @param str The string
     * @return the string
     * @since jEdit 2.3pre1
     */
    public static String charsToEscapes(String str) {
        return charsToEscapes(str, "\n\t\\\"'");
    }

    /**
     * Escapes the specified characters in the specified string.
     *
     * @param str The string
     * @param toEscape Any characters that require escaping
     * @return the string
     * @since jEdit 4.1pre3
     */
    public static String charsToEscapes(String str, String toEscape) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (toEscape.indexOf(c) != -1) {
                if (c == '\n')
                    buf.append("\\n");
                else if (c == '\t')
                    buf.append("\\t");
                else {
                    buf.append('\\');
                    buf.append(c);
                }
            } else
                buf.append(c);
        }
        return buf.toString();
    }

    /**
     * Compares two strings.
     * <p>
     *
     * Unlike <code>String.compareTo()</code>, this method correctly recognizes and handles embedded
     * numbers. For example, it places "My file 2" before "My file 10".
     * <p>
     *
     * @param str1 The first string
     * @param str2 The second string
     * @param ignoreCase If true, case will be ignored
     * @return negative If str1 &lt; str2, 0 if both are the same, positive if str1 &gt; str2
     * @since jEdit 4.0pre1
     */
    public static int compareStrings(String str1, String str2, boolean ignoreCase) {
        char[] char1 = str1.toCharArray();
        char[] char2 = str2.toCharArray();

        int len = Math.min(char1.length, char2.length);

        for (int i = 0, j = 0; i < len && j < len; i++, j++) {
            char ch1 = char1[i];
            char ch2 = char2[j];
            if (Character.isDigit(ch1) && Character.isDigit(ch2) && ch1 != '0' && ch2 != '0') {
                int _i = i + 1;
                int _j = j + 1;

                for (; _i < char1.length; _i++) {
                    if (!Character.isDigit(char1[_i])) {
                        // _i--;
                        break;
                    }
                }

                for (; _j < char2.length; _j++) {
                    if (!Character.isDigit(char2[_j])) {
                        // _j--;
                        break;
                    }
                }

                int len1 = _i - i;
                int len2 = _j - j;
                if (len1 > len2)
                    return 1;
                else if (len1 < len2)
                    return -1;
                else {
                    for (int k = 0; k < len1; k++) {
                        ch1 = char1[i + k];
                        ch2 = char2[j + k];
                        if (ch1 != ch2)
                            return ch1 - ch2;
                    }
                }

                i = _i - 1;
                j = _j - 1;
            } else {
                if (ignoreCase) {
                    ch1 = Character.toLowerCase(ch1);
                    ch2 = Character.toLowerCase(ch2);
                }

                if (ch1 != ch2)
                    return ch1 - ch2;
            }
        }

        return char1.length - char2.length;
    }

    // {{{ objectsEqual() method
    /**
     * Returns if two strings are equal. This correctly handles null pointers, as opposed to calling
     * <code>o1.equals(o2)</code>.
     *
     * @param o1 the o 1
     * @param o2 the o 2
     * @return a boolean.
     * @since jEdit 4.2pre1
     */
    public static boolean objectsEqual(Object o1, Object o2) {
        if (o1 == null) {
            if (o2 == null)
                return true;
            else
                return false;
        } else if (o2 == null)
            return false;
        else
            return o1.equals(o2);
    }

    /**
     * Converts &lt;, &gt;, &amp; in the string to their HTML entity equivalents.
     *
     * @param str The string
     * @return the string
     * @since jEdit 4.2pre1
     */
    public static String charsToEntities(String str) {
        StringBuffer buf = new StringBuffer(str.length());
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            switch(ch) {
            case '<':
                buf.append("&lt;");
                break;
            case '>':
                buf.append("&gt;");
                break;
            case '&':
                buf.append("&amp;");
                break;
            default:
                buf.append(ch);
                break;
            }
        }
        return buf.toString();
    }

    /** The Constant KB_FORMAT. */
    public static final DecimalFormat KB_FORMAT = new DecimalFormat("#.# KB");

    /** The Constant MB_FORMAT. */
    public static final DecimalFormat MB_FORMAT = new DecimalFormat("#.# MB");

    /**
     * Formats the given file size into a nice string (123 bytes, 10.6 KB, 1.2 MB).
     *
     * @param length The size
     * @return the string
     * @since jEdit 4.2pre1
     */
    public static String formatFileSize(long length) {
        if (length < 1024)
            return length + " bytes";
        else if (length < 1024 * 1024)
            return KB_FORMAT.format((double) length / 1024);
        else
            return MB_FORMAT.format((double) length / 1024 / 1024);
    }

    /**
     * Returns the longest common prefix in the given set of strings.
     *
     * @param str The strings
     * @param ignoreCase If true, case insensitive
     * @return the longest prefix
     * @since jEdit 4.2pre2
     */
    public static String getLongestPrefix(List<?> str, boolean ignoreCase) {
        if (str.size() == 0)
            return "";

        int prefixLength = 0;

        loop: for (;;) {
            String s = str.get(0).toString();
            if (prefixLength >= s.length())
                break loop;
            char ch = s.charAt(prefixLength);
            for (int i = 1; i < str.size(); i++) {
                s = str.get(i).toString();
                if (prefixLength >= s.length())
                    break loop;
                if (!compareChars(s.charAt(prefixLength), ch, ignoreCase))
                    break loop;
            }
            prefixLength++;
        }

        return str.get(0).toString().substring(0, prefixLength);
    }

    /**
     * Returns the longest common prefix in the given set of strings.
     *
     * @param str The strings
     * @param ignoreCase If true, case insensitive
     * @return the longest prefix
     * @since jEdit 4.2pre2
     */
    public static String getLongestPrefix(String[] str, boolean ignoreCase) {
        return getLongestPrefix((Object[]) str, ignoreCase);
    }

    /**
     * Returns the longest common prefix in the given set of strings.
     *
     * @param str The strings (calls <code>toString()</code> on each object)
     * @param ignoreCase If true, case insensitive
     * @return the longest prefix
     * @since jEdit 4.2pre6
     */
    public static String getLongestPrefix(Object[] str, boolean ignoreCase) {
        if (str.length == 0)
            return "";

        int prefixLength = 0;

        String first = str[0].toString();

        loop: for (;;) {
            if (prefixLength >= first.length())
                break loop;
            char ch = first.charAt(prefixLength);
            for (int i = 1; i < str.length; i++) {
                String s = str[i].toString();
                if (prefixLength >= s.length())
                    break loop;
                if (!compareChars(s.charAt(prefixLength), ch, ignoreCase))
                    break loop;
            }
            prefixLength++;
        }

        return first.substring(0, prefixLength);
    }

    /**
     * Converts an internal version number (build) into a `human-readable' form.
     *
     * @param build The build
     * @return the string
     */
    public static String buildToVersion(String build) {
        if (build.length() != 11)
            return "<unknown version: " + build + ">";
        // First 2 chars are the major version number
        int major = Integer.parseInt(build.substring(0, 2));
        // Second 2 are the minor number
        int minor = Integer.parseInt(build.substring(3, 5));
        // Then the pre-release status
        int beta = Integer.parseInt(build.substring(6, 8));
        // Finally the bug fix release
        int bugfix = Integer.parseInt(build.substring(9, 11));

        return major + "." + minor + (beta != 99 ? "pre" + beta : (bugfix != 0 ? "." + bugfix : "final"));
    }

    /**
     * Parse a Unix-style permission string (rwxrwxrwx).
     *
     * @param s The string (must be 9 characters long).
     * @return the int
     * @since jEdit 4.1pre8
     */
    public static int parsePermissions(String s) {
        int permissions = 0;

        if (s.length() == 9) {
            if (s.charAt(0) == 'r')
                permissions += 0400;
            if (s.charAt(1) == 'w')
                permissions += 0200;
            if (s.charAt(2) == 'x')
                permissions += 0100;
            else if (s.charAt(2) == 's')
                permissions += 04100;
            else if (s.charAt(2) == 'S')
                permissions += 04000;
            if (s.charAt(3) == 'r')
                permissions += 040;
            if (s.charAt(4) == 'w')
                permissions += 020;
            if (s.charAt(5) == 'x')
                permissions += 010;
            else if (s.charAt(5) == 's')
                permissions += 02010;
            else if (s.charAt(5) == 'S')
                permissions += 02000;
            if (s.charAt(6) == 'r')
                permissions += 04;
            if (s.charAt(7) == 'w')
                permissions += 02;
            if (s.charAt(8) == 'x')
                permissions += 01;
            else if (s.charAt(8) == 't')
                permissions += 01001;
            else if (s.charAt(8) == 'T')
                permissions += 01000;
        }

        return permissions;
    }

    /**
     * Analyze the passed string and determine if it specifies an existing file resource or URL.
     * Return the appropriate object, that must be disambiguated using instanceof. Meant to
     * (inelegantly) solve problems coming from file name encodings in primitive OS (e.g. Windows)
     * that cannot be handled properly in file:// URLs.
     *
     * @param s the s
     * @return a valid URL, existing file, or null. No exceptions are thrown.
     */
    public static Object getSourceForResource(String s) {

        File f = new File(s);

        if (f.exists())
            return f;

        URL url = null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
        }

        if (url != null)
            return url;

        return null;
    }

    /**
     * Uses getSourceForResource on the passed string and tries to open whatever resource is passed
     * and return the correspondent open input stream.
     *
     * @param s the s
     * @return an open input stream or null. No exceptions are thrown.
     */
    public static InputStream getInputStreamForResource(String s) {

        InputStream ret = null;

        Object o = getSourceForResource(s);

        if (o != null) {

            if (o instanceof File) {
                try {
                    ret = new FileInputStream((File) o);
                } catch (FileNotFoundException e) {
                }
            } else if (o instanceof URL) {
                try {
                    ret = ((URL) o).openStream();
                } catch (IOException e) {
                }
            }
        }

        return ret;
    }

    /**
     * Resolve a passed string into an existing file name.
     *
     * @param msource a file name, URL, or string containing a plugin identifier and a resource
     *        separated by :: - in the latter case, we lookup the resource in the named plugin's
     *        classpath
     * @return A local file containing the resource
     * @throws KlabException the klab exception
     */
    public static File resolveUrlToFile(String msource) throws KlabException {

        File ret = null;

        /*
         * if (msource.contains("::")) {
         * 
         * /* plugin classpath: resolve plugin and get resource int x = msource.indexOf("::");
         * String plug = msource.substring(0, x); String reso = msource.substring(x+2);
         * 
         * ThinklabPlugin plugin = Thinklab.resolvePlugin(plug, true); URL rurl =
         * plugin.getResourceURL(reso); ret = CopyURL.getFileForURL(rurl);
         * 
         * } else
         */if (msource.startsWith("http:") || msource.startsWith("file:")) {
            try {
                ret = URLUtils.getFileForURL(new URL(msource));
            } catch (Exception e) {
                throw new KlabResourceNotFoundException("resource " + msource + ": invalid URL");
            }
        } else {
            ret = new File(msource);
        }

        if (!ret.exists())
            throw new KlabResourceNotFoundException("file " + msource + " cannot be read");

        return ret;
    }

    /**
     * Interprets a string as either a valid URL or a file name, and return a proper URL no matter
     * what, or throw an exception if nothing seems to work. No checking is done on whether the URL
     * actually resolves to anything.
     *
     * @param msource the msource
     * @return the URL
     * @throws KlabIOException the klab IO exception
     */
    public static URL getURLForResource(String msource) throws KlabIOException {

        URL murl = null;

        File f = new File(msource);

        // we may get things like shapefile://host/file.shp; change to http if
        // so,
        // but only if this is not an existing file (may be C:/ in stupid
        // Windows).
        if (!f.exists() && msource.contains(":")) {
            String[] pc = msource.split(":");
            if (!(pc[0].equals("file") || pc[0].equals("http"))) {
                // assume http if it's anything else
                msource = "http:" + pc[1];
            }
        }

        try {
            murl = new URL(msource);
        } catch (MalformedURLException e) {
            try {
                murl = f.toURI().toURL();
            } catch (MalformedURLException e1) {
                throw new KlabIOException(e1);
            }
        }

        if (murl == null)
            throw new KlabIOException("can't open resource " + msource);

        return murl;
    }

    // {{{ throwableToString() method
    /**
     * Returns a string containing the user-visualizable version of the given throwable. Only print
     * the stack trace when exception has no useful message.
     *
     * TODO add special treatment and line reporting for common exceptions.
     *
     * @param t the t
     * @return the string
     * @since jEdit 4.2pre6
     */
    public static String throwableToString(Throwable t) {
        if (t.getMessage() != null && !t.getMessage().isEmpty()) {
            return t.getMessage();
        }
        StringWriter s = new StringWriter();
        t.printStackTrace(new PrintWriter(s));
        return s.toString();
    } // }}}

    // {{{ Private members
    private MiscUtilities() {
    }

    // {{{ compareChars()
    /** should this be public? */
    private static boolean compareChars(char ch1, char ch2, boolean ignoreCase) {
        if (ignoreCase)
            return Character.toUpperCase(ch1) == Character.toUpperCase(ch2);
        else
            return ch1 == ch2;
    }

    /**
     * Spaces.
     *
     * @param n the n
     * @return the string
     */
    public static String spaces(int n) {
        String ret = "";
        for (int i = 0; i < n; i++)
            ret += " ";
        return ret;
    }

    /**
     * This method is used for creating a backup of an existing File.
     *
     * @param file the file
     * @throws KlabIOException the klab IO exception
     */
    public static void backupFile(File file) throws KlabIOException {
        File parentFolder = new File(file.getParent() + "/backup");

        if (!parentFolder.exists())
            parentFolder.mkdir();

        int ver = 0;
        File newFile = new File(parentFolder, file.getName() + "." + Integer.toString(ver));
        File backupFile = new File(parentFolder, file.getName() + "." + Integer.toString(ver));

        while(newFile.exists()) {
            backupFile = newFile;
            newFile = new File(parentFolder, file.getName() + "." + Integer.toString(ver++));

        }

        // TODO: Is this a safe way for comparing when last file was modified?
        if (backupFile.lastModified() <= file.lastModified()) {
            try {
                URLUtils.copy(file.toURI().toURL(), newFile);
            } catch (Exception e) {
                throw new KlabIOException(e.getMessage());
            }
        } else
            newFile.delete();

    }

    /**
     * Change extension.
     *
     * @param string the string
     * @param string2 the string 2
     * @return the string
     */
    // TODO make sure things work correctly when it's a URL with parameters
    public static String changeExtension(String string, String string2) {

        String ret = null;
        int sl = string.lastIndexOf(".");
        if (sl > 0) {
            if (sl == string.length() - 1)
                ret += string2;
            else
                ret = string.substring(0, sl + 1) + string2;
        } else {
            ret += "." + string2;
        }

        return ret;
    }

    public static File changeExtension(File file, String extensionWithoutDot) {

        if (file == null) {
            return null;
        }

        String ret = null;
        String string = file.toString();
        int sl = string.lastIndexOf(".");
        if (sl > 0) {
            if (sl == string.length() - 1)
                ret += extensionWithoutDot;
            else
                ret = string.substring(0, sl + 1) + extensionWithoutDot;
        } else {
            ret += "." + extensionWithoutDot;
        }

        return new File(ret);
    }

    /**
     * Resource indicated by s can be a file name or a URL. Returns true if it's an existing file or
     * a URL that can be opened.
     *
     * @param s the s
     * @return true if exists
     */
    public static boolean resourceExists(String s) {

        boolean ret = false;
        Object o = getSourceForResource(s);

        if (o != null) {

            if (o instanceof File) {
                ret = ((File) o).exists();
            } else if (o instanceof URL) {
                try {
                    ret = ((URL) o).openStream() != null;
                } catch (IOException e) {
                }
            }
        }
        return ret;
    }

    /**
     * Change protocol.
     *
     * @param url the url
     * @param protocol the protocol
     * @return the string
     */
    public static String changeProtocol(String url, String protocol) {

        return url.indexOf(':') > 0 ? (protocol + url.substring(url.indexOf(':'))) : (protocol + ":" + url);

    }

    /**
     * Return the path leading to file without the file itself.
     *
     * @param lf the lf
     * @return path string
     */
    public static File getPath(String lf) {

        int n = lf.lastIndexOf(File.separator);
        String s = ".";
        if (n > -1) {
            s = lf.substring(0, n);
        }
        return new File(s);
    }

    /**
     * Removes the fragment.
     *
     * @param uri the uri
     * @return the uri
     */
    public static URI removeFragment(URI uri) {

        URI ret = uri;
        if (ret.toString().contains("#")) {
            String ut = ret.toString().substring(0, ret.toString().indexOf("#"));
            try {
                ret = new URI(ut);
            } catch (URISyntaxException e) {
                throw new KlabValidationException(e);
            }
        }

        return ret;
    }

    /**
     * Prints the vector.
     *
     * @param data the data
     * @return the string
     */
    public static String printVector(int[] data) {
        String ret = "";
        for (int d : data) {
            ret += (ret.equals("") ? "" : " ") + d;
        }
        return ret;
    }

    /**
     * Prints the vector.
     *
     * @param data the data
     * @return the string
     */
    public static String printVector(double[] data) {
        String ret = "";
        for (double d : data) {
            ret += (ret.equals("") ? "" : " ") + d;
        }
        return ret;
    }

    /**
     * Parses the int vector.
     *
     * @param data the data
     * @return the int[]
     */
    public static int[] parseIntVector(String data) {

        String[] ss = data.split("\\s+");
        int[] ret = new int[ss.length];
        int i = 0;
        for (String s : ss) {
            ret[i++] = Integer.parseInt(s);
        }
        return ret;
    }

    /**
     * Parses the int vector.
     *
     * @param data the data
     * @param startAt the start at
     * @return the int[]
     */
    public static int[] parseIntVector(String data, int startAt) {

        String[] ss = data.split("\\s+");
        int len = ss.length - startAt;
        int[] ret = new int[len];
        int n = 0;
        for (int i = startAt; i < ss.length; i++) {
            ret[n++] = Integer.parseInt(ss[i]);
        }
        return ret;
    }

    /**
     * Parses the double vector.
     *
     * @param data the data
     * @return the double[]
     */
    public static double[] parseDoubleVector(String data) {

        String[] ss = data.split("\\s+");
        double[] ret = new double[ss.length];
        int i = 0;
        for (String s : ss) {
            ret[i++] = Double.parseDouble(s);
        }
        return ret;
    }

    /**
     * Parses the double vector.
     *
     * @param data the data
     * @param startAt the start at
     * @return the double[]
     */
    public static double[] parseDoubleVector(String data, int startAt) {

        String[] ss = data.split("\\s+");
        int len = ss.length - startAt;
        double[] ret = new double[len];
        int n = 0;
        for (int i = startAt; i < ss.length; i++) {
            ret[n++] = Double.parseDouble(ss[i]);
        }
        return ret;
    }

    /**
     * Create a new temporary directory. Use something like {@link #recursiveDelete(File)} to clean
     * this directory up since it isn't deleted automatically
     *
     * @return the new directory
     * @throws KlabIOException the klab IO exception
     */
    public static File createTempDir() throws KlabIOException {
        final File sysTempDir = new File(System.getProperty("java.io.tmpdir"));
        File newTempDir;
        final int maxAttempts = 9;
        int attemptCount = 0;
        do {
            attemptCount++;
            if (attemptCount > maxAttempts) {
                throw new KlabIOException("Failed to create a unique temporary directory after " + maxAttempts + " attempts.");
            }
            String dirName = UUID.randomUUID().toString();
            newTempDir = new File(sysTempDir, dirName);
        } while(newTempDir.exists());

        if (newTempDir.mkdirs()) {
            return newTempDir;
        } else {
            throw new KlabIOException("Failed to create temp dir named " + newTempDir.getAbsolutePath());
        }
    }

    /**
     * Recursively delete file or directory.
     *
     * @param fileOrDir the file or dir to delete
     * @return true iff all files are successfully deleted
     */
    public static boolean recursiveDelete(File fileOrDir) {
        if (fileOrDir.isDirectory()) {
            // recursively delete contents
            for (File innerFile : fileOrDir.listFiles()) {
                if (!recursiveDelete(innerFile)) {
                    return false;
                }
            }
        }

        return fileOrDir.delete();
    }

    /**
     * Read integer from string.
     *
     * @param s the s
     * @return the int
     */
    public static int readIntegerFromString(String s) {
        s = s.trim();
        String ss = "";
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)))
                break;
            ss += s.charAt(i);
        }
        return Integer.parseInt(ss);
    }

    /**
     * Split number from string.
     *
     * @param s the s
     * @return the pair
     */
    public static Pair<Double, String> splitNumberFromString(String s) {
        String[] pp = s.split("\\ ");
        if (pp.length == 2) {
            return new Pair<>(Double.parseDouble(pp[0].trim()), pp[1]);
        }
        return new Pair<>(null, s);
    }

    /**
     * Get the last modification date if the resource resolves to a file. If the resource is a URL,
     * we cannot be sure so we assume it's just been modified and return the current time.
     *
     * @param resourceId the resource id
     * @return last mod
     */
    public static long getLastModificationForResource(String resourceId) {

        long ret = new Date().getTime();

        Object o = getSourceForResource(resourceId);
        if (o instanceof File) {
            ret = ((File) o).lastModified();
        }

        return ret;
    }

    /**
     * Read file into strings.
     *
     * @param filename the filename
     * @return the string[]
     * @throws KlabIOException the klab IO exception
     */
    public static String[] readFileIntoStrings(String filename) throws KlabIOException {

        FileReader fileReader;
        List<String> lines = new ArrayList<>();
        try {
            fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
        return lines.toArray(new String[lines.size()]);
    }

    /**
     * Gets the stack trace.
     *
     * @param aThrowable the a throwable
     * @return the stack trace
     */
    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

    /**
     * Defines a custom format for the stack trace as String.
     *
     * @param aThrowable the a throwable
     * @return the exception printout
     */
    public static String getExceptionPrintout(Throwable aThrowable) {

        // add the class name and any message passed to constructor
        final StringBuilder result = new StringBuilder();
        result.append(aThrowable.toString());
        final String NEW_LINE = System.getProperty("line.separator");
        result.append(NEW_LINE);

        // add each element of the stack trace
        for (StackTraceElement element : aThrowable.getStackTrace()) {
            result.append(element);
            result.append(NEW_LINE);
        }
        return result.toString();
    }

    /**
     * Extract index.
     *
     * @param s the s
     * @return the int
     */
    /*
     * find a [n] pattern in the string and return n.
     */
    public static int extractIndex(String s) {

        int sind = s.indexOf('[');
        int eind = s.indexOf(']');

        if (sind >= 0 && eind >= 0 && eind > sind) {
            String n = s.substring(sind + 1, eind);
            return Integer.parseInt(n);
        }

        throw new KlabValidationException("cannot find an array index in " + s);
    }

    /**
     * Gets the file extension.
     *
     * @param f the f
     * @return the file extension
     */
    public static String getFileExtension(File f) {
        return getFileExtension(f.toString());
    }

    /**
     * Gets the file base name.
     *
     * @param f the f
     * @return the file base name
     */
    public static String getFileBaseName(File f) {
        return getFileBaseName(f.toString());
    }

    /**
     * Gets the file name.
     *
     * @param f the f
     * @return the file name
     */
    public static String getFileName(File f) {
        return getFileName(f.toString());
    }

    public static boolean isRelativePath(String export) {
        Path path = Path.of(export);
        return !path.isAbsolute();
    }
}
