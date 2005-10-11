/**
 * $Id: ExtensionFileFilterTest.java,v 1.1 2005/10/11 21:05:19 romale Exp $
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

package org.librazur.util.filefilter;


import java.io.File;
import java.io.FileFilter;

import junit.framework.TestCase;


public class ExtensionFileFilterTest extends TestCase {
    public void testAccept() {
        doTests(new ExtensionFileFilter(".jar"));
        doTests(new ExtensionFileFilter(".JAR"));
        doTests(new ExtensionFileFilter(".Jar"));
        doTests(new ExtensionFileFilter(".jAr"));
        doTests(new ExtensionFileFilter(".jaR"));
    }


    private void doTests(FileFilter fileFilter) {
        assertTrue(fileFilter.accept(new File("test.jar")));
        assertFalse(fileFilter.accept(new File("test.jar2")));
        assertTrue(fileFilter.accept(new File("test.JAR")));
        assertTrue(fileFilter.accept(new File("test.Jar")));
        assertTrue(fileFilter.accept(new File("test.jAr")));
        assertTrue(fileFilter.accept(new File("test.jaR")));
        assertTrue(fileFilter.accept(new File(".jar")));
    }
}
