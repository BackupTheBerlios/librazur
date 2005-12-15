/**
 * $Id: ObjectUtils.java,v 1.6 2005/12/15 09:43:28 romale Exp $
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


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

import org.librazur.util.test.Assert;


/**
 * Object utilities.
 * 
 * @since 1.0
 */
public final class ObjectUtils {
    private static Map<String, Method> methodCache = new WeakHashMap<String, Method>(
            1);


    private ObjectUtils() {
    }


    /**
     * Returns a JavaBean "getter"-like method name for a property.
     */
    public static String getterName(String name) {
        final String propName = StringUtils.trimToNull(name);
        if (propName == null) {
            throw new IllegalArgumentException("name");
        }

        return new StringBuilder("get").append(
                Character.toUpperCase(propName.charAt(0))).append(
                propName.substring(1)).toString();
    }


    /**
     * Returns the value of a property on an object, by invoking the getter
     * method.
     */
    public static Object property(Object obj, String name) {
        Assert.isNotNull("obj", obj);
        Assert.isNotNull("name", name);

        final Class clazz = obj.getClass();
        final String key = clazz.getName() + "." + name;

        synchronized (methodCache) {
            Method method = methodCache.get(key);
            if (method == null) {
                // the method is not in the cache
                try {
                    method = clazz.getMethod(getterName(name));
                    methodCache.put(key, method);
                } catch (NoSuchMethodException e) {
                    throw new IllegalArgumentException(
                            "Unable to find a getter for property " + name
                                    + " in class " + clazz.getName(), e);
                }
            }
            assert method != null : "method shouldn't be null";

            try {
                return method.invoke(obj);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException("Error while getting property "
                        + name + " in class " + clazz.getName(), e);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Unexpected exception", e);
            }
        }
    }


    /**
     * Returns <code>true</code> if two objects are the same. If
     * <code>obj1</code> is <code>null</code> and <code>obj2</code> is
     * <code>null</code>, returns <code>true</code> ; if either
     * <code>obj1</code> or <code>obj2</code> is <code>null</code>, this
     * methods returns <code>false</code>. Otherwise, this methods returns
     * the result of <code>obj1.equals(obj2)</code>.
     */
    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null) {
            if (obj2 == null) {
                return true;
            }
            assert obj2 != null;
            return false;
        }

        assert obj1 != null;
        if (obj2 == null) {
            return false;
        }

        assert obj2 != null;
        return obj1.equals(obj2);
    }


    /**
     * Returns a default value if an {@link Object} is <code>null</code>.
     */
    public static Object defaultIfNull(Object obj, Object def) {
        return obj == null ? def : obj;
    }
}
