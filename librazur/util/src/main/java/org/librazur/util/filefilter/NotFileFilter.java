/**
 * $Id: NotFileFilter.java,v 1.5 2005/12/07 15:52:31 romale Exp $
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

package org.librazur.util.filefilter;


import java.io.File;
import java.io.FileFilter;

import org.librazur.util.test.Assert;


/**
 * {@link FileFilter} adapter which inverts the result of a {@link FileFilter}.
 * 
 * @since 1.0
 */
public class NotFileFilter implements FileFilter {
    private final FileFilter fileFilter;


    public NotFileFilter(final FileFilter fileFilter) {
        Assert.isNotNull("fileFilter", fileFilter);

        this.fileFilter = fileFilter;
    }


    public boolean accept(File file) {
        return !fileFilter.accept(file);
    }
}
