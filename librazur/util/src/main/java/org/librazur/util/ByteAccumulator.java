/**
 * $Id: ByteAccumulator.java,v 1.4 2005/12/05 14:28:16 romale Exp $
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


import java.nio.ByteBuffer;


/**
 * Byte accumulator. Provides a way to add bytes to a
 * {@link java.nio.ByteBuffer}, growing automatically when it is required.
 */
public class ByteAccumulator {
    private ByteBuffer buffer;


    public ByteAccumulator(final ByteBuffer buffer) {
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        this.buffer = buffer;
    }


    public ByteAccumulator() {
        this(1024);
    }


    public ByteAccumulator(int size) {
        this(ByteBuffer.allocate(size));
    }


    /**
     * Appends some bytes into the buffer. If the buffer is too small, a new
     * buffer with more space is created, with data from the old buffer.
     */
    public void append(ByteBuffer buf) {
        if (buffer.remaining() < buf.remaining()) {
            // not enough space: we allocate a new buffer with more space
            final int position = buffer.position();
            buffer.flip();
            final int newCap = (int) Math.round((buffer.remaining() + buf
                    .remaining()) * 2);

            final ByteBuffer newBuffer = buffer.isDirect() ? ByteBuffer
                    .allocateDirect(newCap) : ByteBuffer.allocate(newCap);
            newBuffer.put(buffer);
            buffer = newBuffer;
            buffer.position(position);
        }
        assert buffer.remaining() < buf.remaining();
        buffer.put(buf);
    }


    /**
     * Returns a view of the buffer. All returned buffers share the same
     * content.
     */
    public ByteBuffer get() {
        return buffer.duplicate();
    }
}
