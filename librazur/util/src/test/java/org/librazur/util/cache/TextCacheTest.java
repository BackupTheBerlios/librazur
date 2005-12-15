/**
 * $Id: TextCacheTest.java,v 1.1 2005/12/15 14:40:38 romale Exp $
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

package org.librazur.util.cache;


import junit.framework.TestCase;


public class TextCacheTest extends TestCase {
    public void testGet() {
        final TextCache cache = new TextCache();
        final String text = cache.get("/readme.txt");
        assertEquals("This is a README file.\nThat's all.", text);

        assertSame(text, cache.get("/readme.txt"));

        assertNull(cache.get("/foobar.txt"));
    }
}
