/**
 * $Id: IOUtils.java,v 1.2 2005/10/20 22:44:31 romale Exp $
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


import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * I/O utilities.
 */
public final class IOUtils {
    private IOUtils() {
    }


    /**
     * Quietly closes an object which implements <tt>Closeable</tt>.
     * Gracefully handles the case where the object is <tt>null</tt>. Any
     * exception raised while closing is ignored.
     */
    public static void close(Closeable obj) {
        if (obj == null) {
            return;
        }
        try {
            obj.close();
        } catch (Exception ignore) {
        }
    }


    /**
     * Reads all bytes from an input stream until EOF is reached.
     */
    public static byte[] read(InputStream input, int len) throws IOException {
        final byte[] buf = new byte[Math.max(len, 1024)];
        final ByteArrayOutputStream data = new ByteArrayOutputStream(buf.length);
        for (int bytesRead = 0; (bytesRead = input.read(buf)) != -1;) {
            data.write(buf, 0, bytesRead);
        }

        return data.toByteArray();
    }


    /**
     * Reads all bytes from an input stream until EOF is reached, using a buffer
     * with 1024 bytes.
     */
    public static byte[] read(InputStream input) throws IOException {
        return read(input, 1024);
    }


    /**
     * Copies all bytes from an input stream to an output stream until EOF is
     * reached. The output stream is not closed nor flushed at the end of the
     * copy.
     */
    public static void copy(InputStream input, OutputStream output)
            throws IOException {
        final byte[] buf = new byte[1024];

        for (int bytesRead = 0; (bytesRead = input.read(buf)) != -1;) {
            output.write(buf, 0, bytesRead);
        }
    }
}
