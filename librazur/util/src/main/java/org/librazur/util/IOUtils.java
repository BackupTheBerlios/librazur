/**
 * $Id: IOUtils.java,v 1.3 2005/11/20 16:38:57 romale Exp $
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


import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;


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
     * Reads all bytes from a channel until EOF is reached.
     */
    public static ByteBuffer read(ReadableByteChannel channel, ByteBuffer buffer)
            throws IOException {
        final ByteAccumulator acc = new ByteAccumulator();
        buffer.clear();
        while (channel.read(buffer) != -1) {
            buffer.flip();
            acc.append(buffer);
            buffer.clear();
        }

        final ByteBuffer finalBuf = acc.get();
        finalBuf.flip();

        return finalBuf;
    }


    /**
     * Reads all bytes from an input stream until EOF is reached.
     */
    public static byte[] read(InputStream input, int len) throws IOException {
        final ByteBuffer buf = read(Channels.newChannel(input), ByteBuffer
                .allocate(Math.max(len, 1024)));
        final byte[] data = new byte[buf.remaining()];
        assert buf.hasArray();
        System.arraycopy(buf.array(), buf.arrayOffset(), data, 0, data.length);

        return data;
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
        copy(Channels.newChannel(input), Channels.newChannel(output));
    }


    /**
     * Same as <tt>copy(input, output, ByteBuffer.allocate(1024))</tt>.
     */
    public static void copy(ReadableByteChannel input,
            WritableByteChannel output) throws IOException {
        copy(input, output, ByteBuffer.allocate(1024));
    }


    /**
     * Copies all bytes from a input channel to an output channel until EOF is
     * reached. The output channel is not closed nor flushed at the end of the
     * copy.
     */
    public static void copy(ReadableByteChannel input,
            WritableByteChannel output, ByteBuffer buf) throws IOException {
        buf.clear();
        while (input.read(buf) != -1) {
            buf.flip();
            output.write(buf);

            if (buf.hasRemaining()) {
                buf.compact();
            } else {
                buf.clear();
            }
        }
    }
}
