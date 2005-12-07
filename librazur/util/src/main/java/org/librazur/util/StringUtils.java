/**
 * $Id: StringUtils.java,v 1.7 2005/12/07 14:46:13 romale Exp $
 *
 * Librazur
 * http://librazur.info
 * Copyright (c) 2005 Librazur
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.librazur.util;


import org.librazur.util.test.Assert;


/**
 * String utilities.
 * 
 * @since 1.0
 */
public final class StringUtils {
    /**
     * @since 1.3.1
     */
    public static final String EMPTY = "";

    private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


    private StringUtils() {
    }


    /**
     * Returns a default string if a string is <tt>null</tt>.
     */
    public static String defaultString(String str, String def) {
        return str == null ? def : str;
    }


    /**
     * Returns <tt>true</tt> is a string is null or is empty (same as
     * <tt>trimToNull(str) == null).
     */
    public static boolean isBlank(String str) {
        return trimToNull(str) == null;
    }


    /**
     * Returns a new trimmed string. If the input string is <tt>null</tt>,
     * empty or contains only white spaces, returns <tt>null</tt>.
     */
    public static String trimToNull(String str) {
        if (str == null) {
            return null;
        }

        final String newStr = str.trim();

        return newStr.length() == 0 ? null : newStr;
    }


    /**
     * Strips spaces at the end of a line. A space is a character as defined by
     * <tt>Character.isSpace()</tt>.
     * 
     * @since 1.1
     */
    public static String stripEndSpaces(String line) {
        if (line == null || line.length() == 0) {
            return line;
        }

        int i = line.length() - 1;
        while (i >= 0 && Character.isWhitespace(line.charAt(i))) {
            --i;
        }

        if (i == line.length() - 1) {
            return line;
        }

        // use a new String to workaround a bug in String.substring():
        // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4637640
        return new String(line.substring(0, i + 1));
    }


    /**
     * Encodes a byte array to Base64.
     * 
     * @since 1.2
     */
    public static String encodeBase64(byte[] data) {
        Assert.isNotNull("data", data);

        return Base64.encodeBytes(data);
    }


    /**
     * Decodes a Base64 encoded string to a byte array.
     * 
     * @since 1.2
     */
    public static byte[] decodeBase64(String data) {
        Assert.isNotBlank("data", data);

        return Base64.decode(data);
    }


    /**
     * Encodes a byte array to Hex. Implementation based on StringUtils from <a
     * href="http://melati.org">Melati project</a>.
     * 
     * @since 1.2
     */
    public static String encodeHex(byte[] data) {
        Assert.isNotNull("data", data);

        StringBuffer it = new StringBuffer(data.length * 2);

        for (int i = 0; i < data.length; ++i) {
            int b = data[i];
            it.append(hexDigits[b >> 4 & 0xF]);
            it.append(hexDigits[b & 0xF]);
        }

        return it.toString();
    }


    private static byte decodeHex(char c) {
        if ('0' <= c && c <= '9')
            return (byte) (c - '0');
        else if ('A' <= c && c <= 'F')
            return (byte) (0xA + c - 'A');
        else if ('a' <= c && c <= 'f')
            return (byte) (0xa + c - 'a');
        else
            throw new IllegalArgumentException("Invalid hex digit in string");
    }


    /**
     * Decodes a Hex encoded string to a byte array. Implementation based on
     * StringUtils from <a href="http://melati.org">Melati project</a>.
     * 
     * @since 1.2
     */
    public static byte[] decodeHex(String data) {
        Assert.isNotBlank("data", data);

        int l = data.length() / 2;
        if (l * 2 != data.length())
            throw new IllegalArgumentException(
                    "Hex string has odd number of digits");

        byte[] it = new byte[l];

        for (int i = 0; i < l; ++i)
            it[i] = (byte) (decodeHex(data.charAt(i * 2)) << 4 | decodeHex(data
                    .charAt(i * 2 + 1)));

        return it;
    }
}
