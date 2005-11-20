/**
 * $Id: ChecksumUtilsTest.java,v 1.1 2005/11/20 16:36:42 romale Exp $
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


import java.io.InputStream;

import junit.framework.TestCase;


public class ChecksumUtilsTest extends TestCase {
    public void testMd5() throws Exception {
        final InputStream input = getClass().getResourceAsStream(
                "/linux-logo.png");
        assertNotNull(input);
        assertEquals("a2b3565b17518045517a495672b450eb", StringUtils
                .encodeHex(ChecksumUtils.md5(input)));
    }


    public void testSha1() throws Exception {
        final InputStream input = getClass().getResourceAsStream(
                "/linux-logo.png");
        assertNotNull(input);
        assertEquals("fa8415d4ecb71489ed610fc519eee4c72e3d3b4c", StringUtils
                .encodeHex(ChecksumUtils.sha1(input)));
    }
}
