/**
 * $Id: AbstractLineParser.java,v 1.2 2005/10/20 22:44:12 romale Exp $
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


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.librazur.blc.model.Entry;


/**
 * Abstract line-based implementation of <tt>Parser</tt>.
 */
public abstract class AbstractLineParser extends AbstractParser {
    private final Pattern commentStripPattern = Pattern.compile("(.*)#.*");


    @Override
    protected final Collection<Entry> doParse(InputStream input)
            throws Exception {
        // we use a Set to keep only one element of each
        final Collection<Entry> entries = new LinkedHashSet<Entry>();
        final Matcher matcher = commentStripPattern.matcher("");

        final BufferedReader reader = new BufferedReader(new InputStreamReader(
                input));
        for (String line; (line = reader.readLine()) != null;) {
            String parsedLine = line.trim();
            if (parsedLine.length() == 0 || line.startsWith("#")) {
                // empty lines or comment lines are discarded
                continue;
            }

            matcher.reset(line);
            if (matcher.matches()) {
                // useless data is removed
                final String newLine = matcher.group(1).trim();
                if (log.isInfoEnabled()) {
                    log.info("Stripping comment from line: " + parsedLine
                            + " => " + newLine);
                }
                parsedLine = newLine;
            }

            // let's parse the line
            final Collection<Entry> newEntries = doParse(parsedLine);
            if (newEntries == null) {
                continue;
            }
            entries.addAll(newEntries);
        }

        return entries;
    }


    protected abstract Collection<Entry> doParse(String line) throws Exception;
}
