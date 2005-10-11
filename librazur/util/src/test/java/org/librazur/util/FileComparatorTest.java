/**
 * $Id: FileComparatorTest.java,v 1.1 2005/10/11 21:05:19 romale Exp $
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


import java.io.File;

import junit.framework.TestCase;


public class FileComparatorTest extends TestCase {
    public void testCompare() {
        final File file1 = new File("abc");
        final File file2 = new File("def");
        final File file3 = new File("ABC");

        final FileComparator comp = new FileComparator();
        assertEquals(0, comp.compare(file1, file1));
        assertTrue(comp.compare(file1, file3) > 0);
        assertTrue(comp.compare(file3, file1) < 0);
        assertTrue(comp.compare(file2, file1) > 0);
        assertTrue(comp.compare(file1, file2) < 0);
    }
}
