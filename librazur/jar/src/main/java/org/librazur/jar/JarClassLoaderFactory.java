/**
 * $Id: JarClassLoaderFactory.java,v 1.4 2005/12/07 15:16:14 romale Exp $
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


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * {@link ClassLoaderFactory} implementation for JAR files.
 */
public class JarClassLoaderFactory implements ClassLoaderFactory {
    private List<URL> jarFiles = new ArrayList<URL>(1);
    private final ClassLoader parent;


    public JarClassLoaderFactory(ClassLoader parent) {
        this.parent = parent;
    }


    public JarClassLoaderFactory() {
        this(null);
    }


    public void addAll(Collection elems) {
        for (final Object elem : elems) {
            if (elem instanceof URL) {
                add((URL) elem);
            } else if (elem instanceof File) {
                add((File) elem);
            } else {
                throw new IllegalStateException("Unhandled element type: "
                        + elem.getClass().getName());
            }
        }
    }


    public void add(URL url) {
        if (url == null) {
            throw new NullPointerException("url");
        }
        final String path = url.getPath();
        if (path.length() == 0 || path.endsWith("/")) {
            throw new IllegalArgumentException(
                    "URL doesn't denote a regular file: " + url);
        }
        jarFiles.add(url);
    }


    public void add(File file) {
        add(toURL(file));
    }


    public void remove(URL url) {
        jarFiles.remove(url);
    }


    public void remove(File file) {
        jarFiles.remove(toURL(file));
    }


    public void clear() {
        jarFiles.clear();
    }


    public ClassLoader createClassLoader() {
        final URL[] urls = jarFiles.toArray(new URL[jarFiles.size()]);

        if (parent == null) {
            return new URLClassLoader(urls);
        }

        return new URLClassLoader(urls, parent);
    }


    @Override
    public String toString() {
        return "JarClassLoaderFactory[jarFiles=" + jarFiles + "]";
    }


    @Override
    public int hashCode() {
        return jarFiles.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof JarClassLoaderFactory)) {
            return false;
        }
        return hashCode() == obj.hashCode();
    }


    private URL toURL(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Unexpected exception", e);
        }
    }
}
