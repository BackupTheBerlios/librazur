/**
 * $Id: AssertUtils.java,v 1.3 2005/12/07 14:34:26 romale Exp $
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


import org.librazur.util.test.Assert;


/**
 * Assertion utilities.
 * 
 * @since 1.3
 * @deprecated As of 1.3.1, this class is deprecated: use {@link Assert}
 *             instead. This class will be removed in 1.4.
 */
@Deprecated
public final class AssertUtils {
    private AssertUtils() {
    }


    /**
     * Asserts a value is not null.
     * 
     * @throws NullPointerException if <code>value</code> is null
     */
    public static void assertNotNull(String name, Object value) {
        if (value == null) {
            throw new NullPointerException("Argument must not be null: " + name);
        }
    }


    /**
     * Asserts a {@link String} is not blank, as defined in
     * {@link StringUtils#isBlank(java.lang.String)}.
     * 
     * @throws IllegalArgumentException if
     *             <code>StringUtils.isBlank(value) == true</code>
     */
    public static void assertNotBlank(String name, String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(
                    "Argument must not be null or empty: " + name);
        }
    }


    /**
     * Asserts a value is equal to zero or positive.
     * 
     * @throws IllegalArgumentException if <code>a < 0</code>
     */
    public static void assertZeroOrPositive(String name, int a) {
        if (a < 0) {
            throw new IllegalArgumentException(
                    "Number must be equal to zero or positive: " + name + " = "
                            + a);
        }
    }


    /**
     * Asserts a value is equal to zero or positive.
     * 
     * @throws IllegalArgumentException if <code>a < 0</code>
     */
    public static void assertZeroOrPositive(String name, double a) {
        if (a < 0) {
            throw new IllegalArgumentException(
                    "Argument must be equal to zero or positive: " + name
                            + " = " + a);
        }
    }
}
