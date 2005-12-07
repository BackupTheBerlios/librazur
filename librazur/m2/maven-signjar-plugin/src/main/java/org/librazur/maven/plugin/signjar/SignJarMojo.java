/**
 * $Id: SignJarMojo.java,v 1.5 2005/12/07 16:08:38 romale Exp $
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


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;
import org.librazur.jar.JarSigner;


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

    /**
     * Artifact resolver, needed to download source jars for inclusion in
     * classpath.
     * 
     * @component role="org.apache.maven.artifact.resolver.ArtifactResolver"
     * @required
     * @readonly
     */
    public ArtifactResolver artifactResolver;

    /**
     * Local Maven repository.
     * 
     * @parameter expression="${localRepository}"
     * @required
     * @readonly
     */
    public ArtifactRepository localRepository;

    /**
     * Remote repositories which will be searched for source attachments.
     * 
     * @parameter expression="${project.remoteArtifactRepositories}"
     * @required
     * @readonly
     */
    public List remoteArtifactRepositories;


    public void execute() throws MojoExecutionException, MojoFailureException {
        if ("jar".equals(project.getPackaging())) {
            // deal with the main artifact
            final File jarFile = new File(project.getBuild().getDirectory(),
                    project.getBuild().getFinalName() + ".jar");
            if (jarFile.exists() && jarFile.isFile()) {
                signJar(jarFile, getSignedJarFile(jarFile));
            } else {
                getLog().debug(
                        "Main artifact project does not exist: "
                                + jarFile.getPath());
            }
        } else {
            getLog().debug(
                    "Signing main artifact is only available "
                            + "when project packaging is set to 'jar'");
        }

        getLog().info(project.getTestArtifacts().toString());
        getLog().info("Size=" + project.getTestArtifacts().size());

        if (signDependencies) {
            final Set artifacts = new HashSet();
            
            System.out.println(project.getDependencyArtifacts().toString());

            for (final Iterator i = project.getDependencyArtifacts().iterator(); i
                    .hasNext();) {
                final Artifact artifact = (Artifact) i.next();
                if (artifact.isOptional() && !artifacts.contains(artifact)) {
                    try {
                        artifactResolver.resolve(artifact,
                                remoteArtifactRepositories, localRepository);
                    } catch (Exception e) {
                        throw new MojoExecutionException(
                                "Unable to resolve artifact: "
                                        + artifact.getId(), e);
                    }
                    artifacts.add(artifact);
                }
            }
            
            getLog().info(artifacts.toString());
        }
    }


    private File getSignedJarFile(File jarFile) throws MojoFailureException {
        final File outputDir = new File(project.getBuild().getDirectory(),
                "signedjars");
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new MojoFailureException(
                    "Unable to create output directory: " + outputDir.getPath());
        }
        return new File(outputDir, jarFile.getName());
    }


    private void signJar(File jarFile, File signedJarFile)
            throws MojoExecutionException {
        getLog().info("Signing JAR file: " + jarFile.getName());

        if (!shouldSignJar(jarFile, signedJarFile)) {
            getLog().info("JAR file " + jarFile.getName() + " is up to date");
            return;
        }

        final JarSigner jarSigner = new JarSigner(storePass, alias);

        if (StringUtils.isNotEmpty(keystore)) {
            // try with a relative path to the project basedir
            File keystoreFile = new File(project.getBasedir(), keystore);
            try {
                if (keystoreFile.exists()) {
                    jarSigner.setKeystore(keystoreFile.toURI().toURL());
                } else {
                    // try with an absolute path
                    keystoreFile = new File(keystore);

                    if (keystoreFile.exists()) {
                        jarSigner.setKeystore(keystoreFile.toURI().toURL());
                    } else {
                        getLog().debug(
                                "Keystore does not exist as a local file: "
                                        + "assuming it is an URL");
                        // keystore is an URL (I hope so...)
                        jarSigner.setKeystore(new URL(keystore));
                    }
                }
            } catch (MalformedURLException e) {
                throw new MojoExecutionException(
                        "Error while setting keystore", e);
            }
        }
        jarSigner.setStoreType(storeType);
        jarSigner.setKeyPass(keyPass);
        if (StringUtils.isNotEmpty(sigFile)) {
            final File file = new File(project.getBasedir(), sigFile);
            if (file.exists()) {
                jarSigner.setSigFile(file);
            } else {
                getLog().warn("The signature file does not exist: " + sigFile);
            }
        }
        jarSigner.setVerbose(verbose);
        jarSigner.setInternalSF(internalSF);
        jarSigner.setSectionsOnly(sectionsOnly);

        try {
            jarSigner.sign(jarFile, signedJarFile);
        } catch (Exception e) {
            throw new MojoExecutionException("Error while signing JAR file: "
                    + jarFile.getPath(), e);
        }
    }


    private boolean shouldSignJar(File jarFile, File signedJarFile)
            throws MojoExecutionException {
        if (!signedJarFile.exists()) {
            return true;
        } else if (jarFile.lastModified() <= signedJarFile.lastModified()) {
            return false;
        }

        JarInputStream input = null;
        try {
            input = new JarInputStream(new BufferedInputStream(
                    new FileInputStream(jarFile)));
            for (JarEntry entry; (entry = input.getNextJarEntry()) != null;) {
                final String upperCaseName = entry.getName().toUpperCase();
                if (upperCaseName.startsWith("META-INF/")
                        && upperCaseName.endsWith(".SF")) {
                    return !lazy;
                }
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Error while reading JAR file: "
                    + jarFile.getPath(), e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignore) {
                }
            }
        }

        return true;
    }
}
