/**
 * $Id: FileUtils.java,v 1.9 2005/12/07 14:47:21 romale Exp $
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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.librazur.util.test.Assert;


/**
 * File utilities.
 * 
 * @since 1.0
 */
public final class FileUtils {
    private FileUtils() {
    }


    /**
     * Copies a source file to a destination file. A lock for each file is
     * acquired to ensure no concurrent access would prevent the copy to be
     * done.
     * 
     * @since 1.3
     */
    public static void copy(File src, File dest) throws IOException {
        Assert.isNotNull("src", src);
        Assert.isNotNull("dest", dest);

        FileChannel input = null;
        FileChannel output = null;
        try {
            input = new FileInputStream(src).getChannel();
            output = new FileOutputStream(dest).getChannel();

            final long len = src.length();
            input.lock(0, len, true);
            output.truncate(len);
            output.lock(0, len, false);

            int pos = 0;
            while (pos < len) {
                pos += input.transferTo(pos, len, output);
            }
        } finally {
            IOUtils.close(input);
            IOUtils.close(output);
        }
    }


    /**
     * Quietly closes a <tt>FileLock</tt>.
     * 
     * @since 1.3
     */
    public static void releaseLock(FileLock lock) {
        if (lock == null) {
            return;
        }
        try {
            lock.release();
        } catch (IOException ignore) {
        }
    }


    /**
     * Ensures that a directory exists, creating it if necessary. If
     * <tt>file</tt> is actually a file (not a directory), this method ensures
     * the directory containing the file exists.
     */
    public static void ensureDirectoryExists(File dir) {
        Assert.isNotNull("dir", dir);

        if (dir.isFile()) {
            // get the directory containing this file
            final File parent = dir.getParentFile();
            if (parent != null) {
                ensureDirectoryExists(parent);
            }
            return;
        }
        if (!dir.exists()) {
            // the dir doesn't exist: let's try to create it
            if (!dir.mkdirs()) {
                throw new IllegalStateException("Unable to create directory: "
                        + dir);
            }
        }
    }


    /**
     * Returns the content of a file.
     */
    public static String read(File file) throws IOException {
        Assert.isNotNull("file", file);

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
        Assert.isNotNull("file", file);
        Assert.isNotNull("buf", buf);

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
        Assert.isNotBlank("enc", enc);

        final ByteBuffer buf = Charset.forName(enc).encode(
                CharBuffer.wrap(str == null ? StringUtils.EMPTY : str));
        write(file, buf);
    }


    /**
     * Writes a string to a file using encoding ISO-8859-1 (Latin 1).
     */
    public static void write(File file, CharSequence str) throws IOException {
        write(file, str, "ISO-8859-1");
    }


    /**
     * Tries to delete a directory or a file. The directory or the file may not
     * be deleted if some files have been opened by some other softwares
     * (especially under platforms like Windows). If <tt>force</tt> is set to
     * <tt>true</tt>, an <tt>IOException</tt> will be thrown if an error
     * prevents the element to be deleted. Gracefully handles <tt>null</tt>
     * files.
     * 
     * @since 1.1
     */
    public static void delete(File file, boolean force) throws IOException {
        if (file == null) {
            return;
        }
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
        Assert.isNotNull("input", input);

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
        Assert.isNotNull("file", file);

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
        Assert.isNotNull("files", files);

        final Collection<URL> urls = new ArrayList<URL>();
        for (final File file : files) {
            urls.add(toURL(file));
        }
        return urls;
    }
}
