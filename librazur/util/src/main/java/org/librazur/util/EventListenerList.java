/**
 * $Id: EventListenerList.java,v 1.8 2005/12/07 16:46:10 romale Exp $
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
 * Event listener list, with dynamic capabilities.
 * 
 * @param <T> <tt>EventListener</tt> type
 * @since 1.0
 */
public class EventListenerList<T extends EventListener> {
    private final List<T> listeners = new LinkedList<T>();
    private final Class<? extends EventListener> type;
    private final Map<String, Method> methodCache = new WeakHashMap<String, Method>(
            1);


    public EventListenerList(Class<? extends EventListener> type) {
        Assert.isNotNull("type", type);
        Assert.isInstanceOf(EventListener.class, type.getClass());

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
                    // a listener method must be public, and must take an
                    // argument of type EventObject.
                    if (!Modifier.isPublic(m.getModifiers())) {
                        continue;
                    }
                    final Class<?>[] args = m.getParameterTypes();
                    if (args.length != 1) {
                        continue;
                    }
                    if (!EventObject.class.isAssignableFrom(args[0])) {
                        continue;
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
