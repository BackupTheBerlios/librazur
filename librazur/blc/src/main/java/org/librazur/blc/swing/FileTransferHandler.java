/**
 * $Id: FileTransferHandler.java,v 1.2 2005/10/20 22:44:12 romale Exp $
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

package org.librazur.blc.swing;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FileTransferHandler extends TransferHandler {
    private final Log log = LogFactory.getLog(getClass());
    private final DataFlavor fileFlavor = DataFlavor.javaFileListFlavor;
    private final MainFrame frame;


    public FileTransferHandler(final MainFrame frame) {
        this.frame = frame;
    }


    @SuppressWarnings("unchecked")
    @Override
    public boolean importData(JComponent comp, Transferable t) {
        if (!canImport(comp, t.getTransferDataFlavors())) {
            return false;
        }

        try {
            final List<File> files = (List<File>) t.getTransferData(fileFlavor);
            for (final File file : files) {
                frame.addFileToParse(file);
            }
        } catch (Exception e) {
            log.error("Error while importing data", e);
            return false;
        }
        return true;
    }


    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }


    @Override
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
        if (!hasFileFlavor(transferFlavors)) {
            return false;
        }
        return true;
    }


    private boolean hasFileFlavor(DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; ++i) {
            if (fileFlavor.equals(flavors[i])) {
                return true;
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Can't import data: " + Arrays.asList(flavors));
        }
        return false;
    }
}
