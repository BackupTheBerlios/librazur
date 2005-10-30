/**
 * $Id: SelectingDumperSinkEvent.java,v 1.1 2005/10/30 18:50:46 romale Exp $
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

package org.librazur.blc.event;


import java.util.EventObject;

import org.librazur.blc.model.DumperSink;


public class SelectingDumperSinkEvent extends EventObject {
    private final DumperSink dumperSink;


    public SelectingDumperSinkEvent(final Object source,
            final DumperSink dumperSink) {
        super(source);
        this.dumperSink = dumperSink;
    }


    public DumperSink getDumperSink() {
        return dumperSink;
    }
}
