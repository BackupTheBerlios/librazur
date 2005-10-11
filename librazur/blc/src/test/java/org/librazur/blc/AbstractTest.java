/**
 * $Id: AbstractTest.java,v 1.1 2005/10/11 21:17:40 romale Exp $
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

package org.librazur.blc;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;


public abstract class AbstractTest extends TestCase {
    protected final String loadText(String url) throws Exception {
        InputStream input = null;
        try {
            input = getURL(url).openStream();

            final ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            final byte[] buf = new byte[1024];
            for (int bytesRead = 0; (bytesRead = input.read(buf)) != -1;) {
                bytes.write(buf, 0, bytesRead);
            }

            return new String(bytes.toByteArray(), "ISO-8859-1");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception ignore) {
                }
            }
        }
    }


    protected final URL getURL(String path) {
        final URL url = getClass().getResource(path);
        assertNotNull("Resource not found: " + path, url);

        return url;
    }
}
