/**
 * $Id: HostsParserTest.java,v 1.2 2005/10/20 22:44:12 romale Exp $
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

package org.librazur.blc.parser;


import java.util.Collection;

import org.librazur.blc.AbstractTest;
import org.librazur.blc.model.Entry;


public class HostsParserTest extends AbstractTest {
    public void testParse() throws Exception {
        final Parser parser = new HostsParser();
        final Collection<Entry> entries = parser.parse(getURL("/hosts")
                .openStream());

        assertNotNull(entries);
        assertFalse(entries.isEmpty());

        final String[] expectedHosts = { "localhost", "test", "adserver",
                "ip6-localhost", "ip6-loopback", "ip6-localnet",
                "ip6-mcastprefix", "ip6-allnodes", "ip6-allrouters",
                "ip6-allhosts" };
        int i = 0;
        for (final Entry entry : entries) {
            final String expectedHost = expectedHosts[i];
            assertEquals(expectedHost, entry.getValue());
            ++i;
        }
    }
}
