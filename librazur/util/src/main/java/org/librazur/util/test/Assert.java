/**
 * $Id: Assert.java,v 1.2 2005/12/07 14:47:02 romale Exp $
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


import java.util.Collection;


/**
 * Assertion utilities.
 * 
 * @since 1.3.1
 */
public class Assert {
    public static final Tester NULL = new Null();
    @SuppressWarnings("unchecked")
    public static final Tester NOT_NULL = not(NULL);
    public static final Tester BLANK = new Blank();
    @SuppressWarnings("unchecked")
    public static final Tester NOT_BLANK = not(BLANK);
    public static final Tester TRUE = new True();
    @SuppressWarnings("unchecked")
    public static final Tester FALSE = not(TRUE);
    public static final Tester EMPTY = new Empty();
    @SuppressWarnings("unchecked")
    public static final Tester NOT_EMPTY = not(EMPTY);


    @SuppressWarnings("unchecked")
    public static void isNotNull(String name, Object value) {
        if (NULL.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must not be null");
        }
    }


    @SuppressWarnings("unchecked")
    public static void isNull(String name, Object value) {
        if (NOT_NULL.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must be null: " + value);
        }
    }


    @SuppressWarnings("unchecked")
    public static void isBlank(String name, Object value) {
        if (NOT_BLANK.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must be null or empty: " + value);
        }
    }


    @SuppressWarnings("unchecked")
    public static void isNotBlank(String name, Object value) {
        if (BLANK.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must not be null or empty");
        }
    }


    @SuppressWarnings("unchecked")
    public static void isTrue(String name, boolean value) {
        if (FALSE.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must be true");
        }
    }


    @SuppressWarnings("unchecked")
    public static void isFalse(String name, boolean value) {
        if (TRUE.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must be false");
        }
    }


    @SuppressWarnings("unchecked")
    public static void isEmpty(String name, Collection value) {
        if (NOT_EMPTY.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must be empty: " + value);
        }
    }


    @SuppressWarnings("unchecked")
    public static void isNotEmpty(String name, Collection value) {
        if (EMPTY.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must not be empty: " + value);
        }
    }


    public static void isEqualTo(String name1, Object obj1, String name2,
            Object obj2) {
        if (obj1 != null && obj2 != null && !obj1.equals(obj2)) {
            throw new AssertionFailedException("Variable " + name1
                    + " is not equal to variable " + name2);
        }
    }


    public static void isSameThan(String name1, Object obj1, String name2,
            Object obj2) {
        if (obj1 != null && obj2 != null && obj1 != obj2) {
            throw new AssertionFailedException("Variable " + name1
                    + " and variable " + name2 + " are not the same");
        }
    }


    public static void isEqualTo(String name, Object expected, Object tested) {
        if (expected != null && tested != null && !expected.equals(tested)) {
            throw new AssertionFailedException("Variable " + name
                    + " should be equal to " + expected + ": " + tested);
        }
    }


    @SuppressWarnings("unchecked")
    public static void isMoreThanOrEqualTo(String name, Object ref,
            Comparable tested) {
        Assert.isNotNull("ref", ref);
        Assert.isNotNull("tested", tested);

        if (tested.compareTo(ref) == -1) {
            throw new AssertionFailedException("Variable " + name
                    + " must be more than or equal to " + ref + ": " + tested);
        }
    }


    @SuppressWarnings("unchecked")
    public static void isMoreThan(String name, Object ref, Comparable tested) {
        Assert.isNotNull("ref", ref);
        Assert.isNotNull("tested", tested);

        if (tested.compareTo(ref) <= 0) {
            throw new AssertionFailedException("Variable " + name
                    + " must be more than " + ref + ": " + tested);
        }
    }


    @SuppressWarnings("unchecked")
    public static void isLessThanOrEqualTo(String name, Object ref,
            Comparable tested) {
        Assert.isNotNull("ref", ref);
        Assert.isNotNull("tested", tested);

        if (tested.compareTo(ref) == 1) {
            throw new AssertionFailedException("Variable " + name
                    + " must be less than or equal to " + ref + ": " + tested);
        }
    }


    @SuppressWarnings("unchecked")
    public static void isLessThan(String name, Object ref, Comparable tested) {
        Assert.isNotNull("ref", ref);
        Assert.isNotNull("tested", tested);

        if (tested.compareTo(ref) >= 0) {
            throw new AssertionFailedException("Variable " + name
                    + " must be less than " + ref + ": " + tested);
        }
    }


    public static void isInstanceOf(Class<?> ref, Class<?> tested) {
        Assert.isNotNull("ref", ref);
        Assert.isNotNull("tested", tested);

        if (!ref.isAssignableFrom(tested)) {
            throw new AssertionFailedException("Class "
                    + tested.getClass().getName()
                    + " does not extend from class " + ref.getClass().getName());
        }
    }


    @SuppressWarnings("unchecked")
    public static Tester not(Tester tester) {
        return new Not(tester);
    }
}
