/**
 * $Id: FileUtils.java,v 1.2 2005/10/19 21:55:59 romale Exp $
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

package org.librazur.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * File utilities.
 */
public final class FileUtils {
    private FileUtils() {
    }


    /**
     * Ensures that a directory exists, creating it if necessary.
     */
    public static void ensureDirectoryExists(File dir) {
        if (!dir.exists()) {
            // the dir doesn't exist: let's try to create it
            if (!dir.mkdirs()) {
                throw new IllegalStateException("Unable to create directory: "
                        + dir);
            }
        }
        // we know now that the dir exists, but it may be a regular file
        if (!dir.isDirectory()) {
            throw new IllegalStateException("Invalid directory: " + dir);
        }
    }


    /**
     * Returns the content of a file.
     */
    public static String read(File file) throws IOException {
        final StringBuilder text = new StringBuilder();
        final CharBuffer buf = CharBuffer.allocate(1024);
        final Reader reader = new InputStreamReader(new FileInputStream(file));
        try {
            while (reader.read(buf) != -1) {
                buf.flip();
                text.append(buf);
                buf.clear();
            }
        } finally {
            IOUtils.close(reader);
        }

        return text.toString();
    }


    /**
     * Writes data to a file.
     */
    public static void write(File file, ByteBuffer buf) throws IOException {
        final FileChannel out = new FileOutputStream(file).getChannel();
        try {
            while (buf.hasRemaining()) {
                out.write(buf);
            }
        } finally {
            IOUtils.close(out);
        }
    }


    /**
     * Writes a string to a file with a given encoding.
     */
    public static void write(File file, CharSequence str, String enc)
            throws IOException {
        final ByteBuffer buf = Charset.forName(enc)
                .encode(CharBuffer.wrap(str));
        write(file, buf);
    }


    /**
     * Writes a string to a file using encoding ISO-8859-1 (Latin 1).
     * 
     * @param file
     * @param str
     * @throws IOException
     */
    public static void write(File file, CharSequence str) throws IOException {
        write(file, str, "ISO-8859-1");
    }


    /**
     * Tries to delete a directory or a file. The directory or the file may not
     * be deleted if some files have been opened by some other software
     * (especially under platforms like Windows). If <tt>force</tt> is set to
     * <tt>true</tt>, an <tt>IOException</tt> will be thrown if an error
     * prevents the element to be deleted.
     */
    public static void delete(File file, boolean force) throws IOException {
        if (file.isFile()) {
            if (!file.delete() && force) {
                throw new IOException("Unable to delete file: "
                        + file.getPath());
            }
            return;
        }

        final File[] files = file.listFiles();
        if (files != null) {
            for (final File curFile : files) {
                if (curFile.isFile()) {
                    if (!curFile.delete() && force) {
                        throw new IOException("Unable to delete file: "
                                + curFile.getPath());
                    }
                } else if (curFile.isDirectory()) {
                    delete(curFile);
                }
            }
        }
        if (!file.delete() && force) {
            throw new IOException("Unable to delete directory: "
                    + file.getPath());
        }
    }


    /**
     * Same as <tt>FileUtils.delete(file, false)</tt>.
     */
    public static void delete(File file) throws IOException {
        delete(file, false);
    }


    /**
     * Unzips an input stream into a directory.
     */
    public static void unzip(InputStream input, File dir) throws IOException {
        ensureDirectoryExists(dir);

        ZipInputStream zipInput = null;
        FileOutputStream fileOutputStream = null;
        try {
            zipInput = new ZipInputStream(input);
            for (ZipEntry entry = null; (entry = zipInput.getNextEntry()) != null;) {
                final String entryName = entry.getName();
                final File fileOutput = new File(dir, entryName);

                if (entryName.endsWith("/")) {
                    fileOutput.mkdirs();
                } else {
                    fileOutputStream = new FileOutputStream(fileOutput);
                    IOUtils.copy(zipInput, fileOutputStream);
                    zipInput.closeEntry();
                }
            }
        } finally {
            IOUtils.close(input);
            IOUtils.close(fileOutputStream);
        }
    }


    /**
     * Converts a file to an URL.
     */
    public static URL toURL(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Unexpected exception", e);
        }
    }


    /**
     * Converts a collection of files to a collection of URLs.
     */
    public static Collection<URL> toURL(Collection<File> files) {
        final Collection<URL> urls = new ArrayList<URL>();
        for (final File file : files) {
            urls.add(toURL(file));
        }
        return urls;
    }
}
