/**
 * $Id: HostsParser.java,v 1.1 2005/10/11 21:17:40 romale Exp $
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
import java.util.LinkedHashSet;

import org.librazur.blc.BLC;
import org.librazur.blc.model.Entry;
import org.librazur.blc.util.NetUtils;


/**
 * "hosts"-like file parser.
 */
public class HostsParser extends AbstractLineParser {
    @Override
    protected Collection<Entry> doParse(String line) throws Exception {
        final String[] parts = line.split("\\s");
        if (parts.length < 2) {
            // we only keep lines with:
            // <IP-Address> <Domain> (<Domain>...)
            return null;
        }

        final Collection<Entry> entries = new LinkedHashSet<Entry>();

        for (int i = 1; i < parts.length; ++i) {
            final String part = parts[i];
            entries.add(new Entry(NetUtils.isIPAddress(part) ? Entry.Type.IP
                    : Entry.Type.DOMAIN, part));
        }

        return entries;
    }


    public String getName() {
        return BLC.i18n("parser.hosts");
    }
}
