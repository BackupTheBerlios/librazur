/**
 * $Id: EventListenerList.java,v 1.9 2005/12/08 09:46:10 romale Exp $
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
import java.lang.reflect.Modifier;
import java.util.*;

import org.librazur.util.test.Assert;


/**
 * Event listener list, with dynamic capabilities.<br/>
 * <p>
 * Please note:
 * </p>
 * <p>
 * Since 1.3.1, {@link EventListenerList} is able to call any methods on an
 * interface, whether it takes an {@link EventObject} as an argument or not.
 * This implementation cannot work yet with several methods with the same name:
 * make sure your {@link EventListener} does not have any overloaded methods
 * with the same name.
 * </p>
 * 
 * @param <T> {@link EventListener} interface
 * @since 1.0
 */
public class EventListenerList<T extends EventListener> {
    private final List<T> listeners = new LinkedList<T>();
    private final Class<? extends EventListener> type;
    private final Map<String, Method> methodCache = new WeakHashMap<String, Method>(
            1);


    public EventListenerList(Class<? extends EventListener> type) {
        Assert.isNotNull("type", type);

        this.type = type;
    }


    /**
     * Adds a listener.
     */
    public void add(T l) {
        Assert.isNotNull("l", l);
        listeners.add(l);
    }


    /**
     * Removes a listener.
     */
    public void remove(T l) {
        listeners.remove(l);
    }


    /**
     * Clears the listener list.
     */
    public void clear() {
        listeners.clear();
    }


    /**
     * Fires an event on all the registered listeners.
     * 
     * @since 1.3.1
     */
    public void fireEvent(String methodName, Object... args) {
        Assert.isNotNull("methodName", methodName);
        if (listeners.isEmpty()) {
            return;
        }

        final Method method = getMethod(methodName);
        for (T l : listeners) {
            try {
                method.invoke(l, args);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException("Error while calling listener "
                        + l + " with method " + methodName, e);
            } catch (Exception e) {
                throw new IllegalStateException("Unexpected exception", e);
            }
        }
    }


    /**
     * Fires an event on all the registered listeners.
     */
    public void fireEvent(String methodName, EventObject evt) {
        fireEvent(methodName, new Object[] { evt });
    }


    private Method getMethod(String methodName) {
        Method method = methodCache.get(methodName);
        if (method == null) {
            for (final Method m : type.getMethods()) {
                if (m.getName().equals(methodName)) {
                    if (!Modifier.isPublic(m.getModifiers())) {
                        continue;
                    }

                    if (method != null) {
                        throw new IllegalStateException(
                                "EventListenerList cannot call an event method "
                                        + "if there are several methods "
                                        + "with the same name: " + methodName);
                    }
                    method = m;
                }
            }
            if (method == null) {
                throw new IllegalStateException("Class " + type.getName()
                        + " doesn't have any method " + methodName);
            }
            methodCache.put(methodName, method);
        }

        return method;
    }
}
