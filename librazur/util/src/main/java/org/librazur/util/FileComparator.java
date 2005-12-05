/**
 * $Id: FileComparator.java,v 1.4 2005/12/05 14:48:43 romale Exp $
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

package org.librazur.util;


import java.io.File;
import java.util.Comparator;


/**
 * Comparator for files. Sort two files against their name (case sensitive by
 * default).
 * 
 * @since 1.0
 */
public class FileComparator implements Comparator<File> {
    private boolean caseSensitive;


    /**
     * Constructor.
     */
    public FileComparator() {
        this(true);
    }


    /**
     * Constructor.
     * 
     * @since 1.2
     */
    public FileComparator(final boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }


    public int compare(File o1, File o2) {
        return caseSensitive ? o1.getName().compareTo(o2.getName()) : o1
                .getName().compareToIgnoreCase(o2.getName());
    }
}
