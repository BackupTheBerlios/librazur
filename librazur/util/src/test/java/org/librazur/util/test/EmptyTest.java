/**
 * $Id: EmptyTest.java,v 1.1 2005/12/07 14:33:24 romale Exp $
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

package org.librazur.util.test;


import java.util.Collections;

import junit.framework.TestCase;


public class EmptyTest extends TestCase {
    public void testTest() {
        final Empty tester = new Empty();

        assertTrue(tester.test(Collections.EMPTY_LIST));
        assertFalse(tester.test(Collections.singleton("Hello")));
    }
}
