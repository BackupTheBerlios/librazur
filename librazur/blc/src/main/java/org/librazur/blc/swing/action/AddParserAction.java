/**
 * $Id: AddParserAction.java,v 1.1 2005/10/11 21:17:40 romale Exp $
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

package org.librazur.blc.swing.action;


import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.librazur.blc.BLC;
import org.librazur.blc.swing.MainFrame;


public class AddParserAction extends AbstractAction {
    private final MainFrame frame;


    public AddParserAction(final MainFrame frame) {
        super();
        this.frame = frame;
        putValue(Action.NAME, BLC.i18n("add"));
    }


    public void actionPerformed(ActionEvent evt) {
        final File file = BLC.selectFile(frame);
        if (file == null) {
            return;
        }

        frame.addFileToParse(file);
    }
}
