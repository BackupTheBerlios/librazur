/**
 * $Id: DomainParserTest.java,v 1.1 2005/10/11 21:17:40 romale Exp $
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

package org.librazur.blc.parser;


import java.util.Collection;

import org.librazur.blc.AbstractTest;
import org.librazur.blc.model.Entry;


public class DomainParserTest extends AbstractTest {
    public void testParse() throws Exception {
        final Parser parser = new DomainParser();
        final Collection<Entry> entries = parser.parse(getURL("/domains")
                .openStream());
        assertNotNull(entries);
        assertFalse(entries.isEmpty());
        assertEquals(2, entries.size());

        assertTrue(entries
                .contains(new Entry(Entry.Type.IP, "167.216.142.116")));
        assertTrue(entries.contains(new Entry(Entry.Type.DOMAIN, "ad-up.com")));
    }
}
