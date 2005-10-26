/**
 * $Id: AbstractDumper.java,v 1.3 2005/10/26 16:35:40 romale Exp $
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


import java.util.Collection;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.model.Entry;
import org.librazur.blc.model.MemoryFile;


/**
 * Abstract implementation of <tt>Dumper</tt>.
 */
public abstract class AbstractDumper implements Dumper,
        Comparable<AbstractDumper> {
    protected final Log log = LogFactory.getLog(getClass());


    @SuppressWarnings("unchecked")
    public final Collection<MemoryFile> dump(Collection<Entry> entries) {
        if (entries == null || entries.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        try {
            final Collection<MemoryFile> dumpFiles = doDump(entries);
            if (log.isDebugEnabled()) {
                checkResult(dumpFiles);
            }
            return dumpFiles;
        } catch (Exception e) {
            throw new IllegalStateException("Error while dumping entries", e);
        }
    }


    private void checkResult(Collection<MemoryFile> dumpFiles) {
        if (dumpFiles == null) {
            log.warn("Result dump file collection shouldn't be null");
            return;
        }
        for (final MemoryFile dumpFile : dumpFiles) {
            if (dumpFile == null) {
                log.warn("Result dump file collection shouldn't "
                        + "contain a null DumpFile");
                break;
            }
        }
    }


    @Override
    public String toString() {
        return getName();
    }


    protected abstract Collection<MemoryFile> doDump(Collection<Entry> entries)
            throws Exception;


    public int compareTo(AbstractDumper o) {
        return toString().compareTo(o.toString());
    }
}
