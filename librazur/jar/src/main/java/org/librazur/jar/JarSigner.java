/**
 * $Id: JarSigner.java,v 1.1 2005/11/23 17:32:27 romale Exp $
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


/**
 * Signs JAR files. This class is an interface to the command line tool
 * <tt>jarsigner</tt>. This command must be available in the PATH.
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
     * Signs a JAR file. If <tt>jarsigner</tt> returns a non-zero value, a
     * <tt>RuntimeException is thrown.
     */
    public void sign(File jarFile, File signedJarFile) throws Exception {
        final StringBuffer buf = new StringBuffer("jarsigner");

        if (keystore != null) {
            buf.append(" -keystore ").append(keystore);
        }
        if (!isStringEmpty(storePass)) {
            buf.append(" -storepass ").append(storePass);
        }
        if (!isStringEmpty(storeType)) {
            buf.append(" -storetype ").append(storeType);
        }
        if (!isStringEmpty(keyPass)) {
            buf.append(" -keypass ").append(keyPass);
        }
        if (sigFile != null) {
            buf.append(" -sigfile \"").append(sigFile.getPath()).append('"');
        }
        if (internalSF) {
            buf.append(" internalsf");
        }
        if (sectionsOnly) {
            buf.append(" -sectionsonly");
        }
        if (verbose) {
            buf.append(" -verbose");
        }
        buf.append(" -signedjar \"").append(signedJarFile.getPath()).append(
                "\" \"").append(jarFile.getPath()).append("\" ").append(alias);

        final String cmd = buf.toString();
        final Process proc = Runtime.getRuntime().exec(cmd);
        final int errorCode = proc.waitFor();
        if (errorCode != 0) {
            throw new IllegalStateException(
                    "jarsigner was not able to sign the JAR file '"
                            + jarFile.getPath() + "\nError code: " + errorCode
                            + "\nCommand: " + cmd);
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
