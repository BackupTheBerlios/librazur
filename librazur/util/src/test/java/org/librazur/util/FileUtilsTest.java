/**
 * $Id: FileUtilsTest.java,v 1.5 2005/11/21 15:35:08 romale Exp $
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

package org.librazur.util;


import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import junit.framework.TestCase;


public class FileUtilsTest extends TestCase {
    public void testUnzip() throws Exception {
        final InputStream input = getClass()
                .getResourceAsStream("/zipfile.zip");
        assertNotNull("Unable to locate input zip file", input);
        final File outputDir = new File("unziptest");
        try {
            FileUtils.unzip(input, outputDir);
            final File readmeFile = new File(outputDir,
                    "zipfile/docs/README.txt");
            assertTrue(readmeFile.exists());
            assertTrue(readmeFile.canRead());
            assertEquals(22, readmeFile.length());
        } finally {
            FileUtils.delete(outputDir);
        }
    }


    public void testRead() throws Exception {
        final File file = File.createTempFile("FileUtils-testRead", ".txt");
        file.deleteOnExit();
        final OutputStream output = new FileOutputStream(file);
        try {
            IOUtils.copy(getClass().getResourceAsStream("/readme.txt"), output);

            final String text = FileUtils.read(file);
            assertNotNull(text);
            assertEquals("This is a README file.\nThat's all.", text);
        } finally {
            file.delete();
        }
    }


    public void testWrite() throws Exception {
        final File file = File.createTempFile("FileUtils-testWrite", ".txt");
        file.deleteOnExit();
        if (file.exists()) {
            file.delete();
        }
        assertFalse(file.exists());
        final String text = "Hello world!";
        FileUtils.write(file, text);
        assertEquals(text, FileUtils.read(file));
        file.delete();
    }


    public void testReleaseFileLock() throws Exception {
        FileUtils.releaseLock(null);

        final File tempFile = File.createTempFile("testReleaseFileLock-",
                ".tmp");
        tempFile.deleteOnExit();
        tempFile.createNewFile();
        final FileChannel chan = new FileOutputStream(tempFile).getChannel();
        final FileLock lock = chan.lock();
        FileUtils.releaseLock(lock);

        tempFile.delete();
    }


    public void testCopy() throws Exception {
        final File tempFileSrc = File.createTempFile("testCopy-", ".tmp");
        tempFileSrc.deleteOnExit();
        final File tempFileDst = File.createTempFile("testCopy-", ".tmp");
        tempFileDst.deleteOnExit();

        final String path = "/linux-logo.png";
        IOUtils.copy(getClass().getResourceAsStream(path),
                new FileOutputStream(tempFileSrc));
        assertEquals("a2b3565b17518045517a495672b450eb", ChecksumUtils
                .md5Hex(new FileInputStream(tempFileSrc)));

        FileUtils.copy(tempFileSrc, tempFileDst);
        assertEquals("a2b3565b17518045517a495672b450eb", ChecksumUtils
                .md5Hex(new FileInputStream(tempFileDst)));

        tempFileSrc.delete();
        tempFileDst.delete();
    }
}
