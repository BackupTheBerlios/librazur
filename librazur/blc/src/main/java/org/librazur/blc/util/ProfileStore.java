/**
 * $Id: ProfileStore.java,v 1.1 2005/10/26 16:35:40 romale Exp $
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

package org.librazur.blc.util;


import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.librazur.blc.model.Profile;


public class ProfileStore {
    private final Log log = LogFactory.getLog(getClass());


    public Profile load(File file) {
        log.info("Loading profile from file: " + file.getPath());

        final Profile profile = new Profile();
        profile.setFile(file);

        return profile;
    }


    public void save(File file, Profile profile) {
        log.info("Saving profile '" + profile.getName() + "' to file: "
                + file.getPath());
    }
}
