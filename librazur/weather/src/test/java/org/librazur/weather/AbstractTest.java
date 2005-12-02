/**
 * $Id: AbstractTest.java,v 1.2 2005/12/02 09:14:41 romale Exp $
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

package org.librazur.weather;


import junit.framework.TestCase;


public abstract class AbstractTest extends TestCase {
    protected void assertDoubleEquals(double expected, double test,
            double epsilon) {
        assertTrue("Assertion failed: abs(abs(" + expected + ") - abs(" + test
                + ")) <= " + epsilon, Math.abs(Math.abs(expected)
                - Math.abs(test)) <= epsilon);
    }


    protected void assertDoubleEquals(double expected, double test) {
        assertDoubleEquals(expected, test, 0.001d);
    }
}
