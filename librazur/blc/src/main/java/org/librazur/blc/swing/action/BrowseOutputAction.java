/**
 * $Id: BrowseOutputAction.java,v 1.2 2005/10/20 22:44:12 romale Exp $
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

package org.librazur.blc.swing.action;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;

import org.librazur.blc.BLC;


public class BrowseOutputAction extends AbstractAction {
    private final JTextField field;
    private final Component source;


    public BrowseOutputAction(final Component source, final JTextField field) {
        super();
        this.source = source;
        this.field = field;
        putValue(Action.NAME, BLC.i18n("browse"));
    }


    public void actionPerformed(ActionEvent e) {
        final File dir = BLC.selectDir(source);
        if (dir == null) {
            return;
        }
        field.setText(dir.getPath());
    }
}
