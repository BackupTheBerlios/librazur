/**
 * $Id: CopyImageAction.java,v 1.1 2005/11/21 01:30:15 romale Exp $
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

package org.librazur.ict.swing.action;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.librazur.ict.Resources;
import org.librazur.ict.swing.MainFrame;


public class CopyImageAction extends AbstractAction {
    private final MainFrame mainFrame;


    public CopyImageAction(final MainFrame mainFrame) {
        super();

        this.mainFrame = mainFrame;

        putValue(Action.NAME, Resources.i18n("action.copy"));
        putValue(Action.SHORT_DESCRIPTION, Resources.i18n("action.copy.desc"));
        putValue(Action.SMALL_ICON, Resources.icon("action.copy.icon"));
    }


    public void actionPerformed(ActionEvent e) {
        mainFrame.copyImage();
    }
}
