/**
 * $Id: NotFileFilterTest.java,v 1.1 2005/10/11 21:05:19 romale Exp $
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

import org.easymock.MockControl;


public class NotFileFilterTest extends TestCase {
    public void testAccept() {
        final MockControl control = MockControl.createControl(FileFilter.class);
        final FileFilter mock = (FileFilter) control.getMock();
        control.expectAndReturn(mock.accept(new File("good")), true);
        control.expectAndReturn(mock.accept(new File("bad")), false);
        control.replay();

        final FileFilter fileFilter = new NotFileFilter(mock);
        assertFalse(fileFilter.accept(new File("good")));
        assertTrue(fileFilter.accept(new File("bad")));

        control.verify();
    }


    public void testContructorNullFilters() {
        try {
            new NotFileFilter(null);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // expected
        }
    }
}
