/**
 * $Id: ChecksumUtils.java,v 1.4 2005/12/07 14:35:52 romale Exp $
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


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.librazur.util.test.Assert;


/**
 * Checksum utilities. This class simply delegates to instances of
 * {@link java.security.MessageDigest}.
 * 
 * @since 1.2
 */
public final class ChecksumUtils {
    private ChecksumUtils() {
    }


    public static byte[] md5(InputStream input) throws IOException {
        return doChecksum(input, "MD5");
    }


    public static String md5Hex(InputStream input) throws IOException {
        return StringUtils.encodeHex(md5(input));
    }


    public static byte[] sha1(InputStream input) throws IOException {
        return doChecksum(input, "SHA1");
    }


    public static String sha1Hex(InputStream input) throws IOException {
        return StringUtils.encodeHex(sha1(input));
    }


    public static byte[] md5(byte[] data) {
        try {
            return md5(new ByteArrayInputStream(data));
        } catch (IOException e) {
            throw new IllegalStateException("Unexpected exception", e);
        }
    }


    public static String md5Hex(byte[] data) {
        return StringUtils.encodeHex(md5(data));
    }


    public static byte[] sha1(byte[] data) {
        try {
            return sha1(new ByteArrayInputStream(data));
        } catch (IOException e) {
            throw new IllegalStateException("Unexpected exception", e);
        }
    }


    public static String sha1Hex(byte[] data) {
        return StringUtils.encodeHex(sha1(data));
    }


    private static byte[] doChecksum(InputStream input, String algorithm)
            throws IOException {
        Assert.isNotNull("input", input);
        Assert.isNotBlank("algorithm", algorithm);

        final MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "Unable to instanciate MessageDigest", e);
        }

        final byte[] buf = new byte[1024];
        for (int bytesRead = 0; (bytesRead = input.read(buf)) != -1;) {
            md.update(buf, 0, bytesRead);
        }

        return md.digest();
    }
}
