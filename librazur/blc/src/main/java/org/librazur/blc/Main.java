/**
 * $Id: Main.java,v 1.1 2005/10/11 21:17:40 romale Exp $
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

package org.librazur.blc;


import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.swing.MainFrame;
import org.librazur.blc.swing.SplashScreenWindow;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;


/**
 * Main entry class.
 */
public class Main {
    private static final Log log = LogFactory.getLog(Main.class);


    public static void main(String... args) {
        log.info(BLC.i18n("blc") + " " + BLC.version());
        log.info(BLC.i18n("copyright"));

        log.debug("Home: " + System.getProperty("blc.home"));

        installLF();

        final SplashScreenWindow splash = new SplashScreenWindow(new Frame());
        splash.setVisible(true);

        log.info("Opening main frame");

        final JFrame frame = new MainFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent evt) {
                // once the main frame is displayed, the splashscreen is
                // disposed
                splash.dispose();
            }
        });
        frame.setVisible(true);
    }


    private static void installLF() {
        log.info("Installing Swing L&F");
        try {
            LookUtils.setLookAndTheme(new PlasticXPLookAndFeel(),
                    new ExperienceBlue());
        } catch (Exception e) {
            log.warn("Error while setting Swing L&F", e);
        }
    }
}
