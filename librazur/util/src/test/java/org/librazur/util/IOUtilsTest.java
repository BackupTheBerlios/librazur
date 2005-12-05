/**
 * $Id: IOUtilsTest.java,v 1.5 2005/12/05 14:28:36 romale Exp $
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

import junit.framework.TestCase;


public class IOUtilsTest extends TestCase {
    public void testCloseNull() {
        IOUtils.close(null);
    }


    public void testClose() {
        final InputStream input = new ByteArrayInputStream(new byte[0]);
        IOUtils.close(input);
    }


    public void testCloseException() {
        final InputStream input = new CloseWithExceptionInputStream();
        IOUtils.close(input);
    }


    public void testRead() throws Exception {
        final byte[] data = new byte[] { 1, 2, 3 };

        final ByteArrayInputStream input = new ByteArrayInputStream(data);
        final byte[] testData = IOUtils.read(input);

        assertEquals(data.length, testData.length);
        for (int i = 0; i < data.length; ++i) {
            assertEquals(data[i], testData[i]);
        }
    }


    public void testCopy() throws Exception {
        final String path = "/linux-logo.png";
        final InputStream input = getClass().getResourceAsStream(path);
        assertNotNull(input);

        final File tempFile = File.createTempFile("IOUtils-testCopy-", ".tmp");
        tempFile.deleteOnExit();
        final OutputStream output = new FileOutputStream(tempFile);

        IOUtils.copy(input, output);

        final String md5Source = ChecksumUtils.md5Hex(getClass()
                .getResourceAsStream(path));
        final String md5Target = ChecksumUtils.md5Hex(new FileInputStream(
                tempFile));
        assertEquals("MD5 for source and target files are not the same",
                md5Source, md5Target);

        tempFile.delete();
    }


    public void testNewInputStream() throws IOException {
        final byte[] data = new byte[] { 0, 1, 2, 3, 4 };
        final ByteBuffer buf = ByteBuffer.wrap(data);
        final InputStream input = IOUtils.newInputStream(buf);

        for (int i = 0; i < data.length; ++i) {
            assertEquals(data[i], input.read());
        }

        buf.rewind();
        final byte[] tempBuf = new byte[data.length];
        final int bytesRead = input.read(tempBuf, 0, tempBuf.length);
        assertEquals(data.length, bytesRead);
        for (int i = 0; i < bytesRead; ++i) {
            assertEquals(data[i], tempBuf[i]);
        }

        assertEquals(-1, input.read());
        assertEquals(-1, input.read(new byte[data.length], 0, data.length));
    }


    public void testNewOutputStream() throws IOException {
        final ByteBuffer buf = ByteBuffer.allocate(5);
        final OutputStream output = IOUtils.newOutputStream(buf);

        for (int i = 0; i < buf.capacity(); ++i) {
            output.write(i);
        }
        try {
            output.write(2);
            fail("IOException was expected");
        } catch (IOException e) {
        }

        buf.clear();
        final byte[] data = new byte[buf.capacity()];
        for (int i = 0; i < data.length; ++i) {
            data[i] = (byte) i;
        }
        output.write(data, 0, data.length);
        assertFalse(buf.hasRemaining());
        try {
            output.write(new byte[] { 2 }, 0, 1);
            fail("IOException was expected");
        } catch (IOException e) {
        }
    }
}
