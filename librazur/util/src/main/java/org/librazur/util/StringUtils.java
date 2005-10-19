/**
 * $Id: StringUtils.java,v 1.2 2005/10/19 21:54:04 romale Exp $
 *
 * Librazur
 * http://librazur.eu.org
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


/**
 * String utilities.
 */
public final class StringUtils {
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
     * Strip spaces at the end of a line. A space is a character as defined by
     * <tt>Character.isSpace()</tt>.
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
}
