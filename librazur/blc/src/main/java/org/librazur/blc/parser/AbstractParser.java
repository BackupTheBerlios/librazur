/**
 * $Id: AbstractParser.java,v 1.1 2005/10/11 21:17:40 romale Exp $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.model.Entry;
import org.librazur.util.IOUtils;


/**
 * Abstract implementation of <tt>Parser</tt>.
 */
public abstract class AbstractParser implements Parser {
    protected final Log log = LogFactory.getLog(getClass());


    public final Collection<Entry> parse(InputStream input) throws IOException {
        try {
            final Collection<Entry> entries = doParse(input);

            if (log.isDebugEnabled()) {
                checkResult(entries);
            }

            return entries;
        } catch (Exception e) {
            final IOException exc = new IOException(
                    "Error while parsing stream");
            exc.initCause(e);
            throw exc;
        } finally {
            IOUtils.close(input);
        }
    }


    private void checkResult(Collection<Entry> entries) {
        if (entries == null) {
            log.warn("Result entry collection shouldn't be null");
            return;
        }
        for (final Entry entry : entries) {
            if (entry == null) {
                log.warn("Result entry collection shouldn't "
                        + "contain a null Entry");
                break;
            }
        }
    }


    @Override
    public String toString() {
        return getName();
    }


    protected abstract Collection<Entry> doParse(InputStream input)
            throws Exception;
}
