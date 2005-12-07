/**
 * $Id: RestrictedClassLoader.java,v 1.4 2005/12/07 15:16:14 romale Exp $
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

package org.librazur.jar;


import java.io.IOException;
import java.net.URL;
import java.util.*;


/**
 * Special {@link ClassLoader} allowing to enable or disable access to
 * resources.
 */
public class RestrictedClassLoader extends ClassLoader {
    private final ClassLoader delegate;
    private final Set<String> classResources = new HashSet<String>();
    private final Set<String> regularResources = new HashSet<String>();


    public RestrictedClassLoader(final ClassLoader delegate) {
        if (delegate == null) {
            throw new NullPointerException("delegate");
        }
        this.delegate = delegate;
    }


    public void addClassResources(Collection<String> names) {
        for (final String name : names) {
            addClassResource(name);
        }
    }


    public void addClassResource(String name) {
        if (name == null) {
            throw new NullPointerException("Null element: " + name);
        }
        this.classResources.add(name);
    }


    public void addRegularResources(Collection<String> names) {
        for (final String name : names) {
            addRegularResource(name);
        }
    }


    public void addRegularResource(String name) {
        if (name == null) {
            throw new NullPointerException("Null element: " + name);
        }
        this.regularResources.add(name);
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (!classResources.contains(name)) {
            throw new ClassNotFoundException(name);
        }

        return delegate.loadClass(name);
    }


    @Override
    protected URL findResource(String name) {
        if (!regularResources.contains(name)) {
            return null;
        }

        return delegate.getResource(name);
    }


    @Override
    protected Enumeration<URL> findResources(String name) throws IOException {
        if (!regularResources.contains(name)) {
            return new EmptyEnumeration<URL>();
        }

        return delegate.getResources(name);
    }


    private class EmptyEnumeration<T> implements Enumeration<T> {
        public boolean hasMoreElements() {
            return false;
        }


        public T nextElement() {
            throw new NoSuchElementException();
        }
    }
}
