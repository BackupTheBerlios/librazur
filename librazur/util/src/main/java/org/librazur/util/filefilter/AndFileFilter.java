/**
 * $Id: AndFileFilter.java,v 1.5 2005/12/07 15:52:31 romale Exp $
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.librazur.util.test.Assert;


/**
 * Composite {@link FileFilter} where all file filters must return
 * <code>true</code> in order to accept a {@link File}.
 * 
 * @since 1.0
 */
public class AndFileFilter implements FileFilter {
    private Collection<FileFilter> fileFilters;


    @SuppressWarnings("unchecked")
    public AndFileFilter() {
        this(Collections.EMPTY_LIST);
    }


    public AndFileFilter(final Collection<FileFilter> fileFilters) {
        Assert.isNotNull("fileFilters", fileFilters);

        this.fileFilters = fileFilters;
    }


    public AndFileFilter(final FileFilter left, final FileFilter right) {
        this(Arrays.asList(new FileFilter[] { left, right }));
    }


    public final boolean accept(File file) {
        for (final FileFilter fileFilter : fileFilters) {
            if (!fileFilter.accept(file)) {
                return false;
            }
        }

        return true;
    }


    public Collection<FileFilter> getFileFilters() {
        return fileFilters;
    }


    public void setFileFilters(Collection<FileFilter> fileFilters) {
        this.fileFilters = fileFilters;
    }
}
