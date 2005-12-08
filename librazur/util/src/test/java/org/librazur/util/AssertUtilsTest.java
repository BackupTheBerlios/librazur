/**
 * $Id: AssertUtilsTest.java,v 1.2 2005/12/08 09:20:31 romale Exp $
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


@SuppressWarnings("deprecation")
public class AssertUtilsTest extends TestCase {
    public void testAssertNotNull() {
        AssertUtils.assertNotNull("msg", "hello");
        try {
            AssertUtils.assertNotNull("msg", null);
            fail("NullPointerException was expected");
        } catch (NullPointerException e) {
        }
    }


    public void testAssertNotBlank() {
        AssertUtils.assertNotBlank("msg", "hello");
        AssertUtils.assertNotBlank("msg", "  hello  ");
        try {
            AssertUtils.assertNotBlank("msg", null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
        }
        try {
            AssertUtils.assertNotBlank("msg", "");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
        }
        try {
            AssertUtils.assertNotBlank("msg", "     ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
        }
    }


    public void testAssertZeroOrPositive() {
        AssertUtils.assertZeroOrPositive("a", 0);
        AssertUtils.assertZeroOrPositive("a", 0d);
        AssertUtils.assertZeroOrPositive("a", 1);
        AssertUtils.assertZeroOrPositive("a", 1d);
        try {
            AssertUtils.assertZeroOrPositive("a", -1);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
        }
        try {
            AssertUtils.assertZeroOrPositive("a", -1d);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
        }
    }
}
