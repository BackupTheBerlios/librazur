/**
 * $Id: AssertTest.java,v 1.2 2005/12/07 14:47:02 romale Exp $
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


public class AssertTest extends TestCase {
    public void testIsNotNull() {
        Assert.isNotNull("a", 1);
        Assert.isNotNull("str", "Hello");

        try {
            Assert.isNotNull("obj", null);
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsNull() {
        Assert.isNull("obj", null);

        try {
            Assert.isNull("str", "  ");
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsBlank() {
        Assert.isBlank("str", "   ");
        Assert.isBlank("str", null);
        Assert.isBlank("str", "");

        try {
            Assert.isBlank("str", "  Hello ");
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsNotBlank() {
        Assert.isNotBlank("str", "Hello");

        try {
            Assert.isNotBlank("str", "");
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsEmpty() {
        Assert.isEmpty("obj", Collections.EMPTY_LIST);

        try {
            Assert.isEmpty("obj", Collections.singleton("Hello"));
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsNotEmpty() {
        Assert.isNotEmpty("obj", Collections.singleton("Hello"));

        try {
            Assert.isNotEmpty("obj", Collections.EMPTY_LIST);
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsTrue() {
        Assert.isTrue("a", true);

        try {
            Assert.isTrue("a", false);
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsFalse() {
        Assert.isFalse("a", false);

        try {
            Assert.isFalse("a", true);
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsEqualTo() {
        Assert.isEqualTo("str", "Hello", "Hello");

        try {
            Assert.isEqualTo("str", 1, 2);
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsEqualTo2() {
        Assert.isEqualTo("str1", "Hello", "str2", "Hello");

        try {
            Assert.isEqualTo("str1", "Bonjour", "str2", "Hola");
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsSameThan() {
        final Object obj = new Object();
        final Object clone = obj;
        Assert.isSameThan("obj", obj, "clone", clone);

        try {
            Assert.isSameThan("obj", obj, "badClone", new Object());
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsMoreThan() {
        Assert.isMoreThan("a", 1, 2);

        try {
            Assert.isMoreThan("a", 2, 2);
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsLessThan() {
        Assert.isLessThan("a", 2, 1);

        try {
            Assert.isLessThan("a", 2, 2);
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsMoreThanOrEqualTo() {
        Assert.isMoreThanOrEqualTo("a", 1, 2);
        Assert.isMoreThanOrEqualTo("a", 2, 2);

        try {
            Assert.isMoreThanOrEqualTo("a", 2, 1);
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsLessThanOrEqualTo() {
        Assert.isLessThanOrEqualTo("a", 2, 1);
        Assert.isLessThanOrEqualTo("a", 2, 2);

        try {
            Assert.isLessThanOrEqualTo("a", 1, 2);
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }


    public void testIsInstanceOf() {
        Assert.isInstanceOf(CharSequence.class, String.class);

        try {
            Assert.isInstanceOf(CharSequence.class, Number.class);
            fail("AssertionFailedException was expected");
        } catch (AssertionFailedException e) {
        }
    }
}
