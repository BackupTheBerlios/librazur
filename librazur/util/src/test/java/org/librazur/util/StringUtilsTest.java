/**
 * $Id: StringUtilsTest.java,v 1.4 2005/11/20 16:37:29 romale Exp $
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


import junit.framework.TestCase;


public class StringUtilsTest extends TestCase {
    public void testTrimToNull() {
        assertNull(StringUtils.trimToNull(null));
        assertNull(StringUtils.trimToNull(""));
        assertNull(StringUtils.trimToNull(" "));
        assertNull(StringUtils.trimToNull("    "));
        assertNotNull(StringUtils.trimToNull("aa"));
        assertNotNull(StringUtils.trimToNull("  aa  "));
    }


    public void testDefaultString() {
        assertNull(StringUtils.defaultString(null, null));
        assertEquals("Test", StringUtils.defaultString(null, "Test"));
        assertEquals("Test", StringUtils.defaultString("Test", null));
    }


    public void testIsBlank() {
        assertTrue(StringUtils.isBlank(null));
        assertFalse(StringUtils.isBlank("aa"));
        assertTrue(StringUtils.isBlank("  "));
        assertFalse(StringUtils.isBlank("  aa  "));
    }


    public void testStripEndSpaces() {
        assertEquals("hello", StringUtils.stripEndSpaces("hello  "));
        assertEquals("  hello", StringUtils.stripEndSpaces("  hello  "));
        assertEquals("hello", StringUtils.stripEndSpaces("hello"));
        assertEquals("  hello", StringUtils.stripEndSpaces("  hello"));
        assertEquals("", StringUtils.stripEndSpaces(""));
        assertEquals("a", StringUtils.stripEndSpaces("a"));
        assertEquals("", StringUtils.stripEndSpaces("  "));
        assertNull(StringUtils.stripEndSpaces(null));
    }


    public void testBase64() throws Exception {
        final String str = "Librazur";
        final byte[] data = str.getBytes("UTF-8");
        final byte[] testData = StringUtils.decodeBase64(StringUtils
                .encodeBase64(data));

        assertEquals(data.length, testData.length);
        for (int i = 0; i < data.length; ++i) {
            assertEquals(data[i], testData[i]);
        }
    }

    public void testHex() throws Exception {
        final String str = "Librazur";
        final byte[] data = str.getBytes("UTF-8");
        final byte[] testData = StringUtils.decodeHex(StringUtils
                .encodeHex(data));

        assertEquals(data.length, testData.length);
        for (int i = 0; i < data.length; ++i) {
            assertEquals(data[i], testData[i]);
        }
    }
}
