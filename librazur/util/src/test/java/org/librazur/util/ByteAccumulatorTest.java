/**
 * $Id: ByteAccumulatorTest.java,v 1.2 2005/10/20 22:44:31 romale Exp $
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

import junit.framework.TestCase;


public class ByteAccumulatorTest extends TestCase {
    public void testAppend() {
        final ByteBuffer buf1 = ByteBuffer.allocate(2);
        buf1.put(new byte[] { 1, 2 });
        buf1.flip();

        final ByteBuffer buf2 = ByteBuffer.allocate(4);
        buf2.put(new byte[] { 3, 4 });
        buf2.flip();

        final ByteBuffer buf3 = ByteBuffer.allocate(16);
        buf3.put(new byte[] { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                18, 19, 20 });
        buf3.flip();

        final ByteAccumulator bufAcc = new ByteAccumulator(2);
        assertEquals(2, bufAcc.get().capacity());
        bufAcc.append(buf1);
        assertEquals(2, bufAcc.get().capacity());
        bufAcc.append(buf2);
        assertEquals(8, bufAcc.get().capacity());
        bufAcc.append(buf3);
        assertEquals(40, bufAcc.get().capacity());

        final ByteBuffer buf = bufAcc.get();
        buf.flip();
        assertEquals(20, buf.limit());
        for (int i = 1; i <= 20; ++i) {
            assertEquals(i, buf.get());
        }
    }
}
