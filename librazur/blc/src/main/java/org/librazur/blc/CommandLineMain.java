/**
 * $Id: CommandLineMain.java,v 1.1 2005/11/28 16:13:44 romale Exp $
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


import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.model.Profile;
import org.librazur.blc.util.BlackListConverter;
import org.librazur.blc.util.ProfileStore;


/**
 * Main entry class for command line usage.
 */
public class CommandLineMain {
    private static final Log log = LogFactory.getLog(Main.class);


    public static void main(String... args) {
        log.info(Resources.i18n("blc") + " " + Resources.version());
        log.info(Resources.i18n("blc.copyright"));

        log.debug("Home: " + System.getProperty("blc.home"));

        if (args.length != 1) {
            printUsage();
            System.exit(3);
        }

        // the first argument is the path to a profile file
        final File file = new File(args[0]);
        if (!(file.exists() && file.isFile() && file.canRead())) {
            log.fatal("Unable to read profile " + "from file: "
                    + file.getPath());
            System.exit(2);
        }

        log.info("Using profile from file: " + file.getPath());

        final BLC blc = BLC.getInstance();
        final ProfileStore store = new ProfileStore();
        try {
            final Profile profile = store.load(file);
            log.info("Converting profile...");
            new BlackListConverter(blc, blc, blc).convert(profile);
            log.info("Profile converted!");
        } catch (Exception e) {
            log.fatal("Error while processing profile from file: "
                    + file.getPath(), e);
            System.exit(1);
        }

        System.exit(0);
    }


    private static void printUsage() {
        System.out.println("usage: blc-cl <profile path>");
    }
}
