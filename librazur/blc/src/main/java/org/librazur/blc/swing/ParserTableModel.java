/**
 * $Id: ParserTableModel.java,v 1.1 2005/10/11 21:17:40 romale Exp $
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

package org.librazur.blc.swing;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.BLC;


public class ParserTableModel extends AbstractTableModel implements
        Iterable<ParsedFile> {
    private final List<ParsedFile> parsedFiles = new ArrayList<ParsedFile>();
    private final Log log = LogFactory.getLog(getClass());


    public Iterator<ParsedFile> iterator() {
        return parsedFiles.iterator();
    }


    public void removeAll(int[] indexes) {
        if (indexes == null || indexes.length == 0) {
            return;
        }

        int min = 0;
        int max = 0;
        final Collection<ParsedFile> selectedParsedFiles = new ArrayList<ParsedFile>();
        for (int i = 0; i < indexes.length; ++i) {
            selectedParsedFiles.add(parsedFiles.get(indexes[i]));
            min = Math.min(min, indexes[i]);
            max = Math.max(max, indexes[i]);
        }

        if (log.isInfoEnabled()) {
            log.info("Deleting parsers in range [" + min + ";" + max + "]");
        }

        parsedFiles.removeAll(selectedParsedFiles);
        fireTableRowsDeleted(min, max);
    }


    public void add(ParsedFile parsedFile) {
        if (parsedFile == null) {
            throw new NullPointerException("parsedFile");
        }
        parsedFiles.add(parsedFile);

        final int i = parsedFiles.size() - 1;
        fireTableRowsInserted(i, i);
    }


    public void remove(ParsedFile parsedFile) {
        final int i = parsedFiles.indexOf(parsedFile);
        parsedFiles.remove(parsedFile);
        fireTableRowsDeleted(i, i);
    }


    public int getRowCount() {
        return parsedFiles.size();
    }


    public int getColumnCount() {
        return 2;
    }


    @Override
    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return BLC.i18n("parser.type");
            case 1:
                return BLC.i18n("file.path");
            default:
                throw new IndexOutOfBoundsException("Wrong column index: "
                        + col);
        }
    }


    public Object getValueAt(int row, int col) {
        final ParsedFile parsedFile = parsedFiles.get(row);

        switch (col) {
            case 0:
                return parsedFile.getParser().getName();
            case 1:
                return parsedFile.getFile().getPath();
            default:
                throw new IndexOutOfBoundsException("Wrong column index: "
                        + col);
        }
    }

}
