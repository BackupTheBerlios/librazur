/**
 * $Id: DumperSink.java,v 1.1 2005/10/26 16:35:40 romale Exp $
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

package org.librazur.blc.model;


import java.io.File;

import org.librazur.blc.dumper.Dumper;


public class DumperSink {
    public final Class<? extends Dumper> dumperClass;
    public final File directory;


    public DumperSink(final Class<? extends Dumper> dumperClass,
            final File directory) {
        this.dumperClass = dumperClass;
        this.directory = directory;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DumperSink)) {
            return false;
        }
        return hashCode() == obj.hashCode();
    }


    @Override
    public int hashCode() {
        return (dumperClass.getName() + "/" + directory.getPath()).hashCode();
    }
}
