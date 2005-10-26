/**
 * $Id: Main.java,v 1.3 2005/10/26 16:35:40 romale Exp $
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

package org.librazur.blc;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;


/**
 * Main entry class.
 */
public class Main {
    private static final Log log = LogFactory.getLog(Main.class);


    public static void main(String... args) {
        log.info(Resources.i18n("blc") + " " + Resources.version());
        log.info(Resources.i18n("blc.copyright"));

        log.debug("Home: " + System.getProperty("blc.home"));

        installLF();
        BLC.getInstance().start();
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
