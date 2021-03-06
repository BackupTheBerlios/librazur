/**
 * $Id: IconCacheTest.java,v 1.2 2005/10/26 21:13:24 romale Exp $
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


import javax.swing.Icon;

import junit.framework.TestCase;


public class IconCacheTest extends TestCase {
    public void testGetIcon() {
        final String path = "/linux-logo.png";
        final IconCache cache = new IconCache();
        final Icon icon = cache.get(path);
        assertNotNull("Unable to load icon", icon);

        final Icon icon2 = cache.get(path);
        assertSame("The icons are not the same", icon, icon2);
    }


    public void testGetIconNull() {
        final IconCache cache = new IconCache();
        assertNull(cache.get("/foo.bar"));
    }
}
