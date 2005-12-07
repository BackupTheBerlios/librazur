/**
 * $Id: JarSigner.java,v 1.3 2005/12/07 15:16:14 romale Exp $
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Signs JAR files. This class is an interface to the command line tool
 * <code>jarsigner</code>. This command must be available in the PATH.
 */
public class JarSigner {
    private final String alias;
    private final String storePass;
    private URL keystore;
    private String storeType;
    private String keyPass;
    private File sigFile;
    private boolean verbose;
    private boolean internalSF;
    private boolean sectionsOnly;
    private boolean lazy;


    public JarSigner(final String storePass, final String alias) {
        if (isStringEmpty(storePass)) {
            throw new IllegalArgumentException("storePass");
        }
        if (isStringEmpty(alias)) {
            throw new IllegalArgumentException("alias");
        }
        this.storePass = storePass;
        this.alias = alias;
    }


    /**
     * Signs a JAR file. If <code>jarsigner</code> returns a non-zero value, a
     * {@link java.lang.RuntimeException} is thrown.
     */
    public void sign(File jarFile, File signedJarFile) throws Exception {
        final List<String> args = new ArrayList<String>();
        args.add("jarsigner");

        if (keystore != null) {
            args.add("-keystore");
            args.add(keystore.toString());
        }
        if (!isStringEmpty(storePass)) {
            args.add("-storepass");
            args.add(storePass);
        }
        if (!isStringEmpty(storeType)) {
            args.add("-storetype");
            args.add(storeType);
        }
        if (!isStringEmpty(keyPass)) {
            args.add("-keypass");
            args.add(keyPass);
        }
        if (sigFile != null) {
            args.add("-sigfile");
            args.add(sigFile.getPath());
        }
        if (internalSF) {
            args.add("-internalsf");
        }
        if (sectionsOnly) {
            args.add("-sectionsonly");
        }
        if (verbose) {
            args.add("-verbose");
        }
        args.add("-signedjar");
        args.add(signedJarFile.getPath());
        args.add(jarFile.getPath());
        args.add(alias);

        final Process proc = Runtime.getRuntime().exec(
                args.toArray(new String[args.size()]));
        final int errorCode = proc.waitFor();
        if (errorCode != 0) {
            final StringBuilder cmd = new StringBuilder();
            for (final String arg : args) {
                cmd.append(arg).append(" ");
            }
            throw new IllegalStateException(
                    "jarsigner was not able to sign the JAR file: "
                            + jarFile.getPath() + "\nError code: " + errorCode
                            + "\nCommand: " + cmd.toString().trim());
        }
    }


    public boolean isInternalSF() {
        return internalSF;
    }


    public void setInternalSF(boolean internalSF) {
        this.internalSF = internalSF;
    }


    public String getKeyPass() {
        return keyPass;
    }


    public void setKeyPass(String keyPass) {
        this.keyPass = keyPass;
    }


    public URL getKeystore() {
        return keystore;
    }


    public void setKeystore(URL keystore) {
        this.keystore = keystore;
    }


    public boolean isLazy() {
        return lazy;
    }


    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }


    public boolean isSectionsOnly() {
        return sectionsOnly;
    }


    public void setSectionsOnly(boolean sectionsOnly) {
        this.sectionsOnly = sectionsOnly;
    }


    public File getSigFile() {
        return sigFile;
    }


    public void setSigFile(File sigFile) {
        this.sigFile = sigFile;
    }


    public String getStoreType() {
        return storeType;
    }


    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }


    public boolean isVerbose() {
        return verbose;
    }


    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }


    public String getAlias() {
        return alias;
    }


    public String getStorePass() {
        return storePass;
    }


    private static boolean isStringEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
