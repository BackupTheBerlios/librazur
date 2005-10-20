/**
 * $Id: SquidGuardArchiveParser.java,v 1.2 2005/10/20 22:44:12 romale Exp $
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


import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.zip.GZIPInputStream;

import org.librazur.blc.BLC;
import org.librazur.blc.model.Entry;

import com.ice.tar.TarEntry;
import com.ice.tar.TarInputStream;


/**
 * SquidGuard archive parser.
 */
public class SquidGuardArchiveParser extends AbstractParser {
    private final Parser domainParser = new DomainParser();
    private final Parser urlParser = new URLParser();


    @Override
    protected Collection<Entry> doParse(InputStream input) throws Exception {
        final Collection<Entry> entries = new LinkedHashSet<Entry>();
        Collection<Entry> newEntries = null;

        // we use an UncloseableTarInputStream adapter because we don't want the
        // parsers to close the GZIPInputStream in the end
        final TarInputStream tarInput = new UncloseableTarInputStream(
                new GZIPInputStream(input));
        for (TarEntry tarEntry; (tarEntry = tarInput.getNextEntry()) != null;) {
            final String name = tarEntry.getName();

            newEntries = null;
            if (name.endsWith("/domains") || "domains".equals(name)) {
                if (log.isInfoEnabled()) {
                    log.info("Parsing domain tar entry: " + name);
                }
                newEntries = domainParser.parse(tarInput);
            } else if (name.endsWith("/urls") || "urls".equals(name)) {
                if (log.isInfoEnabled()) {
                    log.info("Parsing URL tar entry: " + name);
                }
                newEntries = urlParser.parse(tarInput);
            }

            if (newEntries == null) {
                if (log.isInfoEnabled()) {
                    log.info("No registered parser for tar entry: " + name);
                }
                continue;
            }
            entries.addAll(newEntries);
        }

        return entries;
    }


    public String getName() {
        return BLC.i18n("parser.squidguard.archive");
    }
}
