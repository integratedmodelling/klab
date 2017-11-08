/*******************************************************************************
 *  Copyright (C) 2007, 2014:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.utils;

import org.integratedmodelling.klab.Klab;

/**
 * Escapes and Unescapes undesirable characters using % (URLEncoding)
 *
 * @author vpro
 * @version $Id: URLEscape.java,v 1.6 2005/07/28 09:23:19 pierre dead $
 */
public class UrlEscape {

    /**
     * List for all ASCII characters whether it can be part of an
     * URL line.
     */
    static boolean isacceptable[] = {
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false, // !"#$%&'
            false,
            false,
            true,
            true,
            true,
            true,
            true,
            false, // ()*+,-./
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true, // 01234567
            true,
            true,
            true,
            false,
            false,
            false,
            false,
            false, // 89:;<=>?
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true, // @ABCDEFG
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true, // HIJKLMNO
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true, // PQRSTUVW
            true,
            true,
            true,
            false,
            false,
            false,
            false,
            true, // XYZ[\]^_
            false,
            true,
            true,
            true,
            true,
            true,
            true,
            true, // `abcdefg
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true, // hijklmno
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true, // pqrstuvw
            true,
            true,
            true,
            false,
            false,
            false,
            false,
            false // xyz{|}~
    };

    /**
     * Hex characters
     */
    static char hex[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * Character to use for escaping invalid characters
     */
    static char HEX_ESCAPE = '%';

    /**
     * Escape a url.
     * Replaces 'invalid characters' with their Escaped code, i.e.
     * the questionmark (?) is escaped with %3F.
     * @param str the urls to escape
     * @return the escaped url.
     */
    public static String escapeurl(String str) {
        byte buf[];
        int i, a;
        StringBuffer esc = new StringBuffer();

        buf = new byte[str.length()];
        str.getBytes(0, str.length(), buf, 0);

        for (i = 0; i < str.length(); i++) {
            a = buf[i] & 0xff;
            if (a >= 32 && a < 128 && isacceptable[a - 32]) {
                esc.append((char) a);
            } else {
                esc.append(HEX_ESCAPE);
                esc.append(hex[a >> 4]);
                esc.append(hex[a & 15]);
            }
        }
        return esc.toString();
    }

    /**
     * converts a HEX-character to its approprtiate byte value.
     * i.e. 'A' is returned as '/011'
     * @param c teh Hex character
     * @return the byte value as a <code>char</code>
     */
    private static char from_hex(char c) {
        return (char) (c >= '0' && c <= '9' ? c - '0'
                : c >= 'A' && c <= 'F' ? c - 'A' + 10 : c - 'a' + 10); /* accept small letters just in case */
    }

    /**
     * Unescape a url.
     * Replaces escapesequenced with the actual character.
     * i.e %3F is replaced with the the questionmark (?).
     * @param str the urls to unescape
     * @return the unescaped url.
     */
    public static String unescapeurl(String str) {
        int i;
        char j, t;
        StringBuffer esc = new StringBuffer();

        if (str != null) {
            for (i = 0; i < str.length(); i++) {
                t = str.charAt(i);
                if (t == HEX_ESCAPE) {
                    t = str.charAt(++i);
                    j = (char) (from_hex(t) * 16);
                    t = str.charAt(++i);
                    j += from_hex(t);
                    esc.append(j);
                } else {
                    esc.append(t);
                }
            }
        } else {
            Klab.INSTANCE.warn("Unescapeurl -> Bogus parameter");
        }
        return esc.toString();
    }

}
