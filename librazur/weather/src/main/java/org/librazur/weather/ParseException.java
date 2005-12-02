/**
 * $Id: ParseException.java,v 1.2 2005/12/02 11:10:24 romale Exp $
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

package org.librazur.weather;


import java.net.URL;


/**
 * Parse exception.
 */
public class ParseException extends Exception {
    private final URL url;
    private final int lineNumber;
    private final String line;


    public ParseException(final URL url, final int lineNumber,
            final String line, final Throwable cause) {
        super("Error while parsing " + url + " on line " + lineNumber + ": "
                + line, cause);
        if (url == null) {
            throw new NullPointerException("url");
        }
        this.url = url;
        this.lineNumber = lineNumber;
        this.line = line;
    }


    public ParseException(final URL url, final int lineNumber, final String line) {
        this(url, lineNumber, line, null);
    }


    public ParseException(final URL url) {
        this(url, -1, null);
    }


    public String getLine() {
        return line;
    }


    public int getLineNumber() {
        return lineNumber;
    }


    public URL getUrl() {
        return url;
    }
}
