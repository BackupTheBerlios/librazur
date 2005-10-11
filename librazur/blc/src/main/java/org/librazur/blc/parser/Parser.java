/**
 * $Id: Parser.java,v 1.1 2005/10/11 21:17:40 romale Exp $
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


import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.librazur.blc.model.Entry;


/**
 * A <tt>Parser</tt> is an object which can parse an input stream and produce
 * a collection of <tt>Entry</tt>.
 */
public interface Parser {
    Collection<Entry> parse(InputStream input) throws IOException;


    String getName();
}
