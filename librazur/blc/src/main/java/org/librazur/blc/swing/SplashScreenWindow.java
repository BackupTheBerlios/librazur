/**
 * $Id: SplashScreenWindow.java,v 1.1 2005/10/11 21:17:40 romale Exp $
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


import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.librazur.blc.BLC;


public class SplashScreenWindow extends JWindow implements MouseListener {
    public SplashScreenWindow(Frame owner) {
        super(owner);
        init();
    }


    private void init() {
        buildUI();
        pack();
        setLocationRelativeTo(null);
    }


    private void buildUI() {
        final Icon icon = new ImageIcon(BLC.image("blc.splash"));
        final JLabel label = new JLabel(icon);
        label.addMouseListener(this);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        setContentPane(panel);
    }


    public void mouseClicked(MouseEvent evt) {
        // if the user clicked on the window, we dispose it
        dispose();
    }


    public void mousePressed(MouseEvent evt) {
    }


    public void mouseReleased(MouseEvent evt) {
    }


    public void mouseEntered(MouseEvent evt) {
    }


    public void mouseExited(MouseEvent evt) {
    }
}
