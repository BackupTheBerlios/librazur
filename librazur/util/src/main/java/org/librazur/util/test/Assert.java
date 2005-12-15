/**
 * $Id: Assert.java,v 1.4 2005/12/15 14:47:25 romale Exp $
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
    /**
     * Asserts a value is not <code>null</code>.
     * 
     * @param name name of the value
     */
    public static void isNotNull(String name, Object value) {
        if (NULL.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must not be null");
        }
    }


    @SuppressWarnings("unchecked")
    /**
     * Asserts a value is <code>null</code>.
     * 
     * @param name name of the value
     */
    public static void isNull(String name, Object value) {
        if (NOT_NULL.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must be null: " + value);
        }
    }


    @SuppressWarnings("unchecked")
    /**
     * Asserts a value is blank, as defined by
     * {@link org.librazur.util.StringUtils#isBlank(String)}.
     * 
     * @param name name of the value
     */
    public static void isBlank(String name, Object value) {
        if (NOT_BLANK.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must be null or empty: " + value);
        }
    }


    @SuppressWarnings("unchecked")
    /**
     * Asserts a value is not blank, as defined by
     * {@link org.librazur.util.StringUtils#isBlank(String)}.
     * 
     * @param name name of the value
     */
    public static void isNotBlank(String name, Object value) {
        if (BLANK.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must not be null or empty");
        }
    }


    @SuppressWarnings("unchecked")
    /**
     * Asserts a value is <code>true</code>.
     * 
     * @param name name of the value
     */
    public static void isTrue(String name, boolean value) {
        if (FALSE.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must be true");
        }
    }


    @SuppressWarnings("unchecked")
    /**
     * Asserts a value is <code>false</code>.
     * 
     * @param name name of the value
     */
    public static void isFalse(String name, boolean value) {
        if (TRUE.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must be false");
        }
    }


    @SuppressWarnings("unchecked")
    /**
     * Asserts a {@link Collection} is empty.
     * 
     * @param name name of the collection
     */
    public static void isEmpty(String name, Collection value) {
        if (NOT_EMPTY.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must be empty: " + value);
        }
    }


    @SuppressWarnings("unchecked")
    /**
     * Asserts a {@link Collection} is not empty.
     * 
     * @param name name of the collection
     */
    public static void isNotEmpty(String name, Collection value) {
        if (EMPTY.test(value)) {
            throw new AssertionFailedException("Variable " + name
                    + " must not be empty: " + value);
        }
    }


    /**
     * Asserts the value <code>obj1</code> is equal to the value
     * <code>obj2</code>. Two <code>null</code> values are supposed to be
     * equal.
     * 
     * @param name1 name of obj1
     * @param name2 name of obj2
     */
    public static void isEqualTo(String name1, Object obj1, String name2,
            Object obj2) {
        if (obj1 == null && obj2 == null) {
            return;
        }
        if (obj1 == null) {
            throw new AssertionFailedException("Variable " + name1
                    + " is not equal to variable " + name2);
        }
        if (obj2 == null) {
            throw new AssertionFailedException("Variable " + name1
                    + " is not equal to variable " + name2);
        }
        if (!obj1.equals(obj2)) {
            throw new AssertionFailedException("Variable " + name1
                    + " is not equal to variable " + name2);
        }
    }


    /**
     * Asserts <code>obj1</code> is <code>obj2</code>, <i>ie</i> the same
     * object. Two <code>null</code> values are supposed to be the same.
     * 
     * @param name1 name of obj1
     * @param name2 name of obj2
     */
    public static void isSameThan(String name1, Object obj1, String name2,
            Object obj2) {
        if (obj1 == null && obj2 == null) {
            return;
        }
        if (obj1 != obj2) {
            throw new AssertionFailedException("Variable " + name1
                    + " and variable " + name2 + " are not the same");
        }
    }


    /**
     * Asserts an object is equal to an expected value. Two <code>null</code>
     * values are supposed to be equal.
     * 
     * @param name name of the value
     */
    public static void isEqualTo(String name, Object expected, Object tested) {
        if (expected == null && tested == null) {
            return;
        }
        if (expected == null) {
            throw new AssertionFailedException("Variable " + name
                    + " should be equal to " + expected + ": " + tested);
        }
        if (tested == null) {
            throw new AssertionFailedException("Variable " + name
                    + " should be equal to " + expected + ": " + tested);
        }
        if (!expected.equals(tested)) {
            throw new AssertionFailedException("Variable " + name
                    + " should be equal to " + expected + ": " + tested);
        }
    }


    @SuppressWarnings("unchecked")
    /**
     * Asserts a value if more than or equal to an other value.
     * 
     * @param name name of the value
     */
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
    /**
     * Asserts a value if more than an other value.
     * 
     * @param name name of the value
     */
    public static void isMoreThan(String name, Object ref, Comparable tested) {
        Assert.isNotNull("ref", ref);
        Assert.isNotNull("tested", tested);

        if (tested.compareTo(ref) <= 0) {
            throw new AssertionFailedException("Variable " + name
                    + " must be more than " + ref + ": " + tested);
        }
    }


    @SuppressWarnings("unchecked")
    /**
     * Asserts a value if less than or equal to an other value.
     * 
     * @param name name of the value
     */
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
    /**
     * Asserts a value if less than an other value.
     * 
     * @param name name of the value
     */
    public static void isLessThan(String name, Object ref, Comparable tested) {
        Assert.isNotNull("ref", ref);
        Assert.isNotNull("tested", tested);

        if (tested.compareTo(ref) >= 0) {
            throw new AssertionFailedException("Variable " + name
                    + " must be less than " + ref + ": " + tested);
        }
    }


    @SuppressWarnings("unchecked")
    /**
     * Asserts a {@link Class} is derived from or an interface from an other
     * {@link Class}.
     * 
     * @param name name of the variable
     */
    public static void isInstanceOf(String name, Class ref, Class tested) {
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
