/**
 * $Id: SavingProfileEvent.java,v 1.2 2005/11/23 11:02:38 romale Exp $
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


import java.io.File;
import java.util.EventObject;

import org.librazur.blc.model.Profile;


public class SavingProfileEvent extends EventObject {
    private final File file;
    private final Profile profile;


    public SavingProfileEvent(final Object source, final Profile profile,
            final File file) {
        super(source);
        this.profile = profile;
        this.file = file;
    }


    public File getFile() {
        return file;
    }


    public Profile getProfile() {
        return profile;
    }
}
