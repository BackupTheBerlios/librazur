/**
 * $Id: IOUtils.java,v 1.10 2005/12/15 14:47:39 romale Exp $
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


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.librazur.util.test.Assert;


/**
 * I/O utilities.
 * 
 * @since 1.0
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
     * Reads all bytes from a {@link Channel} until <code>EOF</code> is
     * reached.
     * 
     * @since 1.2
     */
    public static ByteBuffer read(ReadableByteChannel channel, ByteBuffer buffer)
            throws IOException {
        Assert.isNotNull("channel", channel);
        Assert.isNotNull("buffer", buffer);

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
     * Reads all bytes from an {@link InputStream} until <code>EOF</code> is
     * reached.
     */
    public static byte[] read(InputStream input, int len) throws IOException {
        Assert.isNotNull("input", input);

        final ByteBuffer buf = read(Channels.newChannel(input), ByteBuffer
                .allocate(Math.max(len, 1024)));
        final byte[] data = new byte[buf.remaining()];
        assert buf.hasArray();
        System.arraycopy(buf.array(), buf.arrayOffset(), data, 0, data.length);

        return data;
    }


    /**
     * Reads from an {@link InputStream} and returns a {@link String}.
     * 
     * @since 1.3
     */
    public static String readString(InputStream input) throws IOException {
        Assert.isNotNull("input", input);

        final StringBuilder buf = new StringBuilder();

        Reader reader = null;
        try {
            reader = new InputStreamReader(input);
            char[] charBuf = new char[512];
            for (int charsRead = 0; (charsRead = reader.read(charBuf)) != -1;) {
                buf.append(charBuf, 0, charsRead);
            }
        } finally {
            close(reader);
        }

        return buf.toString();
    }


    /**
     * Reads all bytes from an {@link InputStream} until <code>EOF</code> is
     * reached, using a buffer with 1024 bytes.
     */
    public static byte[] read(InputStream input) throws IOException {
        return read(input, 1024);
    }


    /**
     * Copies all bytes from an {@link InputStream} to an {@link OutputStream}
     * until <code>EOF</code> is reached. The {@link OutputStream} is not
     * closed nor flushed at the end of the copy.
     */
    public static void copy(InputStream input, OutputStream output)
            throws IOException {
        Assert.isNotNull("input", input);
        Assert.isNotNull("output", output);

        copy(Channels.newChannel(input), Channels.newChannel(output));
    }


    /**
     * Same as <code>copy(input, output, ByteBuffer.allocate(1024))</code>.
     * 
     * @since 1.2
     */
    public static void copy(ReadableByteChannel input,
            WritableByteChannel output) throws IOException {
        copy(input, output, ByteBuffer.allocate(1024));
    }


    /**
     * Copies all bytes from an input {@link Channel} to an output
     * {@link Channel} until <code>EOF</code> is reached. The output
     * {@link Channel} is not closed nor flushed at the end of the copy.
     * 
     * @since 1.2
     */
    public static void copy(ReadableByteChannel input,
            WritableByteChannel output, ByteBuffer buf) throws IOException {
        Assert.isNotNull("input", input);
        Assert.isNotNull("output", input);
        Assert.isNotNull("buf", buf);

        buf.clear();
        while (input.read(buf) != -1) {
            buf.flip();
            output.write(buf);

            if (buf.hasRemaining()) {
                // not all bytes were written: we'll write them in the next
                // round
                buf.compact();
            } else {
                buf.clear();
            }
        }
    }


    /**
     * Creates an {@link InputStream} from a {@link ByteBuffer}.
     * 
     * @since 1.3
     */
    public static InputStream newInputStream(final ByteBuffer buf) {
        Assert.isNotNull("buf", buf);

        return new InputStream() {
            public synchronized int read() throws IOException {
                if (!buf.hasRemaining()) {
                    return -1;
                }
                try {
                    return buf.get();
                } catch (Exception e) {
                    final IOException exc = new IOException(
                            "Error while reading from ByteBuffer");
                    exc.initCause(e);
                    throw exc;
                }
            }


            public int read(byte[] b, int off, int len) throws IOException {
                if (!buf.hasRemaining()) {
                    return -1;
                }
                final int maxLen = Math.min(len, buf.remaining());
                try {
                    buf.get(b, off, maxLen);
                } catch (Exception e) {
                    final IOException exc = new IOException(
                            "Error while reading from ByteBuffer");
                    exc.initCause(e);
                    throw exc;
                }
                return maxLen;
            }
        };
    }


    /**
     * Creates an {@link OutputStream} from a {@link ByteBuffer}.
     * 
     * @since 1.3
     */
    public static OutputStream newOutputStream(final ByteBuffer buf) {
        Assert.isNotNull("buf", buf);

        return new OutputStream() {
            public synchronized void write(int b) throws IOException {
                try {
                    buf.put((byte) b);
                } catch (Exception e) {
                    final IOException exc = new IOException(
                            "Error while writing to ByteBuffer");
                    exc.initCause(e);
                    throw exc;
                }
            }


            public synchronized void write(byte[] b, int off, int len)
                    throws IOException {
                try {
                    buf.put(b, off, len);
                } catch (Exception e) {
                    final IOException exc = new IOException(
                            "Error while writing to ByteBuffer");
                    exc.initCause(e);
                    throw exc;
                }
            }
        };
    }
}
