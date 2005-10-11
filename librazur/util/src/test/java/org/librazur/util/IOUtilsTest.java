/**
 * $Id: IOUtilsTest.java,v 1.1 2005/10/11 21:05:19 romale Exp $
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


import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
}
