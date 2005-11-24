/**
 * $Id: RetroweaverMojo.java,v 1.1 2005/11/24 14:15:01 romale Exp $
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
import java.io.IOException;
import java.util.*;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
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
     * @parameter expression="${retroweaver.version}"
     */
    public String version;

    /**
     * If <tt>true</tt>, RetroWeaver will work in lazy mode. Default is true.
     *
     * @parameter expression="${retroweaver.lazy}"
     */
    public boolean lazy = true;

    /**
     * The current project.
     *
     * @parameter expression="${project}"
     * @required
     */
    public MavenProject project;


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

        final String weaverClassName = "com.rc.retroweaver.Weaver";
        final Class weaverClass;
        try {
            weaverClass = Class.forName(weaverClassName);
        } catch (ClassNotFoundException e) {
            throw new MojoExecutionException(
                    "Unable to load RetroWeaver class: " + weaverClassName, e);
        }

        final File classesDir;
        try {
            classesDir = new File(project.getBuild().getOutputDirectory())
                    .getCanonicalFile();
        } catch (IOException e) {
            throw new MojoExecutionException(
                    "Unable to find classes directory", e);
        }

        if (!classesDir.exists() || !classesDir.isDirectory()) {
            getLog().info("There is no classes to \"retroweave\".");
            return;
        }

        // build arguments
        final List args = new ArrayList();
        args.add("-source");
        args.add(classesDir.getPath());
        args.add("-version");
        args.add(version);
        if (lazy) {
            args.add("-lazy");
        }

        getLog().info("Launching Retroweaver...");
        getLog().info("Classes directory: " + classesDir.getPath());
        getLog().info("Targeted JVM version: " + version);
        getLog().info("Lazy mode: " + boolean2string(lazy));

        // launch the main class with the arguments
        try {
            new MainClassLauncher(weaverClass).launch((String[]) args
                    .toArray(new String[args.size()]));
        } catch (Exception e) {
            throw new MojoExecutionException(
                    "Error while launching Retroweaver, using args: " + args, e);
        }
    }


    private static String boolean2string(boolean val) {
        return val ? "enabled" : "disabled";
    }
}
