/**
 * $Id: JarClassLoaderFactoryTest.java,v 1.3 2005/11/24 10:12:01 romale Exp $
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


public class JarClassLoaderFactoryTest extends AbstractClassLoaderFactoryTest {
    public void testCreateClassLoader() throws Exception {
        final JarClassLoaderFactory factory = new JarClassLoaderFactory();
        factory.add(getClass().getResource("/commons-pool-1.2.jar"));

        final ClassLoader cl = factory.createClassLoader();
        assertClassLoadable(cl, "org.apache.commons.pool.ObjectPool");
        assertClassLoadable(cl, "java.lang.String");
        assertClassUnloadable(cl, "foo.Bar");
    }
}
