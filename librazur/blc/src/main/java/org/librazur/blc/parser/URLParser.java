/**
 * $Id: URLParser.java,v 1.3 2005/10/26 16:35:40 romale Exp $
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
import java.util.Collections;

import org.librazur.blc.Resources;
import org.librazur.blc.model.Entry;


/**
 * URL parser.
 */
public class URLParser extends AbstractLineParser {
    @Override
    protected Collection<Entry> doParse(String line) throws Exception {
        return Collections.singleton(new Entry(Entry.Type.URL, line));
    }


    public String getName() {
        return Resources.i18n("parser.url");
    }
}
