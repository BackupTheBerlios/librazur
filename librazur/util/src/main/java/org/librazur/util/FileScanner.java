/**
 * $Id: FileScanner.java,v 1.1 2005/10/11 21:05:19 romale Exp $
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

package org.librazur.util;


import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.librazur.util.filefilter.DirectoryFileFilter;
import org.librazur.util.filefilter.OrFileFilter;
import org.librazur.util.filefilter.TrueFileFilter;


/**
 * File scanner.
 */
public class FileScanner {
    private final File dir;
    private final FileFilter fileFilter;


    public FileScanner(final File dir, final FileFilter fileFilter) {
        if (dir == null) {
            throw new NullPointerException("dir");
        }
        this.dir = dir;
        final FileFilter userFileFilter = fileFilter == null ? new TrueFileFilter()
                : fileFilter;
        this.fileFilter = new OrFileFilter(new DirectoryFileFilter(),
                userFileFilter);
    }


    /**
     * Scans a directory for files.
     */
    public Collection<File> scan() throws IOException {
        if (dir.isFile()) {
            // if we only have a file, just returns it!
            return Collections.singleton(dir);
        }
        return scan(dir, new HashSet<File>());
    }


    private Collection<File> scan(File curDir, Collection<File> result) {
        final File[] files = curDir.listFiles(fileFilter);
        if (files != null) {
            for (int i = 0; i < files.length; ++i) {
                final File file = files[i];
                if (file.isDirectory()) {
                    return scan(file, result);
                }

                result.add(file);
            }
        }

        return result;
    }
}
