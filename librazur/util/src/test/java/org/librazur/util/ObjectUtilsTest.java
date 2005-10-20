/**
 * $Id: ObjectUtilsTest.java,v 1.2 2005/10/20 22:44:31 romale Exp $
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


public class ObjectUtilsTest extends TestCase {
    public void testGetterName() {
        assertEquals("getId", ObjectUtils.getterName("id"));
        assertEquals("getLongValue", ObjectUtils.getterName("longValue"));
        assertEquals("getA", ObjectUtils.getterName("a"));

        try {
            ObjectUtils.getterName("");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
        }

        try {
            ObjectUtils.getterName(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
        }
    }


    public void testProperty() {
        assertEquals("abc", ObjectUtils.property(new SimpleBean("abc"), "id"));
        assertEquals("123", ObjectUtils.property(new SimpleBean("123"), "id"));
        assertEquals("", ObjectUtils.property(new SimpleBean(""), "id"));
        assertNull(ObjectUtils.property(new SimpleBean(null), "id"));

        try {
            ObjectUtils.property(new SimpleBean("foo"), "bar");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
        }
    }
}
