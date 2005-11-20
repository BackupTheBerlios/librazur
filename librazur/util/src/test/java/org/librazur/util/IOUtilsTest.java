/**
 * $Id: IOUtilsTest.java,v 1.3 2005/11/20 16:39:09 romale Exp $
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


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
}
