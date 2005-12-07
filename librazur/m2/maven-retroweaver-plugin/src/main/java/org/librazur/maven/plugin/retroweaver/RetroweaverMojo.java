/**
 * $Id: RetroweaverMojo.java,v 1.4 2005/12/07 16:08:39 romale Exp $
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

package org.librazur.maven.plugin.retroweaver;


import java.io.File;
import java.util.*;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.librazur.jar.MainClassLauncher;


/**
 * Retroweaver plugin.
 * 
 * @goal retroweaver
 * @phase process-classes
 * @description Retroweaver plugin.
 */
public class RetroweaverMojo extends AbstractMojo {
    public static final String VERSION_1_2 = "1.2";
    public static final String VERSION_1_3 = "1.3";
    public static final String VERSION_1_4 = "1.4";
    public static final String DEFAULT_VERSION = VERSION_1_4;

    /**
     * Targeted JVM version. Can be one of { 1.2, 1.3, 1.4 }. Default is 1.4.
     * 
     * @parameter expression="${retroweaver.version}" default-value="1.4"
     */
    public String version;

    /**
     * If <tt>true</tt>, RetroWeaver will work in lazy mode. Default is
     * <code>true</code>.
     * 
     * @parameter expression="${retroweaver.lazy}" default-value="true"
     */
    public boolean lazy = true;

    /**
     * Compile classpath elements will be modified by Retroweaver.
     * 
     * @parameter expression="${project.compileClasspathElements}"
     * @required
     * @readonly
     */
    public List classpathElements;


    public void execute() throws MojoExecutionException, MojoFailureException {
        final Set validVersions = new HashSet(3);
        validVersions.add(VERSION_1_2);
        validVersions.add(VERSION_1_3);
        validVersions.add(VERSION_1_4);

        if (version == null) {
            version = DEFAULT_VERSION;
        }
        if (!validVersions.contains(version)) {
            throw new MojoFailureException(
                    "Wrong value for parameter version: " + version);
        }

        for (final Iterator i = classpathElements.iterator(); i.hasNext();) {
            final String path = (String) i.next();
            final File dir = new File(path).getAbsoluteFile();

            try {
                launchRetroweaver(dir);
            } catch (Exception e) {
                throw new MojoExecutionException(
                        "Error while launching Retroweaver", e);
            }
        }
    }


    private void launchRetroweaver(File dir) throws Exception {
        if (!dir.exists() || !dir.isDirectory()) {
            getLog().info(
                    "There is no classes to \"retroweave\" "
                            + "in this directory: " + dir.getPath());
            return;
        }

        // build arguments
        final List args = new ArrayList();
        args.add("-source");
        args.add(dir.getPath());
        args.add("-version");
        args.add(version);
        if (lazy) {
            args.add("-lazy");
        }

        getLog().info("Launching Retroweaver...");
        getLog().info("Classes directory: " + dir.getPath());
        getLog().info("Targeted JVM version: " + version);
        getLog().info("Lazy mode: " + boolean2string(lazy));

        // locate the main Retroweaver class
        final String weaverClassName = "com.rc.retroweaver.Weaver";
        final Class weaverClass;
        try {
            weaverClass = Class.forName(weaverClassName);
        } catch (ClassNotFoundException e) {
            throw new MojoExecutionException(
                    "Unable to load RetroWeaver class: " + weaverClassName, e);
        }

        new MainClassLauncher(weaverClass).launch((String[]) args
                .toArray(new String[args.size()]));
    }


    private static String boolean2string(boolean val) {
        return val ? "enabled" : "disabled";
    }
}
