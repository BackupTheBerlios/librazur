/**
 * $Id: JarSignerTest.java,v 1.1 2005/11/23 22:31:41 romale Exp $
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import junit.framework.TestCase;

import org.librazur.util.ChecksumUtils;
import org.librazur.util.IOUtils;


public class JarSignerTest extends TestCase {
    public void testSign() throws Exception {
        final URL keystore = copy("/test.keystore").toURI().toURL();
        final File jarFile = copy("/commons-pool-1.2.jar");
        final File signedJarFile = File
                .createTempFile("jarSignerTest-", ".tmp");
        signedJarFile.deleteOnExit();

        final JarSigner jarSigner = new JarSigner("librazurtest", "test");
        jarSigner.setKeystore(keystore.toURI().toURL());
        jarSigner.sign(jarFile, signedJarFile);

        assertTrue(signedJarFile.exists());
        assertFalse(signedJarFile.length() == 0);

        final JarInputStream input = new JarInputStream(new FileInputStream(
                signedJarFile));
        boolean signed = false;
        for (JarEntry entry; (entry = input.getNextJarEntry()) != null
                && !signed;) {
            final String upperCaseName = entry.getName();
            System.out.println(upperCaseName);
            if (upperCaseName.equals("META-INF/LIBRAZUR.SF")) {
                signed = true;
            }
        }
        input.close();

        assertTrue(signed);
    }


    private File copy(String path) throws IOException {
        final File tempFile = File.createTempFile("jarSignerTest-", ".tmp");
        tempFile.deleteOnExit();
        final OutputStream output = new FileOutputStream(tempFile);
        IOUtils.copy(getClass().getResourceAsStream(path), output);
        output.close();

        assertEquals(
                ChecksumUtils.md5Hex(getClass().getResourceAsStream(path)),
                ChecksumUtils.md5Hex(new FileInputStream(tempFile)));

        return tempFile;
    }
}
