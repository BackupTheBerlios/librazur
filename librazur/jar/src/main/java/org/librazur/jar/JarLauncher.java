/**
 * $Id: JarLauncher.java,v 1.1 2005/10/11 20:53:57 romale Exp $
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


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.jar.Attributes;
import java.util.jar.JarException;
import java.util.jar.JarFile;


/**
 * Jar file launcher.
 */
public class JarLauncher {
    private final File file;
    private ClassLoaderFactory classLoaderFactory = new SystemClassLoaderFactory();


    public JarLauncher(final File file) {
        if (file == null) {
            throw new NullPointerException("file");
        }
        this.file = file;
    }


    public void launch(String... args) throws IOException,
            ClassNotFoundException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        final String[] jarArgs = new String[args == null ? 0 : args.length];
        System.arraycopy(args, 0, jarArgs, 0, jarArgs.length);

        final JarFile jarFile = new JarFile(file);
        final Attributes atts = jarFile.getManifest().getMainAttributes();

        final String mainClassName = atts.getValue(Attributes.Name.MAIN_CLASS);
        if (mainClassName == null || mainClassName.length() == 0) {
            throw new JarException("JAR manifest has no Main-Class attribute: "
                    + file.getName());
        }

        final Class mainClass = Class.forName(mainClassName, true,
                classLoaderFactory.createClassLoader());
        final Method method = mainClass.getMethod("main",
                new Class[] { String[].class });
        method.invoke(null, new Object[] { jarArgs });
    }


    public ClassLoaderFactory getClassLoaderFactory() {
        return classLoaderFactory;
    }


    public void setClassLoaderFactory(ClassLoaderFactory classLoaderFactory) {
        this.classLoaderFactory = classLoaderFactory;
    }


    public File getFile() {
        return file;
    }
}
