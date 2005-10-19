/**
 * $Id: SystemClassLoaderFactory.java,v 1.2 2005/10/19 16:17:59 romale Exp $
 *
 * Librazur
 * http://librazur.eu.org
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


/**
 * <tt>ClassLoaderFactory</tt> implementation which delegates to the system
 * <tt<ClassLoader</tt>.
 */
public class SystemClassLoaderFactory implements ClassLoaderFactory {
    public ClassLoader createClassLoader() {
        return getClass().getClassLoader();
    }


    @Override
    public String toString() {
        return "SystemClassLoaderFactory[]";
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SystemClassLoaderFactory)) {
            return false;
        }
        return hashCode() == obj.hashCode();
    }
}
