/**
 * $Id: ParserSourceTableModel.java,v 1.1 2005/10/26 16:35:40 romale Exp $
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


import java.util.EventObject;

import javax.swing.table.AbstractTableModel;

import org.librazur.blc.Resources;
import org.librazur.blc.event.*;
import org.librazur.blc.model.ParserSource;
import org.librazur.blc.model.Profile;
import org.librazur.blc.util.ParserFactory;
import org.librazur.minibus.BusProvider;
import org.librazur.minibus.EventHandler;


public class ParserSourceTableModel extends AbstractTableModel {
    private final BusProvider busProvider;
    private final ParserFactory parserFactory;
    private Profile profile;


    public ParserSourceTableModel(final BusProvider busProvider,
            final ParserFactory parserFactory) {
        super();
        this.busProvider = busProvider;
        this.parserFactory = parserFactory;
        busProvider.getBus().register(new BusHandler());
    }


    public void removeAll(int[] indexes) {
        if (indexes == null || indexes.length == 0) {
            return;
        }

        busProvider.getBus().post(new RemovingParserSourceEvent(this, indexes));
    }


    public int getRowCount() {
        if (profile == null) {
            return 0;
        }
        return profile.getParserSources().size();
    }


    public int getColumnCount() {
        return 2;
    }


    @Override
    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return Resources.i18n("parser.type");
            case 1:
                return Resources.i18n("parser.file");
            default:
                throw new IndexOutOfBoundsException("Wrong column index: "
                        + col);
        }
    }


    public Object getValueAt(int row, int col) {
        if (profile == null) {
            return null;
        }

        final ParserSource source = profile.getParserSources().get(row);
        switch (col) {
            case 0:
                return parserFactory.createParser(source.parserClass).getName();
            case 1:
                return source.url;
            default:
                throw new IndexOutOfBoundsException("Wrong column index: "
                        + col);
        }
    }


    private class BusHandler implements EventHandler {
        public EventObject onEvent(EventObject obj) throws Exception {
            if (obj instanceof ParserSourceRemovedEvent
                    || obj instanceof ParserSourceAddedEvent) {
                fireTableDataChanged();
            }
            if (obj instanceof ProfileCreatedEvent) {
                profile = ((ProfileCreatedEvent) obj).getProfile();
            }
            if (obj instanceof ProfileLoadedEvent) {
                profile = ((ProfileLoadedEvent) obj).getProfile();
            }
            return null;
        }
    }
}
