/**
 * $Id: SignJarMojo.java,v 1.1 2005/11/23 11:07:41 romale Exp $
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

package org.librazur.maven.plugin.signjar;


import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SignJar;
import org.apache.tools.ant.types.FileSet;


/**
 * Signs JAR files.
 *
 * @goal signjar
 * @description Signs JAR files.
 */
public class SignJarMojo extends AbstractMojo {
    /**
     * Alias to sign under.
     *
     * @parameter expression="${signjar.alias}"
     * @required
     */
    public String alias;

    /**
     * Password for keystore integrity.
     *
     * @parameter expression="${signjar.storepass}"
     * @required
     */
    public String storePass;

    /**
     * Keystore location.
     *
     * @parameter expression="${signjar.keystore}"
     */
    public String keystore;

    /**
     * Keystore type.
     *
     * @parameter expression="${signjar.storetype}"
     */
    public String storeType;

    /**
     * Password for private key (if different).
     *
     * @parameter expression="${signjar.keypass}"
     */
    public String keyPass;

    /**
     * Name of .SF/.DSA file.
     *
     * @parameter expression="${signjar.sigfile}"
     */
    public String sigFile;

    /**
     * Verbose output when signing.
     *
     * @parameter expression="${signjar.verbose}"
     */
    public boolean verbose;

    /**
     * Include the .SF file inside the signature block.
     *
     * @parameter expression="${signjar.internalsf}"
     */
    public boolean internalSF;

    /**
     * Don't compute hash of entire manifest.
     *
     * @parameter expression="${signjar.sectionsonly}"
     */
    public boolean sectionsOnly;

    /**
     * Flag to control whether the presence of a signature file means a JAR is
     * signed.
     *
     * @parameter expression="${signjar.lazy}"
     */
    public boolean lazy;

    /**
     * Specifies the maximum memory the jarsigner VM will use. Specified in the
     * style of standard java memory specs (e.g. 128m = 128 MBytes).
     *
     * @parameter expression="${signjar.maxmemory}"
     */
    public String maxMemory;

    /**
     * Whether to sign dependencies or not.
     *
     * @parameter expression="${signjar.signdependencies}"
     */
    public boolean signDependencies = true;

    /**
     * The project containing JAR files to sign.
     *
     * @parameter expression="${project}"
     * @required
     */
    public MavenProject project;


    public void execute() throws MojoExecutionException, MojoFailureException {
        final File outputDir = new File(project.getBuild().getDirectory(),
                "signedjars");
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new MojoFailureException(
                    "Unable to create output directory: " + outputDir.getPath());
        }

        // we can only sign artifacts which are JAR files
        final Set jarCompatiblePackages = new HashSet();
        jarCompatiblePackages.add("jar");
        jarCompatiblePackages.add("war");
        jarCompatiblePackages.add("ear");
        jarCompatiblePackages.add("maven-plugin");

        if (jarCompatiblePackages.contains(project.getPackaging())) {
            // deal with the main artifact
            final File jarFile = new File(project.getBuild().getDirectory(),
                    project.getBuild().getFinalName() + "."
                            + project.getPackaging());
            if (jarFile.exists()) {
                signJar(outputDir, jarFile);
            } else {
                getLog().debug(
                        "Main artifact project does not exist: "
                                + jarFile.getPath());
            }
        } else {
            getLog().debug(
                    "Cannot sign main artifact project with package type '"
                            + project.getPackaging() + "'");
            getLog().debug("Valid packagings are: " + jarCompatiblePackages);
        }

        if (signDependencies) {
            for (final Iterator i = project.getDependencies().iterator(); i
                    .hasNext();) {
                final Dependency dep = (Dependency) i.next();
                // TODO sign dependencies
            }
        }
    }


    private File signJar(File outputDir, File jarFile) {
        getLog().info("Signing JAR file: " + jarFile.getName());

        final File signedJarFile = new File(outputDir, jarFile.getName());

        final Project antProject = new Project();
        antProject.setBaseDir(project.getBasedir());
        antProject.setName(project.getName());
        antProject.setCoreLoader(project.getClass().getClassLoader());

        final SignJar signJar = new SignJar();
        signJar.setAlias(alias);
        signJar.setStorepass(storePass);
        signJar.setKeystore(keystore);
        signJar.setStoretype(storeType);
        signJar.setKeypass(keyPass);
        signJar.setSigfile(sigFile);
        signJar.setVerbose(true);
        signJar.setInternalsf(internalSF);
        signJar.setSectionsonly(sectionsOnly);
        signJar.setLazy(lazy);
        signJar.setMaxmemory(maxMemory);
        signJar.setJar(jarFile);
        signJar.setSignedjar(signedJarFile);

        signJar.setProject(antProject);
        signJar.addFileset(new FileSet());
        signJar.reconfigure();
        signJar.execute();

        return signedJarFile;
    }
}
