/**
 * $Id: DirectoryFileFilterTest.java,v 1.1 2005/10/11 21:05:19 romale Exp $
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


public class DirectoryFileFilterTest extends TestCase {
    private String dirName;
    private String fileName;


    public void testAccept() {
        final FileFilter fileFilter = new DirectoryFileFilter();
        assertTrue(fileFilter.accept(new File(dirName)));
        assertFalse(fileFilter.accept(new File(fileName)));
    }


    @Override
    protected void setUp() throws Exception {
        dirName = "DirectoryFileFilterTest-dir-" + System.currentTimeMillis();
        final File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        assertTrue(dir.exists());
        assertTrue(dir.isDirectory());

        fileName = "DirectoryFileFilterTest-file-" + System.currentTimeMillis();
        final File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        assertTrue(file.exists());
        assertTrue(file.isFile());
    }


    @Override
    protected void tearDown() throws Exception {
        final File dir = new File(dirName);
        if (dir.exists()) {
            dir.delete();
        }
        dirName = null;

        final File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        fileName = null;
    }
}
