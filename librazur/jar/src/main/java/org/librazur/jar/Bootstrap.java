/**
 * $Id: Bootstrap.java,v 1.1 2005/10/11 20:53:57 romale Exp $
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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Application bootstrap. The class reads a property file, pointed by the system
 * property "librazur.bootstrap.file", and start the bootstrapping. The property
 * file contains the following:
 * 
 * <pre>
 *      main = &lt;main jar to start&gt;
 *      class.X = &lt;class dir&gt;
 *      jar.Y = &lt;jar dir&gt;
 * </pre>
 * 
 * There can be several class.X and jar.X as long as X and Y are unique integers
 * starting from 0.
 */
public class Bootstrap {
    public static void main(String[] args) {
        final String sysProp = "librazur.bootstrap.file";
        final String propPath = System.getProperty(sysProp);
        if (propPath == null) {
            System.err.println("System property is not set: " + sysProp);
            System.exit(5);
        }

        final File propFile = new File(propPath);
        if (!propFile.exists() || !propFile.isFile() || !propFile.canRead()) {
            System.err.println("Unable to get bootstrap file: " + propPath);
            System.exit(4);
        }

        final Properties props = new Properties();
        try {
            props.load(new FileInputStream(propFile));
        } catch (IOException e) {
            System.err.println("Error while reading bootstrap file: "
                    + propPath);
            e.printStackTrace();
            System.exit(3);
        }

        final File baseDir = propFile.getParentFile();
        assert baseDir.isDirectory();

        final File mainJar = new File(props.getProperty("main"));
        if (!mainJar.exists() || !mainJar.isFile() || !mainJar.canRead()) {
            System.err.println("Unable to find main jar file: "
                    + mainJar.getPath());
            System.exit(2);
        }

        final JarClassLoaderFactory factory = new JarClassLoaderFactory();
        factory.add(mainJar);

        int jarIndex = 0;
        for (String jarProp = null; (jarProp = props.getProperty("jar."
                + jarIndex)) != null; ++jarIndex) {
            System.out.println(jarProp);

            final File dir = new File(baseDir, jarProp);
            if (!dir.exists() || !dir.isDirectory()) {
                continue;
            }

            final File[] jars = dir.listFiles(new JarFileFilter());
            for (final File jar : jars) {
                if (!jar.canRead()) {
                    continue;
                }
                factory.add(jar);
            }
        }

        final StringBuilder classBuf = new StringBuilder();

        int classIndex = 0;
        for (String classProp = null; (classProp = props.getProperty("class."
                + classIndex)) != null; ++classIndex) {
            System.out.println(classProp);

            classBuf.append(classProp).append(File.pathSeparatorChar);
        }
        if (classBuf.length() > 0) {
            System.setProperty("java.class.path", classBuf.toString());
        }

        final JarLauncher launcher = new JarLauncher(mainJar);
        launcher.setClassLoaderFactory(factory);
        try {
            launcher.launch(args);
        } catch (Exception e) {
            System.err.println("Unable to launch application");
            e.printStackTrace();
            System.exit(1);
        }
    }


    private static class JarFileFilter implements FileFilter {
        public boolean accept(File file) {
            return file.isFile()
                    && file.getName().toLowerCase().endsWith(".jar");
        }
    }
}
