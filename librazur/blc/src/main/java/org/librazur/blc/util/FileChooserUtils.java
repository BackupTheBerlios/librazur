/**
 * $Id: FileChooserUtils.java,v 1.1 2005/10/26 16:35:40 romale Exp $
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

package org.librazur.blc.util;


import java.awt.Component;
import java.io.File;
import java.lang.ref.WeakReference;

import javax.swing.JFileChooser;

import com.l2fprod.common.swing.JDirectoryChooser;


public final class FileChooserUtils {
    private static WeakReference<JFileChooser> fileChooserRef = new WeakReference<JFileChooser>(
            null);
    private static WeakReference<JDirectoryChooser> dirChooserRef = new WeakReference<JDirectoryChooser>(
            null);
    private static File lastDir = new File(System.getProperty("user.dir"));


    private FileChooserUtils() {
    }


    /**
     * Opens a dialog to select a file.
     */
    public static File selectFile(Component parent) {
        JFileChooser fc = fileChooserRef.get();
        if (fc == null) {
            fc = new JFileChooser(lastDir);
            fileChooserRef = new WeakReference<JFileChooser>(fc);
        }
        fc.setCurrentDirectory(lastDir);

        if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        lastDir = fc.getSelectedFile();

        return fc.getSelectedFile();
    }


    /**
     * Opens a dialog to select a directory.
     */
    public static File selectDir(Component parent) {
        JDirectoryChooser dc = dirChooserRef.get();
        if (dc == null) {
            dc = new JDirectoryChooser(lastDir);
            dirChooserRef = new WeakReference<JDirectoryChooser>(dc);
        }
        dc.setCurrentDirectory(lastDir);

        if (dc.showOpenDialog(parent) != JDirectoryChooser.APPROVE_OPTION) {
            return null;
        }
        lastDir = dc.getSelectedFile().getParentFile();

        return dc.getSelectedFile();
    }
}
