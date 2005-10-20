/**
 * $Id: PrivoxyDumperTest.java,v 1.2 2005/10/20 22:44:12 romale Exp $
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

package org.librazur.blc.dumper;


import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

import org.librazur.blc.AbstractTest;
import org.librazur.blc.model.DumpFile;
import org.librazur.blc.model.Entry;


public class PrivoxyDumperTest extends AbstractTest {
    public void testDump() throws Exception {
        final Collection<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(Entry.Type.DOMAIN, "test"));
        entries.add(new Entry(Entry.Type.IP, "192.168.0.1"));
        entries.add(new Entry(Entry.Type.DOMAIN, "adserver"));
        entries.add(new Entry(Entry.Type.DOMAIN, ".banners"));

        final Dumper dumper = new PrivoxyDumper();
        final Collection<DumpFile> dumpFiles = dumper.dump(entries);
        assertNotNull(dumpFiles);
        assertEquals(1, dumpFiles.size());

        final DumpFile dumpFile = dumpFiles.iterator().next();
        assertEquals("user-blc.action", dumpFile.getFileName());

        // we remove the first two lines of the content
        // (generated header)
        final CharBuffer buf = Charset.forName("US-ASCII").decode(
                dumpFile.getContent());
        final String content = buf.toString();
        final StringBuilder newContent = new StringBuilder();
        final String[] lines = content.split("\n");
        for (int i = 2; i < lines.length; ++i) {
            newContent.append(lines[i]).append("\n");
        }

        final String expectedDump = loadText("/privoxy_dump");
        assertEquals(expectedDump, newContent.toString());
    }
}
