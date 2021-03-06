/**
 * $Id: PrivoxyDumper.java,v 1.4 2005/11/07 09:32:46 romale Exp $
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


import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.librazur.blc.Constants;
import org.librazur.blc.Resources;
import org.librazur.blc.model.Entry;
import org.librazur.blc.model.MemoryFile;
import org.librazur.util.ByteAccumulator;


/**
 * Privoxy implementation for <tt>Dumper</tt>.
 */
public class PrivoxyDumper extends AbstractDumper {
    @Override
    protected Collection<MemoryFile> doDump(Collection<Entry> entries)
            throws Exception {
        final String charset = "US-ASCII";

        final String header = Resources.i18n("dumper.comment", Resources
                .i18n("blc")
                + " " + Resources.version(), new Date());

        final ByteAccumulator byteAcc = new ByteAccumulator(ByteBuffer
                .allocateDirect(1024));

        final StringBuilder comment = new StringBuilder().append("# ").append(
                header).append(Constants.LINE_SEPARATOR).append("# ").append(
                Resources.i18n("blc.copyright")).append(
                Constants.LINE_SEPARATOR).append("{ +block \\").append(
                Constants.LINE_SEPARATOR).append("  -handle-as-image \\")
                .append(Constants.LINE_SEPARATOR).append(
                        "  -set-image-blocker \\").append(
                        Constants.LINE_SEPARATOR).append("}").append(
                        Constants.LINE_SEPARATOR);
        byteAcc.append(ByteBuffer.wrap(comment.toString().getBytes(charset)));
        for (final Entry entry : entries) {
            final StringBuilder lineBuf = new StringBuilder();
            
            // we need to add a dot in front of each domain line,
            // in case there is not dot
            if(Entry.Type.DOMAIN.equals(entry.getType()) && !entry.getValue().startsWith(".")) {
                lineBuf.append(".");
            }
            lineBuf.append(entry.getValue()).append(Constants.LINE_SEPARATOR);
            byteAcc.append(ByteBuffer.wrap(lineBuf.toString().getBytes(charset)));
        }

        final ByteBuffer buf = byteAcc.get();
        // we flip the buffer so it is ready to be written to a file
        buf.flip();

        return Collections.singleton(new MemoryFile("user-blc.action", buf));
    }


    public String getName() {
        return Resources.i18n("dumper.privoxy");
    }
}
